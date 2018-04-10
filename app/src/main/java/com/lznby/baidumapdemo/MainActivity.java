package com.lznby.baidumapdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.lznby.baidumapdemo.map.DrawMark;
import com.lznby.baidumapdemo.network.Util;
import com.lznby.baidumapdemo.util.Accessibility;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    private BaiduMap baiduMap;

    private TextView mMarkNameTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        SDKInitializer.initialize(getApplicationContext());

        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        setContentView(R.layout.activity_main);

        //百度地图控件
        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();

        //JSON解析测试
        Util.sendRequestWithOkHttp();

        //权限申请
        Accessibility.getPermission(MainActivity.this,MainActivity.this);


        /**
         * 绘制多个Mark标记及设置监听
         */

        //设置监听事件
        BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MainActivity.this, marker.getId().toString() + "", Toast.LENGTH_SHORT).show();
                mMarkNameTV = (TextView) findViewById(R.id.mark_name_tv);//为什么写在onCreate方法中不行，无法获取到值
                mMarkNameTV.setVisibility(View.VISIBLE);
                return false;
            }
        };

        //绘制标记
        DrawMark.drawMarks(baiduMap);

        //为自定义Mark添加监听
        baiduMap.setOnMarkerClickListener(onMarkerClickListener);


        /**
         * 地图加载完成监听事件
         */
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //LatLng ll = new LatLng(29.86, 121.59);
                LatLng ll = new LatLng(29.86, 121.59);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 12f);//设置缩放大小
                baiduMap.animateMapStatus(update);
            }
        });

    }

    /**
     * 请求权限结果反馈，及错误提示
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    //权限请求成功事件
                    /*requestLocation();*/
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}