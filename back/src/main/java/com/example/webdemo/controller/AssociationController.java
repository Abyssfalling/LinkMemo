package com.example.webdemo.controller;

import com.example.webdemo.dao.LinkDaoNew;
import com.example.webdemo.dao.Link_Dao;
import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.LinkNew;
import com.example.webdemo.model.PointInfo;
import com.example.webdemo.model.response.BaseResponse;
import com.example.webdemo.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AssociationController {

    /**
     * todo 使用依赖注入可以减少创建dao类对象的次数。减少软件组件之间的耦合度
     * <p>
     * </p>
     * 具体文章:
     * <a
     * href="https://zhuanlan.zhihu.com/p/640601681">
     * </a>
     */
    @Autowired
    AssociationRepository associationRepository;

    /**
     * 用户删除的关联关系添加
     *
     * @param uid  用户id
     * @param lid  关联关系id (主单词id)
     * @param type 类型： 1：近义词； 2：反义词； 3：同义词
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "association/add")
    public BaseResponse<Object> associationAdd(@RequestParam(name = "uid") int uid, @RequestParam(name = "lid") int lid, @RequestParam(name = "type") int type) {

        BaseResponse<Object> response = new BaseResponse<>();

        LinkDaoNew linkDao = new LinkDaoNew();
        if (linkDao.getLinkById(lid).size() > 0) {
            associationRepository.insertAssociation(uid, lid, type);
        } else {
            associationRepository.deleteCustomer(lid);
        }
        return response;
    }

    /**
     * 用户关联关系删除
     *
     * @param aid 用户id
     * @return 结果列
     */
    @ResponseBody
    @RequestMapping(value = "association/delete")
    public BaseResponse<Object> associationDelete(@RequestParam(name = "association_id") int aid) {
        BaseResponse<Object> response = new BaseResponse<>();
        associationRepository.delete(aid);
        return response;
    }

    /**
     * 新增 用户自定义关联关系
     *
     * @param uid   用户编号
     * @param mWid  主单词id
     * @param sWord 关联单词
     * @param type  类型
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "association/addCustomer")
    public BaseResponse<PointInfo> addLink(@RequestParam(name = "uid") int uid, @RequestParam(name = "mWid") int mWid, @RequestParam(name = "sWord") String sWord, @RequestParam(name = "type") int type) {
        BaseResponse<PointInfo> result = new BaseResponse<>();
        Point_Dao pointDao = new Point_Dao();
        PointInfo point = pointDao.find_center_wordNew(sWord);
        if (point == null) {
            result.message = "暂未收录此单词！";
        } else {
            associationRepository.insertCustomer(uid, mWid, point.getPoint_id(), type);
        }
        return result;
    }

}
