package com.example.webdemo.model;

public class UserInfo {
    private int id;
    private String user_name;
    private String user_password;

    public int getId() {
        return id;
    }

    public void setUserId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserPasswd() {
        return user_password;
    }

    public void setUserPasswd(String userPasswd) {
        this.user_password = user_password;
    }

    public UserInfo()
    {
        ;
    }

    public UserInfo(int userId, String userName, String userPasswd)
    {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setUserPasswd(userPasswd);
    }
}
