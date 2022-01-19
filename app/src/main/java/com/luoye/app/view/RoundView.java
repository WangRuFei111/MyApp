package com.luoye.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.luoye.app.MyApplication;


/**
 * created by: ls
 * TIME：2021/6/22
 * user：圆型 进度等待 Ui
 */
public class RoundView extends View {

    private Paint paint;
    private int color = Color.parseColor("#FF5837");

    //设置起始样式
    private boolean[] type = new boolean[]{false, false, false, false};

    public RoundView(Context context) {
        super(context);
    }


    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setAlpha(117);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < type.length; i++) {
            if (type[i]) {
                paint.setAlpha(225);
            } else {
                paint.setAlpha(117);
            }
            canvas.drawCircle(dip2px(7.5f + 30 * i), dip2px(7.5f), dip2px(7.5f), paint);
        }


        if (type[3]) {
            type = new boolean[]{false, false, false, false};
        } else {
            for (int i = 0; i < type.length; i++) {
                if (!type[i]) {
                    type[i] = true;
                    break;
                }
            }
        }

        //每隔1s重新绘制
        postInvalidateDelayed(300);
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dip2px(105), dip2px(15));
    }
}
