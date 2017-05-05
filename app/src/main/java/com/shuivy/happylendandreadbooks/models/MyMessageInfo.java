package com.shuivy.happylendandreadbooks.models;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.models
 * 文件名：MyMessageInfo
 * 创建者：陈贤煜
 * 创建时间：2017/4/21 7:40
 * 描述：TODO
 */

public class MyMessageInfo {
    private String objId;
    private String guestToName;
    private String guestToCode;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGuestToCode() {
        return guestToCode;
    }

    public void setGuestToCode(String guestToCode) {
        this.guestToCode = guestToCode;
    }

    public String getGuestToName() {
        return guestToName;
    }

    public void setGuestToName(String guestToName) {
        this.guestToName = guestToName;
    }



    private String content;

}
