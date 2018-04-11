package com.lznby.baidumapdemo.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * okthhp请求头
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
