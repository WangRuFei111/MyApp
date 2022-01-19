package com.luoye.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

//电子表数字view
public class TextLedView extends View {
    private static final String TAG = "---TextLedView";
    //画笔
    private Paint ledPaint;
    //路径
    private Path ledPath;
    //笔画的长度与宽
    private int ledLong, ledWidth;
    //笔画间的缝隙
    private int ledMargin = 1;//缝隙  倍数
    //控件的内边距
    private int viewPaddingH = 0;
    private int viewPaddingV = 0;
    //竖切角
    private int verHorn;
    //斜切角
    private int oblHorn;
    //字体大小
    private int textSize = 60; // 范围 10~90;
    //字体颜色
    private String textColor = "#000000";
    //显示的数字
    private int vel = 5;//0~9


    private boolean isInit = false;

    public TextLedView(Context context) {
        super(context);
    }

    public TextLedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ledPaint = new Paint();
        ledPaint.setAntiAlias(true);
        ledPaint.setStyle(Paint.Style.FILL);
        ledPaint.setColor(Color.parseColor(textColor));

        ledPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isInit) {
            initData();
            isInit = true;
        }

        if (vel == 0 || vel == 2 || vel == 3 || vel == 5 || vel == 6 || vel == 7 || vel == 8 || vel == 9)
            centreUp(canvas);
        if (vel == 2 || vel == 3 || vel == 4 || vel == 5 || vel == 6 || vel == 8 || vel == 9)
            centre(canvas);
        if (vel == 0 || vel == 2 || vel == 3 || vel == 5 || vel == 6 || vel == 8 || vel == 9)
            centreDow(canvas);
        if (vel == 0 || vel == 5 || vel == 4 || vel == 6 || vel == 8 || vel == 9)
            leftUp(canvas);
        if (vel == 0 || vel == 2 || vel == 6 || vel == 8)
            leftDow(canvas);
        if (vel == 0 || vel == 1 || vel == 2 || vel == 3 || vel == 4 || vel == 7 || vel == 8 || vel == 9)
            rightUp(canvas);
        if (vel == 0 || vel == 1 || vel == 3 || vel == 4 || vel == 5 || vel == 6 || vel == 7 || vel == 8 || vel == 9)
            rightDow(canvas);

        setVel(++vel);
        postInvalidateDelayed(1000);
    }

    //初始化数据
    private void initData() {
        int viewWidth = getWidth();
        ledWidth = (int) (viewWidth / 500f * textSize);
        verHorn = ledWidth / 3;
        oblHorn = ledWidth / 3 * 2;
        ledMargin = ledWidth / 2;
        ledLong = viewWidth - viewPaddingH * 2 - ledMargin * 2 - ledMargin / 2;
        viewPaddingH = (viewWidth - ledMargin * 2 - ledLong) / 2;
        viewPaddingV = (getHeight() - ledLong * 2 - ledWidth / 2 * 3) / 2;
        Log.i(TAG, "getWidth: " + getWidth() + " getHeight:" + getHeight());
    }

    //上部横线
    private void centreUp(Canvas canvas) {
        int x, y;
        x = viewPaddingH + ledMargin;
        y = viewPaddingV;
        ledPath.reset();
        ledPath.lineTo(x, y);
        ledPath.lineTo(x + ledLong, y);
        ledPath.lineTo(x + ledLong, y + verHorn);
        ledPath.lineTo(x + ledLong - oblHorn, y + ledWidth);
        ledPath.lineTo(x + oblHorn, y + ledWidth);
        ledPath.lineTo(x, y + verHorn);
        ledPath.lineTo(x, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //中部横线
    private void centre(Canvas canvas) {
        int x, y;
        x = viewPaddingH + ledMargin;
        y = viewPaddingV + ledMargin / 3 + ledLong;
        int width = ledWidth / 5 * 3;
        ledPath.reset();
        ledPath.lineTo(x + width, y);
        ledPath.lineTo(x + ledLong - width, y);
        ledPath.lineTo(x + ledLong, y + width);
        ledPath.lineTo(x + ledLong - width, y + width * 2);
        ledPath.lineTo(x + width, y + width * 2);
        ledPath.lineTo(x, y + width);
        ledPath.lineTo(x + width, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //下部横线
    private void centreDow(Canvas canvas) {
        int x, y;
        x = viewPaddingH + ledMargin;
        y = viewPaddingV + ledMargin + ledLong * 2;
        ledPath.reset();
        ledPath.lineTo(x, y + ledWidth);
        ledPath.lineTo(x, y + ledWidth - verHorn);
        ledPath.lineTo(x + oblHorn, y);
        ledPath.lineTo(x + ledLong - oblHorn, y);
        ledPath.lineTo(x + ledLong, y + oblHorn);
        ledPath.lineTo(x + ledLong, y + ledWidth);
        ledPath.lineTo(x, y + ledWidth);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //左上
    private void leftUp(Canvas canvas) {
        int x, y;

        x = viewPaddingH;
        y = viewPaddingV + ledMargin;
        ledPath.reset();
        ledPath.lineTo(x, y);
        ledPath.lineTo(x + verHorn, y);
        ledPath.lineTo(x + ledWidth, y + oblHorn);
        ledPath.lineTo(x + ledWidth, y + ledLong - oblHorn);
        ledPath.lineTo(x + ledWidth - oblHorn, y + ledLong);
        ledPath.lineTo(x + ledWidth - oblHorn - verHorn, y + ledLong);
        ledPath.lineTo(x, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //左下
    private void leftDow(Canvas canvas) {
        int x, y;
        x = viewPaddingH;
        y = viewPaddingV + ledWidth + +ledLong;
        ledPath.reset();
        ledPath.lineTo(x, y);
        ledPath.lineTo(x + verHorn, y);
        ledPath.lineTo(x + ledWidth, y + oblHorn);
        ledPath.lineTo(x + ledWidth, y + ledLong - oblHorn);
        ledPath.lineTo(x + ledWidth - oblHorn, y + ledLong);
        ledPath.lineTo(x + ledWidth - oblHorn - verHorn, y + ledLong);
        ledPath.lineTo(x, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //右上
    private void rightUp(Canvas canvas) {
        int x, y;
        x = viewPaddingH + ledMargin * 2 + ledLong;
        y = viewPaddingV + ledMargin;
        ledPath.reset();
        ledPath.lineTo(x, y);
        ledPath.lineTo(x - verHorn, y);
        ledPath.lineTo(x - verHorn - oblHorn, y + oblHorn);
        ledPath.lineTo(x - verHorn - oblHorn, y + ledLong - oblHorn);
        ledPath.lineTo(x - verHorn, y + ledLong);
        ledPath.lineTo(x, y + ledLong);
        ledPath.lineTo(x, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //右下
    private void rightDow(Canvas canvas) {
        int x, y;
        x = viewPaddingH + ledMargin * 2 + ledLong;
        y = viewPaddingV + ledWidth + ledLong;
        ledPath.reset();
        ledPath.lineTo(x, y);
        ledPath.lineTo(x - verHorn, y);
        ledPath.lineTo(x - verHorn - oblHorn, y + oblHorn);
        ledPath.lineTo(x - verHorn - oblHorn, y + ledLong - oblHorn);
        ledPath.lineTo(x - verHorn, y + ledLong);
        ledPath.lineTo(x, y + ledLong);
        ledPath.lineTo(x, y);
        ledPath.close(); //封闭路径
        canvas.drawPath(ledPath, ledPaint);
    }

    //重置view大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec * 2);
    }


    //字体大小
    public void setTextSize(int textSize) {
        if (textSize < 10) textSize = 10;
        if (textSize > 90) textSize = 90;
        this.textSize = textSize;
    }

    //字体颜色
    public void setTextColor(String textColor) {
        this.textColor = textColor;
        ledPaint.setColor(Color.parseColor(textColor));
    }

    //显示的数据
    public void setVel(int vel) {
        if (vel < 0) vel = 0;
        if (vel > 9) vel = 0;
        this.vel = vel;
    }
}
