package com.luoye.app.utlis;

import android.content.Context;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：保存数据到本地
 */
public class SaveDataToLocalUtls {

    /**
     * 保存 saveAssets
     *
     * @param assParh ass路径
     * @param path    路径
     */
    public static File saveAssets(Context context, String assParh, String path) {

        try {
            InputStream inStream = context.getResources().getAssets().open(assParh);
            File savefile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(savefile);
            byte[] buffer = new byte[10];
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] bs = outStream.toByteArray();
            fileOutputStream.write(bs);
            outStream.close();
            inStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return savefile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存Raw
     *
     * @param rawId raw ID
     * @param path  路径
     */
    public static File saveRaw(Context context, int rawId, String path) {
        try {
            InputStream inStream = context.getResources().openRawResource(rawId);
            File savefile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(savefile);
            byte[] buffer = new byte[10];
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] bs = outStream.toByteArray();
            fileOutputStream.write(bs);
            outStream.close();
            inStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return savefile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存 Bitmap 到本地
     *
     * @param bitmap 位图
     * @param path   路径
     * @param ex     拓展名
     */
    private String saveBitmap(Bitmap bitmap, String path, String ex) {
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            switch (ex) {
                case "png":
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    break;
                case "jpeg":
                case "jpg":
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                case "webp":
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, fileOutputStream);//压缩到WEBP有损格式 ,文件最小
                    break;
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 保存json到本地
     *
     * @param path    路径
     * @param content 数据
     */
    public String saveJson(String path, String content) {
        try {
            File file = new File(path);
            OutputStream out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
