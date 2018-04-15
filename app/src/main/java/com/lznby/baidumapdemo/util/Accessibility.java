package com.lznby.baidumapdemo.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请
 */
public class Accessibility {

    /**
     * 权限申请
     * @param context 对应的上下文
     * @param activity 对应的活动
     */
    public static void getPermission(Context context, Activity activity) {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(activity, permissions, 1);
        } else {
            //权限申请成功事件
            /*requestLocation();*/
        }
    }

    /**
     * 权限结果反馈提示
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Activity activity,int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(activity, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            activity.finish();
                            return;
                        }
                    }
                    //权限请求成功事件
                    /*requestLocation();*/
                } else {
                    Toast.makeText(activity, "发生未知错误", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                break;
            default:
        }
    }
}
