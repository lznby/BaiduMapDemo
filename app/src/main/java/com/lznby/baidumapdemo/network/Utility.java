package com.lznby.baidumapdemo.network;

import android.text.TextUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lznby.baidumapdemo.entity.Hydrant;
import com.lznby.baidumapdemo.map.DrawMark;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response, BaiduMap baiduMap){
        if(!TextUtils.isEmpty(response)){
            try {
                //使用JSONObject方式解析JSON文件
                JSONArray allHydrant = new JSONArray(response);

                //如果数据库中不为空，删除数据库中所有信息
                DataSupport.deleteAll(Hydrant.class, "hydrant_id > ?" , "0");

                    for (int i = 0; i < allHydrant.length(); i++) {
                        JSONObject jsonObject = allHydrant.getJSONObject(i);
                        //存储解析完成的Hydrant数据到数据库
                        Hydrant hydrant = new Hydrant();
                        hydrant.setHydrant_id(jsonObject.getLong("hydrant_id"));
                        hydrant.setArea_id(jsonObject.getLong("area_id"));
                        hydrant.setNode_id(jsonObject.getLong("node_id"));
                        hydrant.setAddress(jsonObject.getString("address"));
                        hydrant.setCheckpoint_type(jsonObject.getString("checkpoint_type"));
                        hydrant.setCheckpoint_phone(jsonObject.getString("checkpoint_phone"));
                        hydrant.setPressure(jsonObject.getDouble("pressure"));
                        hydrant.setLongitude(jsonObject.getDouble("longitude"));
                        hydrant.setLatitude(jsonObject.getDouble("latitude"));
                        hydrant.setStatus(jsonObject.getInt("status"));
                        hydrant.setPrincipal_name(jsonObject.getString("principal_name"));
                        hydrant.setPrincipal_phone(jsonObject.getString("principal_phone"));
                        hydrant.setFire_control(jsonObject.getString("fire_control"));
                        hydrant.setFire_control_phone(jsonObject.getString("fire_control_phone"));
                        hydrant.setDescription(jsonObject.getString("description"));
                        hydrant.setImg_url(jsonObject.getString("img_url"));
                        hydrant.setTime(jsonObject.getString("time"));
                        hydrant.save();
                    }

                Gson gson = new Gson();
                List<Hydrant> hydrantList = gson.fromJson(response,new TypeToken<List<Hydrant>>(){}.getType());

                //转化为数组,并在地图上进行标识。
                Hydrant[] hydrants  = new Hydrant[hydrantList.size()];
                hydrantList.toArray(hydrants);
                boolean flag = false;
                for (int i=0; i<hydrants.length; i++) {

                    if (hydrants[i].getStatus() == 1 || hydrants[i].getStatus() == 2){
                        flag = true;
                    } else {
                        flag =false;
                    }
                    DrawMark.drawMark(baiduMap,new LatLng(hydrants[i].getLatitude(),hydrants[i].getLongitude()),flag);
                }



                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
