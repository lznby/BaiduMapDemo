package com.lznby.baidumapdemo.util;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

/**
 * 初始化器
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //LitePal初始化
        LitePal.initialize(this);
        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //全局获得context
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
