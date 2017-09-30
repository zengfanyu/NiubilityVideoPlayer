package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.activity.AlbumDetailActivity;
import com.project.fanyuzeng.niubilityvideoplayer.activity.DetailListActivity;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.utils.ImageUtils;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:DetailListFragment中RecyclerView的Adapter
 */

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ItemViewHolder> {
    private int mColumns;
    private Context mContext;
    private ChannelMode mChannelMode;
    private AlbumList mAlbumList = new AlbumList();

    public DetailListAdapter(Context context, ChannelMode channelMode) {
        mContext = context;
        mChannelMode = channelMode;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.detail_list_item, null);
        ItemViewHolder holder = new ItemViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mAlbumList.size() <= 0)
            return;
        final Album album = getItem(position);
        holder.mAlbumName.setText(album.getTitle());
        if (album.getTip().isEmpty()) {
            holder.mAlbumTip.setVisibility(View.GONE);
        } else {
            holder.mAlbumTip.setText(album.getTip());
        }
        Point posterSize = null;
        if (mColumns == 3) {
            posterSize = ImageUtils.getVerPosterSize(mContext, mColumns);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(posterSize.x, posterSize.y);
            holder.mAlbumPoster.setLayoutParams(params);

        } else if (mColumns == 2) {
            posterSize = ImageUtils.getHorPosterSize(mContext, mColumns);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(posterSize.x, posterSize.y);
            holder.mAlbumPoster.setLayoutParams(params);

        }

        if (album.getVerImgUrl() != null) {
            ImageUtils.displayImage(holder.mAlbumPoster, album.getVerImgUrl(), posterSize.x, posterSize.y);

        } else if (album.getHorImgUrl() != null) {
            ImageUtils.displayImage(holder.mAlbumPoster, album.getHorImgUrl(), posterSize.x, posterSize.y);

        } else {
            // TODO: 2017/9/29 默认图
        }
        holder.mAlbumContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChannelMode.getChannelId() == ChannelMode.DOCUMENTRY || mChannelMode.getChannelId() == ChannelMode.MOVIE
                        || mChannelMode.getChannelId() == ChannelMode.VARIETY || mChannelMode.getChannelId() == ChannelMode.MUSIC) {
                    AlbumDetailActivity.lunch((DetailListActivity) mContext, album, 0, true);
                } else {
                    AlbumDetailActivity.lunch((DetailListActivity) mContext, album);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (mAlbumList.size() > 0) {
            return mAlbumList.size();
        }
        return 0;
    }

    private Album getItem(int position) {
        return mAlbumList.get(position);
    }

    public void setColumns(int columns) {
        mColumns = columns;
    }

    public void setData(Album data) {
        mAlbumList.add(data);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mAlbumContainer;
        ImageView mAlbumPoster;
        TextView mAlbumTip;
        TextView mAlbumName;

        ItemViewHolder(View itemView) {
            super(itemView);
            mAlbumContainer = (LinearLayout) itemView.findViewById(R.id.id_album_container);
            mAlbumPoster = (ImageView) itemView.findViewById(R.id.id_album_poster);
            mAlbumTip = (TextView) itemView.findViewById(R.id.id_album_tip);
            mAlbumName = (TextView) itemView.findViewById(R.id.id_album_name);
        }
    }
}
