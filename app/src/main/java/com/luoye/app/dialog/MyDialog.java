package com.luoye.app.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.luoye.app.R;
import com.luoye.app.base.BaseDialog;
import com.luoye.app.databinding.DialogMyBinding;

import java.util.Objects;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：测试弹窗
 */
public class MyDialog extends BaseDialog<DialogMyBinding> {

    public MyDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
    }


    @Override
    protected void initDialog() throws Exception {
        setCanceledOnTouchOutside(true);//边缘点击消失
    }

    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams lp = Objects.requireNonNull(getWindow()).getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

        //动画
        Objects.requireNonNull(getWindow()).setWindowAnimations(R.style.dialog_FadeIn);
        getWindow().setGravity(Gravity.CENTER);
    }

}
