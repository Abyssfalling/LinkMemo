package com.example.webdemo.util;

import com.example.webdemo.controller.LinkController;
import com.example.webdemo.dao.Point_Dao;
import com.example.webdemo.model.PointInfo;

import java.sql.*;
import java.util.Scanner;

public class DBUtilTest {
    public static void main(String[] args) {

            // 创建 LinkController 实例
            LinkController linkController = new LinkController();

            // 模拟输入参数
            String english = "ask";

            // 调用 checkFind 方法
            LinkController.Result result = linkController.checkFind(english);

            // 验证结果是否符合预期
            //System.out.println(result.str);

    }
}