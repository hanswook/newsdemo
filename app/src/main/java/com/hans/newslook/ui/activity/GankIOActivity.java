package com.hans.newslook.ui.activity;


import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hans.newslook.R;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.base.BaseCoreFragment;
import com.hans.newslook.ui.fragment.TypeFragment;
import com.hans.newslook.utils.LogUtil;
import com.hans.newslook.utils.ResourceUtil;
import com.hans.newslook.utils.image.GlideApp;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class GankIOActivity extends BaseActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_nav_view)
    NavigationView mainNavView;
    @BindView(R.id.gankio_drawer_layout)
    DrawerLayout drawerLayout;

    private String mCurrentType;
    private boolean isBackPressed;
    private Map<String, BaseCoreFragment> mTypeFragments;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_io;
    }

    @Override
    protected void initView() {
        super.initView();
        initStatusBar();
        initDrawer();
        initNavigationView();
        doReplace(ResourceUtil.res2String(context, R.string.gank));
    }

    private void initNavigationView() {
        mainNavView.setCheckedItem(R.id.nav_gank);
        mainNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_gank:
                        doReplace(ResourceUtil.res2String(context, R.string.gank));
                        break;
                    case R.id.nav_girl:
                        doReplace(ResourceUtil.res2String(context, R.string.girl));
                        break;
                    case R.id.nav_set:
                        openSet();
                        break;
                    case R.id.nav_about:
                        break;
                }
                //隐藏NavigationView
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void openSet() {
        Toast.makeText(context, "open set。打开设置", Toast.LENGTH_SHORT).show();
    }

    private void initDrawer() {
        setSupportActionBar(mainToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mainToolbar.setTitle("GankIo");

    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.setFitsSystemWindows(true);
            drawerLayout.setClipToPadding(false);
        }
        setStatusBarColor(R.color.colorAccent);

    }

    private void doReplace(String type) {
        LogUtil.e(TAG, "doReplace");

        if (!type.equalsIgnoreCase(mCurrentType)) {
            replaceFragment(TypeFragment.newInstance(type), type, mCurrentType);
            mCurrentType = type;
        }

    }

    private void replaceFragment(BaseCoreFragment baseFragment, String tag, String lastTag) {
        LogUtil.e(TAG, "replaceFragment");
        if (mTypeFragments.get(tag) == null) {
            mTypeFragments.put(tag, baseFragment);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_fragment_container, baseFragment, tag).commit();
        }

        if (mTypeFragments.get(lastTag) != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mTypeFragments.get(lastTag))
                    .show(mTypeFragments.get(tag))
                    .commit();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mTypeFragments = new HashMap<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_share:
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
