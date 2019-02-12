package com.example.hqfwandroidapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.ui.BottomBar;
import com.example.hqfwandroidapp.ui.BottomBarTab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends SupportFragment {
    public static int DISCOVERY = 0;
    public static int SERVICE = 1;
    public static int PERSON = 2;

    private SupportFragment[] mFragment = new SupportFragment[3];
    @BindView(R.id.bottom_bar) BottomBar mBottomBar;




    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // ButterKnife 绑定生效
        ButterKnife.bind(this, view);

        initView(view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment discoveryFragment = findFragment(DiscoveryFragment.class);
        if (discoveryFragment == null) {
            mFragment[DISCOVERY] = DiscoveryFragment.newInstance();
            mFragment[SERVICE] = ServiceFragment.newInstance();
            mFragment[PERSON] = PersonFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, DISCOVERY, mFragment[DISCOVERY], mFragment[SERVICE], mFragment[PERSON]);
        } else {
            mFragment[DISCOVERY] = discoveryFragment;
            mFragment[SERVICE] = findChildFragment(ServiceFragment.class);
            mFragment[PERSON] = findChildFragment(PersonFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar
                .addItem(new BottomBarTab(getContext(), R.drawable.ic_discovery_black_24dp, "发现"))
                .addItem(new BottomBarTab(getContext(), R.drawable.ic_service_black_24dp, "服务"))
                .addItem(new BottomBarTab(getContext(), R.drawable.ic_person_black_24dp, "个人"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragment[position], mFragment[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

}
