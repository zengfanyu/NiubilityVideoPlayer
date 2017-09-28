package com.project.fanyuzeng.niubilityvideoplayer.model;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;
import com.project.fanyuzeng.niubilityvideoplayer.R;

import java.io.Serializable;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SiteMode  implements Serializable{
    public static final int LETV = 2;//乐视
    public static final int SOHU = 1;//搜狐
    public static final int MAX_SITE=2;


    private int siteId;
    private String siteName;

    public SiteMode(int siteId) {
        this.siteId = siteId;

        switch (siteId) {
            case SOHU:
                siteName = AppManager.getContext().getResources().getString(R.string.site_sohu);
                break;
            case LETV:
                siteName = AppManager.getContext().getResources().getString(R.string.site_letv);
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
