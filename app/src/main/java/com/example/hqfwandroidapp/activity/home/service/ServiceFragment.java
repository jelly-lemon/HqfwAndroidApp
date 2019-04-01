package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class ServiceFragment extends SupportFragment {
    @BindView(R.id.tv_title) TextView tv_title;  // 标题
    @BindView(R.id.btn_add) Button btn_add; //
    @BindView(R.id.refresh_layout) SmartRefreshLayout refresh_layout; // 刷新布局
    @BindView(R.id.recycler_view) RecyclerView recycler_view; // 回收视图

    public static ServiceFragment newInstance() {
        ServiceFragment serviceFragment = new ServiceFragment();
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        ButterKnife.bind(this, view); // 绑定视图
        initView(); // 初始化视图
        return view;
    }


    /**
     * 初始化视图
     */
    void initView() {
        tv_title.setText("缴费服务");   // 标题
    }
}
