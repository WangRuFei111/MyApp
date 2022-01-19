package com.luoye.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;


/**
 * created by: ls
 * TIME：2021/9/1
 * user： 拖动控件  回弹事件（未实现）
 */
public class DragFrameLayout extends FrameLayout {
    private static String TAG = "---DragFrameLayout";

    private ViewDragHelper dragHelper;
    //是否开启边界
    private boolean isBoundary = true;

    public DragFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //中间参数表示灵敏度,比如滑动了多少像素才视为触发了滑动.值越大越灵敏.
        dragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }


    //固定写法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当手指抬起或事件取消的时候 就不拦截事件
        int actionMasked = ev.getActionMasked();
        if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_UP)
            return false;
        return dragHelper.shouldInterceptTouchEvent(ev);
    }


    //固定写法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }


    //是否开启边界
    public void setBoundary(boolean boundary) {
        isBoundary = boundary;
    }


    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //child 表示想要滑动的view
            //pointerId 表示触摸点的id, 比如多点按压的那个id
            //返回值表示,是否可以capture,也就是是否可以滑动.可以根据不同的child决定是否可以滑动
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //child 表示当前正在移动的view left 表示当前的view正要移动到左边距为left的地方 dx 表示和上一次滑动的距离间隔
            //取得左边界的坐标
            final int leftBound = getPaddingLeft();
            //取得右边界的坐标
            final int rightBound = getWidth() - child.getWidth() - leftBound;
            /**
             * 这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
             * 如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
             * 如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
             */
            return isBoundary ? Math.min(Math.max(left, leftBound), rightBound) : left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight() - topBound;
            return isBoundary ? Math.min(Math.max(top, topBound), bottomBound) : top;
        }


        //不重写该方法默认返回0，返回0时若还设置了点击事件则竖直方向不能移动
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        //不重写该方法默认返回0，返回0时若还设置了点击事件则竖直方向不能移动
        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

    };


}
