package com.shuivy.happylendandreadbooks.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.models
 * 文件名：BookInfoList
 * 创建者：陈贤煜
 * 创建时间：2017/4/17 11:17
 * 描述：TODO
 */

public class BookInfoList implements Serializable{
    String title;
    Bitmap img;


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    String des;

    private double latitude; //发布用户纬度

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private double longitude;//发布用户经度

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    String objectId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType;
    }

    long time;
    String location;
    String publishType;
}
