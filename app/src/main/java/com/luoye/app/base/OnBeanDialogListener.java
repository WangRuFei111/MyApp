package com.luoye.app.base;

public interface OnBeanDialogListener<T> {
    void info(T t, int pos);

    void info(T t);

    void info(int pos);
}
