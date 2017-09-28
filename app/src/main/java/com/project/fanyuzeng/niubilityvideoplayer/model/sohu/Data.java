package com.project.fanyuzeng.niubilityvideoplayer.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:搜狐视频返回结果中的Data的数据模型
 */

public class Data {
    @Expose
    private int count;
    @Expose
    @SerializedName("videos")
    private List<ResultAlbum> mResultAlbumList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultAlbum> getResultAlbumList() {
        return mResultAlbumList;
    }

    public void setResultAlbumList(List<ResultAlbum> resultAlbumList) {
        mResultAlbumList = resultAlbumList;
    }
}
