package com.example.hqfwandroidapp.activity.home.service;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.GsonUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.OrderFormAdapter;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class AllOrderFormFragment extends SupportFragment {
    // recycler view
    @BindView(R.id.rv_all_order_form) RecyclerView rv_all_order_form;
    // adapter
    private OrderFormAdapter orderFormAdapter;
    // smart refresh layout
    @BindView(R.id.refreshLayout) RefreshLayout refreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载视图
        View view = inflater.inflate(R.layout.fragment_all_order_form, container, false);
        // 绑定变量
        ButterKnife.bind(this, view);
        // 初始化视图
        initView(view);

        return view;
    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        // 初始化 rv_commodity
        // 布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_all_order_form.setLayoutManager(layoutManager);
        // 间距
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);
        rv_all_order_form.addItemDecoration(spacesItemDecoration);
        // 适配器
        orderFormAdapter = new OrderFormAdapter(getContext(), new ArrayList<>());
        rv_all_order_form.setAdapter(orderFormAdapter);

        // refresh layout
        refreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
            // 获取数据
            OkGo.<String>get(Urls.OrderFormServlet)
                    .params("method", "refresh")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            // 设置数据集
                            orderFormAdapter.setData(GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));
                            // finish
                            refreshLayout.finishRefresh();
                        }
                    });
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 获取数据
                OkGo.<String>get(Urls.OrderFormServlet)
                        .params("method", "loadMore")
                        .params("start", orderFormAdapter.getItemCount())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                //JsonArray jsonArray = GsonUtils.fromJson(response.body(), JsonArray.class);
                                List<JsonObject> jsonObjectList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                                if (jsonObjectList.isEmpty()) {
                                    refreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    // 添加数据
                                    orderFormAdapter.addDate(jsonObjectList);
                                    refreshLayout.finishLoadMore();
                                }
                            }
                        });
            }
        });

        // 自动刷新一次
        refreshLayout.autoRefresh();

    }


    /**
     * 创建实例
     * @return 一个实例
     */
    public static AllOrderFormFragment newInstance() {
        AllOrderFormFragment allOrderFormFragment = new AllOrderFormFragment();
        return allOrderFormFragment;
    }



    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(String msg) {
        if (msg.equals("ConfirmPurchaseActivity:close")) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 订阅消息
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // 取消订阅
        EventBus.getDefault().unregister(this);
    }


}
