package com.example.webdemo.util;
import java.sql.*;

public class DBUtil {
    public static Connection connection() {
        Connection con = null;
        String driver = "com.mysql.cj.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/linkwords?&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "1231";
        try {
            //注册JDBC驱动程序
            Class.forName(driver);
            //建立连接
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("数据库连接成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //关闭资源
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet)
    {
        if(resultSet != null)
        {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(statement != null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if(connection != null)
        {
            try{
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
