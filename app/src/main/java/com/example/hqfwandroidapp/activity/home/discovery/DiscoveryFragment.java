package com.example.hqfwandroidapp.activity.home.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.DiscoveryAdapter;
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

/**
 * 发现页面
 */
public class DiscoveryFragment extends SupportFragment implements IDiscoveryFragment {
    // 刷新布局
    @BindView(R.id.rl_discovery) RefreshLayout rl_discovery;
    // 回收视图
    @BindView(R.id.rv_discovery) RecyclerView rv_discovery;
    // 标题
    @BindView(R.id.tv_title) TextView tv_title;
    // 回收视图适配器
    private DiscoveryAdapter discoveryAdapter;
    // 中间人
    private DiscoveryPresenter mPresenter = new DiscoveryPresenter(this);


    /**
     * 启动 PublishDiscoveryActivity
     */
    @OnClick(R.id.ib_add) void goToNewDiscoveryFragment() {
        Intent intent = new Intent(getContext(), PublishDiscoveryActivity.class);   // 创建意图
        startActivity(intent);  // 启动 Activity
    }




    /**
     * 返回一个 DiscoveryFragment 实例
     * @return 一个实例
     */
    public static DiscoveryFragment newInstance() {
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        return discoveryFragment;
    }


    /**
     * 创建视图
     * @param inflater  视图加载器
     * @param container 视图容器
     * @param savedInstanceState 已保存的实例状态
     * @return 加载的视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);    // 加载视图

        // 对象和视图绑定
        ButterKnife.bind(this, view);
        // 初始化视图
        initView();

        return view;


    }


    /**
     * 初始化视图
     */
    private void initView() {
        tv_title.setText("发现"); // 标题

        discoveryAdapter = new DiscoveryAdapter(getContext(), new ArrayList<>());     // 初始化 Adapter
        // 设置下拉刷新监听
        rl_discovery.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();                   // 中间人刷新
            }
        });
        // 设置上拉加载更多监听
        rl_discovery.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int start = discoveryAdapter.getItemCount();    // 获得记录数目
                mPresenter.loadMore(start);             // 中间人加载更多
            }
        });

        // 刷新布局自动刷新一次
        rl_discovery.autoRefresh();

        // 回收视图相关设置
        rv_discovery.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   // 布局管理器
        rv_discovery.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        rv_discovery.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距
        rv_discovery.setAdapter(discoveryAdapter);                                                 // RecyclerView 添加适配器
    }




    /**
     * 显示刷新结果
     * @param discoveryCardList 得到的结果
     */
    @Override
    public void showRefreshResult(List<DiscoveryCard> discoveryCardList) {
        discoveryAdapter.setList(discoveryCardList);    // 重新设置数据集
        discoveryAdapter.notifyDataSetChanged();        // 发出数据集改变的通知
        rl_discovery.finishRefresh();         // 结束下拉刷新
        showToast("刷新成功");              // toast 提示
    }


    /**
     * 显示加载更多的结果
     * @param discoveryCardList 加载更多的结果
     */
    @Override
    public void showLoadMoreResult(List<DiscoveryCard> discoveryCardList) {
        if (discoveryCardList.isEmpty()) {
            showToast("没有更多数据");
            rl_discovery.finishLoadMoreWithNoMoreData();
        } else {
            discoveryAdapter.addList(discoveryCardList);    // 追加数据到线性表中
            discoveryAdapter.notifyDataSetChanged();        // 通知数据已改变
            rl_discovery.finishLoadMore();            // 结束上拉加载更多
        }

    }


    /**
     * 显示气泡
     * @param str 要显示的信息
     */
    private void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();    // 气泡显示

    }



}
