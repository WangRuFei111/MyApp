package com.luoye.app.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;


/**
 * created by: ls
 * TIME：2021/6/9
 * user：弹窗基类
 */
public abstract class BaseDialog<T, E extends ViewBinding> extends Dialog {

    protected E binding;
    protected Context context;
    protected Activity activity;
    protected OnBeanDialogListener<T> onBeanDialogListener;
    protected String TAG = "---BaseDialog";

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.activity = (Activity) context;
    }

    public BaseDialog(@NonNull Context context, int themeResId, OnBeanDialogListener<T> onBeanDialogListener) {
        super(context, themeResId);
        this.context = context;
        this.activity = (Activity) context;
        this.onBeanDialogListener = onBeanDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ViewBindingUtil.create(getClass(), LayoutInflater.from(context));
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);//边缘点击消失
        try {
            initDialog();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "BaseDialog_initDialog_Exception:" + e.getMessage());
        }
    }


    protected abstract void initDialog() throws Exception;

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

}
