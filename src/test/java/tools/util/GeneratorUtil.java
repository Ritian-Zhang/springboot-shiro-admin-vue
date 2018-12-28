package tools.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ritian.Zhang
 * @date 2018/12/20
 **/
public class GeneratorUtil{
    public static  final String AUTHOR = "ritian.Zhang";


    /**
     * 生成文件头部信息
     */
    public static String  getCreateDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    /**
     * 驼峰转下划线
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        if(sb.indexOf("_") == 0) {sb.deleteCharAt(0);}
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c=='_'){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
