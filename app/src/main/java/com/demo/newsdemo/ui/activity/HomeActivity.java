package com.demo.newsdemo.ui.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.demo.newsdemo.R;
import com.demo.newsdemo.adapter.ZhihuListAdapter;
import com.demo.newsdemo.base.BaseActivity;
import com.demo.newsdemo.model.bean.zhihu.StoriesBean;
import com.demo.newsdemo.contract.HomeContract;
import com.demo.newsdemo.di.DaggerHomeComponent;
import com.demo.newsdemo.di.HomeModule;
import com.demo.newsdemo.model.HomeModel;
import com.demo.newsdemo.presenter.HomePresenter;
import com.demo.newsdemo.utils.DateUtil;
import com.demo.newsdemo.utils.LogUtil;
import com.demo.newsdemo.utils.ShortTimeUtil;
import com.demo.newsdemo.utils.recycler.BaseRecyclerAdapter;

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

    private List<StoriesBean> datas;
    private int dataDate, dateNow;
    private String dateShow;
    private ZhihuListAdapter adapter;
    protected Boolean isLoading;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        super.initData();
        homePresenter.attachView(this);
        initDagger();
        initDataType();
        requestData();
    }

    private void requestData() {
        isLoading = true;
        homePresenter.loadData(this);
    }

    private void initDataType() {
        isLoading = false;
        datas = new ArrayList<>();
        dataDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        dateNow = dataDate;
        LogUtil.e(TAG, "dataDate:" + dataDate + ",dateShow:" + dateShow);
    }

    private void initDagger() {
        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this, new HomeModel()))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initTitleBar();
        initRecycler();
        initListener();
    }

    private void initListener() {
        zhihuRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoading) {
                    LogUtil.e(TAG, "正在加载哦哦哦哦哦哦哦哦");
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
                            LogUtil.e(TAG, "滑动间隔过短");
                            return;
                        }
                        loadMore();
                    }
                    LogUtil.e(TAG, lastItemPosition + " /  " + firstItemPosition + " /  " + "/datasize:" + datas.size());
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
                    zhihuToolbar.setTitle(datas.get(firstItemPosition).getDataDate());
                }

            }
        });

    }

    private void initRecycler() {
        adapter = new ZhihuListAdapter(context, datas);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("id", datas.get(position).getId() + "");
                Intent intent = new Intent(context, ZhihuDetailActivity.class);
                intent.putExtra("zhihu_id", datas.get(position).getId() + "");
//                intent.putExtras(bundle);
                context.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });

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
        LogUtil.e(TAG, "zhihu:" + stories.get(0).getDataDate() + ",size:" + stories.size());
        getDataString();
        for (int i = 0; i < stories.size(); i++) {
            stories.get(i).setDelegateType(0);
            stories.get(i).setDataDate(dateShow);
        }
        StoriesBean sb = new StoriesBean();
        sb.setDelegateType(1);
        sb.setDataDate(dateShow);
        stories.add(0, sb);
        datas.addAll(stories);
        adapter.notifyItemRangeInserted(datas.size() - stories.size(), datas.size() - 1);
        LogUtil.e(TAG, datas.size() + "");
        LogUtil.e(TAG, "storiesSize:" + stories.size());
        isLoading = false;
    }

    @Override
    public void showGetdataFailed() {
        LogUtil.e(TAG, "zhihu:showGetdataFailed");
        LogUtil.e(TAG, "getDataFailed");
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
        homePresenter.loadMoreData(dataDate + "", context);
        dataDate--;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.attachView(this);
    }
}
