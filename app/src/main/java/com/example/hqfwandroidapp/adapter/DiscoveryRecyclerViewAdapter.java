package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.discovery.PersonDetailActivity;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscoveryRecyclerViewAdapter extends RecyclerView.Adapter<DiscoveryRecyclerViewAdapter.DiscoveryViewHolder> {
    // 保存上下文
    private Context context;
    // 数据
    private List<DiscoveryCard> discoveryCardArrayList;

    public DiscoveryRecyclerViewAdapter(Context context, ArrayList<DiscoveryCard> discoveryCardArrayList) {
        this.context = context;
        this.discoveryCardArrayList = discoveryCardArrayList;
    }

    // 创建 ViewHolder
    @NonNull
    @Override
    public DiscoveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_discovery, parent, false);  // 加载视图
        return new DiscoveryViewHolder(v);            // 创建 ViewHolder
    }

    // 数据和视图绑定
    @Override
    public void onBindViewHolder(@NonNull DiscoveryViewHolder holder, int position) {
        DiscoveryCard discoveryCard = discoveryCardArrayList.get(position);         // 获取在 position 位置的 DiscoveryCard

        // 头像
        //String headUrl = Urls.HeadPath() + discoveryCard.getUser().getHeadURL();       // head 图片路径
        Glide.with(context).load(Urls.getHOST() + discoveryCard.getUser().getHeadURL())
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.iv_head);  // 加载头像
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 PersonDetailActivity
                Intent intent = new Intent(context, PersonDetailActivity.class);
                intent.putExtra("user", new Gson().toJson(discoveryCard.getUser()));
                context.startActivity(intent);
            }
        });
        holder.tv_name.setText(discoveryCard.getUser().getName());                  // 设置名字
        holder.tv_tag.setText(discoveryCard.getDiscovery().getTag());                 // 设置标签
        holder.tv_time.setText(discoveryCard.getDiscovery().getDateTime().toString());    // 设置时间
        holder.tv_content.setText(discoveryCard.getDiscovery().getContent());         // 设置内容
        // 九宫格图片
        List<ImageInfo> imageInfoList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(discoveryCard.getDiscovery().getImgURL(), JsonArray.class);
        for (JsonElement jsonElement : jsonArray) {
            String url = Urls.getHOST() + jsonElement.getAsString();
            //url = Urls.DiscoveryImgPath() + discoveryCard.getDiscovery().getDiscoveryID() + "/" + url;  // 图片 URL
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(url);
            imageInfo.setOriginalImageUrl(url);
            imageInfoList.add(imageInfo);    // 添加到线性表中
        }
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(context, imageInfoList);    // 适配器
        holder.nine_grid_view.setAdapter(nineGridViewAdapter);    // 九宫格图片加载器设置配置好了的适配器
    }

    @Override
    public int getItemCount() {
        return discoveryCardArrayList.size();
    }

    /**
     * 重置数据集
     * @param discoveryCardArrayList 新的数据集
     */
    public void setList(List<DiscoveryCard> discoveryCardArrayList) {
        this.discoveryCardArrayList = discoveryCardArrayList;
    }

    /**
     * 追加数据集
     * @param discoveryCardArrayList 追加的数据集
     */
    public void addList(List<DiscoveryCard> discoveryCardArrayList) {
        this.discoveryCardArrayList.addAll(discoveryCardArrayList);
    }

    /**
     * 视图持有者
     */
    static class DiscoveryViewHolder extends RecyclerView.ViewHolder {
        // 头像
        @BindView(R.id.iv_head) ImageView iv_head;
        // 名字
        @BindView(R.id.tv_name) TextView tv_name;
        // 标签
        @BindView(R.id.tv_tag) TextView tv_tag;
        // 时间
        @BindView(R.id.tv_time) TextView tv_time;
        // 内容
        @BindView(R.id.tv_content) TextView tv_content;
        // 九宫格
        @BindView(R.id.nine_grid_view) NineGridView nine_grid_view;



        DiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);   // 绑定视图
        }
    }


}
