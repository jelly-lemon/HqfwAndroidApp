package com.example.hqfwandroidapp.utils;

import com.blankj.utilcode.util.GsonUtils;

public class Urls {
    public static String HOST = "http://192.168.1.173:8080";
    private static String HOSTWEB = HOST + "/lmJavaEEWebAppHqfw";

    public static String LoginServlet = HOSTWEB + "/LoginServlet";
    public static String DiscoveryCardServlet = HOSTWEB + "/DiscoveryCardServlet";
    public static String PublishDiscovery = HOSTWEB + "/PublishDiscoveryServlet";
    public static String CommodityServlet = HOSTWEB + "/CommodityServlet";
    public static String OrderFormServlet = HOSTWEB + "/OrderFormServlet";
    public static String CommentCardServlet = HOSTWEB + "/CommentCardServlet";
    public static String CommentServlet = HOSTWEB + "/CommentServlet";
    public static String PurchasedItemCardServlet = HOSTWEB + "/PurchasedItemCardServlet";
    public static String HydropowerBillServlet = HOSTWEB + "/HydropowerBillServlet";





    /**
     * 获取服务器地址
     * @return 服务器地址
     */
    public static String getHOST() {
        return HOST;

    }



}
