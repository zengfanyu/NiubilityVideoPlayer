package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:首页ViewPager的adapter
 */

public class HomePicAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int[] mDescs = new int[]{
            R.string.a_name,
            R.string.b_name,
            R.string.c_name,
            R.string.d_name,
            R.string.e_name};
    private int[] mimgs = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};

    public HomePicAdapter(Activity activity) {
        mContext = activity;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.home_pic_item, null);
        ImageView imgPic = (ImageView) view.findViewById(R.id.id_img);
        TextView tvDesc = (TextView) view.findViewById(R.id.id_tv_des);
        tvDesc.setText(mDescs[position]);
        imgPic.setImageResource(mimgs[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
