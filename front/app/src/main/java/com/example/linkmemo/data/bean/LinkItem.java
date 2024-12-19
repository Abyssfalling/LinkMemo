package com.example.linkmemo.data.bean;

public class LinkItem {


        private Point linkPoint;

        private int centerPointId;

        private int type;

        private int id;

        public Point getLinkPoint() {
            return linkPoint;
        }

        public void setLinkPoint(Point linkPoint) {
            this.linkPoint = linkPoint;
        }

        public int getCenterPointId() {
            return centerPointId;
        }

        public void setCenterPointId(int centerPointId) {
            this.centerPointId = centerPointId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
}
