package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.R2;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.widget.media.IjkVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by fanyuzeng on 2017/10/8.
 * Function:播放视频的Activity
 */

public class PlayActivity extends BaseActivity {
    private static final String TAG = "PlayActivity";
    @BindView(R2.id.id_video_view)
    IjkVideoView mVideoView;
    @BindView(R2.id.id_tv_loading_info)
    TextView mLoadingInfo;
    @BindView(R2.id.id_pb_loading)
    ProgressBar mProgressBar;
    @BindView(R2.id.rl_loading_layout)
    RelativeLayout mLoadingLayout;
    @BindView(R2.id.tv_horiontal_gesture)
    TextView mTvHoriontalGesture;
    @BindView(R2.id.tv_vertical_gesture)
    TextView mTvVerticalGesture;
    @BindView(R2.id.iv_player_close)
    ImageView mIvPlayerClose;
    @BindView(R2.id.tv_player_video_name)
    TextView mTvPlayerVideoName;
    @BindView(R2.id.iv_battery)
    ImageView mIvBattery;
    @BindView(R2.id.tv_sys_time)
    TextView mTvSysTime;
    @BindView(R2.id.fl_player_top_container)
    FrameLayout mFlPlayerTopContainer;
    @BindView(R2.id.iv_player_center_pause)
    ImageView mIvPlayerCenterPause;
    @BindView(R2.id.id_surface_container)
    FrameLayout mIdSurfaceContainer;
    @BindView(R2.id.cb_play_pause)
    CheckBox mCbPlayPause;
    @BindView(R2.id.iv_next_video)
    ImageView mIvNextVideo;
    @BindView(R2.id.tv_current_video_time)
    TextView mTvCurrentVideoTime;
    @BindView(R2.id.sb_player_seekbar)
    SeekBar mSbPlayerSeekbar;
    @BindView(R2.id.tv_total_video_time)
    TextView mTvTotalVideoTime;
    @BindView(R2.id.tv_bitstream)
    TextView mTvBitstream;
    @BindView(R2.id.ll_player_bottom_layout)
    LinearLayout mLlPlayerBottomLayout;
    @BindView(R2.id.activity_play)
    RelativeLayout mActivityPlay;

    private String mVideoUrl;
    private int mStreamType;
    private int mCurrentPosition;
    private Video mVideo;

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        if (intent != null) {
            mVideoUrl = intent.getStringExtra("url");
            mStreamType = intent.getIntExtra("type", 0);
            mCurrentPosition = intent.getIntExtra("currentPosition", 0);
            mVideo = intent.getParcelableExtra("video");
            Log.d(TAG, "initViews " + "videoUrl:" + mVideoUrl + "   ,streamType:" + mStreamType + ",currentPosition:" + mCurrentPosition + ",video:" + mVideo);
        }


        mLoadingInfo.setText("正在加载中...");
        //init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView.setVideoURI(Uri.parse(mVideoUrl));
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mVideoView.start();
            }
        });

        mVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onInfo " + "what:" + what);
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mProgressBar.setVisibility(View.VISIBLE);
                        mLoadingInfo.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mProgressBar.setVisibility(View.GONE);
                        mLoadingInfo.setVisibility(View.GONE);
                }
                return false;
            }
        });


    }

    @Override
    protected void initDatas() {
        if (mVideo != null) {
            mTvPlayerVideoName.setText(mVideo.getVideo_name());
        }
    }

    @OnClick({R.id.iv_player_close, R.id.iv_player_center_pause, R.id.cb_play_pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_player_close: //left top back button
                finish();
                break;
            case R.id.iv_player_center_pause: // center pause button

                break;
            case R.id.cb_play_pause: //left bottom start or pause button
                handlePlayPause();
                break;

        }
    }

    private void handlePlayPause() {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
