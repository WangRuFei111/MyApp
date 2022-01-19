package com.luoye.app.utlis;

import android.content.SharedPreferences;

import com.luoye.app.MyApplication;

import java.util.ArrayList;

public class HistoryUtlis {

    /**
     * 保存搜索记录
     *
     * @param keyword
     */
    public static void saveHistory(String keyword) {
        // 获取搜索框信息
        SharedPreferences mysp = MyApplication.getContext().getSharedPreferences("search_history", 0);
        String old_text = mysp.getString("history", "");
        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(keyword + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(keyword + ",")) {
            SharedPreferences.Editor myeditor = mysp.edit();
            myeditor.putString("history", builder.toString());
            myeditor.commit();
        }
    }

    /**
     * 获取历史
     *
     * @return
     */
    public static ArrayList<String> getHistoryList() {
        // 获取搜索记录文件内容
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("search_history", 0);
        String history = sp.getString("history", "");
        // 用逗号分割内容返回数组
        String[] history_arr = history.split(",");

        ArrayList<String> listHistory = new ArrayList<>();

        for (int i = history_arr.length - 1; i >= 0; i--) {
            listHistory.add(history_arr[i]);
            if (history_arr.length - i > 9) return listHistory;
        }
        return listHistory;
    }

    /**
     * 清除搜索记录
     */
    public static void cleanHistory() {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("search_history", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}

