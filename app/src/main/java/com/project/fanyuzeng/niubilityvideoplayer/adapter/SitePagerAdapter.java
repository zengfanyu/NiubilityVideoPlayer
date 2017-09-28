package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.project.fanyuzeng.niubilityvideoplayer.fragment.DetailListFragment;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;

import java.util.HashMap;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListActivity列表详情页面的ViewPager的Adaper
 */

public class SitePagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SitePagerAdapter";
    private Context mContext;
    private int mChannelId;
    private HashMap<Integer, DetailListFragment> mPagerMap;

    public SitePagerAdapter(FragmentManager fm, Context context, int channelId) {
        super(fm);
        mPagerMap = new HashMap<>();
        mContext = context;
        mChannelId = channelId;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof DetailListFragment) {
            mPagerMap.put(position, (DetailListFragment) object);
        }
        return object;

    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getItem " + position);
        return DetailListFragment.newInstance(position+1, mChannelId);
    }

    @Override
    public int getCount() {
        return SiteMode.MAX_SITE;
    }
}
