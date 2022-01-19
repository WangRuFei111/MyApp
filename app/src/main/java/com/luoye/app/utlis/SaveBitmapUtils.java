package com.luoye.app.utlis;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * Bitmap类型保存 为png,jpg,jpeg,webp格式以及质量压缩 工具类 (适配android 11)
 */
public class SaveBitmapUtils {

    private static String TAG = "---SaveBitmapUtils";

    public enum Type {
        PNG, JPG, JPEG, WEBP;
    }

    private Context context;
    private FileOutputStream fileOutputStream;


    private File file;//保存后的文件路径
    private String fileName = "000000";//文件名字
    private String folderName = "YaoSuo";//文件夹名字
    private String dir;//保存路径
    private boolean isStorage = false;//是否可以存储


    public SaveBitmapUtils(Context context) {
        this.context = context;
        this.isStorage = initStorage();
    }


    public SaveBitmapUtils(Context context, String folderName) {
        this.context = context;
        this.folderName = folderName;
        this.isStorage = initStorage();
    }


    public String saveBitmap(Bitmap bitmap, Type type, int mass) {
        if (!isStorage) {
            Log.i(TAG, "存储失败：读写异常");
            return null;
        }
        getFileName();
        return storageBitmap(bitmap, type, mass);
    }

    public String saveBitmap(Bitmap bitmap, Type type) {
        if (!isStorage) {
            Log.i(TAG, "存储失败：读写异常");
            return null;
        }
        getFileName();
        return storageBitmap(bitmap, type, 100);
    }

    public String saveBitmap(Bitmap bitmap, int mass) {
        if (!isStorage) {
            Log.i(TAG, "存储失败：读写异常");
            return null;
        }
        getFileName();
        return storageBitmap(bitmap, Type.JPG, mass);
    }

    public String saveBitmap(Bitmap bitmap) {
        if (!isStorage) {
            Log.i(TAG, "存储失败：读写异常");
            return null;
        }
        getFileName();
        return storageBitmap(bitmap, Type.JPG, 100);
    }


    /**
     * 初始化 判断文件地址是否可用
     *
     * @return
     */
    private boolean initStorage() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            //私有存储
            dir = context.getFilesDir().getPath() + File.separator + folderName + "/";
        } else {
            //公共存储android10以后不是使用
                dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName + "/";
        }

        // 如文件夹不存在 创建文件夹
        File dirFile = new File(dir);
        if (!dirFile.exists()) dirFile.mkdirs();

        //获取内部存储状态 如果状态不是mounted，无法读写
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "读写异常");
            return false;
        } else {
            Log.i(TAG, "读写正常");
            return true;
        }

    }


    /**
     * 产生文件名
     */
    private void getFileName() {

        Long time = System.currentTimeMillis();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            if (fileName.equals(folderName + simpleDateFormat.format(time))) {
                fileName = folderName + simpleDateFormat.format(time) + ((int) +System.currentTimeMillis() / 10000);
            } else {
                fileName = folderName + simpleDateFormat.format(time);
            }
        } else {
            if (fileName.equals(folderName + time)) {
                fileName = folderName + time + ((int) +System.currentTimeMillis() / 10000);
            } else {
                fileName = folderName + time;
            }
        }

    }


    /**
     * @param bitmap 图片
     * @param type   文件保存格式
     * @param mass   保存质量100 最大
     * @return
     */
    private String storageBitmap(Bitmap bitmap, Type type, int mass) {


        try {
            switch (type) {
                case PNG:
                    file = new File(dir + fileName + ".png");
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, mass, fileOutputStream);
                    break;
                case JPEG:
                    file = new File(dir + fileName + ".jpeg");
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, mass, fileOutputStream);
                    break;
                case JPG:
                    file = new File(dir + fileName + ".jpg");
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, mass, fileOutputStream);
                    break;
                case WEBP:
                    file = new File(dir + fileName + ".webp");
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, mass, fileOutputStream);//压缩到WEBP有损格式 ,文件最小
                    break;
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            flieImageTOGallery();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Log.i(TAG, "私有存储失败");
            } else {
                Log.i(TAG, "共有存储失败");
            }
            return null;
        }

    }


    /**
     * 广播图库更新 android<11
     * 插入系统图库 android>=11
     */
    private void flieImageTOGallery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            //系统图库保存一份
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String mimeType = fileNameMap.getContentTypeFor(file.getName());
            String fileName = file.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                Log.i(TAG, "存储相册失败:URI=null");
                return;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                FileInputStream fis = new FileInputStream(file);
                FileUtils.copy(fis, out);
                fis.close();
                out.close();
                Log.i(TAG, "存储相册成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "存储相册失败:" + e.toString());
            }
        } else {
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getPath(), new File(file.getPath()).getName(), null);
            } catch (Exception e) {
                e.printStackTrace();
            } // 最后通知图库更新

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "")));
            Log.i(TAG, "广播更新");
        }
    }


    public static boolean video(Context context,File file) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = context.getContentResolver();
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getName());
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            Uri uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                Log.i(TAG, "存储相册失败:URI=null");
                return false;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                FileInputStream fis = new FileInputStream(file);
                FileUtils.copy(fis, out);
                fis.close();
                out.close();
                Log.i(TAG, "存储相册成功");

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "存储相册失败:" + e.toString());
                return false;

            }
        } else {
            //广播系统图库更新
            boolean isSetPath = FileUtil.copyFile(file.getPath(),FileUtil.getPath("/storage/emulated/0/DCIM/","video"+ System.currentTimeMillis() ,".mp4"));
            if (isSetPath) {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "")));
                Log.i(TAG, "广播更新");
                return true;
            } else {
                return false;
            }

        }


    }



}
