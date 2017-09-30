package com.project.fanyuzeng.niubilityvideoplayer.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:
 */

public class VideoResult {
    @Expose
    private long status;
    @Expose
    private String statusText;
    @Expose
    private VideoData data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public VideoData getData() {
        return data;
    }

    public void setData(VideoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VideoResult{" +
                "status=" + status +
                ", statusText='" + statusText + '\'' +
                ", data=" + data +
                '}';
    }
}
