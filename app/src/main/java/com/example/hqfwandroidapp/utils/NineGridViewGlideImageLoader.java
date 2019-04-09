package com.example.hqfwandroidapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ninegridview.interfaces.ImageLoader;
//import com.example.ninegridviewapp.interfaces.ImageLoader;


public class NineGridViewGlideImageLoader implements ImageLoader {
    // 加载图片显示
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String s) {
        Glide.with(context).load(s).into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String s) {
        return null;
    }
    /*@Override
    public void displayNineGridImage(Context context, String url, ImageView imageView)
    {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height)
    {
        Glide.with(context).load(url)
                .apply(new RequestOptions().override(width, height))
                .into(imageView);
    }*/
}
