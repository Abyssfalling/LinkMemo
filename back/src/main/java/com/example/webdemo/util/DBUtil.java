package com.example.webdemo.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    /**
     * TODO 应该为单例模式
     */
    private static Connection con = null;

    /**
     * TODO 一样
     */
    public static Connection connection() {

        String driver = "com.mysql.jdbc.Driver";

        // TODO 懒汉式单例模式
        if (con != null) {
            return con;
        }

        /*
            String url = "jdbc:mysql://localhost:3306/linkwords?&useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "1231";
        */

        // todo 数据库ip、端口、帐号密码
        String url = "jdbc:mysql://101.227.236.139:3306/Linkwords?useSSL=false&useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "Bigdata2101~";

        try {
            //注册JDBC驱动程序
            Class.forName(driver);
            //建立连接
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //关闭资源
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> List<T> getInstance(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<T> resultList = new ArrayList<>();
        try {
            preparedStatement = connection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            System.out.println(preparedStatement);

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnCount = rsmd.getColumnCount();
            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {// 遍历每一个列
                    Object columnVal = resultSet.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                resultList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 模糊查询
     *
     * @param clazz 返回的对象
     * @param sql   sql语句
     * @param args  参数
     * @param <T>   泛型
     * @return 返回的泛型列表
     */
    public static <T> List<T> getInstanceByFuzzy(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<T> resultList = new ArrayList<>();
        try {
            preparedStatement = connection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i] + "%");
            }

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnCount = rsmd.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {// 遍历每一个列
                    Object columnVal = resultSet.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                resultList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static void update(String sql, Object... args) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
