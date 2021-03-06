package com.example.hqfwandroidapp.adapter;

import com.example.hqfwandroidapp.activity.home.service.OrderFormCardFragment;
import com.example.hqfwandroidapp.activity.home.service.AllHydropowerBillFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ServiceFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int NORMALORDERFORM = 0;
    private final int HYDROPOWERBILL = 1;

    // 标题
    private String[] title;

    public ServiceFragmentPagerAdapter(@NonNull FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    /**
     * 获取每一项 Fragment
     * @param position 位置
     * @return Fragment 对象
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case NORMALORDERFORM:{
                return OrderFormCardFragment.newInstance();
            }
            case HYDROPOWERBILL: {
                return AllHydropowerBillFragment.newInstance();
            }

        }
        return null;
    }

    /**
     * 获取页面数量
     * @return 页面数量
     */
    @Override
    public int getCount() {
        return title.length;
    }

    /**
     * 获取页面标题
     * @param position 位置
     * @return 标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
