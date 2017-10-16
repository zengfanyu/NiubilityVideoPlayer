package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.LiveItemAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.widget.LiveRecyckerViewDecoration;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class LiveActivity extends BaseActivity {
    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(getResources().getString(R.string.live_title));
        RecyclerView recyclerView = bindViewId(R.id.id_recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new LiveRecyckerViewDecoration(this));
        recyclerView.setAdapter(new LiveItemAdapter(this));

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live;
    }

    public static void lanchAvtivity(Activity activity) {
        Intent intent = new Intent(activity, LiveActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
