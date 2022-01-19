package com.luoye.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/*图片自适应*/
public class MyImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int imageWidth;
    private int imageHeight;

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getImageSize();
    }

    private void getImageSize() {
        Drawable background = this.getBackground();
        if (background == null) return;
        Bitmap bitmap = ((BitmapDrawable) background).getBitmap();
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * imageHeight / imageWidth;
        this.setMeasuredDimension(width, height);
    }
}