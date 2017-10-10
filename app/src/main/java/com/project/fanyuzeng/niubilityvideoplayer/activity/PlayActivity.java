package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by fanyuzeng on 2017/10/8.
 * Function:播放视频的Activity
 */

public class PlayActivity extends BaseActivity {
    private static final String TAG = "PlayActivity";

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        if (intent != null) {
            String videoUrl = intent.getStringExtra("url");
            int streamType = intent.getIntExtra("type", 0);
            int currentPosition = intent.getIntExtra("currentPosition", 0);
            Video video = intent.getParcelableExtra("video");
            Log.d(TAG, "initViews " + "videoUrl:" + videoUrl + "   ,streamType:" + streamType + ",currentPosition:" + currentPosition + ",video:" + video);
        }
        IjkVideoView videoView=bindViewId(R.id.id_video_view);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_play;
    }

    public static void lunchActivity(Activity activity, String videoUrl, Video video, int type, int currentPosition) {
        Intent intent = new Intent(activity, PlayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("url", videoUrl);
        intent.putExtra("video", video);
        intent.putExtra("type", type);
        intent.putExtra("currentPosition", currentPosition);
        activity.startActivity(intent);
    }
}
