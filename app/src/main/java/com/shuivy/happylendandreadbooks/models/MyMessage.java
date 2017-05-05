package com.shuivy.happylendandreadbooks.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.shuivy.happylendandreadbooks.database.MyDataBaseHelper;

import cn.bmob.v3.BmobObject;

/**
 * Created by stk on 2016/8/7 0007.
 */
public class MyMessage extends BmobObject{

    private String guestName;
    private String guestCode;
    private String guestToName;
    private String guestToCode;
    private String content;

    public String getGuestToName() {
        return guestToName;
    }

    public void setGuestToName(String guestToName) {
        this.guestToName = guestToName;
    }

    public String getGuestToCode() {
        return guestToCode;
    }

    public void setGuestToCode(String guestToCode) {
        this.guestToCode = guestToCode;
    }



    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestCode() {
        return guestCode;
    }

    public void setGuestCode(String guestCode) {
        this.guestCode = guestCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


//    public Boolean save(Context context) {
//        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(context);
//        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
//        db.execSQL("INSERT INTO message " +
//                "(guest_name,guest_code,type,content,date)" +
//                "VALUES(" +
//                "'" + this.getGuestName() + "','" + this.getGuestCode() + "','" +
//                this.getType() + "','" + this.getContent() + "','" + this.date + "')");
//        return true;
//    }
}
