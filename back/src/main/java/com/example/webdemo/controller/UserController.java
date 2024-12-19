package com.example.webdemo.controller;

import com.example.webdemo.dao.User_Dao;
import com.example.webdemo.model.UserInfo;
import com.example.webdemo.model.response.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class UserController {

    @RequestMapping(value = "/login")
    public String login() {
        System.out.println("login web");
        return "login";
    }

    @RequestMapping(value = "/login.action")
    public String checkLogin(String name, String pwd) throws SQLException {
        System.out.println("输入：" + name + " " + pwd);

        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name, pwd);

        if (u != null && (u.getUser_password()).equals(pwd)) {
            System.out.println(name + " 登录成功");
            return "Success";
        } else {
            System.out.println(name + " 登录失败");
            return "login";
        }
    }

    @RequestMapping(value = "/register")
    public String register() {
        System.out.println("register web");
        return "register";
    }

    @RequestMapping(value = "/register.action")
    public String checkRegister(String name, String pwd) {
        User_Dao userDao = new User_Dao();
        userDao.insert(name, pwd);
        System.out.println(name + " 注册成功");
        return "Success";
    }

    @RequestMapping(value = "/renew")
    public String renew() {
        System.out.println("renew web");
        return "renew";
    }

    @RequestMapping(value = "/renew.action")
    public String checkRenew(String name, String pwd) {

        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name, pwd);

        if (u != null) {
            d.renew(name, pwd);
            System.out.println(name + " 修改成功");
            return "Success";
        } else {
            System.out.println(name + " 修改失败");
            return "renew";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete() {
        System.out.println("delete web");
        return "delete";
    }

    @RequestMapping(value = "/delete.action")
    public String checkDelete(int id) {

        User_Dao d = new User_Dao();
        UserInfo u = d.findOneById(id);

        if (u != null) {
            d.delete(id);
            return "Success";
        } else {
            return "delete";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/loginForApp")
    public BaseResponse<UserInfo> loginForApp(String name, String pwd) {
        BaseResponse<UserInfo> response = new BaseResponse<>();
        User_Dao d = new User_Dao();
        UserInfo u = d.find_one(name, pwd);
        response.data = u;
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/registerForApp")
    public BaseResponse<Object> registerForApp(String name, String pwd, String nickName) {
        BaseResponse<Object> response = new BaseResponse<>();
        User_Dao d = new User_Dao();
        if (d.findOneByName(name) != null) {
            response.message = "用户名已被注册！";
        } else {
            d.insert(name, pwd, nickName);
        }
        return response;
    }

}
