package com.example.hqfwandroidapp.utils;

public class Urls {
    private static String HOST = "http://192.168.1.173:8080";
    private static String HOSTWEB = "http://192.168.1.173:8080/lmJavaEEWebAppHqfw";

    private static String LoginServlet = HOSTWEB + "/LoginServlet";
    private static String DiscoveryServlet = HOSTWEB + "/DiscoveryServlet";
    private static String HeadPath          = HOST + "/images/head/";
    private static String DiscoveryImgPath = HOST + "/images/discovery/";
    private static String PublishDiscovery = HOSTWEB + "/PublishDiscoveryServlet";


    public static String LoginServlet() {
        return LoginServlet;
    }

    public static String DiscoverServlet() {
        return DiscoveryServlet;
    }

    public static String HeadPath() {
        return HeadPath;
    }

    public static String DiscoveryImgPath() {
        return DiscoveryImgPath;
    }

    public static String PublishDiscovery() {
        return PublishDiscovery;
    }




}
