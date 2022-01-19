package com.luoye.app.utlis;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：file工具类
 */
public class FileUtil {



    /*string 转Uri*/
    public static Uri stringToUri(Context context, String path) {
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(path));
    }

    /**
     * 获取文件路径
     *
     * @param dir  目录
     * @param name 文件名
     * @param ex   拓展名
     */
    public static String getPath(String dir, String name, String ex) {
        /*获取目录*/
        File file = new File(dir);
        /*判断目录是否存在  不存在则创建*/
        if (!file.exists()) file.mkdirs();
        return dir + name + ex;
    }

    /**
     * @param oldPath 原文件路径
     * @param newPath 复制后路径
     * @return 是否成功
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(oldPath);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 遍历某个文件夹下的某个类型的文件
     *
     * @param filePath 路径
     * @param suffix   类型（拓展名）
     * @return
     */
    public static List<String> getListFiles(String filePath, String suffix) {
        List<String> s = new ArrayList<>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        s.clear();
        for (int i = files.length - 1; i >= 0; i--) {
            String fileName = files[i].getName();
            if (fileName.contains(".mp3")) {
                s.add(filePath + fileName);
            }
        }
        return s;
    }

    /*从本地读取json*/
    public String getFilePathJson(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filePath);
            InputStream in = null;
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                sb.append((char) tempbyte);
            }
            in.close();
            return new String(sb.toString().getBytes("iso8859-1"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
