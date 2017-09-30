package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:请求专辑详情数据时的Listener
 */

public interface onGetAlbumDetailListener {

    void onGetAlbumDetailsSuccess(Album album);

    void onGetAlbumDetailFail(ErrorInfo info);
}
