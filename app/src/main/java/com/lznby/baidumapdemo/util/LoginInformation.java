package com.lznby.baidumapdemo.util;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 存储登录信息，以xml方式存储
 */
public class LoginInformation {
    /**
     * 存储登录信息
     * @param username 账号
     * @param password 密码
     */
    public static void saveInformation(String username, String password){
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("userInformation",MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }

    /**
     * 获取登录账号
     * @return 登录账号
     */
    public static String getUsername() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("userInformation",MODE_PRIVATE);
        String username = preferences.getString("username","");
        return username;
    }

    /**
     * 获取登录密码
     * @return 登录密码
     */
    public static String getPassword() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("userInformation",MODE_PRIVATE);
        String password = preferences.getString("password","");
        return password;
    }
}
