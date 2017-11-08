package com.demo.newsdemo.ui.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
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
import com.demo.newsdemo.utils.CommonSubscriber;
import com.demo.newsdemo.utils.ImageLoader;
import com.demo.newsdemo.utils.LogUtil;
import com.demo.newsdemo.utils.ScaleImageView;
import com.demo.newsdemo.utils.recycler.BaseRecyclerAdapter;
import com.demo.newsdemo.utils.recycler.BaseViewHolder;
import com.demo.newsdemo.utils.recycler.CommonAdapter;
import com.demo.newsdemo.utils.recycler.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        adapter = new CommonAdapter<GirlItemData>(getActivity(), R.layout.item_girl_layout, datas) {
            @Override
            protected void convert(BaseViewHolder holder, GirlItemData girlItemData, int position) {
                ScaleImageView imageView = holder.getView(R.id.girl_item_iv);
//                LogUtil.e(TAG, "adapter:w:" + girlItemData.getWidth() + ",h:" + girlItemData.getHeight());
                imageView.setInitSize(girlItemData.getWidth(), girlItemData.getHeight());
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//                layoutParams.height = girlItemData.getHeight();
//                layoutParams.width = girlItemData.getWidth();
//                imageView.setLayoutParams(layoutParams);
                ImageLoader.load(context, girlItemData.getUrl(), imageView);
//                LogUtil.e(TAG, "layoutParams:width:" + layoutParams.width + ",layoutParams.height:" + layoutParams.height);

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
        layoutManager = new MyLinearLayoutManager(context);
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
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

//                if (mLastVisibleItemPosition < layoutManager.findLastVisibleItemPosition())
//                    mFab.show();
//                if (mLastVisibleItemPosition > layoutManager.findLastVisibleItemPosition() && mFab.isShown())
//                    mFab.hide();
//                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
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

        mFab.show();
    }

    private void loadMore() {
        LogUtil.e(TAG, "loadMore:mSubtype:" + mSubtype + ",PAGE_COUNT:" + PAGE_COUNT);
        if (!isLoading) {
            isLoading = true;
            LogUtil.e(TAG, "load data isLoading:" + isLoading);
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
        DaggerGirlItemComponent.builder()
                .girlItemModule(new GirlItemModule(this, new GirlItemModel()))
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
    public void updateUI(List<GirlItemData> data) {
        Observable.just(data).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .map(new Function<List<GirlItemData>, List<GirlItemData>>() {
                    @Override
                    public List<GirlItemData> apply(@NonNull List<GirlItemData> girlItemDatas) throws Exception {
                        for (GirlItemData item : girlItemDatas) {
                            Bitmap bitmap = Glide.with(context).load(item.getUrl())
                                    .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            if (bitmap != null) {
//                                LogUtil.e(TAG, "bitmap:w:" + bitmap.getWidth() + ",h:" + bitmap.getHeight());
                                item.setWidth(bitmap.getWidth());
                                item.setHeight(bitmap.getHeight());
                            }
                        }

                        return girlItemDatas;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<List<GirlItemData>>(this) {
                    @Override
                    public void onNext(List<GirlItemData> girlItemDatas) {
                        isLoading = false;
                        if (isLoadMore) {
                            if (girlItemDatas.size() == 0) {
                                LogUtil.e(TAG, "updateUI : 获取数据成功，数据数量为0");
                            } else {
                                int size = girlItemDatas.size();
                                datas.addAll(girlItemDatas);
                                adapter.notifyItemRangeInserted(PAGE_COUNT * 10, ((PAGE_COUNT * 10) + size));
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
