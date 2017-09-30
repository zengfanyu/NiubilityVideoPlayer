package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoList;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:请求专辑视频数据时的Listener
 */

public interface onGetAlbumVideoListener {

    void onGetAlbumVideoSuccess(VideoList videoList);

    void onGetAlbumVideoFail(ErrorInfo info);
}
