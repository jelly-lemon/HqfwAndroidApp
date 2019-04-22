package com.example.hqfwandroidapp.utils;

import android.app.Application;
import android.content.Context;


import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.User;
/*import com.example.ninegridviewapp.ui.NineGridView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;*/
import com.example.ninegridview.ui.NineGridView;
import com.lzy.okgo.OkGo;
import com.mob.MobSDK;
import com.mob.ums.gui.pickers.ImagePicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;


public class App extends Application {

    public static User user = new User();   // 保存用户

    @Override
    public void onCreate() {
        super.onCreate();

        initOkGo();             // 初始化 OkGo
        initFragmentation();    // 初始化 Fragmentation
        initMob();              // 初始化 MobSDK
        initNineGridView();     // 初始化九宫格图片加载器
        initImagePicker();      // 初始化微信图片加载器
        initSmartRefreshLayout();
    }

    private void initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });

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

    public static void setUser(User user) {
        App.user = user;
    }

    private void initNineGridView() {
        NineGridView.setImageLoader(new NineGridViewGlideImageLoader());    // 为九宫格图片显示器设置图片加载器

    }

    private void initImagePicker() {
        /*ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerGlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                        //显示拍照按钮
        imagePicker.setCrop(true);                              //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                     //是否按矩形区域保存
        imagePicker.setSelectLimit(9);                          //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);    //裁剪框的形状
        imagePicker.setFocusWidth(800);                         //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                           //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                           //保存文件的高度。单位像素*/
    }
}
