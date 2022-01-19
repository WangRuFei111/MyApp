package com.luoye.app.utlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JsonUtis {
    private static String TAG = "---JsonUtis";

    /**
     * 保存json到本地
     *
     * @param content
     */
    public static String saveToSDCard(String content, String path) {
        try {
            File file = new File(path + ".json");
            OutputStream out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.close();

            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从本地读取json
     *
     * @param filePath
     */
    public String readTextFile(String filePath) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new String(sb.toString().getBytes("iso8859-1"), StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
