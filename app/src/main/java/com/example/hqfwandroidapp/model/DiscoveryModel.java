package com.example.hqfwandroidapp.model;


import com.example.hqfwandroidapp.entity.ArticleCard;
import com.example.hqfwandroidapp.interfaces.IDiscoveryPresenter;
import com.example.hqfwandroidapp.presenter.DiscoveryPresenter;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DiscoveryModel {
    private IDiscoveryPresenter iDiscoveryPresenter;

    public DiscoveryModel(DiscoveryPresenter discoveryPresenter) {
        iDiscoveryPresenter = discoveryPresenter;
    }

    public void refresh() {
        OkGo.<String>get(Urls.DiscoverServlet())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body());

                            // 将 ArticleCard 对象取出来放入 List 中
                            ArrayList<ArticleCard> articleCardList = new ArrayList<>();
                            /*
                            int len = jsonArray.length();
                            for (int i = 0; i < len; i++) {
                                ArticleCard articleCard = (ArticleCard) jsonArray.get(i);
                                articleCardList.add(articleCard);
                            }
                            iDiscoveryPresenter.showRefreshResult(articleCardList);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }
    public void loadMore() {
        iDiscoveryPresenter.showLoadMoreResult();
    }
}
