package com.luoye.app.photoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luoye.app.R;

public class PhotoUtils {


    /*
     *#PictureSelector 2.0
     * -keep class com.luck.picture.lib.** { *; }
     *
     * #Ucrop
     * -dontwarn com.yalantis.ucrop**
     * -keep class com.yalantis.ucrop** { *; }
     * -keep interface com.yalantis.ucrop** { *; }
     */

    /*获取相册图片*/
    public static void getPhotoImage(Context context, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create((Activity) context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setPictureUIStyle(getPictureUIStyle())//样式
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .isPreviewImage(false)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isCompress(true)// 是否压缩
                .isGif(false)// 是否显示gif图片


//                .isEnableCrop(true)
//                .withAspectRatio(3, 4)
//                .rotateEnabled(true)
//                .scaleEnabled(true)
                .forResult(onResultCallbackListener);
    }

    /*获取相册图片*/
    public static void getSquareImage(Context context, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create((Activity) context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setPictureUIStyle(getPictureUIStyle())//样式
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .isPreviewImage(false)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isCompress(false)// 是否压缩
                .isGif(false)// 是否显示gif图片
                .isEnableCrop(true)
                .withAspectRatio(1, 1)
                .rotateEnabled(true)
                .scaleEnabled(true)
                .forResult(onResultCallbackListener);
    }


    /*获取相册图片*/
    public static void getCirImage(Context context, OnResultCallbackListener<LocalMedia> onResultCallbackListener) {
        PictureSelector.create((Activity) context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .setPictureUIStyle(getPictureUIStyle())//样式
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .isPreviewImage(false)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isCompress(false)// 是否压缩
                .isGif(false)// 是否显示gif图片
                .isEnableCrop(true)
                .circleDimmedLayer(true)// 是否开启圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)//是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .rotateEnabled(true)//裁剪是否可旋转图片
                .hideBottomControls(true)
                .scaleEnabled(true)//裁剪是否可放大缩小图片

                .withAspectRatio(1, 1)
                .forResult(onResultCallbackListener);
    }


    public static PictureSelectorUIStyle getPictureUIStyle() {

        PictureSelectorUIStyle uiStyle = new PictureSelectorUIStyle();
        uiStyle.picture_statusBarBackgroundColor = Color.parseColor("#ffffff");
        uiStyle.picture_container_backgroundColor = Color.parseColor("#ffffff");

        uiStyle.picture_navBarColor = Color.parseColor("#ffffff");

//        uiStyle.picture_check_style = R.drawable.picture_wechat_num;
//        uiStyle.picture_top_leftBack = R.mipmap.jiantou_l;
        uiStyle.picture_top_titleRightTextColor = new int[]{Color.parseColor("#000000"), Color.parseColor("#000000")};
        uiStyle.picture_top_titleRightTextSize = 14;
        uiStyle.picture_top_titleTextSize = 18;
        uiStyle.picture_top_titleArrowUpDrawable = R.drawable.picture_icon_wechat_up;
        uiStyle.picture_top_titleArrowDownDrawable = R.drawable.picture_icon_wechat_down;
        uiStyle.picture_top_titleTextColor = Color.parseColor("#000000");
        uiStyle.picture_top_titleBarBackgroundColor = Color.parseColor("#ffffff");

        uiStyle.picture_album_textSize = 16;
        uiStyle.picture_album_backgroundStyle = R.drawable.picture_item_select_bg;
        uiStyle.picture_album_textColor = Color.parseColor("#4d4d4d");
        uiStyle.picture_album_checkDotStyle = R.drawable.picture_orange_oval;

        uiStyle.picture_bottom_previewTextSize = 14;
        uiStyle.picture_bottom_previewTextColor = new int[]{Color.parseColor("#000000"), Color.parseColor("#FF5C7C")};

//        uiStyle.picture_bottom_preview_editorTextSize = 14;
//        uiStyle.picture_bottom_preview_editorTextColor = Color.parseColor("#000000");

        uiStyle.picture_bottom_completeRedDotTextSize = 12;
        uiStyle.picture_bottom_completeTextSize = 14;
        uiStyle.picture_bottom_completeRedDotTextColor = Color.parseColor("#ffffff");
        uiStyle.picture_bottom_completeRedDotBackground = R.drawable.picture_num_oval;
        uiStyle.picture_bottom_completeTextColor = new int[]{Color.parseColor("#000000"), Color.parseColor("#FF5C7C")};
        uiStyle.picture_bottom_barBackgroundColor = Color.parseColor("#ffffff");


        uiStyle.picture_adapter_item_camera_backgroundColor = Color.parseColor("#ffffff");
        uiStyle.picture_adapter_item_camera_textColor = Color.parseColor("#000000");
        uiStyle.picture_adapter_item_camera_textSize = 14;
        uiStyle.picture_adapter_item_camera_textTopDrawable = R.drawable.picture_icon_camera;

        uiStyle.picture_adapter_item_textSize = 12;
        uiStyle.picture_adapter_item_textColor = Color.parseColor("#000000");
        uiStyle.picture_adapter_item_video_textLeftDrawable = R.drawable.picture_icon_video;
        uiStyle.picture_adapter_item_audio_textLeftDrawable = R.drawable.picture_icon_audio;

        uiStyle.picture_bottom_originalPictureTextSize = 14;
        uiStyle.picture_bottom_originalPictureCheckStyle = R.drawable.picture_original_wechat_checkbox;
        uiStyle.picture_bottom_originalPictureTextColor = Color.parseColor("#000000");
        uiStyle.picture_bottom_previewNormalText = R.string.picture_preview_num;
        uiStyle.picture_bottom_completeDefaultText = R.string.picture_please_select;
        uiStyle.picture_bottom_completeNormalText = R.string.picture_completed;
        uiStyle.picture_adapter_item_camera_text = R.string.picture_take_picture;
        uiStyle.picture_top_titleRightDefaultText = R.string.picture_cancel;
        uiStyle.picture_top_titleRightNormalText = R.string.picture_cancel;
        uiStyle.picture_bottom_previewDefaultText = R.string.picture_preview;

        uiStyle.picture_switchSelectNumberStyle = true;
        // 如果文本内容设置(%1$d/%2$d)，请开启true
        uiStyle.isCompleteReplaceNum = true;
        Context appContext = PictureAppMaster.getInstance().getAppContext();
        if (appContext != null) {
            uiStyle.picture_top_titleBarHeight = ScreenUtils.dip2px(appContext, 48);
            uiStyle.picture_bottom_barHeight = ScreenUtils.dip2px(appContext, 45);
        }
        return uiStyle;
    }

//    PictureSelector.create(this)
//   .openGallery()//相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
//   .openCamera()//单独使用相机 媒体类型 PictureMimeType.ofImage()、ofVideo()
//   .theme()// xml样式配制 R.style.picture_default_style、picture_WeChat_style or 更多参考Demo
//   .imageEngine()// 图片加载引擎 需要 implements ImageEngine接口
//   .compressEngine() // 自定义图片压缩引擎
//   .selectionMode()//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
//   .isPageStrategy()//开启分页模式，默认开启另提供两个参数；pageSize每页总数；isFilterInvalidFile是否过滤损坏图片
//   .isSingleDirectReturn()//PictureConfig.SINGLE模式下是否直接返回
//   .isWeChatStyle()//开启R.style.picture_WeChat_style样式
//   .setPictureStyle()//动态自定义相册主题
//   .setPictureCropStyle()//动态自定义裁剪主题
//   .setPictureWindowAnimationStyle()//相册启动退出动画
//   .isCamera()//列表是否显示拍照按钮
//   .isZoomAnim()//图片选择缩放效果
//   .imageFormat()//拍照图片格式后缀,默认jpeg, PictureMimeType.PNG，Android Q使用PictureMimeType.PNG_Q
//   .setCameraImageFormat(PictureMimeType.JPEG)// 相机图片格式后缀,默认.jpeg
//   .setCameraVideoFormat(PictureMimeType.MP4)// 相机视频格式后缀,默认.mp4
//   .setCameraAudioFormat(PictureMimeType.AMR)// 录音音频格式后缀,默认.amr
//   .maxSelectNum()//最大选择数量,默认9张
//   .minSelectNum()// 最小选择数量
//   .maxVideoSelectNum()//视频最大选择数量
//   .minVideoSelectNum()//视频最小选择数量
//   .videoMaxSecond()// 查询多少秒以内的视频
//   .videoMinSecond()// 查询多少秒以内的视频
//   .imageSpanCount()//列表每行显示个数
//   .openClickSound()//是否开启点击声音
//   .selectionMedia()//是否传入已选图片
//   .recordVideoSecond()//录制视频秒数 默认60s
//   .filterMinFileSize() // 过滤最小的文件
//   .filterMaxFileSize() // 过滤最大的文件
//   .queryMimeTypeConditions(PictureMimeType.ofJPEG()) // 只查询什么类型的文件
//   .previewEggs()//预览图片时是否增强左右滑动图片体验
//   .cropCompressQuality()// 注：已废弃 改用cutOutQuality()
//   .isGif()//是否显示gif
//   .previewImage()//是否预览图片
//   .previewVideo()//是否预览视频
//   .enablePreviewAudio()//是否预览音频
//   .enableCrop()//是否开启裁剪
//   .cropWH()// 裁剪宽高比,已废弃，改用. cropImageWideHigh()方法
//   .cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//   .withAspectRatio()//裁剪比例
//   .cutOutQuality()// 裁剪输出质量 默认100
//   .freeStyleCropEnabled()//裁剪框是否可拖拽
//   .freeStyleCropMode(OverlayView.DEFAULT_FREESTYLE_CROP_MODE)// 裁剪框拖动模式
//   .isCropDragSmoothToCenter(true)// 裁剪框拖动时图片自动跟随居中
//   .circleDimmedLayer()// 是否开启圆形裁剪
//   .setCircleDimmedColor()//设置圆形裁剪背景色值
//   .setCircleDimmedBorderColor()//设置圆形裁剪边框色值
//   .setCircleStrokeWidth()//设置圆形裁剪边框粗细
//   .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//   .showCropGrid()//是否显示裁剪矩形网格 圆形裁剪时建议设为false
//   .rotateEnabled()//裁剪是否可旋转图片
//   .scaleEnabled()//裁剪是否可放大缩小图片
//   .isDragFrame()//是否可拖动裁剪框(固定)
//   .hideBottomControls()//显示底部uCrop工具栏
//   .basicUCropConfig()//对外提供ucrop所有的配制项
//   .compress()//是否压缩
//   .compressEngine()// 自定义压缩引擎
//   .compressFocusAlpha()//压缩后是否保持图片的透明通道
//   .minimumCompressSize()// 小于多少kb的图片不压缩
//   .videoQuality()//视频录制质量 0 or 1
//   .compressQuality()//图片压缩后输出质量
//   .synOrAsy()//开启同步or异步压缩
//   .queryMaxFileSize()//查询指定大小内的图片、视频、音频大小，单位M
//   .compressSavePath()//自定义压缩图片保存地址，注意Q版本下的适配
//   .sizeMultiplier()//glide加载大小，已废弃
//   .glideOverride()//glide加载宽高，已废弃
//   .isMultipleSkipCrop()//多图裁剪是否支持跳过
//   .isMultipleRecyclerAnimation()// 多图裁剪底部列表显示动画效果
//   .querySpecifiedFormatSuffix()//只查询指定后缀的资源，PictureMimeType.ofJPEG() ...
//   .isReturnEmpty()//未选择数据时按确定是否可以退出
//   .isAndroidQTransform()//Android Q版本下是否需要拷贝文件至应用沙盒内
//   .setRequestedOrientation()//屏幕旋转方向 ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED ...
//   .isOriginalImageControl()//开启原图选项
//   .bindCustomPlayVideoCallback()//自定义视频播放拦截
//   .bindCustomCameraInterfaceListener()//自定义拍照回调接口
//   .bindCustomPreviewCallback()// 自定义图片预览回调接口
//   .cameraFileName()//自定义拍照文件名，如果是相册内拍照则内部会自动拼上当前时间戳防止重复
//   .renameCompressFile()//自定义压缩文件名，多张压缩情况下内部会自动拼上当前时间戳防止重复
//   .renameCropFileName()//自定义裁剪文件名，多张裁剪情况下内部会自动拼上当前时间戳防止重复
//   .setRecyclerAnimationMode()//列表动画效果,AnimationType.ALPHA_IN_ANIMATION、SLIDE_IN_BOTTOM_ANIMATION
//   .isUseCustomCamera()// 开启自定义相机
//   .setButtonFeatures()// 自定义相机按钮状态,CustomCameraView.BUTTON_STATE_BOTH
//   .setLanguage()//国际化语言 LanguageConfig.CHINESE、ENGLISH、JAPAN等
//   .isWithVideoImage()//图片和视频是否可以同选,只在ofAll模式下有效
//   .isMaxSelectEnabledMask()//选择条件达到阀时列表是否启用蒙层效果
//   .isAutomaticTitleRecyclerTop()//图片列表超过一屏连续点击顶部标题栏快速回滚至顶部
//   .loadCacheResourcesCallback()//获取ImageEngine缓存图片，参考Demo
//   .setOutputCameraPath()// 自定义相机输出目录只针对Android Q以下版本，具体参考Demo
//   .forResult();//结果回调分两种方式onActivityResult()和OnResultCallbackListener方式


    // 从v2.4.5开始新增 Callback回调方式返回结果...
//
//  .forResult(new OnResultCallbackListener() {
//        @Override
//        public void onResult(List<LocalMedia> result) {
//            // 例如 LocalMedia 里面返回五种path
//            // 1.media.getPath(); 原图path，但在Android Q版本上返回的是content:// Uri类型
//            // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
//            // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
//            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
//            // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路
//            径；注意：.isAndroidQTransform(false);此字段将返回空
//            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩 （有问题）
//            // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，
//            如旋转角度、经纬度等信息
//
//            for (LocalMedia media : result) {
//                Log.i(TAG, "是否压缩:" + media.isCompressed());
//                Log.i(TAG, "压缩:" + media.getCompressPath());
//                Log.i(TAG, "原图:" + media.getPath());
//                Log.i(TAG, "是否裁剪:" + media.isCut());
//                Log.i(TAG, "裁剪:" + media.getCutPath());
//                Log.i(TAG, "是否开启原图:" + media.isOriginal());
//                Log.i(TAG, "原图路径:" + media.getOriginalPath());
//                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
//            }
//        }
//
//        @Override
//        public void onCancel() {
//            Log.i(TAG, "PictureSelector Cancel");
//        }
//
//    });
}
