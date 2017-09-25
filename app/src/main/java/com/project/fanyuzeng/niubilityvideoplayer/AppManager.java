package com.project.fanyuzeng.niubilityvideoplayer;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class AppManager extends Application {
    private static Gson sGson;
    private static OkHttpClient sOkHttpClient;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sGson = new Gson();
        sOkHttpClient = new OkHttpClient();
    }

    public static Gson getGson() {
        return sGson;
    }

    public static OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

    public static Context getContext() {
        return sContext;
    }

    public static Resources getResource() {
        return sContext.getResources();
    }



}
