package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.text.TextUtils;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
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
    /**
     * 请求专辑列表数据
     *
     * @param channelMode
     * @param pageNo
     * @param pageSize
     * @param channelAlbumListener
     * @param siteId
     */
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
     * 请求专辑详情数据
     *
     * @param album
     * @param listener
     */
    protected void onGetAlbumDetail(Album album, final onGetAlbumDetailListener listener) {
        String url = getAlbumDetailUrl(album);
        if (!TextUtils.isEmpty(url)) {
            doGetAlbumDetailByUrl(album, url, listener);
        }
    }

    private void doGetAlbumDetailByUrl(final Album album, final String url, final onGetAlbumDetailListener listener) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("doGetAlbumDetailByUrl")
                            .setExceptionString(e.toString())
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetAlbumDetailFail(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("onResponse")
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetAlbumDetailFail(errorInfo);
                    return;
                }
                parseAndMappingAlbumDetailDataFromResponse(album, response, listener);
            }
        });
    }

    /**
     * 请求专辑视频数据
     *
     * @param album
     * @param listener
     */
    protected void onGetAlbumVideo(Album album, int pageNo, int pageSize, onGetAlbumVideoListener listener) {
        String url = getAlbumVideoUrl(album, pageNo, pageSize);
        if (!TextUtils.isEmpty(url)) {
            doGetAlbumVideoByUrl(album, url, listener);
        }
    }

    private void doGetAlbumVideoByUrl(final Album album, final String url, final onGetAlbumVideoListener listener) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("doGetAlbumVideoByUrl")
                            .setExceptionString(e.toString())
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetAlbumVideoFail(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("onResponse")
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetAlbumVideoFail(errorInfo);
                    return;
                }
                parseAndMappingAlbumVideoDataFromResponse(album, response, listener);
            }
        });
    }

    /**
     * 请求不同播放码率的视频的url
     *
     * @param siteId
     * @param video
     * @param listener
     */
    protected void onGetVideoPlayUrl(int siteId, Video video, onGetVideoPlayUrlListener listener) {
        String url = getVideoPlayUrl(siteId, video);
        if (!TextUtils.isEmpty(url)) {
            doGetVideoPlayURLByUrl(url, video,listener);
        }
    }

    private void doGetVideoPlayURLByUrl(final String url, final Video video, final onGetVideoPlayUrlListener listener) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("doGetVideoPlayURLByUrl")
                            .setExceptionString(e.toString())
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetFailed(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setUrl(url)
                            .setFunctionName("onResponse")
                            .setType(ErrorInfo.ERROR_TYPE_URL)
                            .build();
                    listener.onGetFailed(errorInfo);
                    return;
                }
                parseAndMappingVideoPlayUrlDataFromResponse(response,video, listener);

            }
        });
    }


    /**
     * 拼接Album视频 url
     *
     * @param album
     * @return
     */
    protected abstract String getAlbumVideoUrl(Album album, int pageNo, int pageSize);


    /**
     * 拼接Album详细内容 url
     *
     * @return
     */
    protected abstract String getAlbumDetailUrl(Album album);

    /**
     * 拼接Album列表url
     *
     * @param channelMode
     * @param pageNo
     * @param pageSize
     * @return
     */
    protected abstract String getAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize);

    /**
     * 拼接Video不同码率的播放地址url
     *
     * @param siteId
     * @param video
     * @return
     */
    protected abstract String getVideoPlayUrl(int siteId, Video video);

    /**
     * 使用okHttp访问url地址，解析Album详细数据，并且映射到实体类
     *
     * @param album
     * @param response
     * @param listener
     */
    protected abstract void parseAndMappingAlbumDetailDataFromResponse(Album album, Response response, onGetAlbumDetailListener listener);

    /**
     * 使用okHttp访问url地址，解析Album列表数据，并且映射到实体类
     *
     * @param response
     * @param listener
     */
    protected abstract void parseAndMappingDataFromResponse(Response response, onGetChannelAlbumListener listener);

    /**
     * 使用okHttp访问url地址，接续Album视频数据，并且映射到实体类中
     *
     * @param album
     * @param response
     * @param listener
     */
    protected abstract void parseAndMappingAlbumVideoDataFromResponse(Album album, Response response, onGetAlbumVideoListener listener);

    /**
     * 使用okhttp访问url地址，解析Video不同码率下的播放地址url，并且映射到实体类中
     *
     * @param response
     * @param listener
     */
    protected abstract void parseAndMappingVideoPlayUrlDataFromResponse(Response response, Video video,onGetVideoPlayUrlListener listener);


}
