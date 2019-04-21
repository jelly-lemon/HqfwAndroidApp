package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolodr> {
    // 上下文
    private Context context;
    // 数据集
    //private JsonArray commodityJsonArray;
    private List<JsonObject> jsonObjectList;
    // 记录选中状态
    //private JsonArray purchasedItemCardJsonArray = new JsonArray();
    private List<JsonObject> purchasedItemCardList = new ArrayList<>();


    public CommodityAdapter(Context context, List<JsonObject> jsonObjectList) {
        this.context = context;
        this.jsonObjectList = jsonObjectList;
    }



    @NonNull
    @Override
    public ViewHolodr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_commodity_shopping, parent, false);
        return new ViewHolodr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolodr holder, int position) {
        // 具体的一个商品
        JsonObject commodity = jsonObjectList.get(position);

        // 图片
        Glide.with(context).load(Urls.HOST + commodity.get("imgURL").getAsString()).into(holder.iv_commodity);
        // name
        holder.tv_name.setText(commodity.get("name").getAsString());
        // price
        holder.tv_price.setText(commodity.get("price").getAsString());
        // 数量，默认 1
        holder.tv_number.setText("1");

        // 记录购买项
        JsonObject purchasedItem = commodity.deepCopy();// 把商品信息复制过来
        purchasedItem.addProperty("number", holder.tv_number.getText().toString());
        // checkBox
        holder.cb_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                purchasedItemCardList.add(purchasedItem);
            } else {
                purchasedItemCardList.remove(purchasedItem);
            }
        });
        // reduce button
        holder.ib_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.valueOf(holder.tv_number.getText().toString());
                if (n >= 2) {
                    n--;
                }
                holder.tv_number.setText(String.valueOf(n));
                // 记录数量
                purchasedItem.addProperty("number", n);
            }
        });
        // add button
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.valueOf(holder.tv_number.getText().toString());
                n++;
                holder.tv_number.setText(String.valueOf(n));
                // 记录数量
                purchasedItem.addProperty("number", n);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jsonObjectList.size();
    }

    /*public JsonArray getPurchasedItemCardJsonArray() {
        return purchasedItemCardJsonArray;
    }*/

    public List<JsonObject> getPurchasedItemCardList() {
        return purchasedItemCardList;
    }

    static class ViewHolodr extends RecyclerView.ViewHolder{
        // 选中按钮
        @BindView(R.id.cb_check) CheckBox cb_check;
        // 图片
        @BindView(R.id.iv_commodity) ImageView iv_commodity;
        // 商品名字
        @BindView(R.id.tv_name__comment) TextView tv_name;
        // 商品描述
        //@BindView(R.id.tv_name_commodity) TextView tv_detail;
        // 单价
        @BindView(R.id.tv_price_commodity) TextView tv_price;
        // 减少数量按钮
        @BindView(R.id.ib_reduce) ImageButton ib_reduce;
        // 数量
        @BindView(R.id.tv_number) TextView tv_number;
        // 增加
        @BindView(R.id.iv_add) ImageButton ib_add;

        public ViewHolodr(@NonNull View itemView) {
            super(itemView);
            // 绑定视图
            ButterKnife.bind(this, itemView);
            // 初始化视图
            initView();
        }

        void initView() {

        }
    }
}
