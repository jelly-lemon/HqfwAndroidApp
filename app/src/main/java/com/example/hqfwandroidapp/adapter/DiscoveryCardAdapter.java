package com.example.hqfwandroidapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.discovery.DiscoveryDetailActivity;
import com.example.hqfwandroidapp.activity.home.discovery.PersonDetailActivity;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoveryCardAdapter extends RecyclerView.Adapter<DiscoveryCardAdapter.DiscoveryViewHolder> {
    // 保存上下文
    private Context context;
    // 数据
    private List<DiscoveryCard> discoveryCardArrayList;

    public DiscoveryCardAdapter(Context context, ArrayList<DiscoveryCard> discoveryCardArrayList) {
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
        Glide.with(context).load(Urls.getHOST() + discoveryCard.getUser().getHeadURL())
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.iv_head_discovery);  // 加载头像
        // 头像点击监听
        holder.iv_head_discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动 PersonDetailActivity
                Intent intent = new Intent(context, PersonDetailActivity.class);
                intent.putExtra("user", new Gson().toJson(discoveryCard.getUser()));
                context.startActivity(intent);
            }
        });
        // 设置名字
        holder.tv_name_discovery.setText(discoveryCard.getUser().getName());
        // 角色
        holder.tv_role_discovery.setText(discoveryCard.getUser().getRole());
        // 设置标签
        holder.tv_tag_discovery.setText(discoveryCard.getDiscovery().getTag());
        // 设置时间
        holder.tv_date_time_discovery.setText(discoveryCard.getDiscovery().getDateTime().toString());
        // 设置内容
        holder.tv_content_discovery.setText(discoveryCard.getDiscovery().getContent());
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
            // 添加到线性表中
            imageInfoList.add(imageInfo);
        }
        // 适配器
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(context, imageInfoList);
        // 九宫格图片加载器设置配置好了的适配器
        holder.nine_grid_view_discovery.setAdapter(nineGridViewAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiscoveryDetailActivity.class);
                intent.putExtra("discoveryCard", new Gson().toJson(discoveryCard));
                context.startActivity(intent);
            }
        });
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
        @BindView(R.id.iv_head_discovery) ImageView iv_head_discovery;
        // 名字
        @BindView(R.id.tv_name_discovery) TextView tv_name_discovery;
        // 角色
        @BindView(R.id.tv_role_discovery) TextView tv_role_discovery;
        // 标签
        @BindView(R.id.tv_tag_discovery) TextView tv_tag_discovery;
        // 时间
        @BindView(R.id.tv_date_time_discovery) TextView tv_date_time_discovery;
        // 内容
        @BindView(R.id.tv_content_discovery) TextView tv_content_discovery;
        // 九宫格
        @BindView(R.id.nine_grid_view_discovery) NineGridView nine_grid_view_discovery;



        DiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);
            // 绑定视图
            ButterKnife.bind(this, itemView);
        }
    }


}
