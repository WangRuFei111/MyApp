package com.luoye.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * created by: ls
 * TIME：2021/7/15
 * user：bpm控制
 */
public class AnimatorView extends View {

    private static final String TAG = "---AnimatorView";

    // 控件宽
    private int width;
    // 控件高
    private int height;
    // 背景画笔
    private Paint bgPaint;

    // 抗锯齿
    private PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public AnimatorView(Context context) {
        this(context, null);
    }

    public AnimatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 控件宽、高
        width = height = Math.min(h, w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        drawBg(canvas);
    }


    /*背景*/
    private void drawBg(Canvas canvas) {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);

        //1
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#33000000"));
        canvas.drawCircle(width / 2, height / 2, width / 2, bgPaint);

        //0.837
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(Color.parseColor("#33FFDE26"));
        bgPaint.setStrokeWidth(2);
        canvas.drawCircle(width / 2, height / 2, width / 2 * 0.785f, bgPaint);

        //0.628
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#0DFFDE26"));
        canvas.drawCircle(width / 2, height / 2, width / 2 * 0.785f, bgPaint);


        //0.465
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#090909"));
        bgPaint.setAlpha(225);
        canvas.drawCircle(width / 2, height / 2, width / 4, bgPaint);



    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }


    public void getValueAnimator(int bpm) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(60 * 1000 / bpm);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();

                Log.i(TAG, "onAnimationUpdate: ");
                invalidate();
            }
        });
    }

}
