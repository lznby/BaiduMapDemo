package com.lznby.baidumapdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.entity.RequestType;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.network.RequestInformation;
import com.lznby.baidumapdemo.util.MusicService;
import com.lznby.baidumapdemo.util.Tools;

import java.io.IOException;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImageView;

    /*private TextView mHydrantIdTV;*/

    private Hydrant hydrant;

/*    private TextView mAreaIdTV;

    private TextView mNodeIdTV;

    private TextView mAddressTV;

    private TextView mCheckPhointTypeTV;

    private TextView mCheckPointPhoneTV;

    private TextView mPressure;

    private TextView mLongitudeTV;

    private TextView mLatitudeTV;

    private TextView mStatusTV;

    private TextView mPrincipalNameTV;

    private TextView mPrincipalPhoneTV;

    private TextView mFireControl;

    private TextView mFireControlPhoneTV;

    private TextView mDescriptionTV;

    private TextView mTimeTV;*/

    private FloatingActionButton mDetailedInformationFAB;

    private LineChart mLineChart;


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

    private Hydrant mHydrant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //获得上一个Activity的传值内容
        //Intent intent = getIntent();
        hydrant = (Hydrant)getIntent().getSerializableExtra("hydrant");
        mHydrant=hydrant;

        //绑定各个控件
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImageView = (ImageView) findViewById(R.id.image_Img);

/*        //基本信息
        mHydrantIdTV = (TextView) findViewById(R.id.hydrant_id_tv);
        mAreaIdTV = (TextView) findViewById(R.id.area_id_tv);
        mNodeIdTV = (TextView) findViewById(R.id.node_id_tv);
        mAddressTV = (TextView) findViewById(R.id.address_id_tv);
        mCheckPhointTypeTV = (TextView) findViewById(R.id.checkpoint_type_tv);
        mCheckPointPhoneTV = (TextView) findViewById(R.id.checkpoint_phone_tv);
        mPressure = (TextView) findViewById(R.id.pressure_tv);
        mLongitudeTV = (TextView) findViewById(R.id.longitude_tv);
        mLatitudeTV = (TextView) findViewById(R.id.latitude_tv);
        mStatusTV = (TextView) findViewById(R.id.status_tv);
        mPrincipalNameTV = (TextView) findViewById(R.id.principal_name_tv);
        mPrincipalPhoneTV = (TextView) findViewById(R.id.principal_phone_tv);
        mFireControl = (TextView) findViewById(R.id.fire_control_tv);
        mFireControlPhoneTV = (TextView) findViewById(R.id.fire_control_phone_tv);
        mDescriptionTV = (TextView) findViewById(R.id.description_tv);
        mTimeTV = (TextView) findViewById(R.id.time_tv);*/
        mDetailedInformationFAB = (FloatingActionButton) findViewById(R.id.detailed_information_fab);


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

        //设置监听
        mDetailedInformationFAB.setOnClickListener(this);

        mMainCancelAlarmBT.setOnClickListener(this);
        mMainPrincipalPhoneBT.setOnClickListener(this);
        mMainFireControlPhoneBT.setOnClickListener(this);
        mMainCheckpointPhoneBT.setOnClickListener(this);

        //设置Toolbar的显示与隐藏及标题等
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(hydrant.getTime());

        //加载位置图片
        Glide.with(this).load(hydrant.getImg_url()).into(mImageView);

/*        //基本信息设置
        mHydrantIdTV.setText("消防栓编号："+hydrant.getHydrant_id()+"");
        mAreaIdTV.setText("区域号："+hydrant.getArea_id()+"");
        mNodeIdTV.setText("节点号："+hydrant.getNode_id()+"");
        mAddressTV.setText("地址："+hydrant.getAddress()+"");
        mCheckPhointTypeTV.setText("检测点类型："+hydrant.getCheckpoint_type()+"");
        mCheckPointPhoneTV.setText("检测点电话："+hydrant.getCheckpoint_phone()+"");
        mPressure.setText("水压："+hydrant.getPressure()+"");
        mLongitudeTV.setText("经度："+hydrant.getLongitude()+"");
        mLatitudeTV.setText("纬度："+hydrant.getLatitude()+"");
        mStatusTV.setText("水压状态："+Tools.estimateStatus(hydrant.getStatus()));
        mPrincipalNameTV.setText("负责人姓名："+hydrant.getPrincipal_name()+"");
        mPrincipalPhoneTV.setText("负责人电话："+hydrant.getPrincipal_phone()+"");
        mFireControl.setText("消防中心："+hydrant.getFire_control()+"");
        mFireControlPhoneTV.setText("消防中心电话："+hydrant.getPrincipal_phone()+"");
        mDescriptionTV.setText("备注："+hydrant.getDescription()+"");
        mTimeTV.setText("更新时间："+hydrant.getTime()+"");*/


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



        /**
         * 水压曲线图绘制
         */
        mLineChart = (LineChart) findViewById(R.id.lineChart);

        //请求水压数据
        RequestInformation.requestPressure(URL.PRESSURE_INFORMATION_JSON_URL,mLineChart,this,null, RequestType.GET);


    }


    /**
     * 重写home键事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * onClick事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailed_information_fab:
                finish();
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



    @Override
    protected void onResume() {
        super.onResume();
    }
}
