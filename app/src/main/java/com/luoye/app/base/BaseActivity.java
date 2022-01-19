package com.luoye.app.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;


public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    protected static String TAG = "---";
    protected Context context;
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ViewBindingUtil.create(getClass(), LayoutInflater.from(this));
        setContentView(binding.getRoot());
        context = this;
        try {
            initActivity();
        } catch (Exception e) {
            Log.i(TAG, "BaseActivity_initActivity_Exception:" + e.getMessage());
        }
    }

    protected abstract void initActivity() throws Exception;

}
