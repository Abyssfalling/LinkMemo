package com.example.webdemo.model;

import org.apache.catalina.User;

public class AssociationInfo {

    private int id;

    private int uid;

    private int lid;

    /**
     * type : 1：近义词, 2：反义词, 3：关联词
     */
    private int type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
