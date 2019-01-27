package com.example.hqfwandroidapp;

public class Urls {
    private static String HOST = "http://192.168.43.15:8080/lmJavaEEWebAppHqfw";
    private static String LOGIN = HOST + "/LoginServlet";

    public static String getLogin() {
        return LOGIN;
    }


}
