package com.example.webdemo.dao;

import com.example.webdemo.controller.LinkController;
import com.example.webdemo.model.PointInfo;
import com.example.webdemo.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

import static sun.misc.Version.print;

public class Point_Dao {


    public static void update(String sql, Object... args) {
        try {
            LinkController.connection = DBUtil.connection();
            LinkController.statement = LinkController.connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                LinkController.statement.setObject(i + 1, args[i]);
            }
            LinkController.statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //7关闭资源
            DBUtil.close(LinkController.connection, LinkController.statement, LinkController.resultSet);
        }
    }

    public void insert(String English,String Chinese) {
        //String sql = "insert into linkwords.point(point_english, point_chinese) value(?,?)";
        String sql = "insert into LinkMemo.point(point_english, point_chinese) value(?,?)";
        update(sql,English,Chinese);
    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        try {
            // 1.获取数据库连接
            // LinkController.connection = DBUtil.connection();

            // 2.预编译sql语句，得到PreparedStatement对象
            LinkController.statement = LinkController.connection.prepareStatement(sql);

            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                LinkController.statement.setObject(i + 1, args[i]);
            }

            // 4.执行executeQuery(),得到结果集：ResultSet
            LinkController.resultSet = LinkController.statement.executeQuery();

            // 5.得到结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd = LinkController.resultSet.getMetaData();

            // 6.1通过ResultSetMetaData得到columnCount,columnLabel；通过ResultSet得到列值
            int columnCount = rsmd.getColumnCount();
            if (LinkController.resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {// 遍历每一个列
                    // 获取列值
                    Object columnVal = LinkController.resultSet.getObject(i + 1);
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
        }
        /*finally {
            // 7.关闭资源
            //DBUtil.close(LinkController.connection, LinkController.statement, LinkController.resultSet);
        }
         */
        return null;
    }

    public PointInfo find_center_word(String s)
    {
        String sql = "select * from LinkMemo.point where point_english = ?";
        return getInstance(PointInfo.class,sql,s);
    }

    public PointInfo find_center_word(int id)
    {
        String sql = "select * from LinkMemo.point where point_id = ?";
        return getInstance(PointInfo.class,sql,id);
    }
}