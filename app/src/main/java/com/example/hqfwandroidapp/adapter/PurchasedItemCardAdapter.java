package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchasedItemCardAdapter extends RecyclerView.Adapter<PurchasedItemCardAdapter.ViewHolder>{
    // 上下文
    private Context context;
    // 数据集
    private JsonArray jsonArray;

    public PurchasedItemCardAdapter(Context context, JsonArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_purchased_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonObject jsonObject = jsonArray.get(position).getAsJsonObject();

        Glide.with(context).load(Urls.HOST + jsonObject.get("imgURL").getAsString()).into(holder.iv_commodity);
        holder.tv_name_commodity.setText(jsonObject.get("name").getAsString());
        holder.tv_price_commodity.setText(jsonObject.get("price").getAsString());
        holder.tv_number_commodity.setText(jsonObject.get("number").getAsString());
    }

    @Override
    public int getItemCount() {
        return jsonArray.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_commodity) ImageView iv_commodity;
        @BindView(R.id.tv_name_commodity) TextView tv_name_commodity;
        @BindView(R.id.tv_price_commodity) TextView tv_price_commodity;
        @BindView(R.id.tv_number_commodity) TextView tv_number_commodity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 绑定视图
            ButterKnife.bind(this, itemView);
        }
    }
}
