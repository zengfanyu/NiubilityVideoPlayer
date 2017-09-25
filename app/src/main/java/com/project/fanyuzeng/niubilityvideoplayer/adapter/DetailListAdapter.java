package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListFragment中RecyclerView的Adapter
 */

public class DetailListAdapter extends RecyclerView.Adapter {
    private int mColumns;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setColumns(int columns) {
        mColumns = columns;
    }
}
