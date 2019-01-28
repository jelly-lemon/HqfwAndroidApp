package com.example.hqfwandroidapp.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hqfwandroidapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

public class LifeFragment extends SupportFragment {

    public static LifeFragment newInstance() {
        Bundle args = new Bundle();

        LifeFragment lifeFragment = new LifeFragment();
        lifeFragment.setArguments(args);
        return lifeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findFragment(LifeFragment.class) == null) {
            loadRootFragment(R.id.fl_container, LifeFragment.newInstance());
        }
    }
}
