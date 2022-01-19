package com.luoye.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;


/**
 * 水平十字
 */
public class PaintingView extends View {
    private Bitmap cross;//十字架

    public float r;//旋转角度

    private int screenWidth;
    private int screenHeight;

    private int x;
    private int y;


    public PaintingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;


        x = dip2px(context, 281);
        y = dip2px(context, 97);


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#CCFF33"));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(dip2px(context, 1));

        cross = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cross);


        canvas.drawLine(0, y, screenWidth, y, paint);

        canvas.drawLine(x, 0, x, screenHeight, paint);

        canvas.drawCircle(x, y, 11, paint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();
        canvas.save();
        Matrix matrix = new Matrix();
        matrix.setRotate(r, x, y);//90就是我们需要选择的90度
        canvas.drawBitmap(cross, matrix, paint);
        canvas.restore();

    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 旋转角度
     *
     * @param r
     */
    public void setR(int r) {
        this.r = r;
        invalidate();
    }
}
