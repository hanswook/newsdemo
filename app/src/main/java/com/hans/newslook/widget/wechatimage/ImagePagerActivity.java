package com.hans.newslook.widget.wechatimage;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import android.widget.TextView;


import com.hans.newslook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_R = "image_r";


    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }
        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);

    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private List<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(fileList.get(position));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.gc();
    }

}
