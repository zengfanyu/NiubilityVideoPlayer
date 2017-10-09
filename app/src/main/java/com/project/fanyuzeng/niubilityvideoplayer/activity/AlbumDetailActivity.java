package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.R2;
import com.project.fanyuzeng.niubilityvideoplayer.api.ErrorInfo;
import com.project.fanyuzeng.niubilityvideoplayer.api.SiteApi;
import com.project.fanyuzeng.niubilityvideoplayer.api.onGetAlbumDetailListener;
import com.project.fanyuzeng.niubilityvideoplayer.api.onGetVideoPlayUrlListener;
import com.project.fanyuzeng.niubilityvideoplayer.fragment.AlbumPlayGridFragment;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.utils.ImageUtils;
import com.project.fanyuzeng.niubilityvideoplayer.utils.NetWorkUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:某一电视剧或者电影或者BalaBala的详情页
 */

public class AlbumDetailActivity extends BaseActivity {
    private static final String TAG = "AlbumDetailActivity";
    @BindView(R2.id.id_iv_album)
    ImageView mAlbumPoster;
    @BindView(R2.id.id_tv_album_name)
    TextView mAlbumName;
    @BindView(R2.id.id_tv_album_director)
    TextView mAlbumDirector;
    @BindView(R2.id.id_tv_album_main_actor)
    TextView mAlbumMainActor;
    @BindView(R2.id.id_tv_album_desc)
    TextView mAlbumDesc;
    @BindView(R2.id.id_rl_album_desc_container)
    RelativeLayout mAlbumDescContainer;
    @BindView(R2.id.id_fragment_container)
    FrameLayout mIdFragmentContainer;
    @BindView(R2.id.id_tv_album_tip)
    TextView mAlbumTip;
    @BindView(R2.id.id_btn_normal)
    Button mNormalStreamPlay;
    @BindView(R2.id.id_btn_high)
    Button mHightStreamPlay;
    @BindView(R2.id.id_btn_super)
    Button mSuperStreamPlay;


    private Album mAlbum;
    private int mVideoNo;
    private boolean mIsShowDesc;
    private boolean mIsFavor;
    private AlbumPlayGridFragment mFragment;
    private int mCurrentVideoPosition;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        mAlbum = intent.getParcelableExtra("album");
        mVideoNo = intent.getIntExtra("videoNo", 0);
        mIsShowDesc = intent.getBooleanExtra("isShowDesc", false);

        setSupportActionBar(); //表示当前页面支持ActionBar
        setTitle(mAlbum.getTitle());
        setSupportArrowActionBar(true);

    }

    @OnClick({R2.id.id_btn_super, R2.id.id_btn_high, R2.id.id_btn_normal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_super:
                Log.d(TAG, "onClick " + "super");
                handleButtonClick(view);
                break;
            case R.id.id_btn_normal:
                Log.d(TAG, "onClick " + "normal");
                handleButtonClick(view);
                break;
            case R.id.id_btn_high:
                Log.d(TAG, "onClick " + "high");
                handleButtonClick(view);
                break;
            default:
                break;
        }
    }

    //三个Button有共同点，tag设置的id是一样的，value值不一样
    private void handleButtonClick(View view) {
        Button button = (Button) view;
        String url = (String) button.getTag(R.id.key_video_url);
        int streamType = (int) button.getTag(R.id.key_video_stream);
        Video video = (Video) button.getTag(R.id.key_video);
        int currentPosition = (int) button.getTag(R.id.key_video_current_number);

        Log.d(TAG, "APNType " + NetWorkUtils.getAPNType() + "");
        switch (NetWorkUtils.getAPNType()) {
            case 0://没网络

                break;
            case 1://wifi
                PlayActivity.lunchActivity(AlbumDetailActivity.this, url, video, streamType, currentPosition);
                break;
            case 2: //2G
            case 3: //3G
            case 4: //4G
                break;
        }

    }

    @Override
    protected void initDatas() {
        SiteApi.onGetAlbumDetail(mAlbum, new onGetAlbumDetailListener() {
            @Override
            public void onGetAlbumDetailsSuccess(final Album album) {
                mAlbum = album; //refresh value
                Log.i(TAG, "onGetAlbumDetailsSuccess " + album.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2017/9/30 处理此处initVideoPosition
                        refreshInfoViews();
                        mFragment = AlbumPlayGridFragment.newInstance(album, mIsShowDesc, 0);
                        mFragment.setVideoSelectedListener(mPlayVideoSelectedListener);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.id_fragment_container, mFragment);
                        transaction.commit();
                        getFragmentManager().executePendingTransactions();
                    }
                });
            }

            @Override
            public void onGetAlbumDetailFail(ErrorInfo info) {

            }
        });

    }

    private void refreshInfoViews() {
        mAlbumName.setText(mAlbum.getTitle());
        //导演
        if (!TextUtils.isEmpty(mAlbum.getDirector())) {
            mAlbumDirector.setText(getResources().getString(R.string.director) + mAlbum.getDirector());
            mAlbumDirector.setVisibility(View.VISIBLE);
        } else {
            mAlbumDirector.setVisibility(View.GONE);
        }
        //主演
        if (!TextUtils.isEmpty(mAlbum.getMainActor())) {
            mAlbumMainActor.setText(getResources().getString(R.string.mainactor) + mAlbum.getMainActor());
            mAlbumMainActor.setVisibility(View.VISIBLE);
        } else {
            mAlbumMainActor.setVisibility(View.GONE);
        }
        //描述
        if (!TextUtils.isEmpty(mAlbum.getAlbumDesc())) {
            mAlbumDesc.setText(mAlbum.getAlbumDesc());
            mAlbumDesc.setVisibility(View.VISIBLE);
        } else {
            mAlbumDesc.setVisibility(View.GONE);
        }
        //海报图 横图
        if (!TextUtils.isEmpty(mAlbum.getHorImgUrl())) {
            ImageUtils.displayImage(mAlbumPoster, mAlbum.getHorImgUrl());
        }
        //海报图 竖图
        if (!TextUtils.isEmpty(mAlbum.getVerImgUrl())) {
            ImageUtils.displayImage(mAlbumPoster, mAlbum.getVerImgUrl());
        }
        //Tip
        if (!TextUtils.isEmpty(mAlbum.getTip())) {
            mAlbumTip.setText(mAlbum.getTip());
            mAlbumTip.setVisibility(View.VISIBLE);
        } else {
            mAlbumTip.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_album_detail;
    }

    public static void lunch(Activity activity, Album album, int videoNo, boolean isShowDesc) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album", album);
        intent.putExtra("videoNo", videoNo);
        intent.putExtra("isShowDesc", isShowDesc);

        activity.startActivity(intent);
    }

    public static void lunch(Activity activity, Album album) {
        Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("album", album);
        activity.startActivity(intent);
    }


    //为Activity指定选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //将用XML定义的菜单资源扩充到 onPrepareOptionsMenu 回调提供的menu中
        getMenuInflater().inflate(R.menu.album_detail_menu, menu);
        return true;
    }

    /**
     * 此方法是根据MenuItem的id来处理它的点击事件的方法
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favor_item:
                if (mIsFavor) {
                    mIsFavor = false;
                    // TODO: 2017/9/30 收藏之后，存数据库
                    invalidateOptionsMenu();
                    Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_unfavor_item:
                if (!mIsFavor) {
                    mIsFavor = true;
                    // TODO: 2017/9/30 取消收藏之后从数据库移除
                    invalidateOptionsMenu();
                    Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //根据在 Activity 生命周期中发生的事件修改选项菜单,也就是菜单更新的方法，此方法必须要通过   invalidateOptionsMenu() 来 invoke
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favItem = menu.findItem(R.id.action_favor_item);
        MenuItem unfavItem = menu.findItem(R.id.action_unfavor_item);
        favItem.setVisible(mIsFavor);
        unfavItem.setVisible(!mIsFavor);
        return super.onPrepareOptionsMenu(menu);
    }

    private AlbumPlayGridFragment.onPlayVideoSelectedListener mPlayVideoSelectedListener = new AlbumPlayGridFragment.onPlayVideoSelectedListener() {
        @Override
        public void onPlayVideoSelected(Video video, int position) {
            mCurrentVideoPosition = position;
            SiteApi.onGetVideoPlayUrl(video, new onGetVideoPlayUrlListener() {
                @Override
                public void onGetSuperUrl(Video video, String url) {
                    Log.d(TAG, "onGetSuperUrl " + url);
                    bindDatatoButton(mSuperStreamPlay, StreamType.SUPER, url, video);
                }

                @Override
                public void onGetNormalUrl(Video video, String url) {
                    Log.d(TAG, "onGetNormalUrl " + url);
                    bindDatatoButton(mNormalStreamPlay, StreamType.NORMAL, url, video);
                }

                @Override
                public void onGetHightUrl(Video video, String url) {
                    Log.d(TAG, "onGetHightUrl " + url);
                    bindDatatoButton(mHightStreamPlay, StreamType.HIGHT, url, video);
                }

                @Override
                public void onGetFailed(ErrorInfo info) {
                    Log.d(TAG, "onGetFailed " + info.toString());
                    hideAllButton();
                }
            });

        }
    };

    private void bindDatatoButton(final Button whatStreamButton, int streamType, String url, Video video) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                whatStreamButton.setVisibility(View.VISIBLE);

            }
        });
        whatStreamButton.setTag(R.id.key_video_url, url);//视频url
        whatStreamButton.setTag(R.id.key_video, video);//视频info
        whatStreamButton.setTag(R.id.key_video_current_number, mCurrentVideoPosition);//当前位置
        whatStreamButton.setTag(R.id.key_video_stream, streamType);

    }

    private void hideAllButton() {
        mSuperStreamPlay.setVisibility(View.GONE);
        mNormalStreamPlay.setVisibility(View.GONE);
        mHightStreamPlay.setVisibility(View.GONE);
    }

    private static class StreamType {
        static final int SUPER = 1;
        static final int NORMAL = 2;
        static final int HIGHT = 3;
    }
}