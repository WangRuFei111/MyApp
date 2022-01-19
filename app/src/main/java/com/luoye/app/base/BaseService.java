package com.luoye.app.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public abstract class BaseService extends Service {

    protected String TAG = "---BaseService";
    protected Context context;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        try {
            initService();
        } catch (Exception e) {
            Log.i(TAG, "BaseService_initService_Exception:" + e.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("noPhone_ss");// 广播的名称
//        myBroadcastReceiver = new MyBroadcastReceiver();
//        registerReceiver(myBroadcastReceiver, intentFilter);
//
        try {
            createNotificationChannel();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onStartCommand:_createNotificationChannel: " + e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }


    protected abstract void createNotificationChannel() throws Exception;


//    private void createNotificationChannel() {
//
//        PendingIntent Pintent = PendingIntent.getBroadcast(this, (int) SystemClock.uptimeMillis(), new Intent("noPhone_ss"), PendingIntent.FLAG_UPDATE_CURRENT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel("important", "Important", NotificationManager.IMPORTANCE_LOW);
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "important")
//                .setContentIntent(Pintent)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("'不玩手机'运行中")// 设置通知中心的标题
//                .setContentText("定时锁机服务运行中");
//        startForeground(1, builder.build());
//
//    }


    // 广播
//    private class MyBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//            startActivity(intent);
//
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //是否清除之前发送的通知
        stopForeground(true);
        //   unregisterReceiver(myBroadcastReceiver);
    }

    protected abstract void initService() throws Exception;

}
