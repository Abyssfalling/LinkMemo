package com.example.webdemo.dao;

import com.example.webdemo.model.LinkInfo;
import com.example.webdemo.model.PointInfo;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.webdemo.util.DBUtil.connection;

public class Link_Dao {

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

    public void insert0(int center_id, String looklike) {
        //String sql = "insert into linkwords.link(link_center, link_looklike) value(?,?)";
        String sql = "insert into Linkwords.linknew(link_center, link_looklike) value(?,?)";
        update(sql, center_id, looklike);
    }

    public void insert1(int center_id, String meanlike) {
        //String sql = "insert into linkwords.link(link_center, link_meanlike) value(?,?)";
        String sql = "insert into Linkwords.linknew(link_center, link_meanlike) value(?,?)";
        update(sql, center_id, meanlike);
    }

    public void insert2(int center_id, String relate) {
        //String sql = "insert into linkwords.link(link_center, link_relate) value(?,?)";
        String sql = "insert into Linkwords.linknew(link_center, link_relate) value(?,?)";
        update(sql, center_id, relate);
    }

    public void renew0(int center_id, String looklike) {
        //String sql = "update linkwords.link set link_looklike = ? where link_center = ?";
        String sql = "update Linkwords.linknew set link_looklike = ? where link_center = ?";
        update(sql, looklike, center_id);
    }

    public void renew1(int center_id, String meanlike) {
        String sql = "update Linkwords.linknew set link_meanlike = ? where link_center = ?";
        update(sql, meanlike, center_id);
    }

    public void renew2(int center_id, String relate) {
        String sql = "update Linkwords.linknew set link_relate = ? where link_center = ?";
        update(sql, relate, center_id);
    }

    public void delete(int center_id) {
        String sql = "delete from Linkwords.linknew where link_center = ?";
        update(sql, center_id);
    }

    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            // 1.获取数据库连接

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

    /**
     * 获取列表
     * @param clazz
     * @param sql
     * @param args
     * @return
     * @param <T>
     */
    public <T> List<T> getInstanceList(Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<T> result = new ArrayList<>();
        try {
            // 1.获取数据库连接

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
                result.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 提供单词，返回单词对应id
    // todo 这里，其实不应该把中心单词的id设为主键，因为一个单词可能有多个联系
    public int get_center_id(String center) {
        Point_Dao center_word = new Point_Dao();
        PointInfo p = center_word.find_center_word(center);
        return p == null ? -1 : p.getPoint_id();
    }

    public LinkInfo find_center_id(int id) {
        String sql = "select * from Linkwords.linknew where centerPointId = ?";
        return getInstance(LinkInfo.class, sql, id);
    }

    public List<LinkInfo> getLinkList(int id) {
        String sql = "select * from Linkwords.linknew where id = ?";
        return getInstanceList(LinkInfo.class, sql, id);
    }

    //查询链接是否存在，存在true，不存在false
    public boolean checkAdd(int id) {
        Link_Dao d = new Link_Dao();
        LinkInfo u = d.find_center_id(id);

        return u != null;
    }

    public String Add(int id, String link, int index) {


        if (index == 0) {
            insert0(id, link);
            System.out.println(id + " " + link + " 添加0成功");
            return "Success";
        } else if (index == 1) {
            insert1(id, link);
            System.out.println(id + " " + link + " 添加1成功");
            return "Success";
        } else if (index == 2) {
            insert2(id, link);
            System.out.println(id + " " + link + " 添加2成功");
            return "Success";
        }

        return "add_link";
    }

    public String Renew(int id, String link, int index) {
        //Link_Dao d = new Link_Dao();

        if (index == 0) {
            renew0(id, link);
            System.out.println(id + " " + link + " 更新0成功");
            return "Success";
        } else if (index == 1) {
            renew1(id, link);
            System.out.println(id + " " + link + " 更新1成功");
            return "Success";
        } else if (index == 2) {
            renew2(id, link);
            System.out.println(id + " " + link + " 更新2成功");
            return "Success";
        }

        return "renew_link";
    }

    public void insertNew(int center_id, int link_looklike,int link_meanlike,int link_relate) {
        //String sql = "insert into linkwords.link(link_center, link_relate) value(?,?)";
        String sql = "insert into Linkwords.link(link_center,link_looklike,link_meanlike, link_relate) value(?,?,?,?)";
        update(sql, center_id ,link_looklike,link_meanlike,link_relate);
    }

}
