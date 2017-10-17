package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.R2;
import com.project.fanyuzeng.niubilityvideoplayer.adapter.FaviroteGridAdapter;
import com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDAO;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class FavoriteActivity extends BaseActivity {
    @BindView(R2.id.id_gv_favorite_favorite)
    GridView mGridView;
    @BindView(R2.id.id_tv_favorite_empty)
    TextView mEmptyTip;
    @BindView(R2.id.id_favorite_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private AlbumList mAlbumList = null;
    private FavoriteDAO mDAO;
    private FaviroteGridAdapter mAdapter;

    @Override
    protected void initViews() {
        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(getResources().getString(R.string.favorite_title));

        initDAO();

        prepareRefreshLayout();

        prepareGridView();


    }

    private void initDAO() {
        mDAO = new FavoriteDAO(this);
        mAlbumList = mDAO.getAllData();
        isNeedShowEmptyView();
    }

    private void isNeedShowEmptyView() {
        if (mAlbumList.size() == 0) {
            mEmptyTip.setVisibility(View.VISIBLE);
            mEmptyTip.setText(getResources().getString(R.string.favorite_empty));
        } else {
            mEmptyTip.setVisibility(View.GONE);
        }
    }

    private void prepareGridView() {
        mAdapter = new FaviroteGridAdapter(this, mAlbumList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (mGridView != null && mGridView.getChildCount() > 0) {
                    //第一个Item是否可见
                    boolean isFirstItemVisiable = mGridView.getFirstVisiblePosition() == 0;
                    boolean isTopOffsetFirstItemVisiable = mGridView.getChildAt(0).getTop() == 0;

                    enable = isFirstItemVisiable && isTopOffsetFirstItemVisiable;
                }
                mSwipeRefreshLayout.setEnabled(enable);
            }
        });
    }

    private void prepareRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_orange_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadData();

            }
        });
    }

    private void reloadData() {
        mAlbumList = mDAO.getAllData();
        isNeedShowEmptyView();
        mAdapter = new FaviroteGridAdapter(FavoriteActivity.this, mAlbumList);
        mGridView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_favotite;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                if (mAdapter.isSelected()) {
                    showDeleteDialog();
                } else {
                    setShowChecked(true);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setAllItemUnChecked() {

    }

    private void setShowChecked(boolean isSelected){
        if (!isSelected){
            mAdapter.unCheckedAllItem();
        }else {
            mAdapter.setShowChecked(isSelected);
            mAdapter.notifyDataSetInvalidated();//刷新数据 不刷UI
        }

    }
    private void showDeleteDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.favorite_dialog_message))
                .setPositiveButton(getResources().getString(R.string.favorite_dialog_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAllSelectedItem();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.favorite_dialog_quit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setShowChecked(false);
                    }
                })
                .create();
        dialog.show();
    }

    private void deleteAllSelectedItem() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mAdapter.getItem(i).isChecked()) {
                list.add(i);
            }
        }
        for (Integer integer : list) {
            Album album = mAdapter.getFavoriteList().get(integer).getAlbum();
            mDAO.delete(album.getAlbumId(), album.getSite().getSiteId());
        }
        reloadData();
    }

    public static void lunchActivity(Activity activity) {
        Intent intent = new Intent(activity, FavoriteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
