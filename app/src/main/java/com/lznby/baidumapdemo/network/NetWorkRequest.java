package com.lznby.baidumapdemo.network;

import com.baidu.mapapi.map.BaiduMap;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetWorkRequest {
    public static void request(final String address, final BaiduMap baiduMap){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleProvinceResponse(responseText,baiduMap);
            }
        });
    }

}
