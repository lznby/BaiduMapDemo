package com.lznby.baidumapdemo.util;

import android.app.ProgressDialog;

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
}
