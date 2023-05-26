package com.example.webdemo.controller;

import com.example.webdemo.dao.Link_Dao;
import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.LinkInfo;
import com.example.webdemo.model.PointInfo;
import com.example.webdemo.util.DBUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LinkController {

    public static class Result
    {
        public String str;

        public Result(String str) {
            this.str = str;
        }
    }

    public static List<PointInfo> point_list = new ArrayList<>();
    public static List<LinkInfo> link_list = new ArrayList<>();
    public static ResultSet resultSet = null;
    public static PreparedStatement statement = null;
    public static Connection connection = null;


    @ResponseBody
    @RequestMapping(value = "/find")
    public Result checkFind(@RequestParam(name = "english") String English) {
        //System.out.println("输入：" + English + " " + Chinese);

        connection = DBUtil.connection();

        Point_Dao p = new Point_Dao();
        PointInfo point = p.find_center_word(English);

        Link_Dao d = new Link_Dao();
        int id = d.get_center_id(English);
        LinkInfo link = d.find_center_id(id);

        StringBuilder result = new StringBuilder();
        String temp = "";

        if(link != null)
        {
            result.append(English).append(";").append(point.getPoint_chinese());

            result.append("-");
            temp = link.getLink_looklike();
            if(temp != null && temp != "")
            {
                String[] numberArray1 = temp.split(",");
                for (int i = 0; i < numberArray1.length; i++) {
                    if(i!=0)
                    {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray1[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            result.append("-");
            temp = link.getLink_meanlike();
            if(temp != null && temp != "")
            {
                String[] numberArray2 = temp.split(",");
                for (int i = 0; i < numberArray2.length; i++) {
                    if(i!=0)
                    {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray2[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            result.append("-");
            temp = link.getLink_meanlike();
            if(temp != null && temp != "")
            {
                String[] numberArray3 = temp.split(",");
                for (int i = 0; i < numberArray3.length; i++) {
                    if(i!=0)
                    {
                        result.append(";");
                    }
                    int num = Integer.parseInt(numberArray3[i]);
                    point = p.find_center_word(num);
                    result.append(point.getPoint_english());
                }
            }

            Result obj = new Result(result.toString());

            System.out.println(obj.str);
            DBUtil.close(connection, statement, resultSet);
            return obj;
        }
        else
        {
            System.out.println("返回失败，存在问题");
            return new Result("");
        }
    }
}
