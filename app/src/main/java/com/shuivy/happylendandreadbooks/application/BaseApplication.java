package com.shuivy.happylendandreadbooks.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


import cn.bmob.v3.Bmob;

/**
 * 项目名：lvyou
 * 包名：com.example.chenxianyu.lvyou.application
 * 文件名：BaseApplication
 * 创建者：陈贤煜
 * 创建时间：2017/2/18 10:54
 * 描述：Application
 */

public class BaseApplication extends Application {
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(this, "2c7922b4a23d8f686280b8aaa33138b5");
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
