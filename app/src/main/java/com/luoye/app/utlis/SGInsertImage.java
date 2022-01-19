package com.luoye.app.utlis;


import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：搜狗图片爬虫
 */
public class SGInsertImage {
    private static String TAG = "---SGInsertImage";


    //html 解析
    //implementation 'org.jsoup:jsoup:1.12.1'

    public interface OnRunComplete {
        void onResult(ArrayList<String> imageUriList);

        void onCancel(Exception e);
    }

    /**
     * 上传图片到搜狗图床
     *
     * @param file
     * @param onRunComplete
     */
    public static void upload(File file, OnRunComplete onRunComplete) {
        ArrayList<String> listImeg = new ArrayList<>();
        new Thread(() -> {
            String url = "http://pic.sogou.com/ris_upload?r=" + System.currentTimeMillis();
            try {
                Log.i(TAG, "upload: " + file.getPath());
                Connection.Response response = Jsoup.connect(url)//请求参数
                        .header("Content-Type", "multipart/form-data")
                        .header("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                        .timeout(100 * 1000)
                        .method(Connection.Method.POST)
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true)
                        .data("upload_item", file.getName(), new FileInputStream(file))
                        .execute();
                String xmlSource = new String(response.bodyAsBytes());
                Pattern pattern = Pattern.compile("src=\"http:.*?\"");
                Matcher m = pattern.matcher(xmlSource);
                while (m.find()) {
                    String imageUri = m.group();
                    imageUri = imageUri.substring(5, imageUri.length() - 1);
                    Log.i(TAG, "run:" + imageUri);
                    listImeg.add(imageUri);
                }
                onRunComplete.onResult(listImeg);
            } catch (Exception e) {
                e.printStackTrace();
                onRunComplete.onCancel(e);
            }
        }).start();
    }


    /**
     * 获取近似图
     *
     * @param imegUrl       图片网址
     * @param onRunComplete 接口
     */
    public static void getSimilarityImage(String imegUrl, int start, OnRunComplete onRunComplete) {
        ArrayList<String> listImeg = new ArrayList<>();
        new Thread(() -> {
            String url = "https://pic.sogou.com/ris?query=%s&start=%s&plevel=0";
            url = String.format(url, imegUrl, start);
            Log.i(TAG, "以图搜图: " + url);
            try {
                Connection.Response response = Jsoup.connect(url)//请求参数
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .timeout(90 * 1000)
                        .method(Connection.Method.GET)
                        .followRedirects(true)
                        .execute();
                String xmlSource = new String(response.bodyAsBytes());
                Pattern pattern = Pattern.compile("\"thumbUrl\":\"https:.*?\"");
                Matcher m = pattern.matcher(xmlSource);
                while (m.find()) {
                    String imageUri = m.group();
                    imageUri = imageUri.substring(0, imageUri.length() - 1);
                    imageUri = imageUri.replace("\\u002F", "/");
                    imageUri = imageUri.substring(12);
                    Log.i(TAG, "run:" + imageUri);
                    listImeg.add(imageUri);
                }
                onRunComplete.onResult(listImeg);
            } catch (Exception e) {
                e.printStackTrace();
                onRunComplete.onCancel(e);
            }
        }).start();
    }

    /**
     * 图片搜索
     *
     * @param data          如（飞机）
     * @param start         第几个开始
     * @param onRunComplete 接口
     */
    public static void getSearchImage(String data, int start, OnRunComplete onRunComplete) {
        ArrayList<String> listImeg = new ArrayList<>();
        new Thread(() -> {
            String url = "https://pic.sogou.com/napi/pc/searchList?start=%s&query=%s";
            url = String.format(url, start, data);
            Connection.Response response = null;
            try {
                response = Jsoup.connect(url)//请求参数
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .timeout(90 * 1000)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .execute();
                String xmlSource = new String(response.bodyAsBytes());
                String reg = "\"thumbUrl\":\"https:.*?\"";
                Pattern pattern = Pattern.compile(reg);
                Matcher m = pattern.matcher(xmlSource);
                while (m.find()) {
                    String imageUri = m.group();
                    imageUri = imageUri.substring(0, imageUri.length() - 1);
                    imageUri = imageUri.replace("\\u002F", "/");
                    imageUri = imageUri.substring(imageUri.indexOf("http"));
                    listImeg.add(imageUri);
                    Log.i(TAG, "run:" + imageUri);
                }
                onRunComplete.onResult(listImeg);
            } catch (Exception e) {
                e.printStackTrace();
                onRunComplete.onCancel(e);
            }
        }).start();
    }
}
