package com.lznby.baidumapdemo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 网络状态变化接收者类
 */
public class NetworkChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiinfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(!networkInfo.isConnected()&&!wifiinfo.isConnected()){

            Toast.makeText(context,"网络不可用!",Toast.LENGTH_SHORT).show();

        }else{
            if(wifiinfo.isConnected()){

                Toast.makeText(context,"wifi连接中!",Toast.LENGTH_SHORT).show();

            }
            if(networkInfo.isConnected()){

                Toast.makeText(context,"流量连接中!",Toast.LENGTH_SHORT).show();

            }
        }

    }
}