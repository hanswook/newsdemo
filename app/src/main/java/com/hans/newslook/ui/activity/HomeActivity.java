package com.hans.newslook.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hans.newslook.R;
import com.hans.newslook.adapter.ZhihuListAdapter;
import com.hans.newslook.base.BaseActivity;
import com.hans.newslook.model.bean.zhihu.StoriesBean;
import com.hans.newslook.contract.HomeContract;
import com.hans.newslook.di.DaggerHomeComponent;
import com.hans.newslook.di.HomeModule;
import com.hans.newslook.model.HomeModel;
import com.hans.newslook.presenter.HomePresenter;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.baseutils.DateUtil;
import com.hans.newslook.utils.baseutils.LogUtils;
import com.hans.newslook.utils.baseutils.ShortTimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @Inject
    HomePresenter homePresenter;

    @BindView(R.id.zhihu_toolbar)
    Toolbar zhihuToolbar;
    @BindView(R.id.zhihu_recycler)
    RecyclerView zhihuRecycler;

    @BindView(R.id.zhihu_swipe)
    SwipeRefreshLayout zhihuSwipe;
    @BindView(R.id.zhihu_noweb_tv)
    TextView zhihuNoInternet;

    private List<StoriesBean> datas;
    private int dataDate, dateNow;
    private String dateShow;
    private ZhihuListAdapter adapter;
    protected Boolean isLoading;


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }


    private void requestData() {
        isLoading = true;
        homePresenter.loadData();
    }

    private void initDataType() {
        isLoading = false;
        datas = new ArrayList<>();
        dataDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        dateNow = dataDate;
        LogUtils.e(TAG, "dataDate:" + dataDate + ",dateShow:" + dateShow);
    }

    private void initDagger() {
        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this, new HomeModel()))
                .build()
                .inject(this);
        addPresenter(homePresenter);
    }

    @Override
    protected void init() {
        initDagger();
        initDataType();
        requestData();

        initTitleBar();
        initRecycler();
        initListener();
        zhihuSwipe.setOnRefreshListener(() -> {
            refreshData();
        });
    }

    private void refreshData() {
        homePresenter.loadData();

    }

    private void initListener() {
        zhihuRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoading) {
                    LogUtils.e(TAG, "正在加载哦哦哦哦哦哦哦哦");
                    return;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (lastItemPosition == datas.size() - 1) {
//                        根据滑动间隔，避免多次加载的情况。
                        if (ShortTimeUtil.isFastClick()) {
                            LogUtils.e(TAG, "滑动间隔过短");
                            return;
                        }
                        loadMore();
                    }
                    LogUtils.e(TAG, lastItemPosition + " /  " + firstItemPosition + " /  " + "/datasize:" + datas.size());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition < datas.size() && firstItemPosition > 0)
                        zhihuToolbar.setTitle(datas.get(firstItemPosition).getDataDate());
                }

            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("zhihu_history", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(datas.get(position).getId() + "", true);
                LogUtils.e(TAG, "datas.get(position).getId()：" + datas.get(position).getId() + ",position:" + position);
                editor.apply();
                ((TextView) adapter.getViewByPosition(zhihuRecycler,position, R.id.item_android_tv_title)).setTextColor(getResources().getColor(R.color.gray_text));
                Intent intent = new Intent(context, ZhihuDetailActivity.class);
                intent.putExtra("zhihu_id", datas.get(position).getId() + "");
                context.startActivity(intent);
            }
        });
    }

    private void initRecycler() {
        adapter = new ZhihuListAdapter(datas);


        zhihuRecycler.setAdapter(adapter);
        zhihuRecycler.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initTitleBar() {
        zhihuToolbar.setTitle("知乎日报");
        zhihuToolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(zhihuToolbar);
    }


    @Override
    public void updateList(List<StoriesBean> stories) {
        if (zhihuSwipe.isRefreshing()) {
            LogUtils.e(TAG, "zhihu:showGetdataFailed");
            zhihuSwipe.setRefreshing(false);
            dataDate = dateNow;
            datas.clear();
            adapter.notifyDataSetChanged();
        }
        LogUtils.e(TAG, "zhihu:" + stories.get(0).getDataDate() + ",size:" + stories.size());
        getDataString();
        StoriesBean sb = new StoriesBean();
        sb.setItemType(Constants.ZHIHU_DATETITLE);
        sb.setDataDate(dateShow);
        stories.add(0, sb);
        datas.addAll(stories);
        adapter.notifyItemRangeInserted(datas.size() - stories.size(), datas.size() - 1);
        LogUtils.e(TAG, datas.size() + "");
        LogUtils.e(TAG, "storiesSize:" + stories.size());
        isLoading = false;
    }

    @Override
    public void showGetdataFailed() {
        LogUtils.e(TAG, "getDataFailed");
        isLoading = false;
    }

    private void getDataString() {
        dateShow = new SimpleDateFormat("MM月dd日 EEEE").format(DateUtil.getTheDay(new Date(), dataDate - dateNow));
        if (dataDate == dateNow) {
            dateShow = "今日要闻";
        }
    }

    private void loadMore() {
        isLoading = true;
        homePresenter.loadMoreData(dataDate + "", this);
        dataDate--;
    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
