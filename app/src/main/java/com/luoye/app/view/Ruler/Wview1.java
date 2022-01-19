package com.luoye.app.view.Ruler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.luoye.app.R;

import java.text.DecimalFormat;

public class Wview1 extends View {


    private float x = 0;//点击时候坐标位置

    private float startx;//自定义view的开始坐标位置
    private int screenWidth, screenHeight;
    private Bitmap bitmap, newBitmap;
    private WindowManager wm;
    private int statusBarHeight;
    private String text = "10";
    private String color = "#FFFFFF";


    private Canvas canvas;

    private Canvas canvastext;

    private Canvas newcanvas;

    private int sppx9;

    private int dppx1, dppx3, dppx14, dppx25, dppx9, dppx55, dppx50;

    private float w;


    public Wview1(Context context) {
        this(context, null);

    }

    public Wview1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        //获取窗口管理器
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;


        w = px2dip(context, screenWidth);

        sppx9 = sp2px(context, 9);
        dppx1 = dip2px(context, 1);
        dppx3 = dip2px(context, 3);
        dppx14 = dip2px(context, 14);
        dppx9 = dip2px(context, 9);
        dppx25 = dip2px(context, 28);
        dppx55 = dip2px(context, 55);
        dppx50 = dip2px(context, 50);


        float mm = (((screenWidth - dppx25) / metrics.xdpi * 25.4f) - (dppx25 / metrics.xdpi * 25.4f)) * 0 / (screenWidth - dppx25 * 2);

        mm = (((screenWidth - dppx25) / metrics.xdpi * 25.4f) - (dppx25 / metrics.xdpi * 25.4f)) - mm;

        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        text = decimalFormat.format(mm / 10);//format 返回的是字符串
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getNewBitmap();
        canvas.drawBitmap(newBitmap, 0, 0, null);
    }


    public Wview1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取点击的xy坐标
                x = event.getRawX();


                //新建一个Rect来获取当前view的坐标位置
                Rect rect = new Rect();
                this.getGlobalVisibleRect(rect);
                startx = (rect.left); //设置当前view x坐标位置


                break;
            case MotionEvent.ACTION_MOVE:

                float c;
                if (startx + event.getRawX() - x < 0) {

                    this.setTranslationX(0);
                    c = 0;

                } else if (startx + event.getRawX() - x > screenWidth - dppx25 * 2) {

                    this.setTranslationX(screenWidth - dppx25 * 2);
                    c = screenWidth - dppx25 * 2;

                } else {

                    this.setTranslationX(startx + event.getRawX() - x);
                    c = startx + event.getRawX() - x;
                }


                //获取屏幕的高度和宽度
                Display display = wm.getDefaultDisplay();
                DisplayMetrics metrics = new DisplayMetrics();
                display.getMetrics(metrics);


                float mm = (((screenWidth - dppx25) / metrics.xdpi * 25.4f) - (dppx25 / metrics.xdpi * 25.4f)) * c / (screenWidth - dppx25 * 2);

                mm = (((screenWidth - dppx25) / metrics.xdpi * 25.4f) - (dppx25 / metrics.xdpi * 25.4f)) - mm;

                DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                text = decimalFormat.format(mm / 10);//format 返回的是字符串
                System.out.println("位置改变" + text + "dddd" + screenWidth + "ccccc" + metrics.xdpi);


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

        canvas.drawBitmap(textBitmap(bitmap), dppx14, screenHeight - dppx55 * 2 - dppx14 + dppx3, null);

    }


    private Bitmap linebitmap(Bitmap bitmap) {

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#855445"));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(dppx1);
        Bitmap newbitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        newcanvas = new Canvas(newbitmap);
        newcanvas.drawLine(dppx25, 0, dppx25, screenHeight - dppx55 * 2, paint);
        newcanvas.drawBitmap(bitmap, dppx9, screenHeight - dppx55 * 2, null);

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


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}