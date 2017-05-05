package com.shuivy.happylendandreadbooks.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * 项目名：HappyBooks
 * 包名：com.shuivy.happylendandreadbooks.util
 * 文件名：BitmapToBase64
 * 创建者：陈贤煜
 * 创建时间：2017/4/17 22:32
 * 描述：TODO
 */

public class BitmapToBase64 {
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
