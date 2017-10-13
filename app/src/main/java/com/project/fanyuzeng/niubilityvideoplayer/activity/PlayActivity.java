package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
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
import com.project.fanyuzeng.niubilityvideoplayer.utils.DateUtils;
import com.project.fanyuzeng.niubilityvideoplayer.widget.media.IjkVideoView;

import java.util.Formatter;
import java.util.Locale;

import butterknife.BindView;
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
    FrameLayout mPlayerTopContainer;
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
    SeekBar mPlayerSeekbar;
    @BindView(R2.id.tv_total_video_time)
    TextView mTvTotalVideoTime;
    @BindView(R2.id.tv_bitstream)
    TextView mTvBitstream;
    @BindView(R2.id.ll_player_bottom_layout)
    LinearLayout mPlayerBottomLayout;
    @BindView(R2.id.activity_play)
    RelativeLayout mActivityPlay;

    public static final int CHECK_TIME = 1;
    public static final int CHECK_BATTERY = 2;
    public static final int CHECK_PROGRESS = 4;

    public static final int AUTO_HIDE_TIME = 10000;
    public static final int AFTER_DRAGGER_HIDE_TIME = 10000;


    private String mVideoUrl;
    private int mStreamType; //normal hight super
    private Video mVideo;
    private EventHandler mEventHandler;
    //top and bottom operation panel
    private boolean mIsPanelShowing = false;
    private int mBatteryLevel;
    private boolean mIsMove = false;
    private boolean mIsDragger;

    private Formatter mFormatter;
    private StringBuilder mFortterBuilder;


    private class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_TIME:
                    mTvSysTime.setText(DateUtils.getCurremntTime());
                    break;
                case CHECK_BATTERY:
                    setCurrentBattery();
                    break;
                case CHECK_PROGRESS:
                    updateProgressAtTime();
                    break;
                default:
                    break;
            }
        }
    }

    private void updateProgressAtTime() {
        long duration = mVideoView.getDuration();
        int currentProgress = mPlayerSeekbar.getProgress();
        long nowDuration = (currentProgress * duration) / 1000L;
        mTvCurrentVideoTime.setText(DateUtils.stringForTime((int) nowDuration, mFormatter, mFortterBuilder));
    }

    @Override
    protected void initViews() {
        mEventHandler = new EventHandler(Looper.getMainLooper());

        getExtraData();

        initSeekBar();

        initVideoView();


        registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        toggleTopAndBottomLayout();
    }

    private void initSeekBar() {
        mPlayerSeekbar.setMax(1000);
        mFortterBuilder = new StringBuilder();
        mFormatter = new Formatter(mFortterBuilder, Locale.getDefault());
        mPlayerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                //2729816
                long duration = mVideoView.getDuration();
                long newPosition = (duration * progress) / 1000L;
                mTvCurrentVideoTime.setText(DateUtils.stringForTime((int) newPosition, mFormatter, mFortterBuilder));
                Log.d(TAG, "onProgressChanged " + "progress:" + progress + ",duration:" + duration + ",newPosition:" + newPosition);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIsDragger = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mIsDragger = false;
                int progress = seekBar.getProgress(); //the end position drag to
                long duration = mVideoView.getDuration();
                long newPosition = (duration * progress) / 1000;
                Log.d(TAG, "onStopTrackingTouch " + "progress:" + progress + ",duration:" + duration + ",newPosition:" + newPosition);
                mVideoView.seekTo((int) newPosition);

                mEventHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideTopAndBottomLayout();
                    }
                }, AFTER_DRAGGER_HIDE_TIME);
            }
        });
    }

    @Override
    protected void initDatas() {
        if (mVideo != null) {
            mTvPlayerVideoName.setText(mVideo.getVideo_name());
        }
        if (mStreamType > 0) {
            updateStreamTypeText();
        }
    }

    private void getExtraData() {
        Intent intent = getIntent();
        if (intent != null) {
            mVideoUrl = intent.getStringExtra("url");
            mStreamType = intent.getIntExtra("type", 0);
            int currentPosition = intent.getIntExtra("currentPosition", 0);
            mVideo = intent.getParcelableExtra("video");
            Log.d(TAG, "initViews " + "videoUrl:" + mVideoUrl + "   ,streamType:" + mStreamType + ",currentPosition:" + currentPosition + ",video:" + mVideo);
        }
    }

    private void initVideoView() {
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
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mProgressBar.setVisibility(View.VISIBLE);
                        mLoadingInfo.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mProgressBar.setVisibility(View.GONE);
                        mLoadingInfo.setVisibility(View.GONE);
                        mTvTotalVideoTime.setText(DateUtils.stringForTime(mVideoView.getDuration(), mFormatter, mFortterBuilder));
                        Log.d(TAG,"onInfo " +"duration:"+mVideoView.getDuration()+",totalTime:"+DateUtils.stringForTime(mVideoView.getDuration(), mFormatter, mFortterBuilder) );
                }
                return false;
            }
        });
    }

    private void setCurrentBattery() {
        Log.d(TAG, "setCurrentBattery " + "mBatteryLevel:" + mBatteryLevel);
        if (0 < mBatteryLevel && mBatteryLevel <= 10) {
            mIvBattery.getDrawable().setLevel(10);
        } else if (10 < mBatteryLevel && mBatteryLevel <= 20) {
            mIvBattery.getDrawable().setLevel(20);
        } else if (20 < mBatteryLevel && mBatteryLevel <= 50) {
            mIvBattery.getDrawable().setLevel(50);
        } else if (50 < mBatteryLevel && mBatteryLevel <= 80) {
            mIvBattery.getDrawable().setLevel(80);
        } else if (80 < mBatteryLevel && mBatteryLevel <= 100) {
            mIvBattery.getDrawable().setLevel(100);
        }
    }

    /**
     * Get phone battery level by broadcast
     */
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            Log.d(TAG, "onReceive " + "mBatteryLevel:" + mBatteryLevel);
        }
    };


    @OnClick({R.id.iv_player_close, R.id.iv_player_center_pause, R.id.cb_play_pause})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_player_close: //left top back button
                finish();
                break;
            case R.id.iv_player_center_pause: // center pause button
                mVideoView.start();
                updatePlayPauseStatus(true);
                break;
            case R.id.cb_play_pause: //left bottom start or pause button
                handlePlayPause();
                break;

        }
    }

    private void handlePlayPause() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
            updatePlayPauseStatus(false);
        } else {
            mVideoView.start();
            updatePlayPauseStatus(true);
        }
    }

    /**
     * 更新中间大的暂停按钮和左下角的暂停播放按钮的状态，保持同步
     *
     * @param isPlaying
     */
    private void updatePlayPauseStatus(boolean isPlaying) {
        mIvPlayerCenterPause.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
        mCbPlayPause.setChecked(isPlaying);
        mCbPlayPause.refreshDrawableState();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (!mIsMove) {
                toggleTopAndBottomLayout();
            }
        }
        return super.onTouchEvent(event);
    }
    //上下面板的开关
    private void toggleTopAndBottomLayout() {

        if (mIsPanelShowing) {
            hideTopAndBottomLayout();
        } else {
            if (mBatteryLevel > 0) {
                showTopAndBottomLayout();
            } else {
                mEventHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showTopAndBottomLayout();
                    }
                }, 500);
            }
        }
        //hide panel after 10 second without any operation
        mEventHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideTopAndBottomLayout();
            }
        }, AUTO_HIDE_TIME);
    }
    //隐藏上下面板
    private void hideTopAndBottomLayout() {
        if (mIsDragger)
            return;
        mIsPanelShowing = false;
        mPlayerTopContainer.setVisibility(View.GONE);
        mPlayerBottomLayout.setVisibility(View.GONE);
    }
    //显示上下面板
    private void showTopAndBottomLayout() {
        mIsPanelShowing = true;
        mPlayerTopContainer.setVisibility(View.VISIBLE);
        mPlayerBottomLayout.setVisibility(View.VISIBLE);

        updateProgress();

        if (mEventHandler != null) {
            mEventHandler.removeMessages(CHECK_TIME);
            mEventHandler.obtainMessage(CHECK_TIME).sendToTarget();

            mEventHandler.removeMessages(CHECK_BATTERY);
            mEventHandler.sendEmptyMessageAtTime(CHECK_BATTERY, 1000);

            mEventHandler.removeMessages(CHECK_PROGRESS);
            mEventHandler.obtainMessage(CHECK_PROGRESS).sendToTarget();
        }
    }

    //更新进度条和当前播放时间
    private void updateProgress() {
        int currentPosition = mVideoView.getCurrentPosition();//ms
        int duration = mVideoView.getDuration();//ms
        if (mPlayerSeekbar != null) {
            if (duration > 0) {//not live
                long pos = currentPosition * 1000 / duration; //cast to long avoid overflow 最大值为1000
                mPlayerSeekbar.setProgress((int) pos);
            }
            int percent = mVideoView.getBufferPercentage();
            mPlayerSeekbar.setSecondaryProgress(percent);
            mTvCurrentVideoTime.setText(DateUtils.stringForTime(currentPosition, mFormatter, mFortterBuilder));
        }
    }

    //更新视频码率文字
    private void updateStreamTypeText() {

        switch (mStreamType) {
            case AlbumDetailActivity.StreamType.NORMAL:
                mTvBitstream.setText(R.string.stream_normal);
                break;
            case AlbumDetailActivity.StreamType.HIGHT:
                mTvBitstream.setText(R.string.stream_high);
                break;
            case AlbumDetailActivity.StreamType.SUPER:
                mTvBitstream.setText(R.string.stream_super);
                break;
            default:
                break;
        }
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
    protected void onPause() {
        super.onPause();
        if (mBatteryReceiver != null) {
            unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
        if (mVideoView != null)
            mVideoView.stopPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoView != null)
            mVideoView.stopPlayback();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
