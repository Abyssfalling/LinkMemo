package com.example.webdemo.dao;

import com.example.webdemo.model.PointInfo;
import com.example.webdemo.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import static com.example.webdemo.util.ArrayUtil.getArrayString;
import static com.example.webdemo.util.DBUtil.connection;

public class Point_Dao {

    public static void update(String sql, Object... args) {
        PreparedStatement preparedStatement = null;
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

    public void insert(String English, String Chinese) {
        String sql = "insert into Linkwords.point(point_english, point_chinese) value(?,?)";
        update(sql, English, Chinese);
    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            // 2.预编译sql语句，得到PreparedStatement对象
            preparedStatement = connection().prepareStatement(sql);

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

    public PointInfo find_center_id(int id) {
        String sql = "select * from Linkwords.point where point_id = ?";
        return getInstance(PointInfo.class, sql, id);
    }

    public PointInfo find_center_word(String s) {
        String sql = "select * from Linkwords.point where point_english = ?";
        return getInstance(PointInfo.class, sql, s);
    }

    public PointInfo find_center_word(int id) {
        String sql = "select * from Linkwords.point where point_id = ?";
        return getInstance(PointInfo.class, sql, id);
    }

    /**
     * 新增 模糊查询
     *
     * @param str 模糊查询关键字
     * @return 符合关键字的结果集
     */
    public List<PointInfo> getPointByFuzzy(String str) {
        String sql = "select * from point where point_english like ?";
        return DBUtil.getInstanceByFuzzy(PointInfo.class, sql, str);
    }

    /**
     * 新增 批量获取单词
     *
     * @param ids 单词id数组
     * @return 符合关键字的结果集
     */
    public List<PointInfo> getPointBatch(int... ids) {
        String sql = "select * from point where point_id in " + getArrayString(ids);
        return DBUtil.getInstance(PointInfo.class, sql);
    }

    /**
     * 新增 根据文字获取实体对象
     *
     * @param s 单词
     * @return 实体对象
     */
    public PointInfo find_center_wordNew(String s) {
        String sql = "select * from Linkwords.point where point_english = ?";
        return getInstance(PointInfo.class, sql, s);
    }

}