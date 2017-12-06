package com.demo.newsdemo.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.newsdemo.R;
import com.demo.newsdemo.adapter.GankItemAdapter;
import com.demo.newsdemo.base.BaseRxFragment;
import com.demo.newsdemo.contract.GankItemContract;
import com.demo.newsdemo.di.DaggerGankItemComponent;
import com.demo.newsdemo.di.GankItemModule;
import com.demo.newsdemo.model.GankItemModel;
import com.demo.newsdemo.model.bean.GankItemData;
import com.demo.newsdemo.presenter.GankItemPresenter;
import com.demo.newsdemo.ui.activity.WebDetailActivity;
import com.demo.newsdemo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankItemFragment extends BaseRxFragment implements GankItemContract.View, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 0;
    private String mSubtype;
    private GankItemAdapter adapter;
    private List<GankItemData> datas;

    private boolean isLoadMore;

    private boolean isLoading;

    private int mLastVisibleItemPosition;

    private LinearLayoutManager layoutManager;


    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipfreshlayout;
    @BindView(R.id.type_item_fab)
    FloatingActionButton mFab;

    @OnClick(R.id.type_item_fab)
    public void onViewClicked() {
        mRecyclerview.smoothScrollToPosition(0);
    }

    @Inject
    GankItemPresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void init() {
        isLoading = false;
        isLoadMore = false;
        datas = new ArrayList<>();
        adapter = new GankItemAdapter(R.layout.item_gank_layout, datas) {
        };
       /* adapter = new CommonAdapter<GankItemData>(getActivity(), R.layout.item_gank_layout, datas) {
            @Override
            protected void convert(BaseViewHolder holder, GankItemData gankItemData, int position) {
                TextView descTv = holder.getView(R.id.gank_item_desc);
                TextView whoTv = holder.getView(R.id.gank_item_who);
                TextView publishDateTv = holder.getView(R.id.gank_item_publishedat);
                ImageView iconImg = holder.getView(R.id.gank_item_icon);

                descTv.setText(gankItemData.getDesc());
                String who = TextUtils.isEmpty(gankItemData.getWho()) ? "null" : gankItemData.getWho();
                whoTv.setText(who);
                publishDateTv.setText(gankItemData.getPublishedAt().substring(0, 10));
                String[] images = gankItemData.getImages();
                if (images != null && images.length > 0) {
                    LogUtil.e(TAG, images[0] + "?imageView2/0/w/100");
                    GlideApp.with(context).load(images[0] + "?imageView2/0/w/100").placeholder(R.mipmap.web).into(iconImg);
                } else {
                    String url = gankItemData.getUrl();
                    int iconId;
                    if (url.contains("github")) {
                        iconId = R.mipmap.github;
                    } else if (url.contains("jianshu")) {
                        iconId = R.mipmap.jianshu;
                    } else if (url.contains("csdn")) {
                        iconId = R.mipmap.csdn;
                    } else if (url.contains("miaopai")) {
                        iconId = R.mipmap.miaopai;
                    } else if (url.contains("acfun")) {
                        iconId = R.mipmap.acfun;
                    } else if (url.contains("bilibili")) {
                        iconId = R.mipmap.bilibili;
                    } else if (url.contains("youku")) {
                        iconId = R.mipmap.youku;
                    } else if (url.contains("weibo")) {
                        iconId = R.mipmap.weibo;
                    } else if (url.contains("weixin")) {
                        iconId = R.mipmap.weixin;
                    } else {
                        iconId = R.mipmap.web;
                    }
                    GlideApp.with(context).load(iconId).into(iconImg);
                }
            }
        };*/
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, WebDetailActivity.class);
                intent.putExtra("gank_item_data_url", datas.get(position).getUrl());
                startActivity(intent);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore = true;
                loadMore();
            }
        }, mRecyclerview);
        layoutManager = new LinearLayoutManager(context);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(adapter);

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLastVisibleItemPosition < layoutManager.findLastVisibleItemPosition())
                    mFab.show();
                if (mLastVisibleItemPosition > layoutManager.findLastVisibleItemPosition() && mFab.isShown())
                    mFab.hide();
                if (dy>50 && mSwipfreshlayout.isRefreshing())
                    mSwipfreshlayout.setRefreshing(false);
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });

        mSwipfreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mSwipfreshlayout.setOnRefreshListener(this);

//        mSwipfreshlayout.post(() -> mSwipfreshlayout.setRefreshing(true));


    }

    private void loadMore() {
        LogUtil.e(TAG, "loadMore:mSubtype:" + mSubtype + ",PAGE_COUNT:" + PAGE_COUNT);
        if (!isLoading) {
            isLoading = true;
            LogUtil.e(TAG, "load data isLoading:" + isLoading);
            PAGE_COUNT++;
            mPresenter.loadData(mSubtype, PAGE_COUNT);
        }

    }

    @Override
    protected void initData() {
        LogUtil.e(TAG, "initData");
        if (getArguments() == null)
            return;
        mSubtype = getArguments().getString(SUB_TYPE);
        LogUtil.e(TAG, "mSubtype:" + mSubtype);

    }

    @Override
    protected void fetchData() {
        LogUtil.e(TAG, "fetchData");
        DaggerGankItemComponent.builder().gankItemModule(new GankItemModule(this, new GankItemModel()))
                .build().inject(this);
        addPresenter(mPresenter);
        mPresenter.loadData(mSubtype, PAGE_COUNT);
        PAGE_COUNT++;

    }


    @Override
    public void showError() {
        isLoading = false;
        mSwipfreshlayout.setRefreshing(false);

    }

    @Override
    public void complete() {

    }

    @Override
    public void updateUI(List<GankItemData> data) {
        isLoading = false;
        if (isLoadMore) {
            if (data.size() == 0) {
                LogUtil.e(TAG, "updateUI : 获取数据成功，数据数量为0");
                adapter.loadMoreFail();
            } else {
                int size = data.size();
                datas.addAll(data);
                adapter.notifyItemRangeInserted(PAGE_COUNT * 10, ((PAGE_COUNT * 10) + size));
                adapter.loadMoreComplete();

            }
        } else {
            datas.clear();
            datas.addAll(data);
            adapter.notifyDataSetChanged();
            mSwipfreshlayout.setRefreshing(false);
        }
    }


    public static GankItemFragment newInstance(String subtype) {
        LogUtil.e("GankItemFragment", "GankItemFragment newInstance");
        GankItemFragment fragment = new GankItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUB_TYPE, subtype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 0;
        loadMore();
    }
}
