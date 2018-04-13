package com.lznby.baidumapdemo.network;

import com.baidu.mapapi.map.BaiduMap;
import com.github.mikephil.charting.charts.LineChart;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetWorkRequest {
    /**
     * 请求标记信息及标记在地图上
     * @param address 请求标记信息的url
     * @param baiduMap 绘制标记的baiduMap对象
     */
    public static void requestHydrantInformation(final String address, final BaiduMap baiduMap){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleHydrantResponse(responseText,baiduMap);
            }
        });
    }

    /**
     * 请求曲线数据信息并绘制
     * @param address 请求url
     * @param lineChart 绘制chart对象
     */
    public static void requestPressure(final String address, final LineChart lineChart) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handlePressureResponse(responseText,lineChart);

            }
        });
    }

}
