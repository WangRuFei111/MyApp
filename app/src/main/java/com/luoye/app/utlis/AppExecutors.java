package com.luoye.app.utlis;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class AppExecutors {

    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;
    private final ScheduledThreadPoolExecutor schedule;
    private static AppExecutors instance;
    private static Object object = new Object();

    public static AppExecutors getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new AppExecutors();
                }
            }
        }
        return instance;
    }

    private AppExecutors() {

        this.mDiskIO = Executors.newSingleThreadExecutor(new MyThreadFactory("single"));

        this.mNetworkIO = Executors.newFixedThreadPool(3, new MyThreadFactory("fixed"));

        this.mMainThread = new MainThreadExecutor();

        this.schedule = new ScheduledThreadPoolExecutor(5, new MyThreadFactory("sc"), new ThreadPoolExecutor.AbortPolicy());
    }

    class MyThreadFactory implements ThreadFactory {

        private final String name;
        private int count = 0;

        MyThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            count++;
            return new Thread(r, name + "-" + count + "-Thread");
        }
    }


    /**
     * 磁盘IO线程池（单线程）
     * <p>
     * 和磁盘操作有关的进行使用此线程(如读写数据库,读写文件)
     * 禁止延迟,避免等待
     * 此线程不用考虑同步问题
     */

    public Executor diskIO() {
        return mDiskIO;
    }

    /**
     * 定时(延时)任务线程池
     * <p>
     * 替代Timer,执行定时任务,延时任务
     */
    public ScheduledThreadPoolExecutor schedule() {
        return schedule;
    }

    /**
     * 网络IO线程池
     * <p>
     * 网络请求,异步任务等适用此线程
     * 不建议在这个线程 sleep 或者 wait
     */
    public Executor networkIO() {
        return mNetworkIO;
    }

    /**
     * UI线程
     * <p>
     * Android 的MainThread
     * UI线程不能做的事情这个都不能做
     */
    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    /**
     * 用法https://blog.csdn.net/weixin_43115440/article/details/90479752
     */
    {
        //UI线程：
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        });


        //磁盘IO线程池
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        });


        //网络IO线程池
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        });


        //延时3秒后执行：
        ScheduledFuture<?> scheduledFuture1 = AppExecutors.getInstance().schedule().schedule(new Runnable() {
            @Override
            public void run() {
                // do something
            }
        }, 3, TimeUnit.SECONDS);

        //5秒后启动第一次,每3秒执行一次(第一次开始执行和第二次开始执行之间间隔3秒)
        ScheduledFuture<?> scheduledFuture2 = AppExecutors.getInstance().schedule().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // do something
            }
        }, 5, 3, TimeUnit.MILLISECONDS);

        //5秒后启动第一次,每3秒执行一次(第一次执行完成和第二次开始之间间隔3秒)
        ScheduledFuture<?> scheduledFuture3 = AppExecutors.getInstance().schedule().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // do something
            }
        }, 5, 3, TimeUnit.MILLISECONDS);


        scheduledFuture1.cancel(false);//取消定时器(等待当前任务结束后，取消定时器)


        scheduledFuture1.cancel(true);//取消定时器(不等待当前任务结束，取消定时器)
    }

}