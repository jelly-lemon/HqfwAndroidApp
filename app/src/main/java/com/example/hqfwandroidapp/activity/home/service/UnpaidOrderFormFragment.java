package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.OrderFormAdapter;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class UnpaidOrderFormFragment extends SupportFragment {
    // 刷新布局
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout smartRefreshLayout;
    // 回收视图
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    // adapter
    private OrderFormAdapter orderFormAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_payment, container, false);

        initView(view);
        return view;
    }

    void initView(View view) {
        // 绑定视图
        ButterKnife.bind(this, view);
        // 适配器
        orderFormAdapter = new OrderFormAdapter(getContext(), new ArrayList<>());
        // 回收视图
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   // 布局管理器
        recyclerView.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        recyclerView.addItemDecoration(spacesItemDecoration);
        recyclerView.setAdapter(orderFormAdapter);
        // 刷新布局
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 获取数据
                OkGo.<String>get(Urls.OrderFormServlet)
                        .params("method", "unpaidRefresh")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // 设置数据
                                orderFormAdapter.setData(GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));
                                // finish
                                refreshLayout.finishRefresh();
                            }
                        });
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 获取数据
                OkGo.<String>get(Urls.OrderFormServlet)
                        .params("method", "unpaidLoadMore")
                        .params("start", orderFormAdapter.getItemCount())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // 获取数据
                                //JsonArray orderFormJsonArray = GsonUtils.fromJson(response.body(), JsonArray.class);
                                List<JsonObject> jsonObjectList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                                if (jsonObjectList.isEmpty()) {
                                    //ToastUtils.showShort("没有更多");
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
        // 自动刷新
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 创建一个实例
     * @return 返回一个实例
     */
    public static UnpaidOrderFormFragment newInstance() {
        UnpaidOrderFormFragment unpaidOrderFormFragment = new UnpaidOrderFormFragment();
        return unpaidOrderFormFragment;
    }

    void showToast(String msg) {
        Toast.makeText(_mActivity, msg, Toast.LENGTH_SHORT).show();
    }
}
