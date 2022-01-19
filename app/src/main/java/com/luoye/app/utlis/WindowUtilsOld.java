package com.luoye.app.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：窗口工具类
 */
public class WindowUtilsOld {
    private static String TAG = "---WindowUtilsOld";
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Context context;
    private View view;
    /*屏幕大小*/
    private float[] screenXY;

    public WindowUtilsOld(Context context) {
        this.context = context;
        this.screenXY = getScreenXY(context);
        initWindow();
        setViewMXY_MAX();
    }

    /*初始化*/
    private void initWindow() {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                //全屏显示
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    }


    /*设置view大小为最大*/
    public void setViewMXY_MAX() {
        layoutParams.width = (int) screenXY[0];
        layoutParams.height = (int) screenXY[1];
    }

    /*设置view大小*/
    public void setViewXY(float x, float y) {
        layoutParams.width = (int) x;
        layoutParams.height = (int) y;
    }

    /*更改位置*/
    private void grid(int x, int y) {
        layoutParams.x = layoutParams.x + x;
        layoutParams.y = layoutParams.y + y;
        //防止滑动到屏幕外
        if (layoutParams.x < 0) layoutParams.x = 0;
        if (layoutParams.y < 0) layoutParams.y = 0;
        float maxX = screenXY[0] - layoutParams.width;
        if (layoutParams.x > maxX) layoutParams.x = (int) maxX;
        float maxY = screenXY[1] - layoutParams.height;
        if (layoutParams.y > maxY) layoutParams.y = (int) maxY;
        windowManager.updateViewLayout(view, layoutParams);
    }

    /*显示View*/
    public void showWindow(View view, boolean isMove) {
        stopWindow();
        this.view = view;
        if (isMove) this.view.setOnTouchListener(new FloatingOnTouchListener());
        windowManager.addView(this.view, layoutParams);
        Log.i(TAG, "悬浮窗 宽：：" + layoutParams.width + " 高:" + layoutParams.height);
        Log.i(TAG, "悬浮窗:开启");
    }

    /*关闭View*/
    public void stopWindow() {
        if (windowManager != null && view != null) {
            windowManager.removeViewImmediate(view);
            view = null;
        }
        Log.i(TAG, "悬浮窗view：清除");
    }

    /*dp转成为px)*/
    public float dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /*获取屏幕大小*/
    public static float[] getScreenXY(Context context) {
        float[] xY = new float[2];
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        xY[0] = metrics.widthPixels;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        xY[1] = displayMetrics.heightPixels;
        return xY;
    }

    /*获取view位置*/
    public int[] getWinGridXY() {
        int[] xY = new int[2];
        xY[0] = layoutParams.x;
        xY[1] = layoutParams.y;
        return xY;
    }

    /*设置view位置*/
    public void setGridXY(int x, int y) {
        grid(x, y);
    }

    /*设置view位置*/
    public void setGridXY(int[] xY) {
        grid(xY[0], xY[1]);
    }

    /*悬浮窗移动事件*/
    class FloatingOnTouchListener implements View.OnTouchListener {
        private int startX;
        private int startY;
        private int x = 0;
        private int y = 0;
        private long time;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    float deviationX = startX - event.getRawX();
                    float deviationY = startY - event.getRawY();
                    long timeDifference = System.currentTimeMillis() - time;
                    if (timeDifference > 250) break;
                    if (deviationX < -20) break;
                    if (deviationX > 20) break;
                    if (deviationY < -20) break;
                    if (deviationY > 20) break;
                    if (viewOnClickListener == null) break;
                    viewOnClickListener.click();
                    break;
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    startX = (int) event.getRawX();
                    startY = (int) event.getRawY();
                    time = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    setGridXY(movedX, movedY);
                    x = nowX;
                    y = nowY;
                    break;
            }
            return false;
        }

    }

    public void setViewOnClickListener(ViewOnClickListener viewOnClickListener) {
        this.viewOnClickListener = viewOnClickListener;
    }

    /*单击事件*/
    private ViewOnClickListener viewOnClickListener;

    public interface ViewOnClickListener {
        void click();
    }

}
