package com.luoye.app.utlis;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * created by: ls
 * TIME：2021/6/29
 * user：多文件分享
 */
public class ShareListUtls {

    /**
     * @param context
     * @param imageUris
     */
    public static void sendMoreImage(Context context, ArrayList<Uri> imageUris) {
        Intent mulIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        mulIntent.setType("image/*");
        context.startActivity(Intent.createChooser(mulIntent, "分享"));
    }

    /**
     * @param filePaths
     * @param context
     */
    public static void ShareFileArr(List<String> filePaths, Context context) {
        ArrayList<Uri> imgUris = new ArrayList<Uri>();   //使用集合保存
        for (String s : filePaths) {
            Uri imgUri1 = getImageContentUri(context, s);
            imgUris.add(imgUri1);
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUris);
        shareIntent.setType("image/*");
        shareIntent = Intent.createChooser(shareIntent, "分享到");
        context.startActivity(shareIntent);
    }


    public static Uri getImageContentUri(Context context, String filePath) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        Uri uri = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                Uri baseUri = Uri.parse("content://media/external/images/media");
                uri = Uri.withAppendedPath(baseUri, "" + id);
            }
            cursor.close();
        }
        if (uri == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        return uri;
    }
}
