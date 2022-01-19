package com.xiaopo.flying.sticker.shuoming;

/**
 * created by: ls
 * TIME：2021/6/10
 * user：贴纸使用说明
 */
public class text {
    /**
     *
     * 参考：https://github.com/wuapnjie/StickerView
     * 
     *<com.xiaopo.flying.sticker.StickerView
     *             android:id="@+id/stickerView"
     *             android:layout_width="wrap_content"
     *             android:layout_height="wrap_content"
     *             app:showBorder="true"
     *             app:showIcons="true" />
     *
     * 设置贴纸控件样式
     * BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_close_white_18dp), BitmapStickerIcon.LEFT_TOP);
     * deleteIcon.setIconEvent(new DeleteIconEvent());
     * BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_scale_white_18dp), BitmapStickerIcon.RIGHT_BOTOM);
     * zoomIcon.setIconEvent(new ZoomIconEvent());
     * BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_flip_white_18dp), BitmapStickerIcon.RIGHT_TOP);
     * flipIcon.setIconEvent(new FlipHorizontallyEvent());
     * stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));
     * 状态回调
     * stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {});
     *
     * 回调的 onStickerNotClicked() 加入stickerView.invalidate(); 触发点击空白全部取消选中
     *
     * 添加   stickerView.addSticker(sticker)
     *
     * 替换    stickerView.replace(sticker);
     *
     * 图片类 sticker= new DrawableSticker((new BitmapDrawable(this.getResources(), bitmap)))
     *
     * 文字类
     *  textSticker = new TextSticker(this);
     *         textSticker.setText("123");
     *         textSticker.setTextColor(color);
     *         textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
     *         textSticker.resizeText();//必须添加
     *
     * 定义类型（Id）
     * sticker.setType(2);
     */
}
