package com.project.fanyuzeng.niubilityvideoplayer.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:下拉刷新和上拉加载的RecyclerView
 */

public class PullLoadRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener, View.OnTouchListener,Animator.AnimatorListener {
    private Context mContext;
    private SwipeRefreshLayout mSwipeLayout;
    private boolean mIsRefreshing, mIsLoadingMore;
    private RecyclerView mRecyclerView;
    private View mFooterView;
    private AnimationDrawable mAnimationDrawable;
    private onPullLoadMoreListener mListener;
    private boolean mRefreshing;

    public PullLoadRecyclerView(Context context) {
        super(context);
        initViews(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    public interface onPullLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setOnPullLoadMoreListener(onPullLoadMoreListener listener) {
        mListener = listener;
    }

    private void initViews(Context context) {
        int[] colors = {android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark, android.R.color.holo_purple};
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.pull_load_layout, null);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_layout);
        mSwipeLayout.setColorSchemeColors(colors);
        mSwipeLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recycler_view);
        mRecyclerView.setHasFixedSize(true);//有固定大小
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//使用默认动画
        mRecyclerView.setVerticalScrollBarEnabled(false);//隐藏滚动条
        mRecyclerView.setOnTouchListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerScrollListener());

        mFooterView = view.findViewById(R.id.id_footer_view);
        ImageView loadImg = (ImageView) mFooterView.findViewById(R.id.id_iv_load);
        loadImg.setBackgroundResource(R.drawable.imooc_loading);
        mAnimationDrawable = (AnimationDrawable) loadImg.getBackground();
        TextView loadDesc = (TextView) mFooterView.findViewById(R.id.id_tv_load);
        mFooterView.setVisibility(GONE);

        //view包含swipeRefreshLayout，RecyclerView，FooterView
        this.addView(view);


    }

    //外部可以设置RecyclerView的列
    public void setGridLayout(int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(mContext, spanCount);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    public void setAdapter(Adapter adapter) {
        if (adapter != null)
            mRecyclerView.setAdapter(adapter);
    }

    public void setRefreshCompleted() {
        mIsRefreshing = false;
        setRefreshing(false);
    }

    public void setLoadMoreCompleted() {
        mIsLoadingMore = false;
        mIsRefreshing = false;
        setRefreshing(false);
        mFooterView.animate().translationY(mFooterView.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(300).start();

    }

    private void refreshData() {
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    private void loadMoreData() {
        if (mListener != null) {
            mListener.onLoadMore();
            mFooterView.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).setListener(this).start();

        }
    }


    private void setRefreshing(final boolean isRefreshing) {
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(isRefreshing);
            }
        });
    }
    @Override
    public void onAnimationStart(Animator animation) {
        mFooterView.setVisibility(VISIBLE);
        mAnimationDrawable.start();
        invalidate();
        mListener.onLoadMore();
    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
    @Override  //SwipeRefresh刷新回调
    public void onRefresh() {
        if (!mIsRefreshing)
            mIsLoadingMore = true;
        refreshData();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mIsLoadingMore || mIsRefreshing;  //此处涉及到事件分发机制，返回true，表示事件被消耗;
    }

    /**
     * RecyclerView的滚动监听器是抽象类，而不是接口
     */
   private class RecyclerScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstItem;
            int lastItem = 0;
            int totleCount;
            LayoutManager manager = recyclerView.getLayoutManager();
            totleCount = manager.getItemCount();
            if (manager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                //第一个完全可见的item
                firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                //最有一个完全可见的item
                lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (firstItem == 0 || firstItem == NO_POSITION) {
                    lastItem = gridLayoutManager.findLastVisibleItemPosition();
                }
            }

            if (mSwipeLayout.isEnabled()) {
                mSwipeLayout.setEnabled(true);
            } else {
                mSwipeLayout.setEnabled(false);
            }
            if (!mIsLoadingMore
                    && totleCount == lastItem
                    && (dx > 0 || dy > 0)
                    && mSwipeLayout.isEnabled()
                    && !mIsRefreshing) {
                mIsLoadingMore = true;
                //在上拉加载更多的时候禁止Swipe刷新
                mSwipeLayout.setEnabled(false);
                loadMoreData();
            }else {
                mSwipeLayout.setEnabled(true);
            }
        }
    }
}
