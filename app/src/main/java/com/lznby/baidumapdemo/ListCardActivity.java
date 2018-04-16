package com.lznby.baidumapdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.network.RequestInformation;
import com.lznby.baidumapdemo.util.HydrantAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 清单形式显示
 */

public class ListCardActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Hydrant> mHydrantList = new ArrayList<>();

    private HydrantAdapter adapter;

    //下拉刷新控件
    private SwipeRefreshLayout swipeRefresh;
    private FloatingActionButton mCardListFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);

        //设置点击监听
        mCardListFAB = (FloatingActionButton) findViewById(R.id.card_list_fab);
        mCardListFAB.setOnClickListener(this);

        //Hydrant信息刷新
        initHydrant();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HydrantAdapter(mHydrantList);
        recyclerView.setAdapter(adapter);

        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);//下拉刷新进度的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //网上申请最新数据
                mHydrantList.clear();
                RequestInformation.requestHydrantInformation(URL.HYDRANT_INFORMATION_JSON_URL,ListCardActivity.this,null,URL.GET);
                initHydrant();//重新初始化Adapter中的信息
                refreshHydrants();
            }
        });


    }


    //下拉刷新线程
    private void refreshHydrants(){
        //请求最新的Hydrant数据
/*        RequestInformation.requestHydrantInformation(URL.HYDRANT_INFORMATION_JSON_URL,this);
        initHydrant();//重新初始化Adapter中的信息*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                Log.d("DrawAllMark", "run: ui");
                swipeRefresh.setRefreshing(false);//刷新事件结束隐藏刷新进度条

            }
        });

    }



    //获得数据库中所有Hydrant信息
    private void initHydrant(){
        List<Hydrant> hydrants = DataSupport.findAll(Hydrant.class);
        mHydrantList = hydrants;
        Log.d("DrawAllMark", "sql: ui");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_list_fab:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
