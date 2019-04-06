package com.example.hqfwandroidapp.interfaces;

import com.example.hqfwandroidapp.viewdata.DiscoveryCard;

import java.util.ArrayList;
import java.util.List;

public interface IDiscoveryPresenter {
    void showRefreshResult(List<DiscoveryCard> discoveryCardList);
    void showLoadMoreResult(List<DiscoveryCard> discoveryCardList);
}
