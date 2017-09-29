package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.content.Context;

import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SiteApi {

    public static void onGetChannelAlbums(Context context,int pageNo,int pageSize,int siteId, int channelId,onGetChannelAlbumListener listener){
        switch (siteId){
            case SiteMode.LETV:
                new LetvAPI().onGetChannelAlbums(new ChannelMode(channelId,context),pageNo,pageSize,listener,SiteMode.LETV);
                break;
            case SiteMode.SOHU:
                new SohuApi().onGetChannelAlbums(new ChannelMode(channelId,context),pageNo,pageSize,listener,SiteMode.SOHU);
                break;
            default:
                break;
        }
    }
}
