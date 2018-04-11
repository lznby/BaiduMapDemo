package com.lznby.baidumapdemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.util.HydrantAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 清单形式显示
 */

public class ListCardActivity extends AppCompatActivity {

    private List<Hydrant> mHydrantList = new ArrayList<>();
    private HydrantAdapter adapter;
    //下拉刷新控件
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);

        //以toolbar代替ActivityBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //水果
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
                refreshHydrants();
            }
        });

    }


    //下拉刷新线程,未完成网络部分功能
    private void refreshHydrants(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initHydrant();//重新初始化Adapter中的水果信息
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);//刷新事件结束隐藏刷新进度条
                    }
                });
            }
        }).start();
    }



    //获得数据库中所有Hydrant信息
    private void initHydrant(){
        //mHydrantList.clear();
        mHydrantList = DataSupport.findAll(Hydrant.class);
    }
}
