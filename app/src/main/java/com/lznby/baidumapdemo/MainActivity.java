package com.lznby.baidumapdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.entity.RequestType;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.map.DrawMark;
import com.lznby.baidumapdemo.map.MapTools;
import com.lznby.baidumapdemo.network.RequestInformation;
import com.lznby.baidumapdemo.util.Accessibility;
import com.lznby.baidumapdemo.util.MusicService;
import com.lznby.baidumapdemo.util.NetworkChange;
import com.lznby.baidumapdemo.util.Tools;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MapView mapView;

    private BaiduMap baiduMap;

    private Hydrant mHydrant;

    private NetworkChange networkChange;

    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private Button mDisplayList;

    private TextView mMainHydrantIdTV;

    private TextView mMainPressureTV;

    private TextView mMainStatusTV;

    private Button mMainCancelAlarmBT;

    private TextView mMainAddressTV;

    private TextView mMainLongitudeLatitudeTV;

    private TextView mMainAreaNodeIdTV;

    private TextView mMainPrincipalNameTV;

    private Button mMainPrincipalPhoneBT;

    private TextView mMainFireControlTV;

    private Button mMainFireControlPhoneBT;

    private TextView mMainCheckpointTypeTV;

    private Button mMainCheckpointPhoneBT;

    private TextView mMainDescriptionTV;

    private TextView mMainTimeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main2);

        //百度地图控件
        mapView = (MapView) findViewById(R.id.bmapView);

        //设置隐藏缩放和扩大的百度地图的默认的比例按钮
        MapTools.changeDefaultBaiduMapView(mapView);

        baiduMap = mapView.getMap();

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mDisplayList = (Button) findViewById(R.id.display_format_list);


        //消防栓详细信息控件
        mMainHydrantIdTV = (TextView) findViewById(R.id.main_hydrant_id);
        mMainPressureTV = (TextView) findViewById(R.id.main_pressure);
        mMainStatusTV = (TextView) findViewById(R.id.main_status);
        mMainCancelAlarmBT = (Button) findViewById(R.id.main_cancel_alarm);
        mMainAddressTV = (TextView) findViewById(R.id.main_address);
        mMainLongitudeLatitudeTV = (TextView) findViewById(R.id.main_longitude_latitude);
        mMainAreaNodeIdTV = (TextView) findViewById(R.id.main_area_node_id);
        mMainPrincipalNameTV = (TextView) findViewById(R.id.main_principal_name);
        mMainPrincipalPhoneBT = (Button) findViewById(R.id.main_principal_phone);
        mMainFireControlTV = (TextView) findViewById(R.id.main_fire_control);
        mMainFireControlPhoneBT = (Button) findViewById(R.id.main_fire_control_phone);
        mMainCheckpointTypeTV = (TextView) findViewById(R.id.main_checkpoint_type);
        mMainCheckpointPhoneBT = (Button) findViewById(R.id.main_checkpoint_phone);
        mMainDescriptionTV = (TextView) findViewById(R.id.main_description);
        mMainTimeTV = (TextView) findViewById(R.id.main_time);

        //Button添加点击事件
        mDisplayList.setOnClickListener(this);
        mMainCancelAlarmBT.setOnClickListener(this);
        mMainPrincipalPhoneBT.setOnClickListener(this);
        mMainFireControlPhoneBT.setOnClickListener(this);
        mMainCheckpointPhoneBT.setOnClickListener(this);

        //请求Hydrant信息并进行JSON解析
        RequestInformation.requestHydrantInformation(URL.HYDRANT_INFORMATION_JSON_URL,this,null, RequestType.GET);

        //权限申请
        Accessibility.getPermission(MainActivity.this,MainActivity.this);

        //地图加载完成监听事件,设置地图显示的范围及中心点,地图加载完成后添加Mark监听事件
        MapTools.setOnMapLoadedCallback(baiduMap,onMarkerClickListener);

        //地图单击事件
        MapTools.setOnMapClickListener(baiduMap,this,mSlidingUpPanelLayout);

        //监听网络状态
        NetworkChange.addNetworkChangeReciver(networkChange);
    }


    //请求权限结果反馈，及错误提示
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //请求结果反馈
        Accessibility.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }


    /**
     * 设置Mark自定义监听事件
     */
    BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {

            //获取坐标的经纬度用于查询对应坐标
            double longitude = marker.getPosition().longitude;//经度
            double latitude = marker.getPosition().latitude;//纬度
            getPoint(longitude,latitude);
            return false;
        }
    };


    /**
     * 设置点击Mark后UI变化事件
     * @param longitude
     * @param latitude
     */
    public void getPoint(double longitude, double latitude){
        final List<Hydrant> hydrantList = DataSupport.where("longitude like ? and latitude like ?", longitude+"",latitude+"").find(Hydrant.class);
        final Hydrant hydrant = hydrantList.get(0);
        mHydrant = hydrant;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //设置上划块高度
                mSlidingUpPanelLayout.setPanelHeight(350);

                //上划块概述信息
                mMainHydrantIdTV.setText("消防栓编号:" + mHydrant.getHydrant_id());
                mMainPressureTV.setText("水压:"+mHydrant.getPressure() + " | ");
                mMainStatusTV.setText(Tools.estimateStatus(mHydrant.getStatus()));
                if (mHydrant.getStatus() != 0) {
                    mMainStatusTV.setTextColor(Color.RED);
                } else {
                    mMainStatusTV.setTextColor(Color.BLACK);
                }

                //上划块位置信息
                mMainAddressTV.setText(mHydrant.getAddress());
                mMainLongitudeLatitudeTV.setText("经度:"+mHydrant.getLongitude() + " | " + "纬度:" + mHydrant.getLatitude());
                mMainAreaNodeIdTV.setText("区域号:" + mHydrant.getArea_id() + " | 节点号:" + mHydrant.getNode_id());

                //上划块负责人信息
                mMainPrincipalNameTV.setText("姓名:" + mHydrant.getPrincipal_name());
                mMainPrincipalPhoneBT.setText(mHydrant.getPrincipal_phone());

                //上划块其他信息
                mMainFireControlTV.setText("消防中心:" + mHydrant.getFire_control());
                mMainFireControlPhoneBT.setText(mHydrant.getFire_control_phone());
                mMainCheckpointTypeTV.setText("检测点类型:" + mHydrant.getCheckpoint_type());
                mMainCheckpointPhoneBT.setText(mHydrant.getCheckpoint_phone());

                //上划块备注信息
                mMainDescriptionTV.setText(mHydrant.getDescription());

                //上划块更新时间
                mMainTimeTV.setText("更新时间 " + mHydrant.getTime());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //绘制所有点,解决了安装程序后，第一次无法绘制mark标记
        baiduMap.clear();
        DrawMark.drawAllMark(baiduMap);
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
        //解除网络状态监听
//        unregisterReceiver(networkChange);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        //跳转到列表形式
        Intent intentList = new Intent(MainActivity.this,ListCardActivity.class);
        switch (v.getId()) {

            case R.id.display_format_list:
                startActivity(intentList);
                break;
            case R.id.main_cancel_alarm:
                //取消警报
                if (MusicService.mediaPlayer.isPlaying() && !MusicService.mediaPlayer.equals(null)) {
                    MusicService.mediaPlayer.stop();
                    try {
                        MusicService.mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.main_principal_phone:
                //拨打负责人电话
                Tools.call(mHydrant.getPrincipal_phone(),this);
                break;
            case R.id.main_fire_control_phone:
                //拨打消防中心电话
                Tools.call(mHydrant.getFire_control_phone(),this);
                break;
            case R.id.main_checkpoint_phone:
                //拨打检测点电话
                Tools.call(mHydrant.getCheckpoint_phone(),this);
                break;
        }
    }
}