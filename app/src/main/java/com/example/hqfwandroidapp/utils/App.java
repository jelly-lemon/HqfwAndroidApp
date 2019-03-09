package com.example.hqfwandroidapp.utils;

import android.app.Application;


import com.example.hqfwandroidapp.entity.User;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.mob.MobSDK;

import java.util.concurrent.TimeUnit;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;


public class App extends Application {

    public static User user = new User();

    @Override
    public void onCreate() {
        super.onCreate();

        initOkGo();

        initFragmentation();

        // 初始化 MobSDK
        initMob();

        // 初始化九宫格图片加载器
        initNineGridView();
    }


    private void initOkGo() {
        // 初始化 OkGo
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(2000L, TimeUnit.MILLISECONDS);
        // 初始化 OkGo
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(0);
    }

    private void initFragmentation() {
        // 初始化 Fragmentation
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }

    private void initMob() {
        MobSDK.init(this);
    }

    public static User getUser() {
        return user;
    }


    private void initNineGridView() {
        NineGridView.setImageLoader(new PicassoImageLoader());
    }
}
