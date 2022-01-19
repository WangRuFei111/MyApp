package com.luoye.app.utlis;

import android.content.Context;
import android.content.SharedPreferences;

import com.luoye.app.MyApplication;


/**
 * @author licy
 * @description: 设置偏好
 * @date :2020/8/29 8:53
 */

public class PreferenceUtils {

    public static SharedPreferences getAppSp() {
        return MyApplication.getContext().getSharedPreferences("html", Context.MODE_PRIVATE);
    }

    /*获取数据*/
    public static String getData() {
        return getAppSp().getString("data", "");
    }

    /*写入数据*/
    public static void setData(String s) {
        getAppSp().edit().putString("data", s).apply();
    }




}
