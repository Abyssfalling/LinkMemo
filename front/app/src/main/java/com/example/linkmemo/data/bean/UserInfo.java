package com.example.linkmemo.data.bean;

public class UserInfo {

    /**
     * id
     **/
    private int user_id;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 密码
     */
    private String user_password;

    /**
     * 昵称
     */
    private String user_nickname;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
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
