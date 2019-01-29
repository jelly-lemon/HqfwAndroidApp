package com.example.hqfwandroidapp.global;

import android.app.Application;


import com.lzy.okgo.OkGo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



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

        /*// 初始化 Fragmentation
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();*/
    }
}
