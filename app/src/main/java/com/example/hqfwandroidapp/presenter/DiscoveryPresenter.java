package com.example.hqfwandroidapp.presenter;

import com.example.hqfwandroidapp.activity.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.interfaces.IDiscoveryFragment;
import com.example.hqfwandroidapp.interfaces.IDiscoveryPresenter;
import com.example.hqfwandroidapp.model.DiscoveryModel;

import java.util.ArrayList;

public class DiscoveryPresenter implements IDiscoveryPresenter {
    IDiscoveryFragment iDiscoveryFragment;
    DiscoveryModel discoveryModel = new DiscoveryModel(this);

    public DiscoveryPresenter(IDiscoveryFragment discoveryFragmentInterface) {
        iDiscoveryFragment = discoveryFragmentInterface;
    }

    public void refresh() {
        discoveryModel.refresh();
    }

    public void loadMore(int start) {
        discoveryModel.loadMore(start);
    }

    // 刷新界面数据
    @Override
    public void showRefreshResult(ArrayList<DiscoveryCard> discoveryCardList) {
        iDiscoveryFragment.showRefreshResult(discoveryCardList);    // 接口回调，显示结果
    }

    // 加载更多
    @Override
    public void showLoadMoreResult(ArrayList<DiscoveryCard> discoveryCardList) {
        iDiscoveryFragment.showLoadMoreResult(discoveryCardList);   // 接口回调，显示结果
    }



}
