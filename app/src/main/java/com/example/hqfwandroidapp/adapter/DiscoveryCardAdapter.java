package com.example.hqfwandroidapp.adapter;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.discovery.PersonDetailActivity;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscoveryCardAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {

    public DiscoveryCardAdapter(int layoutResId) {
        super(layoutResId);
    }

    /*public DiscoveryCardAdapter(int layoutResId, @Nullable List<JsonObject> data) {
        super(layoutResId, data);
    }*/




    @Override
    protected void convert(BaseViewHolder helper, JsonObject discoveryCard) {
        // 头像
        ImageView iv_head_discovery = (ImageView)helper.getView(R.id.iv_head_discovery);
        Glide.with(mContext).load(Urls.HOST + discoveryCard.get("headURL").getAsString())
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(iv_head_discovery);  // 加载头像
        // 头像点击监听
        iv_head_discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 PersonDetailActivity
                Intent intent = new Intent(mContext, PersonDetailActivity.class);
                intent.putExtra("user", GsonUtils.toJson(discoveryCard));
                mContext.startActivity(intent);
            }
        });
        // 名字
        helper.setText(R.id.tv_name_discovery, discoveryCard.get("name").getAsString());
        // 角色
        //helper.setText(R.id.tv_role_discovery, "·"+discoveryCard.get("role").getAsString());
        // 标签
        helper.setText(R.id.tv_tag_discovery, discoveryCard.get("tag").getAsString());
        // 时间
        try {
            Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(discoveryCard.get("dateTime").getAsString());
            helper.setText(R.id.tv_date_time_discovery, new SimpleDateFormat("MM-dd HH:mm").format(dateTime));
        } catch(Exception px) {
            px.printStackTrace();
        }

        // 内容
        if (discoveryCard.get("content").getAsString().isEmpty()) {
            helper.getView(R.id.tv_content_discovery).setVisibility(View.GONE);
        } else {
            helper.setText(R.id.tv_content_discovery, discoveryCard.get("content").getAsString());
        }


        // 九宫格图片
        List<ImageInfo> imageInfoList = new ArrayList<>();
        JsonArray imgURL = GsonUtils.fromJson(discoveryCard.get("imgURL").getAsString(), JsonArray.class);
        for (JsonElement jsonElement : imgURL) {
            String url = Urls.HOST + jsonElement.getAsString();
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(url);
            imageInfo.setOriginalImageUrl(url);
            // 添加到线性表中
            imageInfoList.add(imageInfo);
        }
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(mContext, imageInfoList);// 适配器
        NineGridView nine_grid_view_discovery = (NineGridView)helper.getView(R.id.nine_grid_view_discovery);
        nine_grid_view_discovery.setAdapter(nineGridViewAdapter);// 九宫格图片加载器设置配置好了的适配器
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DiscoveryDetailActivity.class);
                intent.putExtra("discoveryCard", GsonUtils.toJson(discoveryCard));
                mContext.startActivity(intent);
            }
        });*/
    }
}
