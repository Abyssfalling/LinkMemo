package com.example.webdemo.model;

public class LinkInfo {
    private int link_center;
    private String link_looklike;
    private String link_meanlike;
    private String link_relate;

    public int getLink_center() {
        return link_center;
    }

    public String getLink_looklike() {
        return link_looklike;
    }

    public String getLink_meanlike() {
        return link_meanlike;
    }

    public String getLink_relate() {
        return link_relate;
    }

    public LinkInfo() {
    }

    public LinkInfo(int link_center, String link_looklike, String link_meanlike, String link_relate) {
        this.link_center = link_center;
        this.link_looklike = link_looklike;
        this.link_meanlike = link_meanlike;
        this.link_relate = link_relate;
    }
}
