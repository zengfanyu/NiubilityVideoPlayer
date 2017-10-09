package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.content.Context;
import android.util.Log;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SiteApi {
    private static final String TAG = "SiteApi";

    public static void onGetChannelAlbums(Context context, int pageNo, int pageSize, int siteId, int channelId, onGetChannelAlbumListener listener) {
        switch (siteId) {
            case SiteMode.LETV:
                new LetvAPI().onGetChannelAlbums(new ChannelMode(channelId, context), pageNo, pageSize, listener, SiteMode.LETV);
                break;
            case SiteMode.SOHU:
                new SohuApi().onGetChannelAlbums(new ChannelMode(channelId, context), pageNo, pageSize, listener, SiteMode.SOHU);
                break;
            default:
                break;
        }
    }

    public static void onGetAlbumDetail(Album album, onGetAlbumDetailListener listener) {
        int siteId = album.getSite().getSiteId();
        Log.d(TAG, "onGetAlbumDetail " + "siteId:" + siteId);
        switch (siteId) {
            case SiteMode.LETV:
                new LetvAPI().onGetAlbumDetail(album, listener);
                break;
            case SiteMode.SOHU:
                new SohuApi().onGetAlbumDetail(album, listener);
                break;
        }

    }

    public static void onGetAlbumVideo(int pageNo, int pageSize, Album album, onGetAlbumVideoListener listener) {
        int siteId = album.getSite().getSiteId();
        switch (siteId) {
            case SiteMode.LETV:
                new LetvAPI().onGetAlbumVideo(album, pageNo, pageSize, listener);
                break;
            case SiteMode.SOHU:
                new SohuApi().onGetAlbumVideo(album, pageNo, pageSize, listener);
                break;
        }
    }

    public static void onGetVideoPlayUrl(Video video, onGetVideoPlayUrlListener listener) {
        int siteId = video.getSite();
        switch (siteId) {
            case SiteMode.LETV:
                new LetvAPI().onGetVideoPlayUrl(siteId, video, listener);
                break;
            case SiteMode.SOHU:
                new SohuApi().onGetVideoPlayUrl(siteId, video, listener);
                break;
        }

    }
}
