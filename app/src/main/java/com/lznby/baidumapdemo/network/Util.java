package com.lznby.baidumapdemo.network;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lznby.baidumapdemo.json.Hydrant;
import com.lznby.baidumapdemo.map.DrawMark;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {

    //okhttp方式
    public static void sendRequestWithOkHttp(final BaiduMap baiduMap){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://39.108.138.218/hydrant.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData,baiduMap);//GSON解析JSON

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //GSON解析JSON
    private static void parseJSONWithGSON(String jsonData,BaiduMap baiduMap){
        Gson gson = new Gson();
        List<Hydrant> hydrantList = gson.fromJson(jsonData,new TypeToken<List<Hydrant>>(){}.getType());

        //转化为数组,并在地图上进行标识。
        Hydrant[] hydrants  = new Hydrant[hydrantList.size()];
        hydrantList.toArray(hydrants);
        boolean flag = false;
        for (int i=0; i<hydrants.length; i++) {

            if (hydrants[i].getStatus() == 1){
                flag = true;
            } else {
                flag =false;
            }
            DrawMark.drawMark(baiduMap,new LatLng(hydrants[i].getLatitude(),hydrants[i].getLongitude()),flag);
        }

/*测试使用*/
/*        for(Hydrant hydrant : hydrantList){

            Log.d("Hydrant","getHydrant_id is " + hydrant.getHydrant_id());
            Log.d("Hydrant","getArea_id is " + hydrant.getArea_id());
            Log.d("Hydrant","getNode_id is " + hydrant.getNode_id());
            Log.d("Hydrant","getAddress is " + hydrant.getAddress());
            Log.d("Hydrant","getCheckpoint_type is " + hydrant.getCheckpoint_type());
            Log.d("Hydrant","getCheckpoint_phone is " + hydrant.getCheckpoint_phone());
            Log.d("Hydrant","getPressure is " + hydrant.getPressure());
            Log.d("Hydrant","getLongitude is " + hydrant.getLongitude());
            Log.d("Hydrant","getLatitude is " + hydrant.getLatitude());
            Log.d("Hydrant","getStatus is " + hydrant.getStatus());
            Log.d("Hydrant","getPrincipal_name is " + hydrant.getPrincipal_name());
            Log.d("Hydrant","getPrincipal_phone is " + hydrant.getPrincipal_phone());
            Log.d("Hydrant","getFire_control is " + hydrant.getFire_control());
            Log.d("Hydrant","getFire_control_phone is " + hydrant.getFire_control_phone());
            Log.d("Hydrant","getDescription is " + hydrant.getDescription());
            Log.d("Hydrant","getImg_url is " + hydrant.getImg_url());
            Log.d("Hydrant","getTime is " + hydrant.getTime());

        }*/

    }
}
