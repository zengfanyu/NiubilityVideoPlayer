package com.project.fanyuzeng.niubilityvideoplayer.model.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:
 */

public class DetailResult {

    @Expose
    @SerializedName("status")
    private long mStatus;
    @Expose
    @SerializedName("statusText")
    private String mStatusText;
    @Expose
    @SerializedName("data")
    private ResultAlbum mResultAlbum;

    public long getStatus() {
        return mStatus;
    }

    public void setStatus(long status) {
        mStatus = status;
    }

    public String getStatusText() {
        return mStatusText;
    }

    public void setStatusText(String statusText) {
        mStatusText = statusText;
    }

    public ResultAlbum getResultAlbum() {
        return mResultAlbum;
    }

    public void setResultAlbum(ResultAlbum resultAlbum) {
        mResultAlbum = resultAlbum;
    }
}
