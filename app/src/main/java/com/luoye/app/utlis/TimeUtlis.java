package com.luoye.app.utlis;

import android.os.CountDownTimer;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtlis {

    /**
     * 参数一 总时间
     */
    public CountDownTimer countDownTimer = new CountDownTimer( 3000, 1000) {
        @Override
        public void onTick(long l) {
            Log.i("---AbsorbedActivity", "倒计时" + l);
        }
        @Override
        public void onFinish() {
            Log.i("---AbsorbedActivity", "倒计时结束");
        }
    }.start();


    /**
     * @param h 开始小时
     * @param m 开始分钟
     * @param x 运行时间
     * @return 结束时间
     */
    public String time(int h, int m, int x) {
        TimeZone tz = TimeZone.getDefault();
        int H = tz.getRawOffset();
        SimpleDateFormat timeDate = new SimpleDateFormat("kkmm");
        if (h * 3600 >= H / 1000) {
            return timeDate.format(((h * 3600 - H / 1000) + m * 60 + x * 60) * 1000);
        } else {
            return timeDate.format((-(H / 1000 - h * 3600) + m * 60 + x * 60) * 1000);
        }
    }


    /**
     * 获取分位
     *
     * @param seconds
     * @return
     */
    public static String getM(long seconds) {
        return String.format(Locale.getDefault(), "%02d", (seconds % 3600) / 60);
    }

    /**
     * 获取秒位
     *
     * @param seconds
     * @return
     */
    public static String getS(long seconds) {
        return String.format(Locale.getDefault(), "%02d", seconds % 60);
    }

    /**
     * 时间单位转换
     *
     * @param seconds
     * @return
     */
    public static String formatDuration(long seconds) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }



}

