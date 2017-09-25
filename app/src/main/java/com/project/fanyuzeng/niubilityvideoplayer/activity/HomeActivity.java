package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;

import com.project.fanyuzeng.niubilityvideoplayer.FragmentManagerWrapper;
import com.project.fanyuzeng.niubilityvideoplayer.R;
import com.project.fanyuzeng.niubilityvideoplayer.fragment.AboutFragment;
import com.project.fanyuzeng.niubilityvideoplayer.fragment.BaseFragment;
import com.project.fanyuzeng.niubilityvideoplayer.fragment.BlogFragment;
import com.project.fanyuzeng.niubilityvideoplayer.fragment.HomeFragment;

/**
 * Created by fanyuzeng on 2017/9/21.
 * Function:
 */
public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private MenuItem mPreItem;
    private FragmentManager mFragmentManager;
    private BaseFragment mCurrentFragment;

    @Override
    protected void initViews() {

        mDrawerLayout = bindViewId(R.id.id_drawer_layout);
        mNavigationView = bindViewId(R.id.id_navigation_view);

        //设置Toolbar
        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("首页");
        //设置HomeActivity、DrawerLayout、Toolbar的navigationIcon 三者联动
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mPreItem = mNavigationView.getMenu().getItem(0);
        //一打开Drawer默认选中第0个，注意出差check mark效果的使用
        mPreItem.setCheckable(true);
        mPreItem.setChecked(true);
        initFragment();
        handleNavigationViewItem();
    }

    private void initFragment() {
        mCurrentFragment = FragmentManagerWrapper.getInstance().createFragment(HomeFragment.class);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.id_content, mCurrentFragment).commit();
    }

    private void handleNavigationViewItem() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (mPreItem != null) {
                    mPreItem.setCheckable(false);
                    mPreItem.setChecked(false);
                }

                switch (item.getItemId()) {
                    case R.id.navigation_item_video:
                        switchSelectedFragment(HomeFragment.class, R.string.home_title);
                        break;
                    case R.id.navigation_item_blog:
                        switchSelectedFragment(BlogFragment.class, R.string.blog_title);
                        break;
                    case R.id.navigation_item_about:
                        switchSelectedFragment(AboutFragment.class, R.string.about_title);
                        break;

                }
                mPreItem = item;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                item.setCheckable(true);
                item.setChecked(true);

                return false;
            }
        });
    }

    private void switchSelectedFragment(Class<? extends BaseFragment> selectedFragment, int resId) {
        BaseFragment fragment = FragmentManagerWrapper.getInstance().createFragment(selectedFragment);
        if (fragment.isAdded())
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        else
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.id_content, fragment).commitAllowingStateLoss();
        mCurrentFragment = fragment;
        mToolbar.setTitle(resId);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }
}
