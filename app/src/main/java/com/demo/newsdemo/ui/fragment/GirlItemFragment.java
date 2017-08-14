package com.demo.newsdemo.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.newsdemo.R;
import com.demo.newsdemo.base.BaseRxFragment;
import com.demo.newsdemo.contract.GirlItemContract;
import com.demo.newsdemo.contract.GirlItemContract;
import com.demo.newsdemo.di.DaggerGirlItemComponent;
import com.demo.newsdemo.di.GirlItemModule;
import com.demo.newsdemo.model.GirlItemModel;
import com.demo.newsdemo.model.bean.GirlItemData;
import com.demo.newsdemo.model.bean.GirlItemData;
import com.demo.newsdemo.presenter.GirlItemPresenter;
import com.demo.newsdemo.utils.ImageLoader;
import com.demo.newsdemo.utils.LogUtil;
import com.demo.newsdemo.utils.recycler.BaseRecyclerAdapter;
import com.demo.newsdemo.utils.recycler.BaseViewHolder;
import com.demo.newsdemo.utils.recycler.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlItemFragment extends BaseRxFragment implements GirlItemContract.View, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;

    private String mSubtype;

    private CommonAdapter<GirlItemData> adapter;
    private List<GirlItemData> datas;

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
        mRecyclerview.scrollToPosition(0);
    }

    @Inject
    GirlItemPresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void init() {
        isLoading = false;
        isLoadMore = false;
        datas = new ArrayList<>();
        adapter = new CommonAdapter<GirlItemData>(getActivity(), R.layout.item_gank_layout, datas) {
            @Override
            protected void convert(BaseViewHolder holder, GirlItemData girlItemData, int position) {
                TextView descTv = holder.getView(R.id.gank_item_desc);
                TextView whoTv = holder.getView(R.id.gank_item_who);
                TextView publishDateTv = holder.getView(R.id.gank_item_publishedat);
                ImageView iconImg = holder.getView(R.id.gank_item_icon);

                descTv.setText(GirlItemData.getDesc());
                String who = TextUtils.isEmpty(GirlItemData.getWho()) ? "null" : GirlItemData.getWho();
                whoTv.setText(who);
                publishDateTv.setText(GirlItemData.getPublishedAt().substring(0, 10));
                String[] images = GirlItemData.getImages();
                if (images != null && images.length > 0) {
                    LogUtil.e(TAG, images[0] + "?imageView2/0/w/100");
                    ImageLoader.load(context, images[0] + "?imageView2/0/w/100", iconImg, R.mipmap.web);
                } else {
                    String url = GirlItemData.getUrl();
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
                    ImageLoader.load(context, iconId, iconImg);
                }
            }
        };

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Intent intent=new Intent(context,GankDetailActivity.class);
//                intent.putExtra("gank_item_data",datas.get(position))
                Toast.makeText(context, "跳转下一个界面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        layoutManager = new LinearLayoutManager(context);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(adapter);

        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    LogUtil.e(TAG, "lastItemPosition:" + lastItemPosition + ",datas.size():" + datas.size());
                    if (lastItemPosition >= datas.size() - 1) {
                        isLoadMore = true;
                        loadMore();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLastVisibleItemPosition < layoutManager.findLastVisibleItemPosition())
                    mFab.show();
                if (mLastVisibleItemPosition > layoutManager.findLastVisibleItemPosition() && mFab.isShown())
                    mFab.hide();
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });

        mSwipfreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mSwipfreshlayout.setOnRefreshListener(this);

        mSwipfreshlayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipfreshlayout.setRefreshing(true);
            }
        });


    }

    private void loadMore() {
        LogUtil.e(TAG, "loadMore:mSubtype:" + mSubtype + ",PAGE_COUNT:" + PAGE_COUNT);
        if (!isLoading) {
            isLoading=true;
            LogUtil.e(TAG,"load data isLoading:"+isLoading);
            mPresenter.loadData(mSubtype, PAGE_COUNT);
            PAGE_COUNT++;
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
        DaggerGirlItemComponent.builder().GirlItemModule(new GirlItemModule(this, new GirlItemModel()))
                .build().inject(this);
        addPresenter(mPresenter);
        mPresenter.loadData(mSubtype, PAGE_COUNT);
        PAGE_COUNT++;

    }


    @Override
    public void showError() {
        isLoading=false;
        mSwipfreshlayout.setRefreshing(false);

    }

    @Override
    public void complete() {

    }

    @Override
    public void updateUI(List<GirlItemData> data) {
        isLoading=false;
        if (isLoadMore) {
            if (data.size() == 0) {
                LogUtil.e(TAG, "updateUI : 获取数据成功，数据数量为0");
            } else {
                int size = data.size();
                datas.addAll(data);
                adapter.notifyItemRangeInserted(PAGE_COUNT * 10, ((PAGE_COUNT * 10) + size));
            }
        } else {
            datas.clear();
            datas.addAll(data);
            adapter.notifyDataSetChanged();
            mSwipfreshlayout.setRefreshing(false);
        }
    }


    public static GirlItemFragment newInstance(String subtype) {
        LogUtil.e("GirlItemFragment", "GirlItemFragment newInstance");
        GirlItemFragment fragment = new GirlItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUB_TYPE, subtype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        loadMore();
    }
}
