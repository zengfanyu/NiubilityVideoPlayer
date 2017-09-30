package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.project.fanyuzeng.niubilityvideoplayer.R;

import butterknife.ButterKnife;

/**
 * Created by fanyuzeng on 2017/9/21.
 * Function:
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initViews();
        initDatas();

    }

    protected abstract void initDatas();

    protected abstract void initViews();

    protected abstract int getLayoutResId();

    protected <T extends View> T bindViewId(int resId) {
        return (T) findViewById(resId);
    }

    protected void setSupportActionBar() {
        mToolbar = bindViewId(R.id.id_tool_bar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected void setActionBarIcon(int resId) {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(resId);
        }
    }

    protected void setSupportArrowActionBar(boolean isSupport) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isSupport);
    }
}
