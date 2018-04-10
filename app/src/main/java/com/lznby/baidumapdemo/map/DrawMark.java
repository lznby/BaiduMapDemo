package com.lznby.baidumapdemo.map;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DrawMark {

    /**
     * 批量设置多个坐标
     * @param baiduMap
     */
    public static void drawMarks(BaiduMap baiduMap){

        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //设置坐标点
        LatLng point1 = new LatLng(29.82, 121.5);
        LatLng point2 = new LatLng(29.88, 121.62);

        //创建OverlayOptions属性

        OverlayOptions option1 = new MarkerOptions()
                .position(point1)
                .icon(MarkBitmap.getBitmap());
        OverlayOptions option2 = new MarkerOptions()
                .position(point2)
                .icons(MarkBitmap.getGifList()).zIndex(0).period(10);
        //将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        baiduMap.addOverlays(options);
    }

    /**
     * 在地图上绘制单个标记
     * @param baiduMap
     * @param point
     * @param alert true：标识为闪烁状态。false：标识为正常状态
     */
    public static void drawMark(BaiduMap baiduMap, LatLng point, Boolean alert){

        OverlayOptions option;

        if (alert == true) {
            option = new MarkerOptions()
                    .position(point)
                    .icons(MarkBitmap.getGifList());
        } else {
            option = new MarkerOptions()
                    .position(point)
                    .icon(MarkBitmap.getBitmap());
        }

        baiduMap.addOverlay(option);
    }
}

