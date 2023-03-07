package com.example.webdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootTest
class WebDemoApplicationTests {
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
}
