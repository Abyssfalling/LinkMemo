package com.example.webdemo.model;

public class UserInfo {
    private long user_id;
    private String user_name;
    private String user_password;

    private String user_nickname;

    public UserInfo() {
    }

    public UserInfo(int userId, String userName, String userPasswd) {
        this.setUser_id(userId);
        this.setUser_name(userName);
        this.setUser_password(userPasswd);
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}
