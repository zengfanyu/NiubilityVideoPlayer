package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.R2;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.utils.ImageUtils;

import butterknife.BindView;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:
 */

public class AlbumDetailActivity extends BaseActivity {

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


    private Album mAlbum;
    private int mVideoNo;
    private boolean mIsShowDesc;
    private boolean mIsFavor;

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        mAlbum = intent.getParcelableExtra("album");
        mVideoNo = intent.getIntExtra("videoNo", 0);
        mIsShowDesc = intent.getBooleanExtra("isShowDesc", true);

        setSupportActionBar(); //表示当前页面支持ActionBar
        setTitle(mAlbum.getTitle());
        setSupportArrowActionBar(true);

    }

    @Override
    protected void initDatas() {
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
}