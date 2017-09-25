package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.DetailListAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.widget.PullLoadRecyclerView;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListActivity列表详情页面的Fragment
 */

public class DetailListFragment extends BaseFragment {
    private static int sSiteId;
    private static int sChannelId;
    public static final String CHANNEL_ID = "channelId";
    public static final String SIET_ID = "siteId";
    public static final int REFRESH_DURAION = 1500;
    public static final int LOADMORE_DURAION = 3000;
    private PullLoadRecyclerView mRecyclerView;
    private TextView mTvEmpty;
    private DetailListAdapter mAdapter;
    Handler mHandler = new Handler(Looper.getMainLooper());

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMoreData(); //一进来首先加载第一屏数据
        mAdapter = new DetailListAdapter();
        if (sSiteId == SiteMode.LETV) {
            mAdapter.setColumns(2);
        }
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
        mTvEmpty = bindViewId(R.id.id_tv_empty);
        mTvEmpty.setText(getActivity().getResources().getString(R.string.load_more_text));
        mRecyclerView = bindViewId(R.id.id_pull_load_recycler_view);
        mRecyclerView.setGridLayout(2);
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadRecyclerView.onPullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        mRecyclerView.setRefreshCompleted();
                    }
                }, REFRESH_DURAION);
            }

            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData();
                        mRecyclerView.setLoadMoreCompleted();
                    }
                }, LOADMORE_DURAION);
            }
        });
    }

    private void refreshData() {
        // TODO: 2017/9/25 请求接口，刷新数据
    }

    private void loadMoreData() {
        // TODO: 2017/9/25 请求接口加载数据
    }

}
