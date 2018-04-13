package com.hans.newslook.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hans.newslook.R;
import com.hans.newslook.test.ImageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.main_viewpager);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            urls.add("index:" + i);
        }
        MainPager mainPager = new MainPager(getSupportFragmentManager(), urls);
        viewPager.setAdapter(mainPager);

    }

    public class MainPager extends FragmentStatePagerAdapter {

        private List<String> urls;

        public MainPager(FragmentManager fm, List<String> urls) {
            super(fm);
            this.urls = urls;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(urls.get(position));
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }
}
