package com.example.hqfwandroidapp.entity;

import java.util.ArrayList;

public class ArticleCard {
    private Article article;
    private User user;
    private ArrayList<Comment> commentArrayList;

    public ArticleCard(Article article, User user, ArrayList<Comment> commentArrayList) {
        this.article = article;
        this.user = user;
        this.commentArrayList = commentArrayList;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comment> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }
}
