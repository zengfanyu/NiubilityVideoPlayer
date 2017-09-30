package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.api.ErrorInfo;
import com.project.fanyuzeng.niubilityvideoplayer.api.SiteApi;
import com.project.fanyuzeng.niubilityvideoplayer.api.onGetAlbumVideoListener;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:
 */

public class AlbumPlayGridFragment extends BaseFragment {
    private static final String ARGS_ALBUM = "album";
    private static final String ARGS_IS_SHOWDESC = "isShowDesc";
    private static final String ARGS_INIT_POSITION = "initVideoPosition";
    private Album mAlbum;
    private boolean mIsShowDesc;
    private int mInitViewPositon;
    private int mPagerNo;
    private int mPagerSize;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbum = getArguments().getParcelable(ARGS_ALBUM);
            mIsShowDesc = getArguments().getBoolean(ARGS_IS_SHOWDESC);
            mInitViewPositon = getArguments().getInt(ARGS_INIT_POSITION);
            mPagerNo = 0;
            mPagerSize = 50;
            loadData();
        }
    }

    private void loadData() {
        mPagerNo++;
        // TODO: 2017/9/30 完善此处siteId
        SiteApi.onGetAlbumVideo(1, mPagerNo, mPagerSize, mAlbum, new onGetAlbumVideoListener() {
            @Override
            public void onGetAlbumVideoSuccess(Album album) {

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

    }

    public static BaseFragment newInstance(Album album, boolean isShowDesc, int initVideoPosition) {
        AlbumPlayGridFragment fragment = new AlbumPlayGridFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ALBUM, album);
        bundle.putBoolean(ARGS_IS_SHOWDESC, isShowDesc);
        bundle.putInt(ARGS_INIT_POSITION, initVideoPosition);
        fragment.setArguments(bundle);
        return fragment;
    }
}
