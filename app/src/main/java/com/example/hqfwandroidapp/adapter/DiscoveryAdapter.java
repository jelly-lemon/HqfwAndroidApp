package com.example.hqfwandroidapp.adapter;



import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.viewdata.DiscoveryCard;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryViewHolder> {
    private Context context;
    ArrayList<DiscoveryCard> discoveryCardArrayList = new ArrayList<>();

    public DiscoveryAdapter() {

    }

    public DiscoveryAdapter(Context context, ArrayList<DiscoveryCard> discoveryCardArrayList) {
        this.context = context;
        this.discoveryCardArrayList = discoveryCardArrayList;
    }



    @NonNull
    @Override
    public DiscoveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_discovery, parent, false);
        return new DiscoveryViewHolder(v);
    }

    // 数据和视图绑定
    @Override
    public void onBindViewHolder(@NonNull DiscoveryViewHolder holder, int position) {
        DiscoveryCard discoveryCard = discoveryCardArrayList.get(position);// 获取在 position 位置的 DiscoveryCard


        String headUrl = Urls.HeadPath() + discoveryCard.getUser().getHead();   // head 图片路径
        Picasso.get().load(headUrl)
                .placeholder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .into(holder.iv_head);  // 加载头像

        // 加载头像
        /*OkGo.<Bitmap>get(headUrl)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        holder.iv_head.setImageBitmap(response.body());
                    }
                });*/

        holder.tv_name.setText(discoveryCard.getUser().getName());  // 设置名字
        holder.tv_time.setText(discoveryCard.getArticle().getTime().toString());   // 设置时间
        holder.tv_content.setText(discoveryCard.getArticle().getContent()); // 设置内容

        // 获取九宫格中所有图片 URL
        ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
        ArrayList<String> imgURL = discoveryCard.getImgURL();
        for (String url : imgURL) {
            ImageInfo imageInfo = new ImageInfo();
            url = Urls.ArticleImgPath() + url;

            imageInfo.setThumbnailUrl(url);
            imageInfo.setBigImageUrl(url);
            imageInfoList.add(imageInfo);
        }
        // TODO 加载图片
        holder.iv_nine.setAdapter(new NineGridViewClickAdapter(context, imageInfoList));// 九宫格图片加载器设置配置好了的适配器

    }

    @Override
    public int getItemCount() {
        return discoveryCardArrayList.size();
    }

    public void setList(ArrayList<DiscoveryCard> discoveryCardArrayList) {
        this.discoveryCardArrayList = discoveryCardArrayList;
    }

    public static class DiscoveryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head) ImageView iv_head;
        @BindView(R.id.tv_name) TextView tv_name;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.iv_nine) NineGridView iv_nine;


        public DiscoveryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
