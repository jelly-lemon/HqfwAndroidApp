package com.example.hqfwandroidapp.viewdata;

import com.example.hqfwandroidapp.entity.Article;
import com.example.hqfwandroidapp.entity.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DiscoveryCard {
    User user;          // 用户对象
    Article article;    // 文章对象

    public DiscoveryCard(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    // 获取某文章里面所有图片的 URL
    public ArrayList<String> getImgURL() {
        ArrayList<String> imgURLList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(article.getImg_url_json());
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                String url = jsonArray.getString(i);
                imgURLList.add(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imgURLList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
