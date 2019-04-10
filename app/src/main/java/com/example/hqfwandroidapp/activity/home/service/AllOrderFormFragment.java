package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.AllOrderFormAdapter;
import com.example.hqfwandroidapp.adapter.ShoppingAdapter;
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.entity.OrderForm;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class AllOrderFormFragment extends SupportFragment {
    // recycler view
    @BindView(R.id.rv_all_order_form) RecyclerView rv_all_order_form;
    // adapter
    AllOrderFormAdapter allOrderFormAdapter;


    /**
     * 创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);

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
    void initView(View view) {
        // 获取数据
        OkGo.<String>get(Urls.OrderFormServlet)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 获取数据
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<OrderForm>>(){}.getType();
                        List<OrderForm> orderFormList = gson.fromJson(response.body(), type);
                        // adapter
                        initAdapter(orderFormList);
                        // recycler view
                        initRecyclerView();
                    }
                });
    }


    /**
     * 创建实例
     * @return 一个实例
     */
    public static AllOrderFormFragment newInstance() {
        AllOrderFormFragment allOrderFormFragment = new AllOrderFormFragment();
        return allOrderFormFragment;
    }


    void initAdapter(List<OrderForm> orderFormList) {
        allOrderFormAdapter = new AllOrderFormAdapter(getContext(), orderFormList);
    }

    void initRecyclerView() {
        // 初始化 rv_commodity
        rv_all_order_form.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());   // 布局管理器
        rv_all_order_form.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        rv_all_order_form.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距
        rv_all_order_form.setAdapter(allOrderFormAdapter);
    }
}
