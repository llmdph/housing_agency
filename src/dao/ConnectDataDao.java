package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDataDao  {

    String url = "jdbc:mysql://localhost:3306/agency"; //数据库名
    String username = "root";  //数据库用户名
    String password = "230212";  //数据库用户密码
    public Connection getConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");  //驱动程序名
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stat = conn.createStatement();
        return conn;
    }
}
