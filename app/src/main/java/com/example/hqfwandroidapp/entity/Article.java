package com.example.hqfwandroidapp.entity;

public class Article {
    private String article_id;
    private String phone;
    String content;
    String tag;
    String time;
    String allImgStr;

    public String getAllImgStr() {
        return allImgStr;
    }

    public void setAllImgStr(String allImgStr) {
        this.allImgStr = allImgStr;
    }

    public Article() {

    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }





    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
