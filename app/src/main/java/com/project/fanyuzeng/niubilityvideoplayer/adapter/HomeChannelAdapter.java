package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;

/**
 * Created by fanyuzeng on 2017/9/22.
 * Function:首页Channel GridView 的adapter
 */

public class HomeChannelAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public HomeChannelAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return ChannelMode.MAX_COUNT;
    }

    @Override
    public ChannelMode getItem(int position) {
        return new ChannelMode(position + 1, mContext);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChannelMode chanel = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_grid_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.id_channel_txt);
            holder.imageView = (ImageView) convertView.findViewById(R.id.id_channel_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(chanel.getChannelName());
        int id = chanel.getChannelId();
        int imgResId = -1;
        switch (id) {
            case ChannelMode.SERIES:
                imgResId = R.drawable.ic_show;
                break;
            case ChannelMode.MOVIE:
                imgResId = R.drawable.ic_movie;
                break;
            case ChannelMode.COMIC:
                imgResId = R.drawable.ic_comic;
                break;
            case ChannelMode.DOCUMENTRY:
                imgResId = R.drawable.ic_movie;
                break;
            case ChannelMode.MUSIC:
                imgResId = R.drawable.ic_music;
                break;
            case ChannelMode.VARIETY:
                imgResId = R.drawable.ic_variety;
                break;
            case ChannelMode.LIVE:
                imgResId = R.drawable.ic_live;
                break;
            case ChannelMode.FAVORITE:
                imgResId = R.drawable.ic_bookmark;
                break;
            case ChannelMode.HISTORY:
                imgResId = R.drawable.ic_history;
                break;
        }

        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(imgResId));

        return convertView;
    }

   static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}

