package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.VideoItemAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.api.ErrorInfo;
import com.project.fanyuzeng.niubilityvideoplayer.api.SiteApi;
import com.project.fanyuzeng.niubilityvideoplayer.api.onGetAlbumVideoListener;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoList;
import com.project.fanyuzeng.niubilityvideoplayer.widget.CustomGridView;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:AlbumDetailActivity页面中下面展示集数的Fragment
 */

public class AlbumPlayGridFragment extends BaseFragment {
    private static final String TAG = "AlbumPlayGridFragment";
    private static final String ARGS_ALBUM = "album";
    private static final String ARGS_IS_SHOWDESC = "isShowDesc";
    private static final String ARGS_INIT_POSITION = "initVideoPosition";
    private Album mAlbum;
    private boolean mIsShowDesc;
    private int mInitViewPositon;
    private int mPagerNo;
    private int mPagerSize;
    private VideoItemAdapter mVideoItemAdapter;
    private CustomGridView mCustomGridView;
    private int mPageTotal;
    private TextView mEmpytText;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsfirstSecelted = true;
    private int mCurrentPosition;
    private onPlayVideoSelectedListener mVideoSelectedListener;

    public void setVideoSelectedListener(onPlayVideoSelectedListener videoSelectedListener) {
        mVideoSelectedListener = videoSelectedListener;
    }

    public interface onPlayVideoSelectedListener {
        void onPlayVideoSelected(Video video, int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbum = getArguments().getParcelable(ARGS_ALBUM);
            mIsShowDesc = getArguments().getBoolean(ARGS_IS_SHOWDESC);
            mInitViewPositon = getArguments().getInt(ARGS_INIT_POSITION);
            mPagerNo = 0;
            mPagerSize = 50;
            mCurrentPosition = mInitViewPositon;
            mPageTotal = (mAlbum.getVideoTotle() + mPagerSize - 1) / mPagerSize;
            loadData();
        }
    }

    private void loadData() {
        mPagerNo++;
        // TODO: 2017/9/30 完善此处siteId
        SiteApi.onGetAlbumVideo(1, mPagerNo, mPagerSize, mAlbum, new onGetAlbumVideoListener() {
            @Override
            public void onGetAlbumVideoSuccess(VideoList videoList) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mEmpytText.setVisibility(View.GONE);
//                    }
//                });
                Log.d(TAG, "onGetAlbumVideoSuccess " + videoList.size());
                if (mVideoItemAdapter != null) {
                    for (Video video : videoList) {
                        mVideoItemAdapter.addVideo(video);
                    }
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mVideoItemAdapter.notifyDataSetChanged();
                        mEmpytText.setVisibility(View.GONE);
                        if (mVideoItemAdapter.getCount() > mInitViewPositon && mIsfirstSecelted) {
                            mCustomGridView.setSelection(mInitViewPositon);
                            mCustomGridView.setItemChecked(mInitViewPositon, true);
                            mIsfirstSecelted = false;
                            SystemClock.sleep(100);
                            mCustomGridView.smoothScrollToPosition(mInitViewPositon);
                        }
                    }
                });
            }

            @Override
            public void onGetAlbumVideoFail(ErrorInfo info) {

            }
        });
    }

    public AlbumPlayGridFragment() {
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_album_desc;
    }

    @Override
    protected void initViews() {
        mEmpytText = bindViewId(R.id.id_tv_empty);
//        mEmpytText.setVisibility(View.VISIBLE);
        mCustomGridView = bindViewId(R.id.id_gv_video_layout);
        //同样是剧集，综艺节目第xx期，电视剧集是数字，1表示综艺或者纪录片，6表示动漫、电视剧
        mCustomGridView.setNumColumns(mIsShowDesc ? 1 : 6);
        mVideoItemAdapter = new VideoItemAdapter(getContext(), mAlbum.getVideoTotle());
        mVideoItemAdapter.setOnVideoSelectedListener(new VideoItemSelectedListener());
        mVideoItemAdapter.setIsShowTitleContent(mIsShowDesc);
        mCustomGridView.setAdapter(mVideoItemAdapter);
        mVideoItemAdapter.notifyDataSetChanged();
        if (mAlbum.getVideoTotle() > 0 && mAlbum.getVideoTotle() > mPagerSize) {
            mCustomGridView.setHasMore(true);
        } else {
            mCustomGridView.setHasMore(false);
        }
        mCustomGridView.setOnLoadMoreListener(new CustomGridView.onLoadMoreListener() {
            @Override
            public void onLoadMoreItems() {
                loadData();
            }
        });
    }

    public static AlbumPlayGridFragment newInstance(Album album, boolean isShowDesc, int initVideoPosition) {
        AlbumPlayGridFragment fragment = new AlbumPlayGridFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ALBUM, album);
        bundle.putBoolean(ARGS_IS_SHOWDESC, isShowDesc);
        bundle.putInt(ARGS_INIT_POSITION, initVideoPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    private class VideoItemSelectedListener implements VideoItemAdapter.onVideoSelectedListener {

        @Override
        public void onVideoSelected(Video video, int position) {
            if (mCustomGridView != null) {
                mCustomGridView.setSelection(position);
                mCustomGridView.setItemChecked(position, true);
                mCurrentPosition = position;
                if (mVideoSelectedListener != null) {
                    mVideoSelectedListener.onPlayVideoSelected(video, position);
                }

            }
        }
    }
}
