package com.example.hqfwandroidapp.interfaces;

import com.example.hqfwandroidapp.viewdata.DiscoveryCard;

import java.util.ArrayList;

public interface IDiscoveryPresenter {
    void showRefreshResult(ArrayList<DiscoveryCard> discoveryCardList);
    void showLoadMoreResult(ArrayList<DiscoveryCard> discoveryCardList);
}
