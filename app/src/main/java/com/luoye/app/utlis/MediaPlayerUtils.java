package com.luoye.app.utlis;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.File;

public class MediaPlayerUtils {


    private MediaPlayer mediaPlayer;
    public String path;
    public String assPath;

    public MediaPlayerUtils(String path) {
        this.path = path;
        try {
            File file = new File(path);
            if (file.exists()) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MediaPlayerUtils(Context context, String assPath) {
        this.assPath = assPath;
        try {
            AssetFileDescriptor fd = context.getAssets().openFd(assPath);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * 开始播放
     *
     * @return
     */
    public boolean startPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取时间
     *
     * @return
     */
    public int gettime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    /**
     * 停止播放
     *
     * @return
     */
    public boolean stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
            return true;
        } else {
            return false;
        }
    }

}
