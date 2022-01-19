package com.luoye.app.api;

import android.content.Context;

/**
 * created by: ls
 * TIME：2021/7/1
 * user：红外封装
 */
public class ConsumerIrManagerApi {


    /**
     *  红外遥控
     *  <uses-permission android:name="android.permission.TRANSMIT_IR" />
     *  是否仅在支持红外的设备上运行
     *  <uses-feature android:name="android.hardware.ConsumerIrManager" android:required="false" />
     *
     */
    private static ConsumerIrManagerApi instance;
    private static android.hardware.ConsumerIrManager service;

    private ConsumerIrManagerApi(Context context) {
        // 获取系统的红外遥控服务
        service = (android.hardware.ConsumerIrManager) context.getApplicationContext().getSystemService(Context.CONSUMER_IR_SERVICE);
    }
    public static ConsumerIrManagerApi getConsumerIrManager(Context context) {
        if (instance == null) {
            instance = new ConsumerIrManagerApi(context);
        }
        return instance;
    }

    /**
     * 手机是否有红外功能
     *
     * @return
     */
    public static boolean hasIrEmitter() {
        //android4.4及以上版本&有红外功能
        if (service != null) {
            return service.hasIrEmitter();
        } else {
            return false;
        }
    }

    /**
     * 发射红外信号
     *
     * @param carrierFrequency 红外频率
     * @param pattern
     */
    public static void transmit(int carrierFrequency, int[] pattern) {
        if (service != null) service.transmit(carrierFrequency, pattern);
    }

    /**
     * 获取可支持的红外信号频率
     *
     * @return
     */
    public static android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        if (service != null) return service.getCarrierFrequencies();
        return null;
    }
}
