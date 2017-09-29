package com.project.fanyuzeng.niubilityvideoplayer.utils;

import android.content.Context;
import android.graphics.Point;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.fanyuzeng.niubilityvideoplayer.R;

/**
 * Created by fanyuzeng on 2017/9/28.
 * Function:
 */

public class ImageUtils {

    private static final float VER_POSTER_RATIO = 0.73f;
    private static final float HOR_POSTER_RATIO = 1.5f;

    public static void displayImage(ImageView imageView, String imgUrl, int width, int height) {
        if (imageView != null && imgUrl != null && width > 0 && height > 0) {
            if (width > height) {
                Glide
                        .with(imageView.getContext())
                        .load(imgUrl)
                        .error(R.drawable.ic_loading_hor)
                        .fitCenter()
                        .override(height, width)
//                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);

            } else {
                Glide
                        .with(imageView.getContext())
                        .load(imgUrl)
                        .error(R.drawable.ic_loading_hor)
                        .centerCrop()
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }
    }

    /**
     * 获取竖图的最佳比例
     *
     * @param context
     * @param columns RecyclerView中展示的行数
     * @return
     */
    public static Point getVerPosterSize(Context context, int columns) {
        int width = SizeUtils.getScreenSize(context).x / columns;
        //此处减掉的dimen_8dp 是因为在detail_list_item布局文件中，有dimen_8dp的padding
        width = (int) (width - context.getResources().getDimension(R.dimen.dimen_8dp));
        int height = Math.round(width / VER_POSTER_RATIO);
        return new Point(width, height);
    }

    /**
     * 获取竖图的最佳比例
     *
     * @param context
     * @param columns RecyclerView中展示的行数
     * @return
     */
    public static Point getHorPosterSize(Context context, int columns) {
        int width = SizeUtils.getScreenSize(context).x / columns;
        //此处减掉的dimen_8dp 是因为在detail_list_item布局文件中，有dimen_8dp的padding
        width = (int) (width - context.getResources().getDimension(R.dimen.dimen_8dp));
        int height = Math.round(width / HOR_POSTER_RATIO);
        return new Point(width, height);
    }
}
