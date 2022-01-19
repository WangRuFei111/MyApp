package com.luoye.app.utlis;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.luoye.app.view.MyVideoView;
import com.xiaopo.flying.sticker.StickerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class VideoPlayUtils {
    private static String TAG = "---VideoPlayUtils";

    private Context context;


    private MyVideoView myVideoView;
    private SeekBar seekBarTime;
    private CheckBox playCheckBox;
    private TextView startTimeTextView;
    private TextView endTimeTextView;


    /*倒计时*/
    private Timer timer;
    /*视频时长s*/
    private int durationTime = 0;
    /*视频路径*/
    private String videoPath = null;
    /*是否处于暂停*/
    private boolean isPause = false;
    /*是否初始化完毕*/
    private boolean isMyVideoViewinit = false;
    /*视频速度*/
    private float speedVideo = 1f;


    public VideoPlayUtils(Context context, String videoPath, int durationTime, SeekBar seekBarTime, CheckBox playCheckBox, TextView startTimeTextView, TextView endTimeTextView) {
        this.context = context;
        this.seekBarTime = seekBarTime;
        this.playCheckBox = playCheckBox;
        this.startTimeTextView = startTimeTextView;
        this.endTimeTextView = endTimeTextView;
        this.durationTime = durationTime;
        this.videoPath = videoPath;
        init();
    }


    private void init() {

        playCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playCheckBox.isChecked()) {
                    recoveryPaly();
                } else {
                    pausePaly();
                }
            }
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (startTimeTextView != null)
                    startTimeTextView.setText(parseDate(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (timer != null) timer.cancel();
                setSeekBarTime(seekBar.getProgress());
                myVideoView.seekTo(seekBar.getProgress() * 1000);
                myVideoView.start();
                Log.i(TAG, "跳转: " + seekBar.getProgress());
            }
        });


    }


    /*更改 信息*/
    public void setVideoInif(String videoPath, int durationTime) {
        this.videoPath = videoPath;
        this.durationTime = durationTime;
    }

    /*设置视频UI大小*/
    public void setMyVideoViewXY(float x, float y) {


        if (myVideoView != null) {

            if (isMyVideoViewinit) {

                //视频播放 大小适应
                float videoWidth = myVideoView.getVideoWidth();//获取视频宽度
                float videoHeight = myVideoView.getVideoHeight();

                float ra = videoWidth * 1f / videoHeight;
                ViewGroup.LayoutParams layoutParams = myVideoView.getLayoutParams();
                if (videoWidth > videoHeight) {
                    layoutParams.width = (int) x;
                    layoutParams.height = (int) (x / ra);
                } else {
                    layoutParams.width = (int) (y * ra);
                    layoutParams.height = (int) y;
                }
                myVideoView.setLayoutParams(layoutParams);
            } else {

                Log.i(TAG, "setMyVideoViewXY: 尚未初始化完毕");
            }
        } else {
            Log.i(TAG, "setMyVideoViewXY: myVideoView==null");

        }


    }

    /*播放-不循环-自适应大小变化**/
    public void playVideo(MyVideoView myVideoView) {
        this.myVideoView = myVideoView;
        /*设置播放地址*/
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", new File(videoPath));
        myVideoView.setVideoPath(uri);
        //视频准备完成
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {



                /*开启循环*/
                myVideoView.setLooping(false);
                /*开始*/
                myVideoView.start();


                isMyVideoViewinit = true;

                setSeekBarTime(0);

                //视频播放 大小适应
                float videoWidth = myVideoView.getVideoWidth();//获取视频宽度
                float videoHeight = myVideoView.getVideoHeight();

                float widthF = 0.7f;
                float ra = videoWidth * 1f / videoHeight;
                if (ra >= 1) widthF = 0.9f;
                Log.i(TAG, "onPrepared: ra:" + ra);
                ViewGroup.LayoutParams layoutParams = myVideoView.getLayoutParams();
                layoutParams.width = (int) (((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * widthF);
                layoutParams.height = (int) (layoutParams.width / ra);

                myVideoView.setLayoutParams(layoutParams);


            }
        });
    }

    /*播放-不循环-自适应  stickerView跟随MyVideoView大小变化*/
    public void playVideo(MyVideoView myVideoView, StickerView stickerView) {


        Log.i(TAG, "playVideo: 准备播放");
        /*设置播放地址*/
        Uri uri = FileProvider.getUriForFile(context, "com.maoying.makevideo.fileprovider", new File(videoPath));
        myVideoView.setVideoPath(uri);
        //视频准备完成
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {


                /*开启循环*/
                myVideoView.setLooping(false);
                /*开始*/
                myVideoView.start();


                isMyVideoViewinit = true;

                setSeekBarTime(0);

                //视频播放 大小适应
                float videoWidth = myVideoView.getVideoWidth();//获取视频宽度
                float videoHeight = myVideoView.getVideoHeight();

                float widthF = 0.7f;
                float ra = videoWidth * 1f / videoHeight;
                if (ra >= 1) widthF = 0.9f;
                Log.i(TAG, "onPrepared: ra:" + ra);
                ViewGroup.LayoutParams layoutParams = myVideoView.getLayoutParams();
                layoutParams.width = (int) (((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * widthF);
                layoutParams.height = (int) (layoutParams.width / ra);
                myVideoView.setLayoutParams(layoutParams);


                ViewGroup.LayoutParams layoutParams1 = stickerView.getLayoutParams();
                layoutParams1.width = (int) (((Activity) context).getWindowManager().getDefaultDisplay().getWidth() * widthF);
                layoutParams1.height = (int) (layoutParams.width / ra);
                stickerView.setLayoutParams(layoutParams1);

            }
        });
    }


    public void setPlayerSpeed(float speed) {
        this.speedVideo = speed;
        if (myVideoView != null) {
            myVideoView.setPlayerSpeed(speedVideo);
            setSeekBarTime(0);

        }
    }

    /*计时器UI*/
    public void setSeekBarTime(int startTime) {
        if (timer != null) timer.cancel();

        seekBarTime.setMax(durationTime);
        seekBarTime.setProgress(startTime);
        endTimeTextView.setText(parseDate(durationTime));
        /*第一个参数表示总时间，第二个参数表示间隔时间*/
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int time = seekBarTime.getProgress() + 1;
                        seekBarTime.setProgress(time);
                        startTimeTextView.setText(parseDate(time));

                        if (time >= durationTime) {
                            timer.cancel();
                            playCheckBox.setChecked(false);
                            isPause = true;
                            Log.i(TAG, "run: 倒计时结束");
                        }

                    }
                });


            }
        }, (int) (1000 / speedVideo), (int) (1000 / speedVideo));


        playCheckBox.setChecked(true);
    }

    /*恢复*/
    public void recoveryPaly() {
        if (isPause) {
            int startTime = seekBarTime.getProgress();
            if (durationTime == seekBarTime.getProgress()) startTime = 0;
            myVideoView.seekTo(startTime * 1000);
            setSeekBarTime(startTime);
            myVideoView.start();
            if (playCheckBox != null) playCheckBox.setChecked(true);
            isPause = false;
            Log.i(TAG, "recoveryPaly: 恢复播放成功");
        } else {
            Log.i(TAG, "recoveryPaly: 不在暂停状态");
        }
    }

    /*暂停*/
    public void pausePaly() {
        if (myVideoView != null) {
            if (myVideoView.isPlaying()) {
                myVideoView.pause();
                if (timer != null) timer.cancel();
                if (playCheckBox != null) playCheckBox.setChecked(false);
                isPause = true;
                Log.i(TAG, "pausePaly: 暂停成功");
            } else {
                Log.i(TAG, "pausePaly: 不在播放状态");
            }
        } else {
            Log.i(TAG, "pausePaly: myVideoView == null");
        }
    }

    /*停止*/
    public void stopPaly() {
        if (timer != null) timer.cancel();
        if (myVideoView != null) myVideoView.stop();
        Log.i(TAG, "stopPaly: 停止");

    }

    /*秒转  200:05*/
    public static String parseDate(int times) {
        int s = times % 60;
        long ms = s * 1000 - TimeZone.getDefault().getRawOffset();
        SimpleDateFormat formatter = new SimpleDateFormat(":ss");
        int m = ((times - s) / 60);
        if (m < 10) {
            return "0" + m + formatter.format(ms);
        } else {
            return m + formatter.format(ms);
        }
    }

}
