package tools.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/7/3.
 */
public class DatabaseUtils {
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/shiro_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

   /* 获取数据库连接的函数*/
    public static Connection openConnection() {
        Connection con = null;  //创建用于连接数据库的Connection对象
        try {
            Class.forName(CLASS_NAME);// 加载Mysql数据驱动
            con = DriverManager.getConnection( URL, USERNAME, PASSWORD);// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; //返回所建立的数据库连接
    }

    public static void closeDatabase(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
