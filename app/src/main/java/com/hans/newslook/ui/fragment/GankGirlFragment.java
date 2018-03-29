package com.hans.newslook.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hans.newslook.R;
import com.hans.newslook.adapter.GankGirlAdapter;
import com.hans.newslook.base.BaseRxFragment;
import com.hans.newslook.contract.GankItemContract;
import com.hans.newslook.model.bean.GankItemData;
import com.hans.newslook.presenter.GankItemPresenter;
import com.hans.newslook.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class GankGirlFragment extends BaseRxFragment implements GankItemContract.View {

    @BindView(R.id.gank_recycler_view)
    RecyclerView gankRecyclerView;

    private GankGirlAdapter girlsAdapter;
    private List<GankItemData> datas;

    GankItemPresenter mPresenter;

    private String type = "福利";
    private int pageNum = 0;

    private boolean isLoading = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_girl;
    }

    @Override
    protected void init() {
        LogUtils.e(TAG, "init");
        girlsAdapter = new GankGirlAdapter(R.layout.item_gankio_girls, datas);
        gankRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        gankRecyclerView.setAdapter(girlsAdapter);

        /*DaggerGankItemComponent.builder().gankItemModule(new GankItemModule(this))
                .build().inject(this);*/
        mPresenter = new GankItemPresenter(this);
        addPresenter(mPresenter);
//        mPresenter.loadData(type, pageNum);
        girlsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e(TAG, "onLoadMoreRequested");
                if (isLoading) {
                    girlsAdapter.loadMoreComplete();
                    return;
                }
                isLoading = true;
                mPresenter.loadData(type, pageNum);
            }
        }, gankRecyclerView);

    }

    @Override
    protected void initData() {
        LogUtils.e(TAG, "initData");
        datas = new ArrayList<>();
    }

    @Override
    protected void fetchData() {
        LogUtils.e(TAG, "fetchData");

    }


    @Override
    public void showError() {
        Toast.makeText(context, "数据获取错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUI(List<GankItemData> data) {
        isLoading = false;
        pageNum++;
        girlsAdapter.addData(data);
        girlsAdapter.loadMoreComplete();
    }
}
