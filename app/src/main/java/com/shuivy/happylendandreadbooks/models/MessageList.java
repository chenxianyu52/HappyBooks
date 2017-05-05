package com.shuivy.happylendandreadbooks.models;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.models
 * 文件名：MessageList
 * 创建者：陈贤煜
 * 创建时间：2017/4/18 23:26
 * 描述：TODO
 */

public class MessageList extends BmobObject{
    private String userName;
    private String messageId;
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
