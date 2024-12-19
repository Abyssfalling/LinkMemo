package com.example.webdemo.dao;

import com.example.webdemo.model.AssociationInfo;
import com.example.webdemo.model.UserCustomerAssociationInfo;
import com.example.webdemo.repository.AssociationRepository;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.webdemo.util.DBUtil.*;

/**
 * 这里其实是一个接口的实现类，如果严格来说的话，dao类应该是个接口（也就是repository文件夹下的AssociationRepository接口），
 * 此类应该是一个impl的实现类，用来实现dao类定义的方法
 */
@Repository
public class AssociationDao implements AssociationRepository {


    @Override
    public void insertAssociation(int uid,  int lid, int type) {
        String sql = "insert into Linkwords.association_delete(id,uid,lid,type) value((RAND() * 900000 + 100000),?,?,?)";
        update(sql, uid,  lid, type);
    }

    /**
     * 新增 新增关联关系，但是是在另一个表中
     *
     * @param uid  用户编号
     * @param mWid 主要单词编号
     * @param sWid 次要单词编号
     * @param type 类型
     */
    public void insertCustomer(int uid, int mWid, int sWid, int type) {
        String sql = "insert into Linkwords.association_customer(id,uid, mWid,sWid,type) value((RAND() * 900000 + 100000),?,?,?,?)";
        update(sql, uid, mWid, sWid, type);
    }

    /**
     * 新增 获取删除列表
     *
     * @param uid 用户id
     * @return 关联关系列表
     */
    public List<AssociationInfo> getDeleteList(int uid) {
        String sql = "select * from association_delete where uid = ? ";
        return getInstance(AssociationInfo.class, sql, uid);
    }

    /**
     * 新增 删除
     *
     * @param aid 关联关系id
     */
    public void delete(int aid) {
        String sql = "delete from Linkwords.association where aid = ?";
        update(sql, aid);
    }

    /**
     * 新增 删除用户关联关系
     *
     * @param aid 用户关联关系表中的id
     */
    @Override
    public void deleteCustomer(int aid) {
        String sql = "delete from Linkwords.association_customer where id = ?";
        update(sql, aid);
    }

    /**
     * 获取用户自定义关联关系
     *
     * @param uid  用户id
     * @param mWid 主单词id
     * @return 关联关系列表
     */
    public List<UserCustomerAssociationInfo> getCustomerList(int uid, int mWid) {

        List<UserCustomerAssociationInfo> result = new ArrayList<>();

        // 首先是正向的数据
        String sql_forward = "select * from Linkwords.association_customer where uid = ? and mWid = ?";
        result.addAll(getInstance(UserCustomerAssociationInfo.class, sql_forward, uid, mWid));
        // 其次是反向的数据，也就是说主要单词是其他关联关系中的次要单词
        String sql_reverse = "select id, mWid AS sWid, sWid AS mWid, uid, type from association_customer where uid = ? and sWid = ? ";
        result.addAll(getInstance(UserCustomerAssociationInfo.class, sql_reverse, uid, mWid));

        // 根据id去重
        List<UserCustomerAssociationInfo> distinctPersons = new ArrayList<>(result.stream()
                .collect(Collectors.toMap(UserCustomerAssociationInfo::getId, p -> p, (p1, p2) -> p1))
                .values());

        return distinctPersons;
    }
}
