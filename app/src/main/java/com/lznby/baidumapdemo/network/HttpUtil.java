package com.lznby.baidumapdemo.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * okthhp请求头
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback, RequestBody requestBody, String flog){
        OkHttpClient client = new OkHttpClient();
        Request request;
        if ("POST".equals(flog)){
            request = new Request.Builder().url(address).post(requestBody).build();
        } else {
            request = new Request.Builder().url(address).build();
        }

        client.newCall(request).enqueue(callback);
    }
}
