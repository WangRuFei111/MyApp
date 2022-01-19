package com.luoye.app.utlis;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import java.util.Objects;

public class HomeUtils {


    private static final String TAG = "---HomeUtils";

    /*是否位于桌面*/
    public static boolean isHome(Context context) {
        return getHome(context).equals(getTopPackageName(context));
    }

    /*获取桌面包名*/
    private static String getHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ActivityInfo activityInfo = Objects.requireNonNull(context.getPackageManager().resolveActivity(intent, 0)).activityInfo;
        if (activityInfo == null || activityInfo.packageName.equals("android")) {
            return "";
        } else {
            return activityInfo.packageName;
        }
    }

    /*获取显示的app包名*/
    private static String getTopPackageName(Context context) {
        String topPackageName = "";
        long currentTimeMillis = System.currentTimeMillis() + ((long) 10000);
        int i = 1;
        while (true) {
            Log.i(TAG, "getTopPackageName: ");
            UsageStatsManager systemService = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            UsageEvents queryEvents = systemService.queryEvents(currentTimeMillis - (long) (60000 * i), currentTimeMillis);
            while (queryEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                queryEvents.getNextEvent(event);
                if (event.getEventType() == 1) topPackageName = event.getPackageName();
                event.getEventType();
            }
            if (!topPackageName.equals("") || i > 10000) return topPackageName;
            i *= 10;


        }
    }

}
