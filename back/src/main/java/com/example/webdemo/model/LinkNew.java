package com.example.webdemo.model;

/**
 * 新增 新关联关系数据库
 */
public class LinkNew {

    private int id;

    private int centerPointId;

    private int linkPointId;

    private PointInfo linkPoint;

    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCenterPointId() {
        return centerPointId;
    }

    public void setCenterPointId(int centerPointId) {
        this.centerPointId = centerPointId;
    }

    public int getLinkPointId() {
        return linkPointId;
    }

    public void setLinkPointId(int linkPointId) {
        this.linkPointId = linkPointId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PointInfo getLinkPoint() {
        return linkPoint;
    }

    public void setLinkPoint(PointInfo linkPoint) {
        this.linkPoint = linkPoint;
    }
}
