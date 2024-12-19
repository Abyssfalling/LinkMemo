package com.example.webdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.webdemo.dao.Link_Dao;
import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.AssociationInfo;
import com.example.webdemo.model.LinkInfo;
import com.example.webdemo.model.PointInfo;
import com.example.webdemo.model.UserCustomerAssociationInfo;
import com.example.webdemo.model.response.BaseResponse;
import com.example.webdemo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LinkController {

    @Autowired
    AssociationRepository associationRepository;

    public static class Result {
        public String str;

        public Result(String str) {
            this.str = str;
        }
    }

    public static List<PointInfo> point_list = new ArrayList<>();
    public static List<LinkInfo> link_list = new ArrayList<>();


    @ResponseBody
    @RequestMapping(value = "/find")
    public Result checkFind(@RequestParam(name = "english") String English) {

        Point_Dao p = new Point_Dao();

        // 寻找单词
        PointInfo point = p.find_center_word(English);

        Link_Dao d = new Link_Dao();

        int id = d.get_center_id(English);
        LinkInfo link = d.find_center_id(id);

        StringBuilder result = new StringBuilder();
        String temp = "";

        if (link != null) {

            result.append(English).append(";").append(point.getPoint_chinese());

            result.append("-");
            temp = link.getLink_looklike();
            if (temp != null && temp != "") {
                String[] numberArray1 = temp.split(",");
                for (int i = 0; i < numberArray1.length; i++) {
                    if (i != 0) {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray1[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            result.append("-");
            temp = link.getLink_meanlike();
            if (temp != null && temp != "") {
                String[] numberArray2 = temp.split(",");
                for (int i = 0; i < numberArray2.length; i++) {
                    if (i != 0) {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray2[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            result.append("-");
            temp = link.getLink_relate();
            if (temp != null && temp != "") {
                String[] numberArray3 = temp.split(",");
                for (int i = 0; i < numberArray3.length; i++) {
                    if (i != 0) {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray3[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            return new Result(result.toString());
        } else {
            return new Result("");
        }
    }

    /**
     * 新增 查询关系
     *
     * @param English 英文
     * @return json数据，包括，近义词、反义词、关联词的id等信息
     */
    /*@ResponseBody
    @RequestMapping(value = "/findNew")
    public String checkFindNew(@RequestParam(name = "uid") int uid, @RequestParam(name = "english") String English) {

        JSONObject jsonObject = new JSONObject();

        Point_Dao p = new Point_Dao();
        Link_Dao d = new Link_Dao();
        // 寻找单词
        PointInfo point = p.find_center_word(English);

        if (point == null) {
            return jsonObject.toJSONString();
        }

        LinkInfo link = d.find_center_id(point.getPoint_id());

        if (link == null) {
            link = new LinkInfo();
            link.setLink_center(0);
            link.setLink_looklike("0");
            link.setLink_meanlike("0");
            link.setLink_relate("0");
        }

        jsonObject.put("point_id", point.getPoint_id());
        jsonObject.put("point_english", point.getPoint_english());
        jsonObject.put("point_chinese", point.getPoint_chinese());

        // 这里去用户的关系表中查询删除的数据，如果有，则不需要查询单词转而查询用户自定义关系表
        List<AssociationInfo> associationInfoList = link.getLink_center() == 0 ? new ArrayList<>() : associationRepository.getDeleteList(uid, link.getLink_center());

        // 查询近义词对象(使用stream流判断列表中是否有type为1的记录，如果有说明此用户删掉了该关系，不再查询)
        PointInfo synonym = null;
        // 反义词
        PointInfo antonym = null;
        // 关联词
        PointInfo relate = null;

        // 首先判断用户登录状态
        // 如果没有登录直接获取原始数据
        if (uid == 0) {
            synonym = p.find_center_id(Integer.parseInt(link.getLink_looklike()));
            antonym = p.find_center_word(Integer.parseInt(link.getLink_meanlike()));
            relate = p.find_center_word(Integer.parseInt(link.getLink_relate()));
        } else {

            // 用户状态为已登录
            // 首先判断是否有单词可使用户添加的，如果有，直接添加。如果没有用户添加，判断是否有用户删除的行为。
            List<UserCustomerAssociationInfo> customerAssociationSynonyms = associationRepository.getCustomerList(uid, point.getPoint_id(), 1);
            if (customerAssociationSynonyms.size() > 0) {
                // 如果有用户自定义关联关系，则显示关联关系
                synonym = p.find_center_id(customerAssociationSynonyms.get(0).getsWid());
            } else if (associationInfoList.stream().anyMatch(item -> item.getType() == 1)) {
                // 如果没有关联关系，也没有用户删除记录。则显示原始数据
            }else{
                synonym = p.find_center_id(Integer.parseInt(link.getLink_looklike()));
            }
            // 剩下的就是用户有删除记录的情况，则为空


            List<UserCustomerAssociationInfo> customerAssociationAntonyms = associationRepository.getCustomerList(uid, point.getPoint_id(), 2);
            if (customerAssociationAntonyms.size() > 0) {
                antonym = p.find_center_id(customerAssociationAntonyms.get(0).getsWid());
            } else if (associationInfoList.stream().anyMatch(item -> item.getType() == 2)) {

            }else{
                antonym = p.find_center_id(Integer.parseInt(link.getLink_meanlike()));
            }

            List<UserCustomerAssociationInfo> customerAssociationRelates = associationRepository.getCustomerList(uid, point.getPoint_id(), 3);
            if (customerAssociationRelates.size() > 0) {
                relate = p.find_center_id(customerAssociationRelates.get(0).getsWid());
            } else if (associationInfoList.stream().anyMatch(item -> item.getType() == 3)) {

            }else{
                relate = p.find_center_word(Integer.parseInt(link.getLink_relate()));
            }
        }

        jsonObject.put("synonym", synonym);
        jsonObject.put("antonym", antonym);
        jsonObject.put("relate", relate);

        return jsonObject.toJSONString();
    }*/

    /**
     * 新增-关联关系增加
     *
     * @param pid 主要单词id
     * @param sid 近义词id
     * @param aid 反义词id
     * @param rid 关联词id
     */
    @ResponseBody
    @RequestMapping(value = "/addNew")
    public BaseResponse<String> addLink(@RequestParam(name = "pid") int pid, @RequestParam(name = "sid") int sid, @RequestParam(name = "aid") int aid, @RequestParam(name = "rid") int rid) {
        Link_Dao d = new Link_Dao();
        d.insertNew(pid, sid, aid, rid);
        return new BaseResponse<>();
    }

    /**
     * 新增-删除关系
     *
     * @param pid 主要单词id
     */
    @ResponseBody
    @RequestMapping(value = "/deleteNew")
    public BaseResponse<String> deleteLink(@RequestParam(name = "pid") int pid) {
        Link_Dao d = new Link_Dao();
        d.delete(pid);
        return new BaseResponse<>();
    }

}
