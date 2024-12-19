package com.example.webdemo.repository;

import com.example.webdemo.model.LinkNew;

import java.util.List;

public interface LinkRepository {

    /**
     * 根据中心词语id查询
     *
     * @param centerPointId 中心词语id
     * @return 关联关系列表
     */
    List<LinkNew> getLinkByCenterPointId(int centerPointId);

    /**
     * 根据id查询
     * @param id 关联id
     * @return 关联关系列表
     */
    List<LinkNew> getLinkById(int id);

}
