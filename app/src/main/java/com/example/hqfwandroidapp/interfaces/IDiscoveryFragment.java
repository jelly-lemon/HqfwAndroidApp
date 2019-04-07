package com.example.hqfwandroidapp.interfaces;

//import com.example.hqfwandroidapp.viewdata.DiscoveryCard;

import com.example.hqfwandroidapp.entity.DiscoveryCard;

import java.util.ArrayList;
import java.util.List;

public interface IDiscoveryFragment {
    void showRefreshResult(List<DiscoveryCard> discoveryCardList);
    void showLoadMoreResult(List<DiscoveryCard> discoveryCardList);
}
