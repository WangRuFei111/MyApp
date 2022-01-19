package com.luoye.app.view.Ruler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class RulerView extends View {

    private int screenWidth;
    private int screenHeight;

    private Bitmap ruler;

    private String color = "#855445";

    private int size = 16;

    private int ruler_scaleLength = 5;

    private int w = 1, h = 1;

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //获取窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        size = dip2px(context, ruler_scaleLength);


        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(dip2px(context, 1));
        paint.setAntiAlias(true);
        paint.setDither(true);

        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);  //画笔
        paint2.setColor(Color.parseColor(color));
        paint2.setTextSize(sp2px(context, 12));
        paint2.setAntiAlias(true);
        paint2.setDither(true);


        ruler = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ruler);
        canvas.drawText("0", screenWidth - sp2px(context, 12), sp2px(context, 12), paint2);

        //横向刻度
        for (int i = 0; i < (screenWidth / metrics.ydpi * 25.4f); i++) {

            System.out.println("___x___" + metrics.ydpi * (1.0f / 25.4f));
            int cole1 = screenWidth - size /5*28;
            int cole2 = (int) (screenWidth / (screenWidth / metrics.ydpi * 25.4f) * i);


            if (i % 5 == 0 && i != 0) {
                if (i % 10 == 0) {

                    canvas.drawLine(cole1 - cole2, 0, cole1 - cole2, size * 3, paint);
                    if (w >= 10) {
                        canvas.drawText(Integer.toString(w), cole1 - cole2 - sp2px(context, 12) / 2, size * 6, paint2);
                    } else {
                        canvas.drawText(Integer.toString(w), cole1 - cole2 - sp2px(context, 12) / 3, size * 6, paint2);
                    }
                    w++;
                } else {
                    canvas.drawLine(cole1 - cole2, 0, cole1 - cole2, size * 2, paint);
                }
            } else {
                canvas.drawLine(cole1 - cole2, 0, cole1 - cole2, size, paint);

            }

        }

        //纵向刻度

        for (int i = 0; i < (screenHeight / metrics.xdpi * 25.4f); i++) {
            System.out.println("___y___" + metrics.ydpi * (1.0f / 25.4f));

            int cole1 = -size / 5*28;
            int cole2 = (int) (screenHeight / (screenHeight / metrics.xdpi * 25.4f) * i);
            if (i % 5 == 0 && i != 0) {
                if (i % 10 == 0) {
                    canvas.drawLine(screenWidth - size * 3, cole2 - cole1, screenWidth, cole2 - cole1, paint);
                    if (h >= 10) {
                        canvas.drawText(Integer.toString(h), screenWidth - size * 6 - sp2px(context, 12) / 2, cole2 - cole1 + sp2px(context, 12) / 3, paint2);
                    } else {
                        canvas.drawText(Integer.toString(h), screenWidth - size * 6, cole2 - cole1 + sp2px(context, 12) / 3, paint2);
                    }
                    h++;
                } else {
                    canvas.drawLine(screenWidth - size * 2, cole2 - cole1, screenWidth, cole2 - cole1, paint);
                }
            } else {
                canvas.drawLine(screenWidth - size, cole2 - cole1, screenWidth, cole2 - cole1, paint);
            }

        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(ruler, 0, 0, null);

    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
