package com.luoye.app.utlis;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 万能扫描工具
 */
public class ScanTool {
    private static final String TAG = "---ScanTool";


    /*需要扫描文件的类型*/
    private static String[] arrTypeSuffix = {".xlsx"};
    private static final String selectionModel = MediaStore.Files.FileColumns.DATA + " LIKE '%";
    public interface OnScanListener {
        void complete(List<String> listOutPath);

        void error();
    }


    /*开始扫描*/
    public static void startScan(Context context, String[] arrTypeSuffix, OnScanListener onScanListener) {
        ScanTool.arrTypeSuffix = arrTypeSuffix;
        int token = 0;


        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection = new String[]{MediaStore.Files.FileColumns.DATA};

        StringBuilder selection = new StringBuilder();

        selection.append("(");
        for (int i = 0; i < arrTypeSuffix.length; i++) {
            if (i == 0) {
                selection.append(selectionModel).append(arrTypeSuffix[i]).append("'");
            } else if (i < arrTypeSuffix.length - 1) {
                selection.append(" or ").append(selectionModel).append(arrTypeSuffix[i]).append("'");
            } else {
                selection.append(" or ").append(selectionModel).append(arrTypeSuffix[i]).append("'").append(")");
            }
        }
        Log.i(TAG, "startScan: " + selection.toString());

        String[] selectionArgs = null;
        String orderBy = null;// MediaStore.Files.FileColumns.DATE_ADDED + "DESC";
        new QueryHandler(context.getContentResolver()).startQuery(
                token,  //一个令牌，主要用来标识查询,保证唯一即可
                onScanListener, //你想传给onXXXComplete方法使用的一个对象。(没有的话传递null即可)
                uri, //进行查询的通用资源标志符
                projection, //projection查询的列
                selection.toString(),  //限制条件
                selectionArgs, //查询参数
                orderBy //排序条件
        );
    }


    private static class QueryHandler extends AsyncQueryHandler {

        public QueryHandler(ContentResolver cr) {
            super(cr);
        }


        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            if (cursor != null && token == 0) {
                List<String> listPath = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                    //过滤文件防止发生意外
//                    for (String typeSuffix : arrTypeSuffix)
//                        if (new File(path).getName().toUpperCase().endsWith(typeSuffix.toUpperCase()))
                         listPath.add(path);
                }
                if (cookie != null) ((OnScanListener) cookie).complete(listPath);
                cursor.close();
            } else {
                if (cookie != null && token == 0) ((OnScanListener) cookie).error();
            }

        }

    }


}
