package com.luoye.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected String TAG = "---BaseFragment";
    protected Context context;
    protected Activity activity;
    protected T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewBindingUtil.create(getClass(), inflater, container);
        context = getActivity();
        activity = getActivity();
        try {
            initFragment();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "BaseFragment_initFragment_Exception:" + e.getMessage());
        }
        return binding.getRoot();
    }

    protected abstract void initFragment() throws Exception;


}
