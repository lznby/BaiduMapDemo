package com.lznby.baidumapdemo.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 非重要逻辑处理类
 */
public class Tools {
    /**
     * 将数值型状态，转换为文字形式
     * @param status
     * @return
     */
    public static String estimateStatus(int status){
        if (status==1)
            return "水压过高";
        if (status==2)
            return "水压过低";
        else
            return "水压正常";
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    public static void call(String phone, Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 显示进度对话框
     */
    public static void showProgressDialog(ProgressDialog mProgressDialog) {
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(MyApplication.getContext());
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public static void closeProgressDialog(ProgressDialog mProgressDialog) {
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    /**
     * 获取手机屏幕的高和宽
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    /**
     * 动态设置margin
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
