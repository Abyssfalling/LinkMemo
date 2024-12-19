package com.example.linkmemo.data.bean;

import java.util.List;

public class Link {

    private String point_english;
    private int point_id;
    private List<LinkItem> synonym;

    /**
     * 反义词
     */
    private List<LinkItem> antonym;

    /**
     * 关联词
     */
    private List<LinkItem> relate;
    private String point_chinese;
    public void setPoint_english(String point_english) {
        this.point_english = point_english;
    }
    public String getPoint_english() {
        return point_english;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }
    public int getPoint_id() {
        return point_id;
    }

    public List<LinkItem> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<LinkItem> synonym) {
        this.synonym = synonym;
    }

    public List<LinkItem> getAntonym() {
        return antonym;
    }

    public void setAntonym(List<LinkItem> antonym) {
        this.antonym = antonym;
    }

    public List<LinkItem> getRelate() {
        return relate;
    }

    public void setRelate(List<LinkItem> relate) {
        this.relate = relate;
    }

    public void setPoint_chinese(String point_chinese) {
        this.point_chinese = point_chinese;
    }
    public String getPoint_chinese() {
        return point_chinese;
    }



}
