package com.example.hqfwandroidapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;





import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoveryRecyclerViewAdapter extends RecyclerView.Adapter<DiscoveryRecyclerViewAdapter.DiscoveryViewHolder> {
    private Context context;                                                // 保存上下文
    List<DiscoveryCard> discoveryCardArrayList = new ArrayList<>();    // 数据

    public DiscoveryRecyclerViewAdapter() {

    }

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

        String headUrl = Urls.HeadPath() + discoveryCard.getUser().getHeadURL();       // head 图片路径
        Glide.with(context).load(headUrl)
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.iv_head);  // 加载头像

        holder.tv_name.setText(discoveryCard.getUser().getName());                  // 设置名字
        holder.tv_tag.setText(discoveryCard.getDiscovery().getTag());                 // 设置标签
        holder.tv_time.setText(discoveryCard.getDiscovery().getDateTime().toString());    // 设置时间
        holder.tv_content.setText(discoveryCard.getDiscovery().getContent());         // 设置内容

        // 获取九宫格中所有图片 URL
        ArrayList<ImageInfo> imageInfoArrayList = new ArrayList<>();
        ArrayList<String> imgURL = discoveryCard.getImgURL();
        for (String url : imgURL) {
            url = Urls.ArticleImgPath() + url;  // 图片 URL

            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(url);
            imageInfo.setOriginalImageUrl(url);

            imageInfoArrayList.add(imageInfo);    // 添加到线性表中
        }
        /*holder.nine_grid_view.setMaxNum(6);
        holder.nine_grid_view.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha) {

            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                NineGirdImageContainer nineGirdImageContainer = new NineGirdImageContainer(context, );
            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }
        });*/
        //holder.nine_grid_view.setImageLoader(new NineGridViewGlideImageLoader());  // 设置图片加载器
        //holder.nine_grid_view.setDataList(imageInfoArrayList);  // 设置数据
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(context, imageInfoArrayList);    // 适配器
        holder.nine_grid_view.setAdapter(nineGridViewAdapter);    // 九宫格图片加载器设置配置好了的适配器

    }

    @Override
    public int getItemCount() {
        return discoveryCardArrayList.size();
    }

    public void setList(List<DiscoveryCard> discoveryCardArrayList) {
        this.discoveryCardArrayList = discoveryCardArrayList;
    }

    // 追加数据
    public void addList(List<DiscoveryCard> discoveryCardArrayList) {
        this.discoveryCardArrayList.addAll(discoveryCardArrayList);
    }

    public static class DiscoveryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head) ImageView iv_head;
        @BindView(R.id.tv_name) TextView tv_name;
        @BindView(R.id.tv_tag) TextView tv_tag;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.nine_grid_view) NineGridView nine_grid_view;    // 九宫格



        public DiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);   // 绑定视图
        }
    }
}
