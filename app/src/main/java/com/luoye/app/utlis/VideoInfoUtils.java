package com.luoye.app.utlis;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.Objects;


public class VideoInfoUtils {

    private static String TAG = "---VideoInfoUtils";


    /**
     * 获取本地视频基础信息
     */
    public static void getVideoInfo(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(path);
            /*时长*/
            Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
            /*速度*/
            Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)));
            /*视频方向*/
            int orientation = Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)));
            if (orientation == 0 || orientation == 180 || orientation == -180 || orientation == 360) {
                /*宽度*/
                Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)));
                /*高度*/
                Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)));
            } else if (orientation == 90 || orientation == -90 || orientation == 270 || orientation == -270) {
                /*宽度*/
                Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)));
                /*高度*/
                Integer.parseInt(Objects.requireNonNull(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)));
            }

            Log.i(TAG, "getVideoInfo: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mmr.release();
        }

    }
}
