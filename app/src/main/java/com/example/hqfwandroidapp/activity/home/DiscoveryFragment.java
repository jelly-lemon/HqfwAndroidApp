package com.example.hqfwandroidapp.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.DiscoveryAdapter;
import com.example.hqfwandroidapp.entity.ArticleCard;
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
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);

        ButterKnife.bind(this, view); // 对象和视图绑定

        initView(); // 初始化界面
        return view;
    }


    private void initView() {

        mRefreshLayout.autoRefresh();// 自动刷新
        // 设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();

                refreshLayout.finishRefresh(2000);
            }
        });
        // 设置上拉加载更多监听
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();

                refreshLayout.finishLoadMore(2000);
            }
        });

        // RecyclerView 自适应尺寸
        mRecyclerView.setHasFixedSize(true);
        // 布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        // 初始化适配器
        ArrayList<String> testList = new ArrayList<>();
        testList.add("one");
        testList.add("two");
        testList.add("three");
        testList.add("four");
        testList.add("five");
        testList.add("six");
        testList.add("seven");
        testList.add("eight");
        testList.add("nine");
        testList.add("ten");
        mAdapter = new DiscoveryAdapter(testList);
        // RecyclerView 添加适配器
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showRefreshResult(ArrayList<ArticleCard> articleCardList) {
        // TODO mRecyclerView 更新

    }

    @Override
    public void showLoadMoreResult() {
        // TODO mRecyclerView 加载更多
    }
}
