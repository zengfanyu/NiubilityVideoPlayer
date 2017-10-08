package com.project.fanyuzeng.niubilityvideoplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanyuzeng on 2017/10/1.
 * Function:展示集数或者期数的GridView
 */

public class CustomGridView extends GridView {

    private Context mContext;
    private boolean mIsLoading;
    private boolean mHasMore;
    private onLoadMoreListener mOnLoadMoreListener;
    private onScrolledListener mOnScrollListener;
    private List<ViewHolder> mFooterViewList = new ArrayList<>();
    /**
     * 加载更多的接口
     */
    public interface onLoadMoreListener {

        void onLoadMoreItems();
    }

    /**
     * 滑动监听接口
     */
    public interface onScrolledListener {
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    public void setOnLoadMoreListener(onLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    public void setOnScrolledListener(onScrolledListener listener) {
        this.mOnScrollListener = listener;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }



    public CustomGridView(Context context) {
        super(context);
        initViews(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        mContext = context;
        mIsLoading = false;
        //添加loadingView
        LoadingView loadingView = new LoadingView(context);
        addFooterView(loadingView);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (mOnScrollListener!=null){
                        mOnScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
                    }
                    if (totalItemCount>0){
                        int lastViewVisiable=firstVisibleItem+visibleItemCount;
                        if (!mIsLoading&&mHasMore&&lastViewVisiable==totalItemCount){
                            if (mOnLoadMoreListener!=null){
                                mOnLoadMoreListener.onLoadMoreItems();
                            }
                        }
                    }
            }
        });
    }

    public void addFooterView(View view) {
        addFooterView(view, null, true);
    }

    //添加footerView
    public void addFooterView(View view, Object data, boolean isSelcted) {
        ViewHolder holder = new ViewHolder();
        FrameLayout fl = new FullWidthViewLayout(mContext);
        fl.addView(view);
        holder.view = view;
        holder.data = data;
        holder.viewContainer = fl;
        holder.isSelected = isSelcted;
        mFooterViewList.add(holder);
    }

    // 移除footerview
    public void removeFooterView(View v) {
        if (mFooterViewList.size() > 0) {
            removeHolder(v, mFooterViewList);
        }
    }

    private void removeHolder(View view, List<ViewHolder> list) {
        for (int i = 0; i < list.size(); i++) {
            ViewHolder holder = list.get(i);
            if (holder.view == view) {
                list.remove(i);
                break;
            }
        }
    }

    public void notifyChanged() {
        this.requestLayout();
        this.invalidate();
    }

    //FooterView容器
    class ViewHolder{
        public View view;
        public ViewGroup viewContainer;
        public Object data;
        public boolean  isSelected;
    }

    /**
     * 此layout用于FooterView填充整个宽度
     */
    class FullWidthViewLayout extends FrameLayout {

        public FullWidthViewLayout(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int targetWidth = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            MeasureSpec.makeMeasureSpec(targetWidth, MeasureSpec.getMode(widthMeasureSpec));
        }
    }


}
