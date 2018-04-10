package com.lznby.baidumapdemo.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lznby.baidumapdemo.json.Hydrant;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {
    //okhttp方式
    public static void sendRequestWithOkHttp(){
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
                    parseJSONWithGSON(responseData);//GSON解析JSON

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //GSON解析JSON
    private static void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        List<Hydrant> testList = gson.fromJson(jsonData,new TypeToken<List<Hydrant>>(){}.getType());
        for(Hydrant hydrant : testList){

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

        }
    }
}
