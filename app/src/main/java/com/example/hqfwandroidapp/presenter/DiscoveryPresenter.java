package com.example.hqfwandroidapp.presenter;

import com.example.hqfwandroidapp.entity.ArticleCard;
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
    public void showRefreshResult(ArrayList<ArticleCard> articleCardList) {
        // 处理业务逻辑


        // 完成之后
        iDiscoveryFragment.showRefreshResult(articleCardList);
    }

    @Override
    public void showLoadMoreResult() {
        iDiscoveryFragment.showLoadMoreResult();
    }



}
