package com.example.hqfwandroidapp.activity.home.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hqfwandroidapp.R;

import com.example.hqfwandroidapp.adapter.DiscoveryCardAdapter;
import com.example.hqfwandroidapp.utils.ItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 发现页面
 */
public class DiscoveryFragment extends SupportFragment{
    // 刷新
    @BindView(R.id.rl_discovery) SwipeRefreshLayout rl_discovery;
    // 列表
    @BindView(R.id.rv_discovery) RecyclerView rv_discovery;
    // 标题
    @BindView(R.id.tv_title) TextView tv_title;
    // 回收视图适配器
    private DiscoveryCardAdapter discoveryCardAdapter;

    /**
     * 启动 PublishDiscoveryActivity
     */
    @OnClick(R.id.iv_add) void startPublishDiscoveryActivity() {
        Intent intent = new Intent(getContext(), PublishDiscoveryActivity.class);   // 创建意图
        startActivity(intent);  // 启动 Activity
    }

    @OnClick(R.id.iv_filter) void filter() {
        ToastUtils.showShort("暂未实现");
    }


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
     * 返回一个 DiscoveryFragment 实例
     * @return 一个实例
     */
    public static DiscoveryFragment newInstance() {
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        return discoveryFragment;
    }





    /**
     * 初始化视图
     */
    private void initView() {
        // 标题
        tv_title.setText("发现");

        // 适配器
        discoveryCardAdapter = new DiscoveryCardAdapter(R.layout.item_discovery);
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.view_empty_no_data, (ViewGroup) rv_discovery.getParent(), false);
        discoveryCardAdapter.setEmptyView(view);
        discoveryCardAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JsonObject discoveryCard = (JsonObject)adapter.getItem(position);
                Intent intent = new Intent(_mActivity, DiscoveryDetailActivity.class);
                intent.putExtra("discoveryCard", discoveryCard.toString());
                startActivity(intent);
            }
        });
        discoveryCardAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rv_discovery);



        // 列表视图
        rv_discovery.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_discovery.addItemDecoration(new ItemDecoration(ItemDecoration.BiliBili));
        rv_discovery.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rv_discovery.setAdapter(discoveryCardAdapter);

        // 刷新
        rl_discovery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        rl_discovery.setRefreshing(true);// 自动刷新一次
        refresh();
    }


    @Override
    public void onStart() {
        super.onStart();
        // 注册广播
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取关广播
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String msg) {
        if (msg.equals("PublishDiscoveryActivity:success")) {
            //ToastUtils.showShort("发布成功");
            // 自动刷新
            rl_discovery.setRefreshing(true);
            refresh();
        }
    }

    private void refresh() {
        OkGo.<String>get(Urls.DiscoveryCardServlet)
                .params("method", "refresh")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<JsonObject> discoveryCardList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                        discoveryCardAdapter.setNewData(discoveryCardList);
                        // 停止刷新
                        rl_discovery.setRefreshing(false);
                    }
                });
    }

    private void loadMore() {
        OkGo.<String>get(Urls.DiscoveryCardServlet)
                .params("method", "loadMore")
                .params("start", discoveryCardAdapter.getData().size())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 得到数据集
                        List<JsonObject> discoveryCardList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                        if (discoveryCardList.isEmpty()) {
                            // 没有新数据了
                            discoveryCardAdapter.loadMoreEnd();
                        } else {
                            // 添加新数据
                            discoveryCardAdapter.addData(discoveryCardList);
                            discoveryCardAdapter.loadMoreComplete();
                        }
                    }
                });
    }
}
