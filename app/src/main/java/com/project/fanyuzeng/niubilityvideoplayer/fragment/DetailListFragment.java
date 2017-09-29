package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.DetailListAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.api.ErrorInfo;
import com.project.fanyuzeng.niubilityvideoplayer.api.SiteApi;
import com.project.fanyuzeng.niubilityvideoplayer.api.onGetChannelAlbumListener;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.utils.ThreadJuedeUtils;
import com.project.fanyuzeng.niubilityvideoplayer.widget.PullLoadRecyclerView;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListActivity列表详情页面的Fragment
 */

public class DetailListFragment extends BaseFragment {
    private static final String TAG = "DetailListFragment";
    private int mSiteId;
    private int mChannelId;
    public static final String CHANNEL_ID = "channelId";
    public static final String SITE_ID = "siteId";
    public static final int REFRESH_DURAION = 1500;
    public static final int LOADMORE_DURAION = 3000;
    private PullLoadRecyclerView mRecyclerView;
    private TextView mTvEmpty;
    private DetailListAdapter mAdapter;
    Handler mHandler = new Handler(Looper.getMainLooper());
    private int mPageSize = 30;
    private int mPageNo = 0; // 0 和 1加载的都是第一页数据，此处和加载更多统一处理
    private int mColumns;

    public DetailListFragment() {
    }

    //提供给外界实例化DetailListFragment的方法
    public static DetailListFragment newInstance(int siteId, int channelId) {
        DetailListFragment detailListFragment = new DetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CHANNEL_ID, channelId);
        bundle.putInt(SITE_ID, siteId);
        detailListFragment.setArguments(bundle);
        return detailListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSiteId = getArguments().getInt(SITE_ID);
            mChannelId = getArguments().getInt(CHANNEL_ID);
        }
        if (mSiteId == SiteMode.LETV) {
            mColumns = 2;
        } else {
            mColumns = 3;
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
        mRecyclerView.setGridLayout(mColumns);
        mAdapter = new DetailListAdapter(getActivity(), new ChannelMode(mChannelId, getActivity()));
        mAdapter.setColumns(mColumns);
        mRecyclerView.setAdapter(mAdapter);
        loadMoreData(); //一进来首先加载第一屏数据
        Log.i(TAG, "initViews " + "isMainThread?" + ThreadJuedeUtils.isMainThread());
        mRecyclerView.setOnPullLoadMoreListener(new PullLoadRecyclerView.OnPullLoadMoreListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh " + "isMainThread?" + ThreadJuedeUtils.isMainThread());
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
                Log.i(TAG, "onLoadMore " + "isMainThread?" + ThreadJuedeUtils.isMainThread());
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
        mPageNo = 0;
        mAdapter = null;
        if (mSiteId == SiteMode.SOHU) {
            mColumns = 3;
        } else if (mSiteId==SiteMode.LETV){
            mColumns = 2;
        }
        mAdapter = new DetailListAdapter(getContext(), new ChannelMode(mChannelId, getActivity()));
        mAdapter.setColumns(mColumns);
        mRecyclerView.setGridLayout(mColumns);
        mRecyclerView.setAdapter(mAdapter);
        loadMoreData();
        Toast.makeText(getActivity(), "已加载到最新数据", Toast.LENGTH_SHORT).show();
    }

    private void loadMoreData() {
        // TODO: 2017/9/25 请求接口加载数据
        Log.d(TAG, "loadMoreData " + "channelId:" + mChannelId + "siteId:" + mSiteId);
        mPageNo++;
        SiteApi.onGetChannelAlbums(getActivity(), mPageNo, mPageSize, mSiteId, mChannelId, new onGetChannelAlbumListener() {
            @Override
            public void onGetChannelAlbumSuccess(AlbumList albumList) {
                for (Album album : albumList) {
                    Log.d(TAG, "onGetChannelAlbumSuccess " + album.toString());
                }
                Log.i(TAG, "onGetChannelAlbumSuccess " + "isMainThread?" + ThreadJuedeUtils.isMainThread());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvEmpty.setVisibility(View.GONE);//拿到数据之后，隐藏掉Tip
                    }
                });
                for (Album album : albumList) {
                    mAdapter.setData(album);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });


            }

            @Override
            public void onGetChannelAlbumFailed(ErrorInfo info) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvEmpty.setText(getActivity().getResources().getString(R.string.data_failed_tip));
                    }
                });
            }
        });
    }

}
