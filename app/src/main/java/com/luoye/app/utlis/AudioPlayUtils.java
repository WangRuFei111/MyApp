package com.luoye.app.utlis;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayUtils {

    private static String TAG = "---AudioPlayUtils";
    private MediaPlayer mediaPlayer;
    private Context context;
    /*倒计时*/
    private Timer timer;
    /*视频时长s*/
    private int durationTime = 0;
    /*是否处于暂停*/
    private boolean isPause = false;

    private SeekBar seekBarTime;
    private CheckBox playCheckBox;
    private TextView startTimeTextView;
    private TextView endTimeTextView;


    public AudioPlayUtils(Context context, String audioPath, int durationTime, SeekBar seekBarTime, CheckBox playCheckBox, TextView startTimeTextView, TextView endTimeTextView) {
        this.context = context;
        this.seekBarTime = seekBarTime;
        this.playCheckBox = playCheckBox;
        this.startTimeTextView = startTimeTextView;
        this.endTimeTextView = endTimeTextView;
        this.durationTime = durationTime;
        try {
            File file = new File(audioPath);
            if (file.exists()) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(audioPath);
                mediaPlayer.prepare();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }



    /**
     * 是否在播放
     *
     * @return
     */
    public boolean isPlay() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        } else {
            return false;
        }
    }


    private void seekTo(int time) {
        try {
            if (time < 0) time = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mediaPlayer.seekTo(time, MediaPlayer.SEEK_CLOSEST);
            } else {
                mediaPlayer.seekTo(time);
            }
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*开始播放*/
    public boolean startPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            setSeekBarTime(0);
            return true;
        } else {
            return false;
        }
    }

    /*恢复*/
    public void recoveryPaly() {
        if (isPause) {
            int startTime = seekBarTime.getProgress();
            if (durationTime == seekBarTime.getProgress()) startTime = 0;
            seekTo(startTime * 1000);

            setSeekBarTime(startTime);
            if (playCheckBox != null) playCheckBox.setChecked(true);
            isPause = false;
            Log.i(TAG, "recoveryPaly: 恢复播放成功");
        } else {
            Log.i(TAG, "recoveryPaly: 不在暂停状态");
        }
    }

    /*暂停*/
    public void pausePaly() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
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
        }, (int) (1000), (int) (1000));


        playCheckBox.setChecked(true);
    }

    /*停止*/
    public void stopPaly() {
        if (timer != null) timer.cancel();
        if (mediaPlayer != null) mediaPlayer.stop();
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
                mediaPlayer.seekTo(seekBar.getProgress() * 1000);
                mediaPlayer.start();
                Log.i(TAG, "跳转: " + seekBar.getProgress());
            }
        });


    }


}
