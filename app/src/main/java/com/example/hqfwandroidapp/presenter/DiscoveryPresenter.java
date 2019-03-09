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

    public void loadMore() {
        discoveryModel.loadMore();
    }

    @Override
    public void showRefreshResult(ArrayList<DiscoveryCard> discoveryCardList) {



        iDiscoveryFragment.showRefreshResult(discoveryCardList);
    }

    @Override
    public void showLoadMoreResult() {
        iDiscoveryFragment.showLoadMoreResult();
    }



}
