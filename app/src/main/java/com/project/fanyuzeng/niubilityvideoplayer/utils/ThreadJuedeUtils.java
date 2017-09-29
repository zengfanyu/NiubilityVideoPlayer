package com.project.fanyuzeng.niubilityvideoplayer.utils;

import android.os.Looper;

/**
 * Created by fanyuzeng on 2017/9/28.
 * Function:
 */

public class ThreadJuedeUtils {

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
