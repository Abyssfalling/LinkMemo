package com.example.webdemo.controller;

import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.PointInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinkController {
    @RequestMapping(value = "/load")
    public String login(){
        System.out.println("load web");
        return "load";
    }

    @RequestMapping(value = "/load.action")
    public String checkLoad(String English,String Chinese) {
        System.out.println("输入：" + English + " " + Chinese);

        Point_Dao d = new Point_Dao();
        PointInfo u = d.find_one(English);

        if(u == null)
        {
            d.insert(English,Chinese);
            System.out.println(English + " 添加成功");
            return "Success";
        }
        else
        {
            System.out.println(English + " 添加失败，单词可能已存在");
            return "load";
        }
    }