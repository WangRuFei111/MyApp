package com.luoye.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.luoye.app.base.BaseActivity;
import com.luoye.app.databinding.ActivityCamera2Binding;
import com.mylibrary.camera2.listener.JCameraListener;

import java.io.File;

public class Camera2Activity extends BaseActivity<ActivityCamera2Binding> {


    @Override
    protected void initActivity() throws Exception {
        //设置视频缓存路径
        binding.JCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");

        //JCameraView监听
        binding.JCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                Toast.makeText(context, "拍照", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                Toast.makeText(context, "录像:" + url, Toast.LENGTH_SHORT).show();

            }
        });

    }


}