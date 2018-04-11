package com.lznby.baidumapdemo.map;

import com.baidu.mapapi.map.MapView;

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
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.removeViewAt(1);//最后移除默认百度地图的logo View
    }
}
