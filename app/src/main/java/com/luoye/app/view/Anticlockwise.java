package com.luoye.app.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.luoye.app.MyApplication;

/**
 * created by: ls
 * TIME：2021/8/2
 * user：倒计时
 */
public class Anticlockwise extends View {

    private Context context;

    public Anticlockwise(Context context) {
        super(context);
    }

    public Anticlockwise(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(sp2px(13));
        textPaint.setColor(Color.parseColor("#FF1B4B"));
        //   textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);


        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //  bgPaint.setColor(Color.parseColor("#FF1B4B"));
        bgPaint.setColor(Color.parseColor("#ffffff"));
        bgPaint.setAntiAlias(true);
    }

    private Paint textPaint;
    private Paint bgPaint;
    private long mTime;
    private long initTime = getInitTime();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTime = (getData() - System.currentTimeMillis()) / 1000;
        if (mTime <= 0) {
            setData(System.currentTimeMillis() + initTime * 1000);
            mTime = initTime;
        }

        String[] strTime = parseDate(mTime);
        int height = getMeasuredHeight();

        RectF rectH = new RectF(dip2px(5), 0, textPaint.measureText("00") + dip2px(2 + 5), height);
        Paint.FontMetrics fontMetricsH = textPaint.getFontMetrics();
        float distanceH = (fontMetricsH.bottom - fontMetricsH.top) / 2 - fontMetricsH.bottom;
        float baselineH = rectH.centerY() + distanceH;
        canvas.drawRoundRect(rectH, dip2px(2), dip2px(2), bgPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawText(strTime[2], dip2px(5 + 1), baselineH, textPaint);

        canvas.drawCircle(dip2px(26), dip2px(5), dip2px(1.5f), bgPaint);
        canvas.drawCircle(dip2px(26), dip2px(11), dip2px(1.5f), bgPaint);

        //计算baseline
        RectF rectM = new RectF(rectH.width() + dip2px(5 + 5 + 4), 0, rectH.width() + dip2px(5 + 8 + 2 + 1) + textPaint.measureText("00"), height);
        Paint.FontMetrics fontMetricsM = textPaint.getFontMetrics();
        float distanceM = (fontMetricsM.bottom - fontMetricsM.top) / 2 - fontMetricsM.bottom;
        float baselineM = rectM.centerY() + distanceM;
        canvas.drawRoundRect(rectM, dip2px(2), dip2px(2), bgPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawText(strTime[1], textPaint.measureText(strTime[2]) + dip2px(5 * 3 + 2), baselineM, textPaint);

        canvas.drawCircle(dip2px(52), dip2px(5), dip2px(1.5f), bgPaint);
        canvas.drawCircle(dip2px(52), dip2px(11), dip2px(1.5f), bgPaint);


        //计算baseline
        RectF rectS = new RectF(rectH.width() + rectM.width() + dip2px(5 + 5 + 5 + 5 + 4), 0, rectH.width() + rectM.width() + dip2px(5 * 4 + 2 * 3) + textPaint.measureText("00"), height);
        Paint.FontMetrics fontMetricsS = textPaint.getFontMetrics();
        float distanceS = (fontMetricsS.bottom - fontMetricsS.top) / 2 - fontMetricsS.bottom;
        float baselineS = rectS.centerY() + distanceS;
        canvas.drawRoundRect(rectS, dip2px(2), dip2px(2), bgPaint);//第二个参数是x半径，第三个参数是y半径
        canvas.drawText(strTime[0], textPaint.measureText("0000") + dip2px(5 * 5 + 2 * 2), baselineS, textPaint);


        //每隔1s重新绘制
        postInvalidateDelayed(1000);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dip2px(80), dip2px(15));
    }


    /*清除记忆 修改默认值*/
    public void deleteData(long initTime) {
        this.initTime = initTime;
        setInitTime(initTime);
        setData(0);
    }

    /*秒转  01:01:01*/
    private String[] parseDate(long times) {
        String[] strTime = new String[3];
        long ss = times % 60;
        long ms = (times - ss) / 60 % 60;
        long hs = (times - ms - ss) / 3600 % 60;
        if (ss < 10) strTime[0] = "0" + ss;
        else strTime[0] = ss + "";
        if (ms < 10) strTime[1] = "0" + ms;
        else strTime[1] = ms + "";
        if (hs < 10) strTime[2] = "0" + hs;
        else strTime[2] = hs + "";
        return strTime;
    }


    private SharedPreferences getAppSp() {
        return MyApplication.getContext().getSharedPreferences("Anticlockwise", Context.MODE_PRIVATE);
    }

    /*获取数据*/
    public long getData() {
        return getAppSp().getLong("data", 0);
    }

    /*写入数据*/
    public void setData(long time) {
        getAppSp().edit().putLong("data", time).apply();
    }

    /*获取数据*/
    private long getInitTime() {
        return getAppSp().getLong("init", 48 * 60 * 60);
    }

    /*写入数据*/
    private void setInitTime(long time) {
        getAppSp().edit().putLong("init", time).apply();
    }


    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp转px
     *
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
