package com.example.webdemo.controller;

import com.example.webdemo.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.webdemo.dao.User_Dao;

import java.sql.SQLException;

@Controller
public class UserController {
    @RequestMapping(value = "/login")
    public String login(){
        System.out.println("login web");
        return "login";
    }

    @RequestMapping(value = "/login.action")
    public String checkLogin(String name,String pwd) throws SQLException {
        System.out.println("输入：" + name + " " + pwd);

        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name);

        if(u != null && (u.getUserPasswd()).equals(pwd))
        {
            System.out.println(name + " 登录成功");
            return "Success";
        }
        else
        {
            System.out.println(name + " 登录失败");
            return "login";
        }
    }

    @RequestMapping(value = "/register")
    public String register(){
        System.out.println("register web");
        return "register";
    }

    @RequestMapping(value = "/register.action")
    public String checkRegister(String name,String pwd) {
        User_Dao userDao = new User_Dao();
        userDao.insert(name,pwd);
        System.out.println(name + " 注册成功");
        return "Success";
    }

    @RequestMapping(value = "/renew")
    public String renew(){
        System.out.println("renew web");
        return "renew";
    }

    @RequestMapping(value = "/renew.action")
    public String checkRenew(String name,String pwd) {

        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name);

        if(u != null)
        {
            d.renew(name,pwd);
            System.out.println(name + " 修改成功");
            return "Success";
        }
        else
        {
            System.out.println(name + " 修改失败");
            return "renew";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(){
        System.out.println("delete web");
        return "delete";
    }

    @RequestMapping(value = "/delete.action")
    public String checkDelete(String name) {

        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name);

        if(u != null)
        {
            d.delete(name);
            System.out.println(name + " 删除成功");
            return "Success";
        }

        else
        {
            System.out.println(name + " 删除失败");
            return "delete";
        }
    }
}
