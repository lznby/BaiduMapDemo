package com.lznby.baidumapdemo.map;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.lznby.baidumapdemo.R;

import java.util.ArrayList;

/**
 * 地图标记图片及报警动画标识
 */
public class MarkBitmap {

    /**
     * 报警闪烁地图标识
     * @return
     */
    public static ArrayList<BitmapDescriptor> getGifList(){

        // 通过Marker的icons设置一组图片，再通过period设置多少帧刷新一次图片资源
        BitmapDescriptor bitmap_red = BitmapDescriptorFactory
                .fromResource(R.drawable.mark_red);
        BitmapDescriptor bitmap_white = BitmapDescriptorFactory
                .fromResource(R.drawable.mark_white);

        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();

        giflist.add(bitmap_red);
        giflist.add(bitmap_white);
        return giflist;
    }

    /**
     * 正常态地图标识
     * @return
     */
    public static BitmapDescriptor getBitmap(){

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.mark_green);
        return bitmap;
    }
}
