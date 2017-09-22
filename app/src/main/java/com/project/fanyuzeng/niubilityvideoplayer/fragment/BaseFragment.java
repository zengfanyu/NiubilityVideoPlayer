package com.project.fanyuzeng.niubilityvideoplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:
 */
public abstract class BaseFragment extends Fragment {

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = getActivity().getLayoutInflater().inflate(getLayoutResId(), container,false);
        initViews();
        initDatas();
        return mContentView;
    }

    protected abstract void initDatas();

    protected abstract int getLayoutResId();

    protected abstract void initViews();

    protected <T extends View> T bindViewId(int resId) {
        return (T) mContentView.findViewById(resId);
    }
}
