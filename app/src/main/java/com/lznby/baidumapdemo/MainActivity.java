package com.lznby.baidumapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.lznby.baidumapdemo.util.NetworkChange;
import com.lznby.baidumapdemo.util.Tools;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MapView mapView;

    private BaiduMap baiduMap;

    private RelativeLayout mMarkShowRL;

    private TextView mMarkAddressTV;

    private TextView mMarkStatusTV;

    private TextView mMarkPressureTV;

    private TextView mMarkTimeTV;

    private TextView mMarkPrincipalNameTV;

    private TextView mMarkPrincicalPhoneTV;

    private Hydrant mHydrant;

    private Button mDetailedInformation;

    private FloatingActionButton mMarkMapFAB;

    private NetworkChange networkChange;

    private SlidingUpPanelLayout mSlidingUpPanelLayout;


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

        //标记缩略信息控件
        mMarkAddressTV = (TextView) findViewById(R.id.mark_address_tv);
        mMarkShowRL = (RelativeLayout) findViewById(R.id.mark_show_rl);
        mMarkStatusTV = (TextView) findViewById(R.id.mark_status_tv);
        mMarkPressureTV = (TextView) findViewById(R.id.mark_pressure_tv);
        mMarkTimeTV = (TextView) findViewById(R.id.mark_time_tv);
        mMarkPrincipalNameTV = (TextView) findViewById(R.id.mark_principal_name_tv);
        mMarkPrincicalPhoneTV = (TextView) findViewById(R.id.mark_principal_phone_tv);
        mDetailedInformation = (Button) findViewById(R.id.detailed_information_bt);
        mMarkMapFAB = (FloatingActionButton) findViewById(R.id.mark_map_fab);
        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        //Button添加点击事件
        mDetailedInformation.setOnClickListener(this);
        mMarkMapFAB.setOnClickListener(this);

        //请求Hydrant信息并进行JSON解析
        RequestInformation.requestHydrantInformation(URL.HYDRANT_INFORMATION_JSON_URL,this,null, RequestType.GET);

        //权限申请
        Accessibility.getPermission(MainActivity.this,MainActivity.this);

        //地图加载完成监听事件,设置地图显示的范围及中心点,地图加载完成后添加Mark监听事件
        MapTools.setOnMapLoadedCallback(baiduMap,onMarkerClickListener);

        //地图单击事件
        MapTools.setOnMapClickListener(baiduMap,this,mMarkShowRL,mSlidingUpPanelLayout);

        //监听网络状态
        //NetworkChange.addNetworkChangeReciver(networkChange);
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

            mMarkShowRL.setVisibility(View.VISIBLE);

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
                mMarkAddressTV.setText(mHydrant.getAddress());
                mMarkStatusTV.setText(Tools.estimateStatus(mHydrant.getStatus()));
                mMarkPressureTV.setText(mHydrant.getPressure()+"");
                mMarkTimeTV.setText(mHydrant.getTime());
                mMarkPrincipalNameTV.setText(mHydrant.getPrincipal_name());
                mMarkPrincicalPhoneTV.setText(mHydrant.getPrincipal_phone());
                //设置上划块高度
                mSlidingUpPanelLayout.setPanelHeight(400);
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
        switch (v.getId()) {

            case R.id.detailed_information_bt:
                Intent intent = new Intent(MainActivity.this,DetailedActivity.class);
                intent.putExtra("hydrant",mHydrant);
                startActivity(intent);
                break;

            case R.id.mark_map_fab:
                Intent intentList = new Intent(MainActivity.this,ListCardActivity.class);
                startActivity(intentList);
                break;
        }
    }
}