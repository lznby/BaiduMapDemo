package com.lznby.baidumapdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.map.MapTools;
import com.lznby.baidumapdemo.network.NetWorkRequest;
import com.lznby.baidumapdemo.util.Accessibility;
import com.lznby.baidumapdemo.util.Tools;

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
    private Button mListInformationBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

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
        mListInformationBT = (Button) findViewById(R.id.list_information_bt);

        //Button添加点击事件
        mDetailedInformation.setOnClickListener(this);
        mListInformationBT.setOnClickListener(this);


        //JSON解析测试及绘制标记
        NetWorkRequest.request(URL.HYDRANT_INFORMATION_JSON_URL,baiduMap);

        //权限申请
        Accessibility.getPermission(MainActivity.this,MainActivity.this);


        /**
         * 地图加载完成监听事件,设置地图显示的范围及中心点,地图加载完成后添加Mark监听事件
         */
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //LatLng ll = new LatLng(29.86, 121.59);
                LatLng ll = new LatLng(29.86, 121.59);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 12f);//设置缩放大小
                baiduMap.animateMapStatus(update);

                //为自定义Mark添加监听
                baiduMap.setOnMarkerClickListener(onMarkerClickListener);
            }
        });


        /**
         * 地图单击事件
         */
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            /**
             * 地图单击事件回调函数
             * @param latLng 点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng latLng) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMarkShowRL.setVisibility(View.GONE);
                    }
                });
            }

            /**
             * 地图内 Poi 单击事件回调函数
             * @param mapPoi 点击的 poi 信息
             */
            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
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
        final Hydrant hydrant = hydrantList.remove(0);
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
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailed_information_bt:
                //Toast.makeText(this,"进入详情界面"+Tools.estimateStatus(mHydrant.getStatus()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,DetailedActivity.class);
                intent.putExtra("hydrant",mHydrant);
                startActivity(intent);
                break;
            case R.id.list_information_bt:
                Intent intentList = new Intent(MainActivity.this,ListCardActivity.class);
                startActivity(intentList);
        }
    }
}