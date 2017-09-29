package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.google.gson.annotations.Expose;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class ErrorInfo {
    public static final int ERROR_TYPE_HTTP=1;
    public static final int ERROR_TYPE_URL=2;
    public static final int ERROR_TYPE_FATAL=3;
    public static final int ERROR_TYPE_DATA_CONVERT=4;
    public static final int ERROR_TYPE_JSON_PARSE=5;

    @Expose
    int type;
    @Expose
    String tag;
    @Expose
    String functionName;
    @Expose
    String className;
    @Expose
    SiteMode mSiteMode;
    @Expose
    String reson;
    @Expose
    String exceptionString;
    @Expose
    String url;

    public ErrorInfo(int siteId,int type){
        mSiteMode=new SiteMode(siteId);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public SiteMode getSiteMode() {
        return mSiteMode;
    }

    public void setSiteMode(SiteMode siteMode) {
        mSiteMode = siteMode;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }
}
