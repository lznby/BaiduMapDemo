package com.lznby.baidumapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.entity.MessageEvent;
import com.lznby.baidumapdemo.entity.RequestType;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.network.RequestInformation;
import com.lznby.baidumapdemo.util.HydrantAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 清单形式显示
 */

public class ListCardActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Hydrant> mHydrantList = new ArrayList<>();

    private HydrantAdapter mBaseAdapter;


    //下拉刷新控件
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView mRecyclerView;
    private Button mDisplayMap;
    private Button mDisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);


        EventBus.getDefault().register(this);


        //设置点击监听
        mDisplayMap = (Button) findViewById(R.id.display_format_map);
        mDisplayList = (Button) findViewById(R.id.display_format_list);
        mDisplayMap.setOnClickListener(this);

        //设置切换模式按钮颜色
        mDisplayMap.setBackgroundResource(R.color.colorLightBlue);
        mDisplayList.setBackgroundResource(R.color.logButtonGray);

        //Hydrant信息刷新
        initHydrant();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(layoutManager);

        //为adapter设置绑定RecyclerView子布局
        mBaseAdapter = new HydrantAdapter(R.layout.hydrant_card_item);
        //为RecyclerView布局设置adapter
        mRecyclerView.setAdapter(mBaseAdapter);
        //设置设置初次加载的值
        mBaseAdapter.setNewData(mHydrantList);

        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorLightBlue);//下拉刷新进度的颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //网上申请最新数据
                RequestInformation.requestHydrantInformation(URL.HYDRANT_INFORMATION_JSON_URL,ListCardActivity.this,null, RequestType.GET);
            }
        });

        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ListCardActivity.this,DetailedActivity.class);
                intent.putExtra("hydrant",mHydrantList.get(position));
                startActivity(intent);
            }
        });

    }


    //获得数据库中所有Hydrant信息
    private void initHydrant(){
        List<Hydrant> hydrants = DataSupport.findAll(Hydrant.class);
        mHydrantList = hydrants;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.display_format_map:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        mHydrantList= event.getHydrantList();//
        mBaseAdapter.setNewData(mHydrantList);//更新adapter数据
        swipeRefresh.setRefreshing(false);//刷新事件结束隐藏刷新进度条
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除eventBus
    }
}
