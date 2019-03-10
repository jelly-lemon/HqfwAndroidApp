package com.example.hqfwandroidapp.activity.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.DiscoveryAdapter;
import com.example.hqfwandroidapp.activity.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.interfaces.IDiscoveryFragment;
import com.example.hqfwandroidapp.presenter.DiscoveryPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class DiscoveryFragment extends SupportFragment implements IDiscoveryFragment {
    @BindView(R.id.rl_discovery) RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_discovery) RecyclerView mRecyclerView;

    DiscoveryAdapter mAdapter;
    DiscoveryPresenter mPresenter = new DiscoveryPresenter(this);





    public static DiscoveryFragment newInstance() {
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        return discoveryFragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);    // 加载视图
        ButterKnife.bind(this, view); // 对象和视图绑定

        initView(); // 初始化该界面

        return view;
    }



    private void initView() {
         mAdapter = new DiscoveryAdapter(getContext(), new ArrayList<DiscoveryCard>()); // 初始化 Adapter
        // 设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();   // 中间人刷新
            }
        });
        // 设置上拉加载更多监听
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();  // 中间人加载更多
            }
        });

        mRefreshLayout.autoRefresh();   // 自动刷新一次


        mRecyclerView.setHasFixedSize(true);    // RecyclerView 自适应尺寸
        // 布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter); // RecyclerView 添加适配器
    }


    @Override
    public void showRefreshResult(ArrayList<DiscoveryCard> discoveryCardList) {
        mAdapter.setList(discoveryCardList);    // 重新设置数据集
        mAdapter.notifyDataSetChanged();        // 发出数据集改变的通知
        mRefreshLayout.finishRefresh();         // 结束下拉刷新
        showToast("刷新成功");  // toast 提示
    }

    @Override
    public void showLoadMoreResult() {
        // TODO mRecyclerView 加载更多

        mRefreshLayout.finishLoadMore(); // 结束上拉加载更多
    }

    private void showToast(String str) {
        Context context = getContext();

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
