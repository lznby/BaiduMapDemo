package com.lznby.baidumapdemo.network;

import android.app.Activity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求
 */
public class RequestInformation {

    /**
     * 请求标记信息及标记在地图上
     * @param address 请求标记信息的url
     * @param activity 当前activity
     */
    public static void requestHydrantInformation(final String address, final Activity activity, RequestBody requestBody, String flog){
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"网络请求失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleHydrantResponse(responseText);
            }
        }, requestBody, flog);
    }


    /**
     * 请求曲线数据信息并绘制
     * @param address 请求url
     * @param lineChart 绘制chart对象
     * @param activity 当前activity
     */
    public static void requestPressure(final String address, final LineChart lineChart, final Activity activity, RequestBody requestBody, String flog) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"网络请求失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handlePressureResponse(responseText,lineChart);
            }
        },requestBody, flog);
    }


    /**
     * 登录post请求
     * @param address 请求地址
     * @param activity 当前activity
     * @param requestBody post请求内容
     * @param flog 判断请求类型
     */
    public static void requestLogin(final String address, final Activity activity, final RequestBody requestBody, String flog) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"网络请求失败！", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleLoginResponse(responseText,activity);
            }
        }, requestBody, flog);
    }

}
