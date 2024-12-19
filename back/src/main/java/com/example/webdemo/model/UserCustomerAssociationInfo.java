package com.example.webdemo.model;

/**
 * 用户添加的自定义关联关系
 */
public class UserCustomerAssociationInfo {

    private int id;

    /**
     * 用户id
     */
    private int uid;

    /**
     * 主要单词id
     */
    private int mWid;

    /**
     * 次要单词id
     */
    private int sWid;

    /**
     * 类型
     */
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getmWid() {
        return mWid;
    }

    public void setmWid(int mWid) {
        this.mWid = mWid;
    }

    public int getsWid() {
        return sWid;
    }

    public void setsWid(int sWid) {
        this.sWid = sWid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

