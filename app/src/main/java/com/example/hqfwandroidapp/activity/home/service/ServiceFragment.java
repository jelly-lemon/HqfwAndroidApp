package com.example.hqfwandroidapp.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.ServiceFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class ServiceFragment extends SupportFragment {
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;  // 标题
    // ViewPager
    @BindView(R.id.viewPager) ViewPager viewPager;
    // tabLayout
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    // 添加按钮
    @OnClick(R.id.iv_add_toolbar) void goToShoppingActivity() {
        Intent intent = new Intent(getContext(), ShoppingActivity.class);
        startActivity(intent);
    }


    public static ServiceFragment newInstance() {
        ServiceFragment serviceFragment = new ServiceFragment();
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        ButterKnife.bind(this, view); // 绑定视图
        initView(); // 初始化视图
        return view;
    }


    /**
     * 初始化视图
     */
    void initView() {
        tv_title_toolbar.setText("缴费服务");   // 标题

    }

    /**
     * 懒加载初始化视图
     * @param savedInstanceState 保存的实例状态
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // ViewPager 初始化
        String[] title = {"普通订单",  "水电费"};
        viewPager.setAdapter(new ServiceFragmentPagerAdapter(getChildFragmentManager(), title));
        // TabLayout 添加 tab
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        // TabLayout 设置 ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
