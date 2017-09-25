package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;

import com.project.fanyuzeng.niubilityvideoplayer.R;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListActivity列表详情页面的Fragment
 */

public class DetailListFragment extends BaseFragment {
    private static int sSiteId;
    private static int sChannelId;
    public static final String CHANNEL_ID = "channelId";
    public static final String SIET_ID = "siteId";


    public DetailListFragment() {
    }

    //提供给外界实例化DetailListFragment的方法
    public static DetailListFragment newInstance(int siteId, int channelId) {
        DetailListFragment detailListFragment = new DetailListFragment();
        sSiteId = siteId;
        sChannelId = channelId;
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID, sChannelId);
        bundle.putInt(SIET_ID, sSiteId);
        detailListFragment.setArguments(bundle);
        return detailListFragment;
    }


    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_detail_list;
    }

    @Override
    protected void initViews() {
        // TODO: 2017/9/25  拿Bundle

    }
}
