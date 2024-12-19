package com.example.webdemo.model;

public class PointInfo {

    private String point_english;
    private String point_chinese;
    private int point_id;

    public PointInfo() {
    }

    public PointInfo(String point_english, String point_chinese, int point_id) {
        this.point_english = point_english;
        this.point_chinese = point_chinese;
        this.point_id = point_id;
    }

    public String getPoint_english() {
        return point_english;
    }

    public String getPoint_chinese() {
        return point_chinese;
    }

    public int getPoint_id() {
        return point_id;
    }

    @Override
    public String toString() {
        return "PointInfo{" +
                "point_english='" + point_english + '\'' +
                ", point_chinese='" + point_chinese + '\'' +
                ", point_id=" + point_id +
                '}';
    }
}
