package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hqfwandroidapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

public class AllOrderFragment extends SupportFragment {


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
        return view;
    }

    /**
     * 初始化视图
     * @param view
     */
    void initView(View view) {

    }


    /**
     * 创建实例
     * @return 一个实例
     */
    public static AllOrderFragment newInstance() {
        AllOrderFragment allOrderFragment = new AllOrderFragment();
        return allOrderFragment;
    }
}
