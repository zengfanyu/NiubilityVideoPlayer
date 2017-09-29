package com.project.fanyuzeng.niubilityvideoplayer.utils;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class okHttpUtils {
    private static final String REQUEST_TAG = "okHttp";

    private static Request buildRequest(String url) {
        if (NetWorkUtils.isNetWorkAvaliable()) {
            return new Request.Builder().tag(REQUEST_TAG).url(url).build();
        } else {
            return null;
        }
    }

    public static void execute(String url, Callback callback) {
        Request request = buildRequest(url);
        execute(request, callback);

    }

    private static void execute(Request request, Callback callback) {
        AppManager.getOkHttpClient().newCall(request).enqueue(callback);

    }
}
