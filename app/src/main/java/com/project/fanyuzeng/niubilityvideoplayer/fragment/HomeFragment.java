package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.activity.DetailListActivity;
import com.project.fanyuzeng.niubilityvideoplayer.activity.FavoriteActivity;
import com.project.fanyuzeng.niubilityvideoplayer.activity.LiveActivity;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.HomeChannelAdapter;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "HomeFragment";

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        //影响Log查看
//        LoopViewPager viewPager = bindViewId(R.id.id_loop_view_pager);
//        CircleIndicator indicator = bindViewId(R.id.id_indicator);
//        viewPager.setAdapter(new HomePicAdapter(getActivity()));
//        viewPager.setLooperPic(true);
//        indicator.setViewPager(viewPager);
        GridView mChannelView = bindViewId(R.id.id_gv_channel);
        mChannelView.setAdapter(new HomeChannelAdapter(getActivity()));
        mChannelView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick " + "position:" + position);
        switch (position) {
            case 6:
                LiveActivity.lanchAvtivity(getActivity());
                break;
            case 7:
                FavoriteActivity.lunchActivity(getActivity());
                break;
            case 8:
                // TODO: 2017/9/22  跳历史记录
                break;
            default:
                DetailListActivity.lunchDetailList(getActivity(), position + 1);
                break;
        }
    }
}
