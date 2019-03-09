package com.example.hqfwandroidapp.model;


import com.example.hqfwandroidapp.activity.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.entity.Article;
import com.example.hqfwandroidapp.entity.User;
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
                            // 获取到的字符串转化为 JSONArray
                            JSONArray jsonArray = new JSONArray(response.body());


                            ArrayList<DiscoveryCard> discoveryCardList = new ArrayList<>(); // 保存所有 DiscoveryCard

                            // 提取内容，创建 DiscoveryCard 对象
                            int len = jsonArray.length();
                            for (int i = 0; i < len; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                User user = (User) jsonObject.get("user");
                                Article article = (Article) jsonObject.get("article");
                                DiscoveryCard discoveryCard = new DiscoveryCard(user, article);

                                discoveryCardList.add(discoveryCard);   // 放入 discoveryCardList 中
                            }



                            iDiscoveryPresenter.showRefreshResult(discoveryCardList);   // 接口回调，通知界面显示结果

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
