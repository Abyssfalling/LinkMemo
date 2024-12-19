package com.example.webdemo.dao;

import com.example.webdemo.model.UserInfo;
import com.example.webdemo.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class User_Dao {
    static List<UserInfo> list = new ArrayList<>();
    static ResultSet resultSet = null;


    public static void update(String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = DBUtil.connection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(String Name, String Password) {
        String sql = "insert into Linkwords.user(user_name,user_password) value(?,?)";
        update(sql, Name, Password);
    }

    public void renew(String Name, String psw) {
        String sql = "update Linkwords.user set user_password = ? where user_name = ?";
        update(sql, psw, Name);
    }

    public void delete(int id) {
        String sql = "delete from Linkwords.user where user_id = ?";
        update(sql, id);
    }

    // 通用的针对于不同表的查询:返回一个对象 (version 1.0)
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {

            // 2.预编译sql语句，得到PreparedStatement对象
            preparedStatement = DBUtil.connection().prepareStatement(sql);

            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            // 4.执行executeQuery(),得到结果集：ResultSet
            resultSet = preparedStatement.executeQuery();

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
        }
        return null;
    }

    public UserInfo find_one(String username, String password) {
        String sql = "select * from Linkwords.user where user_name = ? and user_password = ?";
        return getInstance(UserInfo.class, sql, username, password);
    }

    public UserInfo findOneById(int id) {
        String sql = "select * from Linkwords.user where User_id = ?";
        return getInstance(UserInfo.class, sql, id);
    }

    /**
     * todo 新方法（注册）
     *
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     */
    public void insert(String username, String password, String nickName) {
        String sql = "insert into Linkwords.user(user_name,user_password,user_nickname) value(?,?,?)";
        update(sql, username, password, nickName);
    }

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    public UserInfo findOneByName(String username) {
        String sql = "select * from Linkwords.user where user_name = ?";
        return getInstance(UserInfo.class, sql, username);
    }

}