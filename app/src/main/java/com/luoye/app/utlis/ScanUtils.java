package com.luoye.app.utlis;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 万能扫描工具
 */
public class ScanUtils {

    private Context context;
    /*扫描线程*/
    private Thread scanThread;
    /*定时器  用于定时检测扫描线程的状态*/
    private Timer scanTimer;
    /*检测扫描线程的任务*/
    private TimerTask scanTask;
    /*需要扫描文件的类型*/
    private String[] endFilter = {".jpg", ".jpeg", ".png", ".webp"};
    /*符合条件的文件*/
    private List<String> listPath = new ArrayList<>();
    /*扫描完场回调*/
    private scanState scanState;

    public interface scanState {
        void end(List<String> list);
    }


    public ScanUtils(Context context) {
        this.context = context;
    }

    public ScanUtils(Context context, String[] endFilter) {
        this.endFilter = endFilter;
        this.context = context;
    }

    /*开始扫描*/
    public void startScan(scanState scanState) {
        this.scanState = scanState;
        scanThread = new Thread(new Runnable() {
            @Override
            public void run() {
                searchType();
            }
        });



        /*判断扫描是否完成 其实就是个定时任务 时间可以自己设置  每2s获取一下扫描线程的状态  如果线程状态为结束就说明扫描完成*/
        scanTimer = new Timer();
        scanTask = new TimerTask() {
            @Override
            public void run() {
                Log.i("---ScanUtils", "线程状态:" + scanThread.getState().toString());
                if (scanThread.getState() == Thread.State.TERMINATED) {
                    /*说明扫描线程结束 扫描完成 接口回调*/
                    Log.i("---ScanUtils", "线程结束,扫描完成");

                    ScanUtils.this.scanState.end(listPath);
                    //结束线程
                    cancelTask();
                }
            }
        };

        scanTimer.schedule(scanTask, 0, 1000);
        /*开始扫描*/
        scanThread.start();


    }

    /*结束扫描*/
    public void cancelTask() {

        Log.i("---ScanUtils", "结束任务");
        if (scanTask != null) {
            scanTask.cancel();
        }

        if (scanTimer != null) {
            scanTimer.purge();
            scanTimer.cancel();
        }
    }


    /*扫描*/
    private void searchType() {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = resolver.query(uri,
                new String[]{MediaStore.Files.FileColumns.DATA},
                null,
                null,
                null);


        if (cursor == null) {
            Log.i("---ImageUtlis", "mCursor == null");
            return;
        }
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            for (String endFilter : endFilter) {
                if (new File(path).getName().toUpperCase().endsWith(endFilter.toUpperCase())) {
                    /*是符合后缀名的文件*/
                    listPath.add(path);
                    Log.i("---ImageUtlis", "符合要求文件" + path);
                }
            }
        }
        cursor.close();
    }

}
