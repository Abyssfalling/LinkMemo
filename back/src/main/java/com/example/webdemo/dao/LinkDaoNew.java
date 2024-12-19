package com.example.webdemo.dao;

import com.example.webdemo.model.LinkNew;
import com.example.webdemo.repository.LinkRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.webdemo.util.DBUtil.getInstance;

@Repository
public class LinkDaoNew implements LinkRepository {

    /**
     * 根据中心词语id查询
     *
     * @param centerPointId 中心词语id
     * @return 关联关系列表
     */
    @Override
    public List<LinkNew> getLinkByCenterPointId(int centerPointId) {
        String sql = "select * from linknew where centerPointId = ?";
        return getInstance(LinkNew.class, sql, centerPointId);
    }

    /**
     * 根据id查询
     *
     * @param id 关联id
     * @return 关联关系列表
     */
    @Override
    public List<LinkNew> getLinkById(int id) {
        String sql = "select * from linknew where id = ?";
        return getInstance(LinkNew.class, sql, id);
    }
}
