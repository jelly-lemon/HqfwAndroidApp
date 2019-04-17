package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.DeleteOrderFormAdapter;
import com.example.hqfwandroidapp.entity.OrderForm;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class UnpaidOrderFormFragment extends SupportFragment {
    // 刷新布局
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout smartRefreshLayout;
    // 回收视图
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    // adapter
    DeleteOrderFormAdapter deleteOrderFormAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pending_payment, container, false);

        //initView(view);
        return view;
    }

    void initView(View view) {
        // 绑定视图
        ButterKnife.bind(this, view);
        // 适配器
        deleteOrderFormAdapter = new DeleteOrderFormAdapter(_mActivity);
        // 回收视图
        recyclerView.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   // 布局管理器
        recyclerView.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        recyclerView.addItemDecoration(spacesItemDecoration);
        recyclerView.setAdapter(deleteOrderFormAdapter);
        // 刷新布局
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 获取数据
                OkGo.<String>get(Urls.OrderFormServlet)
                        .params("method", "getUnPaidOrderForm")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // 获取数据
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<OrderForm>>(){}.getType();
                                List<OrderForm> orderFormList = gson.fromJson(response.body(), type);
                                // 设置数据
                                deleteOrderFormAdapter.setOrderFormList(orderFormList);
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
                        .params("method", "loadMoreUnPaidOrderForm")
                        .params("start", deleteOrderFormAdapter.getItemCount())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // 获取数据
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<OrderForm>>(){}.getType();
                                List<OrderForm> orderFormList = gson.fromJson(response.body(), type);
                                if (orderFormList.isEmpty()) {
                                    showToast("没有更多数据");
                                    refreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    // 添加数据
                                    deleteOrderFormAdapter.addOrderFormList(orderFormList);
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
