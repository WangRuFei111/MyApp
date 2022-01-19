package com.luoye.app.utlis;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class BitmapUtils {



    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 缩放为原来的ratio  （0.0-1.0）
     *
     * @param bitmap
     * @param ratio
     * @return
     */
    public static Bitmap ratioBitmap(Bitmap bitmap, float ratio) {

        if (ratio <= 0) {
            ratio = 0.1f;
        }
        Log.i("---BitmapUtils", "比例缩放前--大小：" + bitmap.getWidth() + ":" + bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Log.i("---BitmapUtils", "比例缩放后--" + "缩放率：" + ratio + "大小：" + newBmp.getWidth() + ":" + newBmp.getHeight());
        return newBmp;
    }

    /**
     * 指定缩放
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap appointBitmap(Bitmap bitmap, float width, float height) {
        Log.i("---BitmapUtils", "指定缩放前--大小：" + bitmap.getWidth() + ":" + bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(width / bitmap.getWidth(), height / bitmap.getHeight());
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Log.i("---BitmapUtils", "指定缩放后--大小：" + newBmp.getWidth() + ":" + newBmp.getHeight());
        return newBmp;
    }



    /**
     * 以宽为基准等比缩放方法
     *
     * @param bitmap
     * @param w
     * @return
     */
    public static Bitmap widthResize(Bitmap bitmap, float w) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     * 以高为基准等比缩放方法
     *
     * @param bitmap
     * @param h
     * @return
     */
    public static Bitmap heightResize(Bitmap bitmap, float h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleHidth = ((float) h / height);
        matrix.postScale(scaleHidth, scaleHidth);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     * 图片旋转
     *
     * @param bitmap
     * @param angle
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.setRotate(angle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);//angle就是我们需要选择的度数
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }

    /**
     * 图片镜像
     *
     * @param bitmap
     * @param key
     * @return
     */
    public static Bitmap imageBitmap(Bitmap bitmap, boolean key) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                if (key) {
                    m.postScale(-1, 1);   //镜像水平翻转
                } else {
                    m.postScale(1, -1);   //镜像垂直翻转
                }
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }


    //获得一个圆形图
    public static Bitmap getOvalBitmap(Bitmap bitmap, int r) {

        Bitmap output = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, r, r);

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * UI画成图片
     *
     * @param view
     * @return
     */
    public static Bitmap loadBitmapFromView(View view) {
        view.measure(0, 0);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        /** 如果不设置canvas画布为白色，则生成透明 */
        view.layout(0, 0, width, height);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 文字化图片
     *
     * @param context   上下文
     * @param text      内容
     * @param textSize  字体大小
     * @param fonts     自定义字体
     * @param textColor 文字颜色  {}渐变色
     * @param color     背景色
     * @return
     */
    public static Bitmap textBitmap(Context context, String text, int textSize, String fonts, int[] textColor, int color) {
        //sp转px
        int spToPx = (int) (textSize * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);

        //  int dpToPx = (int) (textSize * context.getResources().getDisplayMetrics().density + 0.5f);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(spToPx);
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), fonts));


        // 文字的宽度
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();
        int height = rect.height();


        int[] colors = {textColor[0], textColor[textColor.length - 1]};//颜色的数组
        float[] position = {0f, 1.0f};//颜色渐变位置的数组
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, height, colors, position, Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradient);

        Bitmap bitmap = Bitmap.createBitmap(width * 5 / 4, height * 5 / 4, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        canvas.drawText(text, width / 10, height * 5 / 4, paint);

        return bitmap;

    }
}
