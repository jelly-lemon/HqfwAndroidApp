package com.example.hqfwandroidapp.interfaces;

import com.example.hqfwandroidapp.entity.ArticleCard;

import java.util.ArrayList;

public interface IDiscoveryPresenter {
    void showRefreshResult(ArrayList<ArticleCard> articleCardList);
    void showLoadMoreResult();
}
