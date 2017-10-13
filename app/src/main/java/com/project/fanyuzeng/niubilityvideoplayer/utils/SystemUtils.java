package com.project.fanyuzeng.niubilityvideoplayer.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.provider.Settings;

/**
 * Created by fanyuzeng on 2017/10/13.
 * Function:
 */

public class SystemUtils {

    public static int getBrightness(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "screen_brightness", -1);
    }

    public static void setBrightness(Context context, int param) {
        Settings.System.putInt(context.getContentResolver(), "screen_brightness", param);
    }

    public static int getDefaultBrightness(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("shared_preferences_light", -1);
    }
}
