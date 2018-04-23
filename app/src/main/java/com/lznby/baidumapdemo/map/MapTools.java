package com.lznby.baidumapdemo.map;

import android.app.Activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * 修改默认百度地图的View
 */
public class MapTools {

    /**
     * 设置百度地图logo、缩放按钮及比例为隐藏状态
     * @param mapView
     */
    public static void changeDefaultBaiduMapView(MapView mapView) {
        //设置隐藏缩放和扩大的百度地图的默认的比例按钮
        mapView.showZoomControls(false
        );
        mapView.showScaleControl(false);
        mapView.removeViewAt(1);//最后移除默认百度地图的logo View
    }


    /**
     * 地图加载完成监听事件,设置地图显示的范围及中心点,地图加载完成后添加Mark监听事件
     * @param baiduMap 设置监听的baiduMap对象
     * @param onMarkerClickListener mark的点击事件监听
     */
    public static void setOnMapLoadedCallback(final BaiduMap baiduMap, final BaiduMap.OnMarkerClickListener onMarkerClickListener) {
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
    }


    /**
     * 地图单击事件
     * @param baiduMap 设置回调的BaiduMap对象
     * @param activity 当前activity
     * @param mSlidingUpPanelLayout 设置SlidingUpPanelLayout隐藏
     */
    public static void setOnMapClickListener (BaiduMap baiduMap, final Activity activity, final SlidingUpPanelLayout mSlidingUpPanelLayout) {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            /**
             * 地图单击事件回调函数
             * @param latLng 点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng latLng) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //隐藏SlidingUpPanelLayout
                        mSlidingUpPanelLayout.setPanelHeight(0);
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
}
