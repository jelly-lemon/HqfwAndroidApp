package com.example.hqfwandroidapp.global;

import android.app.Application;

import com.example.hqfwandroidapp.BuildConfig;
import com.lzy.okgo.OkGo;

import me.yokeyword.fragmentation.Fragmentation;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 OkGo
        OkGo.getInstance().init(this);

        // 初始化 Fragmentation
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }
}
