package com.luoye.app.utlis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * 获取网络图片
 */
public class GetHttpImageUtlis {
    private static String TAG = "---GetHttpImageUtlis";

    public interface OnRunGetHttpImage {
        void onResult(Bitmap bitmap);

        void onCancel(String e);
    }

    public static void donwloadImg(String imageUrl, OnRunGetHttpImage onRunGetHttpImage) {
        new Thread(() -> {
            try {
                // 对资源链接
                URL url = new URL(imageUrl);
                //打开输入流
                InputStream inputStream = url.openStream();
                //对网上资源进行下载转换位图图片

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                if (bitmap != null) {
                    onRunGetHttpImage.onResult(bitmap);
                } else {
                    onRunGetHttpImage.onCancel("bitmap==null");
                }

            } catch (Exception e) {
                e.printStackTrace();
                onRunGetHttpImage.onCancel(e.toString());
            }
        }).start();
    }


}
