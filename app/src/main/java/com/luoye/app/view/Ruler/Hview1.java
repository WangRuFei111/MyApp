package com.luoye.app.view.Ruler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.luoye.app.R;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

public class Hview1 extends View {

    private float y = 0;
    private float statty;
    private int h;//顶部高度
    private int statusBarHeight; //状态栏高度
    private int screenWidth, screenHeight;

    private Bitmap newBitmap, bitmap;

    private String color = "#FFFFFF";

    private String text = "0.0";
    private WindowManager wm;

    private Canvas canvas;

    private Canvas canvastext;

    private Canvas newcanvas;

    private int sppx9;

    private int left, top, right, bottom;

    private int dppx1, dppx3, dppx14, dppx25, dppx9, dppx55, dppx50;


    public Hview1(Context context) {
        this(context, (AttributeSet) null);
    }


    public Hview1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


        h = dip2px(context, 55);
        sppx9 = sp2px(context, 9);
        dppx1 = dip2px(context, 1);
        dppx3 = dip2px(context, 3);
        dppx14 = dip2px(context, 14);
        dppx9 = dip2px(context, 9);
        dppx25 = dip2px(context, 28);
        dppx55 = dip2px(context, 55);
        dppx50 = dip2px(context, 50);


        //获取窗口管理器
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;


        float mm = (((screenHeight - dppx55 - statusBarHeight) / metrics.xdpi * 25.4f) / (screenHeight - statusBarHeight - dppx55) * 0);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        text = decimalFormat.format(mm / 10);//format 返回的是字符串


    }

    public Hview1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getNewBitmap();
        canvas.drawBitmap(newBitmap, 0, 0, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取点击的xy坐标

                y = event.getRawY();

                //新建一个Rect来获取当前view的坐标位置
                Rect rect = new Rect();
                this.getGlobalVisibleRect(rect);

                //获取状态来的高度
                Rect frame = new Rect();
                ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;

                statty = (rect.top - statusBarHeight);//设置当前view y坐标 需要减去状态栏+其他顶部控件高度


                break;
            case MotionEvent.ACTION_MOVE:

                float c;
                if (statty + (event.getRawY() - y) - dppx55 < 0) {

                    //根据手指移动来算出移动的xy坐标
                    this.setTranslationY(0);
                    c = 0;

                } else if (statty + event.getRawY() - y - dppx55 > screenHeight - statusBarHeight - dppx55 - dppx50) {

                    this.setTranslationY(screenHeight - statusBarHeight - dppx55 - dppx50);
                    c = screenHeight - statusBarHeight - dppx55 - dppx50;

                } else {
                    //根据手指移动来算出移动的xy坐标
                    this.setTranslationY(statty + event.getRawY() - y - dppx55);
                    c = statty + event.getRawY() - y - dppx55;

                }


                //获取屏幕的高度和宽度
                Display display = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                float mm = (((screenHeight - dppx55 - statusBarHeight) / metrics.xdpi * 25.4f) / (screenHeight - statusBarHeight - dppx55) * c);

                DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                text = decimalFormat.format(mm / 10);//format 返回的是字符串
                System.out.println("位置改变" + text);


                invalidate();


                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    private void getNewBitmap() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        newBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(newBitmap);
        canvas.drawBitmap(linebitmap(bitmap), 0, 0, null);
        canvas.drawBitmap(rotatePhotos(textBitmap(bitmap)), bitmap.getWidth() / 2 - dppx3, dppx14, null);

    }

    private Bitmap linebitmap(Bitmap bitmap) {

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#855445"));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(dppx1);
        Bitmap newbitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        newcanvas = new Canvas(newbitmap);
        newcanvas.drawLine(dppx9, dppx25, screenWidth, dppx25, paint);
        newcanvas.drawBitmap(bitmap, 0, dppx9, null);

        return newbitmap;
    }

    private Bitmap textBitmap(Bitmap bitmap) {

        Bitmap textBitmap = Bitmap.createBitmap(bitmap.getWidth() * 2, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvastext = new Canvas(textBitmap);
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);  //画笔
        paint2.setColor(Color.parseColor(color));
        paint2.setTextSize(sppx9);
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        canvastext.drawText(text + "cm", 0, bitmap.getHeight(), paint2);
        return textBitmap;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    //旋转90
    public static Bitmap rotatePhotos(Bitmap bitmap) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.setRotate(90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);//90就是我们需要选择的90度
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }

    //获取虚拟按键的高度
    private static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }

        return result;


    }

    // 检查是否存在虚拟按键栏
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    //判断虚拟按键栏是否重写
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        try {
            Class c = Class.forName("android.os.SystemProperties");
            Method m = c.getDeclaredMethod("get", String.class);
            m.setAccessible(true);
            sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
        } catch (Exception e) {

        }
        return sNavBarOverride;
    }


}