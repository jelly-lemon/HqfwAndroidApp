package com.example.hqfwandroidapp.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hqfwandroidapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

public class ServiceFragment extends SupportFragment {
    public static ServiceFragment newInstance() {
        ServiceFragment serviceFragment = new ServiceFragment();
        return serviceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }
}