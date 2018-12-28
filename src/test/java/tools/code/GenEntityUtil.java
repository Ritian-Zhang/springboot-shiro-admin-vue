package tools.code;


import org.apache.commons.io.FileUtils;
import tools.util.DatabaseUtils;
import tools.util.GeneratorUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/7/3.
 */
public class GenEntityUtil {
    private String[] colNames; // 列名数组
    private String[] attrNames; //实体类属性名数字
    private String[] colTypes; // 列名类型数组
    private int[] colSizes; // 列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
   // private static String AUTHER = "ritian.Zhang"; //作者
    private static String FILE_HEADER = "/**\r\n" +
            " * @author ritian.Zhang\r\n" +
            " * @date CREATE_DATE\r\n" +
            " **/\r\n";
    private static String FILE_HEADER_LOMBOK = "/**\r\n" +
            " * @author ritian.Zhang\r\n" +
            " * @date CREATE_DATE\r\n" +
            " **/\r\n"+
            "@Data\r\n" +
            "@NoArgsConstructor\r\n";

    public static void main(String[] args) {
        String packagePath = "com.ritian.model";
        String tableName = "sys_role";
        new GenEntityUtil(packagePath,tableName,true);
    }


    public GenEntityUtil(String packagePath, String tableName,boolean isLombok) {
        Connection conn = DatabaseUtils.openConnection(); // 得到数据库连接
        PreparedStatement pstmt = null;
        String strsql = "select * from " + tableName;
        try {
            pstmt = conn.prepareStatement(strsql);
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            colNames = new String[size];
            attrNames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
                attrNames[i] = this.getCamelStr(colNames[i]);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);


                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image")
                        || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            try {
                String content = parse(colNames, colTypes, colSizes, packagePath, tableName,isLombok);
                String path = this.getClass().getResource("/").toString().replace("file:/","").replace("/target/test-classes/","").replace("/target/classes/","") + "/src/main/java/" + packagePath.replaceAll("\\.", "/");
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                String resPath = path+"/"+initcap(tableName) + ".java";
                File javaFile = new File(resPath);
                if(javaFile.exists()){
                    throw new Exception("文件路径："+resPath+",该文件已存在");
                }
                System.out.println("文件写入路径：path=" + resPath);
                FileUtils.writeStringToFile(new File(resPath), content,"utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtils.closeDatabase(conn);
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private String parse(String[] colNames, String[] colTypes, int[] colSizes, String packagePath, String tableName,boolean isLombok) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packagePath + ";\r\n\r\n");
        if(isLombok){
            sb.append("import lombok.Data;\r\n" +
                    "import lombok.NoArgsConstructor;\r\n\r\n");
        }
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n\r\n\r\n");
        }
        processFileHeader(sb,isLombok);
        sb.append("public class " + initcap(tableName) + " {\r\n\r\n");
        processAllAttrs(sb);
        sb.append("\r\n");
        if(!isLombok){
            processAllMethod(sb);
        }
        sb.append("}\r\n");
        System.out.println(sb.toString());
        return sb.toString();

    }

    /**
     * 生成文件头部信息
     */
    private void processFileHeader(StringBuffer sb,boolean isLombok){
        if(isLombok){
            sb.append(FILE_HEADER_LOMBOK.replace("CREATE_DATE",GeneratorUtil.getCreateDate()));
        }else{
            sb.append(FILE_HEADER.replace("CREATE_DATE",GeneratorUtil.getCreateDate()));
        }

    }

    /**
     * 生成所有的方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < attrNames.length; i++) {
            sb.append("\tpublic void set" + initcap(attrNames[i]) + "("
                    + sqlType2JavaType(colTypes[i]) + " " + attrNames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + attrNames[i] + " = " + attrNames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
                    + initcap(attrNames[i]) + "(){\r\n");
            sb.append("\t\treturn " + attrNames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
        }
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < attrNames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + attrNames[i] + ";\r\n");
        }
    }

    /**
     * 把输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0]-32);
        }
        return this.getCamelStr(new String(ch));
    }

    //例：user_name --> userName
    private String getCamelStr(String s){
        while(s.indexOf("_")>0){
            int index = s.indexOf("_");
            //System.out.println(s.substring(index+1, index+2).toUpperCase());
            s = s.substring(0, index) + s.substring(index+1, index+2).toUpperCase() + s.substring(index+2);
        }
        return s;
    }

    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "bool";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        }

        else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        }
        return null;
    }


}
