package com.luoye.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * 可以 拖动 缩放 旋转 动画控件
 */
public class DragView extends FrameLayout {
    private static final String TAG = "---DragView";
    // 属性变量
    private float scale = 1f; // 伸缩比例
    private float rotation; // 旋转角度
    // 移动过程中临时变量
    private float actionX;
    private float actionY;
    private float spacing;
    private float degree;
    private OnClickListener onClickListener;
    private boolean isFence = true; //栅栏
    private boolean isMove = true;   //移动
    private boolean isZoom = false;  //缩放
    private boolean isRotate = false;//旋转

    private float fenceX = 0;//围栏宽高
    private float fenceY = 0;//围栏宽高


    //按压时的位置
    private float downX, downY;
    //按压与抬起的时间间隔
    private long interval = 0;

    private TouchType touchType = TouchType.MOVE;

    private enum TouchType {MOVE, ZOOM_ROTATE}

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchType = TouchType.MOVE;
                downX = event.getRawX();
                downY = event.getRawY();
                actionX = event.getRawX();
                actionY = event.getRawY();
                interval = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchType = TouchType.ZOOM_ROTATE;
                spacing = getSpacing(event);
                degree = getDegree(event);
                break;
            case MotionEvent.ACTION_MOVE:
                switch (touchType) {
                    case MOVE:
                        if (isMove) {
                            float x = getX() + event.getRawX() - actionX;
                            float y = getY() + event.getRawY() - actionY;
                            if (isFence) {
                                fence(x, y);
                            } else {
                                setX(x);
                                setY(y);
                            }
                            actionX = event.getRawX();
                            actionY = event.getRawY();
                        }
                        break;
                    case ZOOM_ROTATE:
                        if (isZoom) {
                            scale = scale * getSpacing(event) / spacing;
                            setScaleX(scale);
                            setScaleY(scale);
                        }
                        if (isRotate) {
                            rotation = rotation + getDegree(event) - degree;
                            if (rotation > 360) rotation = rotation - 360;
                            if (rotation < -360) rotation = rotation + 360;
                            setRotation(rotation);
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                touchType = TouchType.MOVE;
                break;
            case MotionEvent.ACTION_UP:
                //当按压时间小于600毫秒并且移动范围低于5px则认为用户为点击事件
                if (System.currentTimeMillis() - interval < 600) {
                    if (Math.abs(downX - event.getRawX()) < 5 || Math.abs(downY - event.getRawY()) < 5) {
                        if (onClickListener != null) onClickListener.onClick(getRootView());
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    // 触碰两点间距离
    private float getSpacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取旋转角度
    private float getDegree(MotionEvent event) {
        //得到两个手指间的旋转角度
        double delta_x = event.getX(0) - event.getX(1);
        double delta_y = event.getY(0) - event.getY(1);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    //围栏
    private void fence(float x, float y) {
        //todo 栅栏功能暂无适配 旋转 旋转+缩放
        ViewGroup viewGroup = (ViewGroup) getParent();
        float maxX = viewGroup.getWidth();
        float maxY = viewGroup.getHeight();
        float myWidth = getWidth();
        float myHeight = getHeight();
        float minX = 0;
        float minY = 0;

        //设置围栏宽高
        minX = minX + fenceX;
        minY = minY + fenceY;
        maxX = maxX - fenceX;
        maxY = maxY - fenceY;

        //todo 适配一般缩放
        minX -= (myWidth * (1 - getScaleX()) / 2f);
        minY -= (myHeight * (1 - getScaleY()) / 2f);
        maxX = maxX - myWidth + (myWidth * (1 - getScaleX()) / 2f);
        maxY = maxY - myHeight + (myHeight * (1 - getScaleY()) / 2f);

        //todo 暂时适配90/-90度的旋转
        if (Math.abs(getRotation()) == 90) {
            int c = getWidth() / 2 - getHeight() / 2;
            minX -= c;
            minY += c;
            maxX += c;
            maxY -= c;
        }
        setX(Math.min(Math.max(x, minX), maxX));
        setY(Math.min(Math.max(y, minY), maxY));
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //旋转
    public void setRotate(float rotation) {
        this.rotation = rotation;
        setRotation(rotation);
        if (isFence) fence(getX(), getY());
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public void setZoom(boolean zoom) {
        isZoom = zoom;
    }

    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }

    public void setFence(boolean fence) {
        isFence = fence;
    }

    //围栏宽高
    public void setfenceXY(float x, float y) {
        this.fenceX = x;
        this.fenceY = y;
        post(() -> fence(getX(), getY()));
    }


    public void setMarginTop_Left(int topPx, int leftPx) {
        post(() -> {

            if (leftPx != -1) {
                float myWidth = getWidth();
                float minX = 0;
                minX = minX + fenceX;
                minX -= (myWidth * (1 - getScaleX()) / 2f);
                setX(minX + leftPx);
            }


            if (topPx != -1) {
                float myHeight = getHeight();
                float minY = 0;
                minY = minY + fenceY;
                minY -= (myHeight * (1 - getScaleY()) / 2f);
                setY(minY + topPx);
            }
        });
    }

    public void setMarginTop_Right(int topPx, int rightPx) {
        post(() -> {

            ViewGroup viewGroup = (ViewGroup) getParent();
            if (rightPx != -1) {
                float maxX = viewGroup.getWidth();
                float myWidth = getWidth();
                maxX = maxX + fenceX;
                maxX = maxX - myWidth + (myWidth * (1 - getScaleX()) / 2f);
                setX(maxX - rightPx);
            }

            if (topPx != -1) {
                float myHeight = getHeight();
                float minY = 0;
                minY = minY + fenceY;
                minY -= (myHeight * (1 - getScaleY()) / 2f);
                setY(minY + topPx);
            }
        });
    }

    public void setMarginBottom_Left(int BottomPx, int leftPx) {
        post(() -> {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (leftPx != -1) {
                float myWidth = getWidth();
                float minX = 0;
                minX = minX + fenceX;
                minX -= (myWidth * (1 - getScaleX()) / 2f);
                setX(minX + leftPx);
            }

            if (BottomPx != -1) {
                float myHeight = getHeight();
                float maxY = viewGroup.getHeight();
                maxY = maxY - fenceY;
                maxY = maxY - myHeight + (myHeight * (1 - getScaleY()) / 2f);
                setY(maxY - BottomPx);
            }
        });
    }

    public void setMarginBottom_Right(int BottomPx, int rightPx) {
        post(() -> {
            ViewGroup viewGroup = (ViewGroup) getParent();

            if (rightPx != -1) {
                float maxX = viewGroup.getWidth();
                float myWidth = getWidth();
                maxX = maxX + fenceX;
                maxX = maxX - myWidth + (myWidth * (1 - getScaleX()) / 2f);
                setX(maxX - rightPx);
            }

            if (BottomPx != -1) {
                float myHeight = getHeight();
                float maxY = viewGroup.getHeight();
                maxY = maxY - fenceY;
                maxY = maxY - myHeight + (myHeight * (1 - getScaleY()) / 2f);
                setY(maxY - BottomPx);
            }
        });
    }


}