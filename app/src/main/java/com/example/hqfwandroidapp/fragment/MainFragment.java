package com.example.hqfwandroidapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
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

    @OnClick(R.id.navigation_discovery) void showDiscoveryFragment() {
        Toast.makeText(getContext(), "navigation_discovery", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.navigation_service) void showServiceFragment() {
        Toast.makeText(getContext(), "navigation_service", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.navigation_person) void showPersonFragment() {
        Toast.makeText(getContext(), "navigation_person", Toast.LENGTH_SHORT).show();
    }

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


}
