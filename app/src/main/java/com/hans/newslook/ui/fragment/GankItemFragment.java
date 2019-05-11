package com.hans.newslook.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hans.newslook.R;
import com.hans.newslook.adapter.GankItemAdapter;
import com.hans.newslook.base.BaseRxFragment;
import com.hans.newslook.contract.GankItemContract;
import com.hans.newslook.di.DaggerGankItemComponent;
import com.hans.newslook.di.GankItemModule;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.presenter.GankItemPresenter;
import com.hans.newslook.ui.activity.WebDetailActivity;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.baseutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankItemFragment extends BaseRxFragment implements GankItemContract.View, SwipeRefreshLayout.OnRefreshListener {

    private String mSubtype;
    private GankItemAdapter itemAdapter;
    private List<GankItemData> datas;

    private boolean isLoadMore;

    private boolean isLoading;


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
        itemAdapter = new GankItemAdapter(R.layout.item_gank_layout, datas) {
        };
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, WebDetailActivity.class);
                intent.putExtra("gank_item_data_url", itemAdapter.getData().get(position).getUrl());
                startActivity(intent);
            }
        });
        itemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore = true;
                loadMore();
            }
        }, mRecyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerview.setAdapter(itemAdapter);
        mSwipfreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipfreshlayout.setOnRefreshListener(this);
       /* mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtils.e(TAG, "dx:" + dx + ",dy:" + dy);
                if (dy > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
        });*/
    }

    private void loadMore() {
        if (!isLoading) {
            isLoading = true;
            LogUtils.e(TAG, "load data isLoading:" + isLoading);
            mPresenter.loadData(mSubtype);
        }

    }


    @Override
    protected void initData() {
        LogUtils.e(TAG, "initData");
        if (getArguments() == null)
            return;
        mSubtype = getArguments().getString(Constants.SUB_TYPE);
        LogUtils.e(TAG, "mSubtype:" + mSubtype);

    }

    @Override
    protected void fetchData() {
        LogUtils.e(TAG, "fetchData");
        DaggerGankItemComponent.builder().gankItemModule(new GankItemModule(this))
                .build().inject(this);
        addPresenter(mPresenter);
        mPresenter.loadData(mSubtype);

    }


    @Override
    public void showError() {
        isLoading = false;
        mSwipfreshlayout.setRefreshing(false);

    }


    @Override
    public void updateUI(List<GankItemData> data) {
        isLoading = false;
        if (isLoadMore) {
            if (data.size() == 0) {
                LogUtils.e(TAG, "updateUI : 获取数据成功，数据数量为0");
                itemAdapter.loadMoreFail();
            } else {
                int size = data.size();
                itemAdapter.addData(data);
                itemAdapter.loadMoreComplete();

            }
        } else {
            itemAdapter.replaceData(data);
            itemAdapter.notifyDataSetChanged();
            mSwipfreshlayout.setRefreshing(false);
        }
    }


    public static GankItemFragment newInstance(String subtype) {
        LogUtils.e("GankItemFragment", "GankItemFragment newInstance");
        GankItemFragment fragment = new GankItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SUB_TYPE, subtype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        refresh();
    }

    private void refresh(){
        if (!isLoading) {
            isLoading = true;
            LogUtils.e(TAG, "load data isLoading:" + isLoading);
            mPresenter.refreshData(mSubtype);
        }
    }
}
