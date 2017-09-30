package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.SitePagerAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class DetailListActivity extends BaseActivity {
    private static final String TAG = "DetailListActivity";
    public static final String CHANNEL_ID = "channelId";
    private int mChannelId;
    private ViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private List<String> mTitleDataList = new ArrayList<>();

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        if (intent != null) {
            mChannelId = intent.getIntExtra(CHANNEL_ID, -1);
        }
        ChannelMode channelMode = new ChannelMode(mChannelId, this);
        String titleName = channelMode.getChannelName();
        Log.i(TAG, "initViews " + "titleName:" + titleName);

        mTitleDataList.add("SOHU");
        mTitleDataList.add("LETV");

        setSupportActionBar(); //表示当前页面支持ActionBar
        setTitle(titleName);
        setSupportArrowActionBar(true);
        mViewPager = bindViewId(R.id.id_view_pager);
        mViewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(), this, mChannelId));
        mMagicIndicator = bindViewId(R.id.id_magic_indicator);
        CommonNavigator navigator = new CommonNavigator(this);
        navigator.setAdapter(new MagicNavigationAdapter());
        mMagicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);

    }


    @Override   //处理左上角返回箭头
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail_list;
    }

    public static void lunchDetailList(Context context, int channelId) {
        Intent intent = new Intent(context, DetailListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  //栈顶复用模式
        intent.putExtra(CHANNEL_ID, channelId);
        context.startActivity(intent);
    }

    private class MagicNavigationAdapter extends CommonNavigatorAdapter {

        @Override
        public int getCount() {
            return SiteMode.MAX_SITE;
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
            colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.colorPrimary));
            colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
            colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(index);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
            return indicator;
        }
    }
}
