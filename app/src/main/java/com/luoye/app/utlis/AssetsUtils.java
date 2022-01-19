package com.luoye.app.utlis;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtils {

    /*获取Assets图片资源*/
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /* 读取Assets文件夹当中得内容，存放到字符串当中 */
    public static String getJsonFromAssets(Context context, String filename) {
        /* 1、获取Assets文件管理器*/
        AssetManager am = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*2、获取文件输入流*/
        try {
            InputStream is = am.open(filename);
            /* 读取内容存放到内存流当中*/
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while (true) {
                hasRead = is.read(buf);
                if (hasRead == -1) {
                    break;
                }
                baos.write(buf, 0, hasRead);
            }
            String msg = baos.toString();
            is.close();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
