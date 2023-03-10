package com.example.webdemo.dao;

import com.example.webdemo.model.PointInfo;
import com.example.webdemo.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import static sun.misc.Version.print;

public class Point_Dao {
    static List<PointInfo> list = new ArrayList<>();
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

    public void insert(String English,String Chinese) {
        String sql = "insert into linkwords.point(point_english, point_chinese) value(?,?)";
        update(sql,English,Chinese);
    }

    public void renew(String English,String Chinese)
    {
        String sql = "update linkwords.point set point_chinese = ? where point_english = ?";
        update(sql,Chinese,English);
    }

    public void delete(String English)
    {
        String sql = "delete from linkwords.point where point_english = ?";
        update(sql,English);
    }

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

    public PointInfo find_center_word(String s)
    {
        String sql = "select * from linkwords.point where point_english = ?";
        //System.out.println(s);
        return getInstance(PointInfo.class,sql,s);
    }
}