package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoList;

/**
 * Created by fanyuzeng on 2017/10/1.
 * Function: CustomGridView的Adapter
 */

public class VideoItemAdapter extends BaseAdapter {
    private static final String TAG = "VideoItemAdapter";
    private onVideoSelectedListener mListener;
    private Context mContext;
    private int mTotalCount;
    private VideoList mVideos = new VideoList();
    private boolean mIsShowTitleContent;
    private boolean isFirstEnter=true;

    public void addVideo(Video videos) {
        Log.d(TAG, "addVideo ");
        mVideos.add(videos);
    }

    public interface onVideoSelectedListener {
        void onVideoSelected(Video video, int position);

    }

    public void setOnVideoSelectedListener(onVideoSelectedListener listener) {
        this.mListener = listener;
    }

    public VideoItemAdapter(Context context, int totalCount) {
        mContext = context;
        mTotalCount = totalCount;
        Log.d(TAG, "VideoItemAdapter ");
    }

    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Video getItem(int position) {
        return mVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //是否显示没寄，每期的内容，如：奔跑吧兄弟之xxx
    public void setIsShowTitleContent(boolean isShow) {
        mIsShowTitleContent = isShow;
    }

    public boolean getIsShowTitleContent() {
        return mIsShowTitleContent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Video video = getItem(position);
        Log.d(TAG, "getView ");
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_item_layout, parent, false);
            holder = new ViewHolder();
            holder.videoContainer = (LinearLayout) convertView.findViewById(R.id.id_video_container);
            holder.videoTitle = (Button) convertView.findViewById(R.id.id_btn_video_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getIsShowTitleContent()) {
            if (!TextUtils.isEmpty(video.getVideo_name())) {
                holder.videoTitle.setText(video.getVideo_name());
            } else {
                holder.videoTitle.setText(String.valueOf(position + 1));//position 从0开始， 剧集从1开始
            }
        } else {
            holder.videoTitle.setText(String.valueOf(position + 1));//position 从0开始， 剧集从1开始
        }
        if (position==0&&isFirstEnter){ //首次进入详情页不显示button问题
            mListener.onVideoSelected(video,position);
            isFirstEnter=false;
        }
        holder.videoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onVideoSelected(video, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        LinearLayout videoContainer;
        Button videoTitle;
    }
}
