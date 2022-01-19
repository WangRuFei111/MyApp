package com.luoye.app.base;

/**
 * Created by：luoye
 * Date：2022/1/18
 * 介绍：
 */
public abstract class OnAdapterListener<T> {

    public abstract void onClick(T t);

    void info(int pos);

}
