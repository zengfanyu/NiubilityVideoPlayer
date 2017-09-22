package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.fanyuzeng.niubilityvideoplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanyuzeng on 2017/9/21.
 * Function:
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener ,View.OnClickListener{
    private static final String TAG = "GuideActivity";
    private List<View> mGuideViews;
    private ViewPager mViewPager;
    private ImageView[] mDots;
    private int mLastSeceltPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        intiViewPager();
        initdots();
        initStartBtn();
    }

    private void initStartBtn() {
        View contentView = mGuideViews.get(2);
        ImageButton startBtn= (ImageButton) contentView.findViewById(R.id.id_btn_start);
        startBtn.setOnClickListener(this);
    }

    private void initdots() {
        LinearLayout dotContainer = (LinearLayout) findViewById(R.id.id_indicator_container);
        mDots = new ImageView[mGuideViews.size()];
        for (int i = 0; i < mGuideViews.size(); i++) {
            mDots[i] = (ImageView) dotContainer.getChildAt(i);
            mDots[i].setEnabled(false);
        }
        mLastSeceltPosition=0;
        mDots[0].setEnabled(true);

    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mGuideViews = new ArrayList<>(3);
        mGuideViews.add(inflater.inflate(R.layout.guide_one_layout, null));
        mGuideViews.add(inflater.inflate(R.layout.guide_two_layout, null));
        mGuideViews.add(inflater.inflate(R.layout.guide_three_layout, null));
    }

    private void intiViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        mViewPager.addOnPageChangeListener(this);
        GuidePagerAdapter adapter = new GuidePagerAdapter(mGuideViews, this);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentPosition(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setCurrentPosition(int currentPosition) {
        mDots[currentPosition].setEnabled(true);
        mDots[mLastSeceltPosition].setEnabled(false);
        mLastSeceltPosition=currentPosition;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,HomeActivity.class);
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        sp.edit().putBoolean("mIsFirstIn",false).apply();
        startActivity(intent);
        finish();
    }


    private class GuidePagerAdapter extends PagerAdapter {
        private List<View> mGuideViews;
        private Context mContext;

        public GuidePagerAdapter(List<View> guideViews, Context context) {
            super();
            mGuideViews = guideViews;
            mContext = context;
        }

        @Override
        public int getCount() {
            if (mGuideViews.size() != 0)
                return mGuideViews.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mGuideViews.get(position));
            return mGuideViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mGuideViews.get(position));
        }
    }
}
