package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.SitePagerAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class DetailListActivity extends BaseActivity {
    private static final String TAG = "DetailListActivity";
    public static final String CHANNEL_ID = "channelId";
    private int mChannelId;
    private ViewPager mViewPager;

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
        Log.i(TAG, "initViews " + "titleName:"+titleName);

        setSupportActionBar(); //表示当前页面支持ActionBar
        setTitle(titleName);
        setSupportArrowActionBar(true);
        mViewPager = bindViewId(R.id.id_view_pager);
        mViewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(), this, mChannelId));

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
}
