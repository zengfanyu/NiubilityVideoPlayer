package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.text.TextUtils;

import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.utils.okHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public abstract class BaseSiteAPI {

    protected void onGetChannelAlbums(ChannelMode channelMode, int pageNo, int pageSize, onGetChannelAlbumListener channelAlbumListener, int siteId) {
        String url = getAlbumUrl(channelMode, pageNo, pageSize);
        if (!TextUtils.isEmpty(url)) {
            doGetChannelAlbumsByUrl(url, channelAlbumListener, siteId);
        }
    }

    protected void doGetChannelAlbumsByUrl(final String url, final onGetChannelAlbumListener channelAlbumListener, final int siteId) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (channelAlbumListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("doGetChannelAlbumsByUrl")
                            .setExceptionString(e.toString())
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .setSiteId(siteId).build();
                    channelAlbumListener.onGetChannelAlbumFailed(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("onResponse")
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .setSiteId(siteId).build();
                    channelAlbumListener.onGetChannelAlbumFailed(errorInfo);
                    return;
                }
                parseAndMappingDataFromResponse(response, channelAlbumListener);
            }
        });
    }

    /**
     * 通过channelMode pageNo pageSize，拼接出真实url地址的方法
     *
     * @param channelMode
     * @param pageNo
     * @param pageSize
     * @return
     */
    protected abstract String getAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize);

    /**
     * 使用okHttp访问真实url地址，返回的Response是successful之后，会执行的解析数据和映射数据的方法
     *
     * @param response
     * @param listener
     */
    protected abstract void parseAndMappingDataFromResponse(Response response, onGetChannelAlbumListener listener);


}
