package com.example.hqfwandroidapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPhone(Context ctx, String phone) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PHONE, phone);
        editor.apply();
    }

    public static String getPhone(Context ctx) {
        return getSharedPreferences(ctx).getString(PHONE, "");
    }

    public static void setPassword(Context ctx, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String getPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PASSWORD, "");
    }


}
