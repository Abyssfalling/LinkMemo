package com.example.webdemo.dao;

import com.example.webdemo.model.UserInfo;
import com.example.webdemo.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import static sun.misc.Version.print;

public class User_Dao {
    static List<UserInfo> list = new ArrayList<>();
    static ResultSet resultSet = null;
    static PreparedStatement statement = null;
    static Connection connection = null;

    public static void update(String sql, Object... args) {
        try {
            connection = DBUtil.connection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //7关闭资源
            DBUtil.close(connection, statement, resultSet);
        }
    }

    public void insert(String Name,String Password) {
        String sql = "insert into linkwords.user(user_name,user_password) value(?,?)";
        update(sql,Name,Password);
    }

    public void renew(String Name,String psw)
    {
        String sql = "update linkwords.user set user_password = ? where user_name = ?";
        update(sql,psw,Name);
    }

    public void delete(String Name)
    {
        String sql = "delete from linkwords.user where user_name = ?";
        update(sql,Name);
    }

    // 通用的针对于不同表的查询:返回一个对象 (version 1.0)
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        try {
            // 1.获取数据库连接
            connection = DBUtil.connection();

            // 2.预编译sql语句，得到PreparedStatement对象
            statement = connection.prepareStatement(sql);

            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            // 4.执行executeQuery(),得到结果集：ResultSet
            resultSet = statement.executeQuery();

            // 5.得到结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd = resultSet.getMetaData();

            // 6.1通过ResultSetMetaData得到columnCount,columnLabel；通过ResultSet得到列值
            int columnCount = rsmd.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {// 遍历每一个列
                    // 获取列值
                    Object columnVal = resultSet.getObject(i + 1);
                    // 获取列的别名:列的别名，使用类的属性名充当
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 6.2使用反射，给对象的相应属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.关闭资源
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    public UserInfo find_one(String now_name)
    {
        String sql = "select * from linkwords.user where user_name = ?";
        UserInfo u = getInstance(UserInfo.class,sql,now_name);
        return u;
    }
}