package com.luoye.app.utlis;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by: ls
 * TIME：2021/7/12
 * user：可以动态改变的循环定时器
 */
public class SetTimerTask {

    private static final String TAG = "---SetTimerTask";

    private static SetTimerTask setTimerTask;
    private boolean isRun = false;


    public SetTimerTask() {

    }

    //单例模式
    public static synchronized SetTimerTask getInstance() {
        if (setTimerTask == null) setTimerTask = new SetTimerTask();
        return setTimerTask;
    }


    private MyTimerTask myTimerTask;
    private Timer timer;
    private long cycle = 1000;//周期


    public void setOnClockInterface(OnClockInterface onClockInterface) {
        this.onClockInterface = onClockInterface;
    }

    public void setOnClockInterfaceService(OnClockInterfaceService onClockInterfaceService) {
        this.onClockInterfaceService = onClockInterfaceService;
    }


    public void startTimerTask() {
        if (myTimerTask == null && timer == null) {
            isRun = true;
            myTimerTask = new MyTimerTask();
            timer = new Timer();
            timer.schedule(myTimerTask, 1, cycle);
            Log.i(TAG, "cycle:" + cycle);
            if (onClockInterface != null) onClockInterface.outStart();
        }
    }

    /*设置周期*/
    public boolean setTimeCycle(long cycle) {
        this.cycle = cycle;
        if (myTimerTask != null) {
            myTimerTask.setPeriod(cycle);
            return true;
        } else {
            return false;
        }
    }

    /*获取周期*/
    public long getTimeCycle() {
        return cycle;
    }


    /*结束*/
    public void stop() {
        if (myTimerTask != null && timer != null) {
            timer.cancel();
            timer = null;
            isRun = false;
            myTimerTask = null;
            onClockInterface.outStop();
        }
    }


    public boolean isRun() {
        return isRun;
    }


    /*接口返回 计时器*/
    private OnClockInterface onClockInterface;

    public interface OnClockInterface {
        void outStart();

        void outTime();

        void outStop();
    }

    /*接口返回 计时器*/
    private OnClockInterfaceService onClockInterfaceService;

    public interface OnClockInterfaceService {
        void outTime();
    }


    /*通过Java反射机制动态修改TimerTask的执行周期并立即生效*/
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }

        public void setPeriod(long period) {
            Date now = new Date();
            // 设置下一次执行时间
            long nextExecutionTime = now.getTime() + period;
            setDeclaredField(TimerTask.class, this, "nextExecutionTime", nextExecutionTime);
            // 修改执行周期
            setDeclaredField(TimerTask.class, this, "period", period);
        }

        //通过反射修改字段的值
        boolean setDeclaredField(Class<?> clazz, Object obj, String name, Object value) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                field.set(obj, value);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    if (onClockInterface != null) onClockInterface.outTime();
                    if (onClockInterfaceService != null) onClockInterfaceService.outTime();
                    break;
            }
        }
    };

}
