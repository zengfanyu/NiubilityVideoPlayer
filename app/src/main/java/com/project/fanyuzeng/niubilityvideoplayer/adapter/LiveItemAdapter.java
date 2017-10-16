package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.activity.PlayActivity;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class LiveItemAdapter extends RecyclerView.Adapter<LiveItemAdapter.LiveItemHolder> {
    private Context mContext;
    // 数据集
    private String[] mDataList = new String[]{
            "CCTV-1 综合", "CCTV-2 财经", "CCTV-3 综艺", "CCTV-4 中文国际(亚)", "CCTV-5 体育",
            "CCTV-6 电影", "CCTV-7 军事农业", "CCTV-8 电视剧", "CCTV-9 纪录", "CCTV-10 科教",
            "CCTV-11 戏曲", "CCTV-12 社会与法", "CCTV-13 新闻", "CCTV-14 少儿", "CCTV-15 音乐"
    };

    private int[] mIconList = new int[]{
            R.drawable.cctv_1, R.drawable.cctv_2, R.drawable.cctv_3, R.drawable.cctv_4, R.drawable.cctv_5,
            R.drawable.cctv_6, R.drawable.cctv_7, R.drawable.cctv_8, R.drawable.cctv_9, R.drawable.cctv_10,
            R.drawable.cctv_11, R.drawable.cctv_12, R.drawable.cctv_13, R.drawable.cctv_14, R.drawable.cctv_15

    };

    private String[] mUrlList = new String[]{
            "http://183.251.61.207/PLTV/88888888/224/3221225812/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225923/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225924/index.m3u8",
            "http://183.207.249.15/PLTV/3/224/3221225534/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225925/index.m3u8",
            "http://120.87.4.5/PLTV/88888894/224/3221225491/index.m3u8",
            "http://120.87.4.5/PLTV/88888894/224/3221225492/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225928/index.m3u8",
            "http://120.87.4.5/PLTV/88888894/224/3221225494/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225931/index.m3u8",
            "http://120.87.4.5/PLTV/88888894/224/3221225496/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225932/index.m3u8",
            "http://183.207.249.15/PLTV/3/224/3221225560/index.m3u8",
            "http://183.251.61.207/PLTV/88888888/224/3221225933/index.m3u8",
            "http://112.96.28.43:8090/service/indexfile/CCTV15.m3u8",
    };

    public LiveItemAdapter(Context context) {
        mContext = context;
    }

    @Override
    public LiveItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_item, null);
        return new LiveItemHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveItemHolder holder, final int position) {
        holder.mLiveIcon.setImageResource(mIconList[position]);
        holder.mLiveTitle.setText(mDataList[position]);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.lunchActivity((Activity) mContext, mUrlList[position], mDataList[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    static class LiveItemHolder extends RecyclerView.ViewHolder {
        ImageView mLiveIcon;
        TextView mLiveTitle;

        public LiveItemHolder(View itemView) {
            super(itemView);
            mLiveIcon = (ImageView) itemView.findViewById(R.id.id_iv_live_icon);
            mLiveTitle = (TextView) itemView.findViewById(R.id.id_tv_live_title);
        }
    }
}
