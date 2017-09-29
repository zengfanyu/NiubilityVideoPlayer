package com.project.fanyuzeng.niubilityvideoplayer.model.sohu;

import com.google.gson.annotations.Expose;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:搜狐视频返回的真实数据模型
 */

public class Result {
    //http://lm.tv.sohu.com/union/open_platform.do
    /*
    * {
        "status": 200,
        "statusText": "OK",
        "data": {}
      }
    * */
    @Expose
    private long status;
    @Expose
    private String statusText;
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
