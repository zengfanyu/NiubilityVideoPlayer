package com.project.fanyuzeng.niubilityvideoplayer.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by fanyuzeng on 2017/10/12.
 * Function:
 */

public class DateUtils {
    private static final String TAG = "DateUtils";

    public static String getCurremntTime() {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        return format.format(curDate);

    }

    public static String stringForTime(int timeMs, Formatter formatter, StringBuilder formatterBuilder) {
        int totalSeconds = timeMs / 1000;
        int second = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        Log.d(TAG, "stringForTime " + "totalSeconds:" + totalSeconds + ",second:" + second + ",minutes:" + minutes + ",hours:" + hours);
        formatterBuilder.setLength(0);

        if (hours > 0) {
            return formatter.format("%d:%02d:%02d", hours, minutes, second).toString();
        } else {
            return formatter.format("%02d:%02d", minutes, second).toString();
        }

    }


}
