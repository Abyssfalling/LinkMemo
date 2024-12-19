package com.example.webdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.webdemo.dao.LinkDaoNew;
import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.AssociationInfo;
import com.example.webdemo.model.LinkNew;
import com.example.webdemo.model.PointInfo;
import com.example.webdemo.model.UserCustomerAssociationInfo;
import com.example.webdemo.repository.AssociationRepository;
import com.example.webdemo.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LinkControllerNew {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private AssociationRepository associationRepository;

    /**
     * 获取单词信息
     *
     * @param uid
     * @param English
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPointInfo")
    public String getPointInfo(@RequestParam(name = "uid") int uid, @RequestParam(name = "english") String English) {

        JSONObject jsonObject = new JSONObject();
        Point_Dao pointDao = new Point_Dao();
        LinkDaoNew linkDaoNew = new LinkDaoNew();

        // 寻找单词
        PointInfo point = pointDao.find_center_word(English);

        if (point == null) {
            return jsonObject.toJSONString();
        }

        jsonObject.put("point_id", point.getPoint_id());
        jsonObject.put("point_english", point.getPoint_english());
        jsonObject.put("point_chinese", point.getPoint_chinese());

        // 查询近义词对象(使用stream流判断列表中是否有type为1的记录，如果有说明此用户删掉了该关系，不再查询)

        // 反义词
        List<PointInfo> antonym = null;
        // 关联词
        List<PointInfo> relate = null;

        // 关联关系列表
        List<LinkNew> linkNewList = linkDaoNew.getLinkByCenterPointId(point.getPoint_id());
        // 取出关联单词的id
        int[] linkPoints = linkNewList.stream().map(LinkNew::getLinkPointId).collect(Collectors.toList()).stream()
                .mapToInt(Integer::intValue)
                .toArray();
        // 取出关联单词(原始数据)
        List<PointInfo> pointInfos = pointDao.getPointBatch(linkPoints);

        // 将关联单词对象放入关联列表中(双循环)
        linkNewList.forEach(linkNew -> pointInfos.forEach(PointInfo -> {
            if (PointInfo.getPoint_id() == linkNew.getLinkPointId()) {
                linkNew.setLinkPoint(PointInfo);
            }
        }));

        // 如果用户是登录状态，则查询用户自定义表
        if (uid > 0) {

            // 用户关联关系
            List<PointInfo> customerPointInfos;
            // 被用户删掉的连接
            List<AssociationInfo> deleteList = associationRepository.getDeleteList(uid);
            List<UserCustomerAssociationInfo> customerAssociationSynonyms = associationRepository.getCustomerList(uid, point.getPoint_id());

            // 首先删掉用户的删掉的原关联关系
            List<Integer> ids = deleteList.stream().map(AssociationInfo::getLid).collect(Collectors.toList());
            linkNewList = linkNewList.stream()
                    .filter(obj1 -> ids.stream().noneMatch(obj2 -> obj1.getId() == obj2))
                    .collect(Collectors.toList());

            // 添加用户新增的
            if (customerAssociationSynonyms.size() > 0) {
                List<LinkNew> finalLinkNewList = linkNewList;
                customerAssociationSynonyms.forEach(item -> {
                    int wid = item.getsWid();
                    List<PointInfo> list = pointDao.getPointBatch(wid);
                    if (list != null && !list.isEmpty()) {
                        PointInfo pointInfo = list.get(0);
                        LinkNew linkNew = new LinkNew();
                        linkNew.setLinkPoint(pointInfo);
                        linkNew.setType(item.getType());
                        linkNew.setCenterPointId(point.getPoint_id());
                        linkNew.setLinkPointId(pointInfo.getPoint_id());
                        linkNew.setId(item.getId());
                        finalLinkNewList.add(linkNew);
                    }
                });
            }
        }

        // 根据关联单词
        jsonObject.put("synonym", linkNewList.stream().filter(item -> item.getType() == 1).collect(Collectors.toList()));
        jsonObject.put("antonym", linkNewList.stream().filter(item -> item.getType() == 2).collect(Collectors.toList()));
        jsonObject.put("relate", linkNewList.stream().filter(item -> item.getType() == 3).collect(Collectors.toList()));


        return jsonObject.toJSONString();
    }

}
