package tools.code;


import tools.util.GeneratorUtil;

import java.io.*;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/14.
 */
public class GeneratorFileTools {
    private String packageNameString = "";
    private String classNameString = "";
    private String templateFilePathString = "tools/code/";
    private String saveJavaFilePathString = "";
    private String saveJspFilePathString = "";
    private String jspPathString = "";
    private String urlPathString = "";
    private Class<?> domainClass;
    private Field[] fields;
    private static String AUTHOR = "ritian.Zhang";

    public GeneratorFileTools(Class<?> domainClass) {
        this.domainClass = domainClass;
        String classAllName = this.domainClass.getName();
        this.packageNameString = classAllName.substring(0, classAllName.indexOf("model") - 1);
        this.classNameString = classAllName.substring(classAllName.lastIndexOf(".") + 1);
        this.saveJavaFilePathString = domainClass.getResource("/").toString().replace("file:/", "").replace("/target/test-classes/", "").replace("/target/classes/", "") + "/src/main/java/" + this.packageNameString.replaceAll("\\.", "/");
        this.saveJspFilePathString = domainClass.getResource("/").getPath().replace("target/classes/", "src/main/webapp/WEB-INF/views/") + jspPathString;
        this.fields = domainClass.getDeclaredFields();
    }

    public GeneratorFileTools(Class<?> domainClass, String jspPathString, String urlPathString) {
        this.jspPathString = jspPathString;
        this.urlPathString = urlPathString;
        this.domainClass = domainClass;
        String classAllName = this.domainClass.getName();
        this.packageNameString = classAllName.substring(0, classAllName.indexOf("model") - 1);
        this.classNameString = classAllName.substring(classAllName.lastIndexOf(".") + 1);
        this.saveJavaFilePathString = domainClass.getResource("/").toString().replace("file:/", "").replace("/target/test-classes/", "").replace("/target/classes/", "") + "/src/main/java/" + this.packageNameString.replaceAll("\\.", "/");
        this.saveJspFilePathString = domainClass.getResource("/").getPath().replace("target/classes/", "src/main/webapp/WEB-INF/views/") + jspPathString;
        this.fields = domainClass.getDeclaredFields();
    }

    public static void main(String[] args) {
//        GeneratorFileTools tools = new GeneratorFileTools(SysRole.class);
//        tools.exportDaoFile();
//        tools.exportSearchFile();
//        tools.exportMapperFile();
        //tools.exportServiceFile();
    }

    /**
     * 导java文件
     */
    public void exportJavaFile() {
        this.exportDaoFile();
        this.exportSearchFile();
        this.exportMapperFile();
        this.exportServiceFile();
//        this.exportCtlFile();
    }

    /**
     * 导出search文件
     */
    private void exportSearchFile() {
        String searchFileContentString = this.readTxtFile("JavaSearchTemplate.txt");
        searchFileContentString = searchFileContentString.replaceAll("#packageName#", this.packageNameString);
        searchFileContentString = searchFileContentString.replaceAll("#className#", this.classNameString);
        String savePath = this.saveJavaFilePathString + "/model/search/" + this.classNameString + "Search.java";
        if (this.saveAsFileWriter(searchFileContentString, savePath)) {
            System.out.println("文件路径:" + savePath + ", 导出search文件成功。");
        } else {
            System.out.println("导出search文件失败。。。");
        }
    }

    /**
     * 导出mapper文件
     */
    private void exportMapperFile() {
        String mapperFileContentString = this.readTxtFile("JavaMapperTemplate.txt");
        mapperFileContentString = mapperFileContentString.replaceAll("#packageName#", this.packageNameString);
        mapperFileContentString = mapperFileContentString.replaceAll("#className#", this.classNameString);
        mapperFileContentString = mapperFileContentString.replaceAll("#tableName#", GeneratorUtil.camelToUnderline(this.classNameString));

        String columns = "";
        String attrs = "";
        String updateColumns = "";

        for (Field field : fields) {
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName) && !"id".equals(fieldName)) {
                columns = columns + GeneratorUtil.camelToUnderline(fieldName) + ",";
                attrs = attrs + "#{" + fieldName + "},";
               // updateColumns = updateColumns + GeneratorUtil.camelToUnderline(fieldName) + "=#{" + fieldName + "},";
                updateColumns = updateColumns +"<if test=\""+GeneratorUtil.camelToUnderline(fieldName)+" != null \">"+fieldName+"=#{"+GeneratorUtil.camelToUnderline(fieldName)+"},</if>\r\n";
            }
        }
        columns = columns.substring(0, columns.length() - 1);
        attrs = attrs.substring(0, attrs.length() - 1);
        updateColumns = updateColumns.substring(0, updateColumns.length() - 1);
        mapperFileContentString = mapperFileContentString.replaceAll("#columns#", columns);
        mapperFileContentString = mapperFileContentString.replaceAll("#attrs#", attrs);
        mapperFileContentString = mapperFileContentString.replaceAll("#updateColumns#", updateColumns);


        if (this.saveAsFileWriter(mapperFileContentString, this.saveJavaFilePathString.substring(0, this.saveJavaFilePathString.indexOf("main")) + "main/resources/mapper/"  + this.classNameString + ".map.xml")) {
            System.out.println("导出mapper文件成功。。。");
        } else {
            System.out.println("导出mapper文件失败。。。");
        }
    }

    /**
     * 导出dao文件
     */
    private void exportDaoFile() {
        String daoFileContentString = this.readTxtFile("JavaDaoTemplate.txt");
        daoFileContentString = daoFileContentString.replaceAll("#packageName#", this.packageNameString);
        daoFileContentString = daoFileContentString.replaceAll("#className#", this.classNameString);
        daoFileContentString = daoFileContentString.replaceAll("#usedExpose#", "");
        String savePath = this.saveJavaFilePathString + "/mapper/" + this.classNameString + "Dao.java";
        if (this.saveAsFileWriter(daoFileContentString, savePath)) {
            System.out.println("文件路径:" + savePath + ", 导出dao文件成功。");
        } else {
            System.out.println("导出dao文件失败。。。");
        }
    }

    private void exportServiceFile() {
        String content = this.readTxtFile("JavaServiceTemplate.txt");
        content = content.replaceAll("#packageName#", this.packageNameString).replaceAll("#className#", this.classNameString);
        String daoNameStr = Character.toLowerCase(this.classNameString.charAt(0)) + this.classNameString.substring(1) + "Dao";
        content = content.replaceAll("#daoName#", daoNameStr);
        int index_ = this.packageNameString.lastIndexOf(".");//com.ccamazing.modules.shop
//       String prePackageName = this.packageNameString.substring(0,index_);
        String prePackageName = "com.ccamazing";
        String packageExtName = "admin" + this.packageNameString.substring(index_);
        content = content.replaceAll("#packagePreName#", prePackageName);
        content = content.replaceAll("#packageExtName#", packageExtName);
        int index = this.saveJavaFilePathString.lastIndexOf("/");//C:/dfiles/workspaces/ideWorkspaces/zao-activity/src/main/java/com/ccamazing/modules/shop
        String ext = this.saveJavaFilePathString.substring(index);
        String servicePath = this.saveJavaFilePathString.substring(0, this.saveJavaFilePathString.substring(0, index).lastIndexOf("/"));
        if (this.saveAsFileWriter(content, servicePath + "/admin" + ext + "/service/" + this.classNameString + "Service.java")) {
            System.out.println("导出admin Service文件成功。。。");
        } else {
            System.out.println("导出admin Service文件失败。。。");
        }


    }


    private void exportCtlFile() {
        String ctlFileContentString = this.readTxtFile(this.templateFilePathString + "JavaControllerTemplate.txt");
        ctlFileContentString = ctlFileContentString.replaceAll("#packageName#", this.packageNameString);
        ctlFileContentString = ctlFileContentString.replaceAll("#className#", this.classNameString);
        String serviceNameStr = Character.toLowerCase(this.classNameString.charAt(0)) + this.classNameString.substring(1) + "Service";
        ctlFileContentString = ctlFileContentString.replaceAll("#serviceName#", serviceNameStr);
        ctlFileContentString = ctlFileContentString.replaceAll("#url_path#", "/" + this.urlPathString);
        ctlFileContentString = ctlFileContentString.replaceAll("#auth_path#", this.urlPathString.replaceAll("/", ":"));
        ctlFileContentString = ctlFileContentString.replaceAll("#jspPath#", this.jspPathString + "/");

        int index_ = this.packageNameString.lastIndexOf(".");
        String prePackageNameString = this.packageNameString.substring(0, index_);
        String packageExtNameString = "admin" + this.packageNameString.substring(index_);
        ctlFileContentString = ctlFileContentString.replaceAll("#packagePreName#", prePackageNameString);
        ctlFileContentString = ctlFileContentString.replaceAll("#packageExtName#", packageExtNameString);

        int index = this.saveJavaFilePathString.lastIndexOf("/");
        String saveJavaCtlFilePathString = this.saveJavaFilePathString.substring(0, index);
        String ext = this.saveJavaFilePathString.substring(index);
        if (this.saveAsFileWriter(ctlFileContentString, saveJavaCtlFilePathString + "/admin" + ext + "/controller/" + this.classNameString + "Controller.java")) {
            System.out.println("导出admin Controller文件成功。。。");
        } else {
            System.out.println("导出admin Controller文件失败。。。");
        }
    }

    /**
     * 导出jsp文件
     */
    public void exportJspFile() {
        File file = new File(this.saveJspFilePathString);
        if (file.exists()) {
            System.out.println("文件或者文件夹已存在，请删除后再导出jsp文件！");
        } else {
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            this.exportListJspFile();
            this.exportAddJspFile();
            this.exportUpdateJspFile();
        }
    }

    private String readTxtFile(String filePath) {
        StringBuffer buffer = new StringBuffer();
        try {
            String e = "UTF-8";
            InputStream is = this.getClass().getResourceAsStream(filePath);
            InputStreamReader read = new InputStreamReader(is, e);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                buffer.append(lineTxt);
                buffer.append("\n");
            }

            read.close();
        } catch (Exception var8) {
            System.out.println("读取" + filePath + "文件内容出错");
            var8.printStackTrace();
        }

        return buffer.toString().replace("AUTHOR", GeneratorUtil.AUTHOR).replace("CREATE_DATE", GeneratorUtil.getCreateDate());
    }

    private boolean saveAsFileWriter(String content, String filePath) {
        boolean isOk = false;
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("文件或者文件夹已存在，请删除后再导出！filePath=" + filePath);
        } else {
            FileWriter fwriter = null;
            try {
                fwriter = new FileWriter(filePath);
                fwriter.write(content);
                isOk = true;
            } catch (IOException var15) {
                var15.printStackTrace();
            } finally {
                try {
                    fwriter.flush();
                    fwriter.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }
        }
        return isOk;
    }


    private void exportUpdateJspFile() {
        String jspFileContentString = this.readTxtFile(this.templateFilePathString + "JspUpdateTemplate.txt");
        jspFileContentString = jspFileContentString.replaceAll("#url_path#", this.urlPathString);
        StringBuffer fromString = new StringBuffer();
        Field[] var9 = fields;
        int var8 = fields.length;
        for (int var7 = 0; var7 < var8; ++var7) {
            Field field = var9[var7];
            String fieldTypeString = field.getType().getName();
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName) && !"id".equals(fieldName)) {
                fromString.append("\t\t\t\t<tr>").append("\n");
                fromString.append("\t\t\t\t\t<td>" + fieldName + ":</td>\n").append("\t\t\t\t\t<td><input name=\"" + fieldName + "\" value=\"\\${entity." + fieldName + "}\" class=\"easyui-textbox\" type=\"text\" /></td>").append("\n");
                fromString.append("\t\t\t\t</tr>").append("\n");
            }
        }
        jspFileContentString = jspFileContentString.replaceAll("#fieldForm#", fromString.toString());
        this.saveAsFileWriter(jspFileContentString, this.saveJspFilePathString + "/update.jsp");
        System.out.println("导出update.jsp文件成功。。。");
    }

    private void exportAddJspFile() {
        String jspFileContentString = this.readTxtFile(this.templateFilePathString + "JspAddTemplate.txt");
        jspFileContentString = jspFileContentString.replaceAll("#url_path#", this.urlPathString);
        StringBuffer fromString = new StringBuffer();
        Field[] var9 = fields;
        int var8 = fields.length;
        for (int var7 = 0; var7 < var8; ++var7) {
            Field field = var9[var7];
            String fieldTypeString = field.getType().getName();
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName) && !"id".equals(fieldName)) {
                fromString.append("\t\t\t\t<tr>").append("\n");
                fromString.append("\t\t\t\t\t<td>" + fieldName + ":</td>\n").append("\t\t\t\t\t<td><input name=\"" + fieldName + "\" class=\"easyui-textbox\" type=\"text\"  /></td>").append("\n");
                fromString.append("\t\t\t\t</tr>").append("\n");
            }
        }
        jspFileContentString = jspFileContentString.replaceAll("#fieldForm#", fromString.toString());
        this.saveAsFileWriter(jspFileContentString, this.saveJspFilePathString + "/create.jsp");
        System.out.println("导出create.jsp文件成功。。。");
    }

    private void exportListJspFile() {
        String jspFileContentString = this.readTxtFile(this.templateFilePathString + "JspListTemplate.txt");
        jspFileContentString = jspFileContentString.replaceAll("#url_path#", this.urlPathString);
        jspFileContentString = jspFileContentString.replaceAll("#auth_path#", this.urlPathString.replaceAll("/", ":"));
        StringBuffer columns = new StringBuffer("");
        Field[] var9 = fields;
        int var8 = fields.length;
        for (int var7 = 0; var7 < var8; ++var7) {
            Field field = var9[var7];
            String fieldNameString = field.getName();
            if (!"serialVersionUID".equals(fieldNameString) && !"id".equals(fieldNameString)) {
                columns.append("\t\t\t{field:'" + fieldNameString + "',title:'" + fieldNameString + "',width:10}" + (var7 == var8 - 1 ? "" : ",")).append("\n");
            }
        }
        jspFileContentString = jspFileContentString.replaceAll("#columns#", columns.toString());
        this.saveAsFileWriter(jspFileContentString, this.saveJspFilePathString + "/list.jsp");
        System.out.println("导出list.jsp文件成功。。。");
    }


}
