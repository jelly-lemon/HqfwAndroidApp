package com.example.hqfwandroidapp.utils;

public class Urls {
    public static String HOST = "http://192.168.1.173:8080";
    private static String HOSTWEB = HOST + "/lmJavaEEWebAppHqfw";

    private static String LoginServlet = HOSTWEB + "/LoginServlet";
    public static String DiscoveryCardServlet = HOSTWEB + "/DiscoveryCardServlet";
    //private static String HeadPath          = HOST + "/images/head/";
    //private static String DiscoveryImgPath = HOST + "/images/discovery/";
    private static String PublishDiscovery = HOSTWEB + "/PublishDiscoveryServlet";
    public static String CommodityServlet = HOSTWEB + "/CommodityServlet";
    public static String OrderFormServlet = HOSTWEB + "/OrderFormServlet";
    //public static String OrderFormCardServlet = HOSTWEB + "/OrderFormCardServlet";
    public static String CommentCardServlet = HOSTWEB + "/CommentCardServlet";
    public static String CommentServlet = HOSTWEB + "/CommentServlet";
    public static String PurchasedItemCardServlet = HOSTWEB + "/PurchasedItemCardServlet";


    public static String LoginServlet() {
        return LoginServlet;
    }

    public static String DiscoverServlet() {
        return DiscoveryCardServlet;
    }

    public static String PublishDiscovery() {
        return PublishDiscovery;
    }

    /**
     * 获取服务器地址
     * @return 服务器地址
     */
    public static String getHOST() {
        return HOST;
    }



}
