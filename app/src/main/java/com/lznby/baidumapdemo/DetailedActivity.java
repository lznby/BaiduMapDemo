package com.lznby.baidumapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ImageView mImageView;

    private TextView mHydrantIdTV;

    private Hydrant hydrant;
    private TextView mAreaIdTV;
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
    private TextView mTimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //获得上一个Activity的传值内容
        Intent intent = getIntent();
        hydrant = (Hydrant)getIntent().getSerializableExtra("hydrant");

        //绑定各个控件
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImageView = (ImageView) findViewById(R.id.image_Img);

        //基本信息
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
        mTimeTV = (TextView) findViewById(R.id.time_tv);


        //设置Toolbar的显示与隐藏及标题等
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(hydrant.getTime());

        //加载位置图片
        Glide.with(this).load(hydrant.getImg_url()).into(mImageView);

        //基本信息设置
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


        /**
         * 水压曲线图绘制
         */

        LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }


        XAxis xAxis = mLineChart.getXAxis();//得到X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置, 值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setGranularity(1f);//设置X轴间最小距离

        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        //xAxis.setLabelCount(12, true);//设置X轴的刻度数量

    }


    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
