package com.example.hqfwandroidapp.model;


import com.example.hqfwandroidapp.viewdata.DiscoveryCard;
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

import java.sql.Timestamp;
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
                        ArrayList<DiscoveryCard> discoveryCardList = convertData(response.body());  // 转化格式
                        iDiscoveryPresenter.showRefreshResult(discoveryCardList);   // 接口回调，通知界面显示结果
                    }
                });
    }

    // 加载更多
    public void loadMore(int start) {
        OkGo.<String>post(Urls.DiscoverServlet())
                .params("start", start)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ArrayList<DiscoveryCard> discoveryCardList = convertData(response.body());  // 转化格式
                        iDiscoveryPresenter.showLoadMoreResult(discoveryCardList);   // 接口回调，通知界面显示结果
                    }
                });

    }

    // 将返回的字符串转化位数组列表
    private ArrayList<DiscoveryCard> convertData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);   // 获取到的字符串转化为 JSONArray
            ArrayList<DiscoveryCard> discoveryCardList = new ArrayList<>(); // 保存所有 DiscoveryCard
            // 提取内容，创建 DiscoveryCard 对象
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // 获取一个 JSONObject 对象
                // 创建 user 对象
                User user = new User();
                user.setHead(jsonObject.optString("head"));  // 头像
                user.setName(jsonObject.optString("name"));  // 名字
                // 创建 Article 对象
                Article article = new Article();
                article.setArticle_id(jsonObject.optString("article_id"));          // 文章编号
                // 时间转化
                String timeStr = jsonObject.optString("time");
                Timestamp timestamp = Timestamp.valueOf(timeStr);
                article.setTime(timestamp);                                                // 时间
                article.setTag(jsonObject.optString("tag"));                        // 标签
                article.setContent(jsonObject.optString("content"));                // 内容
                article.setImg_url_json(jsonObject.optString("img_url_json"));      // 图片路径

                DiscoveryCard discoveryCard = new DiscoveryCard(user, article);         // 创建对应的 ViewData
                discoveryCardList.add(discoveryCard);                                   // 放入 discoveryCardList 中
            }
            return discoveryCardList;   // 返回结果

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
