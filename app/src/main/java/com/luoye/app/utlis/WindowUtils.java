package com.luoye.app.utlis;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：窗口工具类
 */
public class WindowUtils {

    private final WindowManager windowManager;
    private final WindowManager.LayoutParams layoutParams;
    private View view;
    private Context context;
    private int activityInfo = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    private int winWidth, winHeight;


    public WindowUtils(Context context) {
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = Build.VERSION.SDK_INT >= 26 ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;
        winWidth = context.getResources().getDisplayMetrics().widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        winHeight = displayMetrics.heightPixels;
        initLayoutParams();
    }


    /**
     * 更新LayoutParams
     *
     */
    public void initLayoutParams() {
        layoutParams.screenOrientation = activityInfo;  //方向
        layoutParams.format = PixelFormat.RGBA_8888;   //窗口透明
        layoutParams.gravity = Gravity.START | Gravity.TOP;    //位置
        layoutParams.width = winWidth;//窗口宽
        layoutParams.height = winHeight;//窗口高
        layoutParams.flags = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |//全屏显示
                WindowManager.LayoutParams.MEMORY_TYPE_CHANGED |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |//允许窗口扩展到屏幕之外
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;//硬件加速
    }


    /**
     * @param activityInfo 设置方向
     * 强制横屏 ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
     * 强制竖屏 ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
     * 根据传感器旋转 ActivityInfo.SCREEN_ORIENTATION_SENSOR
     * 根据传感器横屏旋转 ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
     * 旋转后界面不变  ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
     * 跟随系统 ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
     */
    public void setActivityInfo(int activityInfo) {
        this.activityInfo = activityInfo;
        initLayoutParams();
    }

    public void addView(View view) {
        if (this.view != null) windowManager.removeViewImmediate(this.view);
        windowManager.addView(view, layoutParams);
        this.view = view;
    }

    public void removeViewImmediate() {
        if (view != null) windowManager.removeViewImmediate(view);
    }


}
