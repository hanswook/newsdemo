package com.hans.newslook.ui.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hans.newslook.R;
import com.hans.newslook.adapter.DbGirlsAdapter;
import com.hans.newslook.base.BaseRxFragment;
import com.hans.newslook.contract.GirlItemContract;
import com.hans.newslook.di.DaggerGirlItemComponent;
import com.hans.newslook.di.GirlItemModule;
import com.hans.newslook.model.GirlItemModel;
import com.hans.newslook.model.bean.GirlItemData;
import com.hans.newslook.presenter.GirlItemPresenter;
import com.hans.newslook.utils.CommonSubscriber;
import com.hans.newslook.utils.Constants;
import com.hans.newslook.utils.LogUtil;
import com.hans.newslook.utils.image.GlideApp;
import com.hans.newslook.utils.wechatimage.ImagePagerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlItemFragment extends BaseRxFragment implements GirlItemContract.View, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 0;

    private String mSubtype;
    private DbGirlsAdapter adapter;
    private List<GirlItemData> datas;
    private ArrayList<String> imageDatas;
    private boolean isLoadMore;

    private boolean isLoading;


    private StaggeredGridLayoutManager layoutManager;


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
        imageDatas = new ArrayList<>();

        adapter = new DbGirlsAdapter(R.layout.item_girl_layout, datas);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context, "跳转下一个界面", Toast.LENGTH_SHORT).show();
                ImagePagerUtils.imageBrower(position, imageDatas, context);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtil.e(TAG, "onLoadMoreRequested,data.size:" + datas.size() + "/pageNo:" + PAGE_COUNT);
                if (datas.size() > (PAGE_COUNT * 20)) {
                    LogUtil.e("没有加载更多,data.size:" + datas.size() + "/pageNo:" + PAGE_COUNT);
                    return;
                } else {
                    isLoadMore = true;
                    loadMore();
                    LogUtil.e("加载更多了一次,当前页数为:" + PAGE_COUNT + "加载后总页数会加一");
                }
            }
        }, mRecyclerview);


        mRecyclerview.setAdapter(adapter);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);

        mSwipfreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);

        mSwipfreshlayout.setOnRefreshListener(this);

//        mSwipfreshlayout.post(() -> mSwipfreshlayout.setRefreshing(true));
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.e(TAG, "dx:" + dx + ",dy:" + dy);
                if (dy > 15)
                    mFab.show();
                if (dy < -15 && mFab.isShown())
                    mFab.hide();
                if (dy>50 && mSwipfreshlayout.isRefreshing())
                    mSwipfreshlayout.setRefreshing(false);
            }
        });
    }

    private void loadMore() {
        LogUtil.e(TAG, "loadMore:mSubtype:" + mSubtype + ",PAGE_COUNT:" + PAGE_COUNT);
        LogUtil.e(TAG, "load data isLoading:" + isLoading);
        if (!isLoading) {
            isLoading = true;
            PAGE_COUNT++;
            mPresenter.loadData(mSubtype, PAGE_COUNT);
        }

    }

    @Override
    protected void initData() {
        LogUtil.e(TAG, "initData");
        if (getArguments() == null)
            return;
        mSubtype = getArguments().getString(Constants.SUB_TYPE);
        LogUtil.e(TAG, "mSubtype:" + mSubtype);

    }

    @Override
    protected void fetchData() {
        LogUtil.e(TAG, "fetchData");
        DaggerGirlItemComponent.builder()
                .girlItemModule(new GirlItemModule(this, new GirlItemModel()))
                .build().inject(this);
        addPresenter(mPresenter);
        PAGE_COUNT++;
        mPresenter.loadData(mSubtype, PAGE_COUNT);

    }


    @Override
    public void showError() {
        isLoading = false;
        mSwipfreshlayout.setRefreshing(false);

    }


    @Override
    public void updateUI(List<GirlItemData> data) {
        Observable.just(data).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .map(girlItemDatas -> {
                    for (GirlItemData item : girlItemDatas) {
                        imageDatas.add(item.getUrl());
                        Bitmap bitmap = GlideApp.with(context).asBitmap().load(item.getUrl())
                                .apply(new RequestOptions()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get();
                        if (bitmap != null) {
//                                LogUtil.e(TAG, "bitmap:w:" + bitmap.getWidth() + ",h:" + bitmap.getHeight());
                            item.setWidth(bitmap.getWidth());
                            item.setHeight(bitmap.getHeight());
                        }
                    }

                    return girlItemDatas;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<GirlItemData>>(this) {
                    @Override
                    public void onNext(List<GirlItemData> girlItemDatas) {
                        isLoading = false;
                        if (isLoadMore) {
                            if (girlItemDatas.size() == 0) {
                                LogUtil.e(TAG, "updateUI : 获取数据成功，数据数量为0");
                                adapter.loadMoreFail();
                            } else {
                                int originSize = datas.size();
                                int size = girlItemDatas.size();
                                datas.addAll(girlItemDatas);
//                                adapter.notifyDataSetChanged();
                                LogUtil.e(TAG, "originsize:" + originSize + ",size:" + size);
                                adapter.notifyItemRangeInserted(originSize, datas.size() - originSize);
                                adapter.loadMoreComplete();
                            }
                        } else {
                            datas.clear();
                            datas.addAll(girlItemDatas);
                            adapter.notifyDataSetChanged();
                            mSwipfreshlayout.setRefreshing(false);
                        }
                    }
                });


    }


    public static GirlItemFragment newInstance(String subtype) {
        LogUtil.e("GirlItemFragment", "GirlItemFragment newInstance");
        GirlItemFragment fragment = new GirlItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SUB_TYPE, subtype);
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
