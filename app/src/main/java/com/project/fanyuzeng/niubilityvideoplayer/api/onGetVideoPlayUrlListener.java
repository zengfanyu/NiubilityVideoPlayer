package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;

/**
 * Created by fanyuzeng on 2017/10/8.
 * Function:取三种码流的Listener
 */

public interface onGetVideoPlayUrlListener {

    void onGetSuperUrl(Video video,String url); //超清url

    void  onGetNormalUrl(Video video,String url);//标清url

    void  onGetHightUrl(Video video,String url);//高清url

    void onGetFailed(ErrorInfo info);//获取失败

}
