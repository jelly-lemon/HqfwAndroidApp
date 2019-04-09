package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hqfwandroidapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

public class PendingPaymentFragment extends SupportFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pending_payment, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {


    }

    /**
     * 创建一个实例
     * @return 返回一个实例
     */
    public static PendingPaymentFragment newInstance() {
        PendingPaymentFragment pendingPaymentFragment = new PendingPaymentFragment();
        return pendingPaymentFragment;
    }
}
