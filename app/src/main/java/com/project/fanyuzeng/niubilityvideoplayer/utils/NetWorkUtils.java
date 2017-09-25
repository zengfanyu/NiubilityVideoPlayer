package com.project.fanyuzeng.niubilityvideoplayer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class NetWorkUtils {
    public static boolean isNetWorkAvaliable() {
        ConnectivityManager cm = (ConnectivityManager) AppManager.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }
}
