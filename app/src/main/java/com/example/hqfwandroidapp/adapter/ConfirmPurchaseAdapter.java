package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmPurchaseAdapter extends RecyclerView.Adapter<ConfirmPurchaseAdapter.ViewHolder> {
    // context
    private Context context;
    // data list
    private JsonArray jsonArray;

    public ConfirmPurchaseAdapter(Context context, JsonArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_commodity_confirm, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonObject jsonObject = jsonArray.get(position).getAsJsonObject();
        Gson gson = new Gson();
        Commodity commodity = gson.fromJson(jsonObject.get("commodity").getAsString(), Commodity.class);
        int number = jsonObject.get("number").getAsInt();

        // 图片
        Glide.with(context).load(Urls.HOST + commodity.getImgURL()).into(holder.iv_commodity);
        // name
        holder.tv_name.setText(commodity.getName());
        // detail
        holder.tv_detail.setText(commodity.getDetail());
        // price
        holder.tv_price.setText(String.valueOf(commodity.getPrice()));
        // number
        holder.tv_number.setText(String.valueOf(number));
    }

    @Override
    public int getItemCount() {
        return jsonArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // 图片
        @BindView(R.id.iv_commodity) ImageView iv_commodity;
        // name
        @BindView(R.id.tv_name__comment) TextView tv_name;
        // detail
        @BindView(R.id.tv_detail) TextView tv_detail;
        // number
        @BindView(R.id.tv_number) TextView tv_number;
        // price
        @BindView(R.id.tv_price) TextView tv_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 绑定视图
            ButterKnife.bind(this, itemView);
        }


        void initView() {

        }
    }

    /**
     * 返回总金额
     * @return 总金额
     */
    public float getTotalPrice() {
        float totalPrice = 0;
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
            Commodity commodity = gson.fromJson(jsonObject.get("commodity").getAsString(), Commodity.class);
            int number = jsonObject.get("number").getAsInt();
            totalPrice += commodity.getPrice() * number;
        }
        return totalPrice;
    }
}
