package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.google.gson.annotations.Expose;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function: 链式调用 错误信息
 */
public class ErrorInfo {
    public static final int ERROR_TYPE_HTTP = 1;
    public static final int ERROR_TYPE_URL = 2;
    public static final int ERROR_TYPE_FATAL = 3;
    public static final int ERROR_TYPE_DATA_CONVERT = 4;
    public static final int ERROR_TYPE_JSON_PARSE = 5;

    @Expose
    private int type;
    @Expose
    private String tag;
    @Expose
    private String functionName;
    @Expose
    private String className;
    @Expose
    private String reson;
    @Expose
    private String exceptionString;
    @Expose
    private String url;
    @Expose
    private int siteId;

    private ErrorInfo() {

    }

    public static class Builder {

        public Builder() {
        }

        private ErrorInfo mErrorInfo;

        public Builder setType(int type) {
            mErrorInfo.setType(type);
            return this;
        }

        public Builder setTag(String tag) {
            mErrorInfo.setTag(tag);
            return this;
        }

        public Builder setFunctionName(String functionName) {
            mErrorInfo.setFunctionName(functionName);
            return this;
        }

        public Builder setClassName(String className) {
            mErrorInfo.setClassName(className);
            return this;
        }

        public Builder setReson(String reson) {
            mErrorInfo.setReson(reson);
            return this;
        }

        public Builder setExceptionString(String exceptionString) {
            mErrorInfo.setExceptionString(exceptionString);
            return this;
        }

        public Builder setUrl(String url) {
            mErrorInfo.setUrl(url);
            return this;
        }

        public Builder setSiteId(int siteId) {
            mErrorInfo.setSiteId(siteId);
            return this;
        }

        public ErrorInfo build() {
            mErrorInfo = new ErrorInfo();
            return mErrorInfo;
        }
    }


    private void setType(int type) {
        this.type = type;
    }

    private void setTag(String tag) {
        this.tag = tag;
    }

    private void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    private void setClassName(String className) {
        this.className = className;
    }

    private void setReson(String reson) {
        this.reson = reson;
    }

    private void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
