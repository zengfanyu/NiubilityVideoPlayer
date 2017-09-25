package com.project.fanyuzeng.niubilityvideoplayer.model;

import android.content.Context;

import com.project.fanyuzeng.niubilityvideoplayer.R;

import java.io.Serializable;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SiteMode  implements Serializable{
    public static final int LETV = 1;//乐视
    public static final int SOHU = 2;//搜狐
    public static final int MAX_SITE=2;


    private int siteId;
    private String siteName;
    private Context mContext;

    public SiteMode(int siteId, Context context) {
        this.siteId = siteId;
        mContext = context;
        switch (siteId) {
            case LETV:
                siteName = mContext.getResources().getString(R.string.site_letv);
                break;
            case SOHU:
                siteName = mContext.getResources().getString(R.string.site_sohu);
                break;

        }
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
