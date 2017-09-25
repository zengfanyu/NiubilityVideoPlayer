package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public interface onGetChannelAlbumListener {
    void onGetChannelAlbumSuccess(AlbumList albumList);

    void onGetChannelAlbumFailed(ErrorInfo info);

}
