package com.example.hqfwandroidapp.utils;

public class Urls {
    private static String HOST = "http://192.168.1.102:8080/lmJavaEEWebAppHqfw";
    private static String LoginServlet = HOST + "/LoginServlet";
    private static String DiscoveryServlet = HOST + "/DiscoveryServlet";
    private static String HeadPath          = HOST + "/images/head/";
    private static String ArticleImgPath    = HOST + "/images/article/";


    public static String LoginServlet() {
        return LoginServlet;
    }

    public static String DiscoverServlet() {
        return DiscoveryServlet;
    }

    public static String HeadPath() {
        return HeadPath;
    }

    public static String ArticleImgPath() {
        return ArticleImgPath;
    }




}