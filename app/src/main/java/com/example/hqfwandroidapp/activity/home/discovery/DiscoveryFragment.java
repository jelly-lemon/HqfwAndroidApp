package com.example.hqfwandroidapp.activity.home.discovery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.DiscoveryRecyclerViewAdapter;
//import com.example.hqfwandroidapp.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.interfaces.IDiscoveryFragment;
import com.example.hqfwandroidapp.presenter.DiscoveryPresenter;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class DiscoveryFragment extends SupportFragment implements IDiscoveryFragment {
    @BindView(R.id.rl_discovery) RefreshLayout mRefreshLayout;
    @BindView(R.id.rv_discovery) RecyclerView mRecyclerView;
    @BindView(R.id.tv_title) TextView tv_title;  // 标题

    DiscoveryRecyclerViewAdapter mAdapter;  // 适配器
    DiscoveryPresenter mPresenter = new DiscoveryPresenter(this);   // 中间人

    // 启动 PublishDiscoveryActivity
    @OnClick(R.id.btn_add) void goToNewDiscoveryFragment() {
        Intent intent = new Intent(getContext(), PublishDiscoveryActivity.class);   // 创建意图
        startActivity(intent);  // 启动 Activity
    }


    public static DiscoveryFragment newInstance() {
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        return discoveryFragment;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);    // 加载视图
        ButterKnife.bind(this, view);           // 对象和视图绑定
        initView();                                     // 初始化该界面

        return view;


    }


    /**
     * 初始化视图
     */
    private void initView() {
        tv_title.setText("发现"); // 标题

         mAdapter = new DiscoveryRecyclerViewAdapter(getContext(), new ArrayList<DiscoveryCard>());     // 初始化 Adapter
        // 设置下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();                   // 中间人刷新
            }
        });
        // 设置上拉加载更多监听
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int start = mAdapter.getItemCount();    // 获得记录数目
                mPresenter.loadMore(start);             // 中间人加载更多
            }
        });

        mRefreshLayout.autoRefresh();   // 自动刷新一次


        mRecyclerView.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   // 布局管理器
        mRecyclerView.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        mRecyclerView.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距
        mRecyclerView.setAdapter(mAdapter);                                                 // RecyclerView 添加适配器
    }


    // 显示刷新结果
    @Override
    public void showRefreshResult(List<DiscoveryCard> discoveryCardList) {
        mAdapter.setList(discoveryCardList);    // 重新设置数据集
        mAdapter.notifyDataSetChanged();        // 发出数据集改变的通知
        mRefreshLayout.finishRefresh();         // 结束下拉刷新
        showToast("刷新成功");              // toast 提示
    }

    // 显示加载更多的结果
    @Override
    public void showLoadMoreResult(List<DiscoveryCard> discoveryCardList) {
        if (discoveryCardList.isEmpty() | discoveryCardList == null) {
            showToast("没有更多数据");
        } else {
            mAdapter.addList(discoveryCardList);    // 追加数据到线性表中
            mAdapter.notifyDataSetChanged();        // 通知数据已改变
        }
        mRefreshLayout.finishLoadMore();            // 结束上拉加载更多
    }

    // 显示气泡
    private void showToast(String str) {
        Context context = getContext();                             // 上下文
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();    // 气泡显示
    }


}
