package com.project.fanyuzeng.niubilityvideoplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.activity.AlbumDetailActivity;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class FaviroteGridAdapter extends BaseAdapter {
    public static final int TYPE_COUNT = 2;
    private Context mContext;
    private boolean mShowChecked;
    private List<FavoriteAlbum> mFavoriteList;
    private AlbumList mDatas;

    public FaviroteGridAdapter(Context context, AlbumList albumList) {
        mContext = context;
        mShowChecked = false;
        mDatas = albumList;
        mFavoriteList = new ArrayList<>();
        if (albumList != null && albumList.size() > 0) {
            for (Album album : albumList) {
                mFavoriteList.add(new FavoriteAlbum(album));
            }
        }

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public FavoriteAlbum getItem(int position) {
        return mFavoriteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mShowChecked ? 1 : 0;
    }

    public boolean isSelected() {
        return mShowChecked;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FavoriteAlbum favoriteAlbum = getItem(position);
        final Album album = favoriteAlbum.getAlbum();
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.favorite_item, null);
            holder.albumPoster = (ImageView) convertView.findViewById(R.id.iv_album_poster);
            holder.albumText = (TextView) convertView.findViewById(R.id.tv_album_name);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.cb_favorite);
            holder.mRlContainer = (RelativeLayout) convertView.findViewById(R.id.favorite_container);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mShowChecked) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.GONE);
        }
        if (mFavoriteList.size() > 0) {
            Point posterSize = null;
            posterSize = ImageUtils.getVerPosterSize(mContext, 3);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(posterSize.x, posterSize.y);
            holder.albumPoster.setLayoutParams(params);

            if (album.getVerImgUrl() != null) {
                ImageUtils.displayImage(holder.albumPoster, album.getVerImgUrl(), posterSize.x, posterSize.y);
            }
            holder.albumText.setText(album.getTitle());
            holder.mCheckBox.setChecked(favoriteAlbum.isChecked);

            if (!mShowChecked) {
                holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击跳转详情页
                        AlbumDetailActivity.lunch((Activity) mContext, album);
                    }
                });
                //长按删除
                final ViewHolder finalHolder = holder;
                holder.mRlContainer.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        favoriteAlbum.setChecked(true);//当前item选中
                        setShowChecked(true);
                        mFavoriteList.get(position).setChecked(true);
                        finalHolder.mRlContainer.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();//刷新,相当于调用getview
                        return true;
                    }
                });
            } else {
                final ViewHolder finalHolder1 = holder;
                holder.mRlContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = favoriteAlbum.isChecked();
                        favoriteAlbum.setChecked(checked);
                        finalHolder1.mCheckBox.setChecked(!checked);
                        favoriteAlbum.setChecked(!checked);
                    }
                });
            }

        }
        return convertView;
    }

    public void setShowChecked(boolean isSelected) {
        this.mShowChecked = isSelected;
    }


    public List<FavoriteAlbum> getFavoriteList() {
        return mFavoriteList;
    }

    public void unCheckedAllItem(){
        for (FavoriteAlbum favoriteAlbum : mFavoriteList) {
            favoriteAlbum.setChecked(false);
        }
    }
    public static class FavoriteAlbum {
        Album album;
        boolean isChecked;

        private FavoriteAlbum(Album album) {
            this.album = album;
        }

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    class ViewHolder {
        ImageView albumPoster;
        TextView albumText;
        CheckBox mCheckBox;
        RelativeLayout mRlContainer;
    }
}
