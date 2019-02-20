package com.example.hqfwandroidapp.activity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.interfaces.DiscoveryFragmentInterface;
import com.example.hqfwandroidapp.presenter.DiscoveryPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class DiscoveryFragment extends SupportFragment implements DiscoveryFragmentInterface {
    @BindView(R.id.progressBar) ProgressBar progressBar;
    DiscoveryPresenter mPresenter = new DiscoveryPresenter(this);


    public static DiscoveryFragment newInstance() {
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        return discoveryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        ButterKnife.bind(this, view); // 视图和控件绑定

        initView(view);
        return view;
    }


    private void initView(View view) {
        showProgressBar();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);     // 不确定时间，一直旋转
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
