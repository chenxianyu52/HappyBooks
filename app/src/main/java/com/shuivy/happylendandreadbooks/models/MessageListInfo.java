package com.shuivy.happylendandreadbooks.models;

import java.util.Date;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.models
 * 文件名：MessageListInfo
 * 创建者：陈贤煜
 * 创建时间：2017/4/21 10:43
 * 描述：TODO
 */

public class MessageListInfo {
    private String userName;
    private String content;
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
