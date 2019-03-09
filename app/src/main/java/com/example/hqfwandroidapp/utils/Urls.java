package com.example.hqfwandroidapp.utils;

public class Urls {
    private static String HOST = "http://192.168.1.102:8080/lmJavaEEWebAppHqfw";
    private static String LoginServlet = HOST + "/LoginServlet";
    private static String DiscoveryServlet = HOST + "/DiscoveryServlet";

    public static String LoginServlet() {
        return LoginServlet;
    }

    public static String DiscoverServlet() {
        return DiscoveryServlet;
    }


}
