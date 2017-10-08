package com.project.fanyuzeng.niubilityvideoplayer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.project.fanyuzeng.niubilityvideoplayer.R;

/**
 * Created by fanyuzeng on 2017/10/1.
 * Function:
 */

public class LoadingView extends LinearLayout {
    public LoadingView(Context context) {
        super(context);
        initViews(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        inflate(context, R.layout.loading_view_layout,this);


    }
}
