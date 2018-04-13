package com.lznby.baidumapdemo.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lznby.baidumapdemo.MainActivity;
import com.lznby.baidumapdemo.R;
import com.lznby.baidumapdemo.util.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JIGUANG";

    // RegistrationID 定义
    // 集成了 JPush SDK 的应用程序在第一次成功注册到 JPush 服务器时，
    // JPush 服务器会给客户端返回一个唯一的该设备的标识 - RegistrationID。
    // JPush SDK 会以广播的形式发送 RegistrationID 到应用程序。
    // 应用程序可以把此 RegistrationID 保存以自己的应用服务器上，
    // 然后就可以根据 RegistrationID 来向设备推送消息或者通知。
    public static String regId;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            Bundle bundle = intent.getExtras();
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));


                Intent intent2 = new Intent(MyApplication.getContext(),MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(MyApplication.getContext(),0,intent2,0);
                //获取通知管理器
                NotificationManager manager = (NotificationManager)MyApplication.getContext().getSystemService(NOTIFICATION_SERVICE);
                //创建一个通知对象
                Notification notification =  new NotificationCompat.Builder(MyApplication.getContext())
                        .setContentTitle("this is title")//设置通知标题
                        .setContentText("this is content textthis is content textthis is content textthis is content textthis is content textthis is content textthis is content text")//设置通知内容
                        .setWhen(System.currentTimeMillis())//设置通知显示的延迟时间
                        .setSmallIcon(R.mipmap.ic_launcher)//设置通知小图标
                        .setContentIntent(pi)//添加启动另一个活动的意图
                        .setAutoCancel(true)//设置点击通知后，通知是否会消失，true小时，false不消失
                        .setSound(Uri.fromFile(new File("/system/media/audio/audio/ringtones/Luna.ogg")))//设置通知声音
                        .setVibrate(new long[]{1000,1000,1000,1000})//设置消息震动，出入的为一个long的数组，偶数位为不震动的时间，奇数位为震动时间
                        .setLights(Color.GREEN,1000,1000)
                        .setPriority(NotificationCompat.PRIORITY_MAX)//设置通知优先级为最高
                        //.setStyle(new NotificationCompat.BigTextStyle().bigText("这是一个非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常非常长的标题！"))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(MyApplication.getContext().getResources(),R.drawable.hydrant)))//通知中显示大图片
                        .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(),R.mipmap.ic_launcher)).build();//设置通知大图标
                //启动通知
                manager.notify(1,notification);





                // 对应极光后台的 - 自定义消息  默认不会出现在notification上 所以一般都选用发送通知
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context,MainActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        }catch (Exception e){

        }


    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {

        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


}