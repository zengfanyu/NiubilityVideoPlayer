package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public abstract class BaseSiteAPI {

    public abstract void onGetChannelAlbums(ChannelMode channelMode,int pageNo,int pageSize,onGetChannelAlbumListener channelAlbumListener);
}
