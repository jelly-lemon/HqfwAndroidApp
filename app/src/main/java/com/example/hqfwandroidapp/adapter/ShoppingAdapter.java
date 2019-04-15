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
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolodr> {
    // 上下文
    private Context context;
    // 数据集
    private List<Commodity> commodityList;
    // 记录选中状态
    private List<Integer> numberList = new ArrayList<>();


    public ShoppingAdapter(Context context, List<Commodity> commodityList) {
        this.context = context;
        this.commodityList = commodityList;

        // 初始化选中列表，默认为 0 个数量
        for (int i = 0; i < commodityList.size(); i++) {
            numberList.add(0);
        }
    }

    /**
     * 获取 shoppingList
     * @return shoppingList JSON
     */
    public String getShoppingList() {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < numberList.size(); i++) {
            if (numberList.get(i) != 0) {
                JsonObject jsonObject = new JsonObject();
                //jsonObject.addProperty("commodityID", commodityList.get(i).getCommodityID());
                //jsonObject.addProperty("name", commodityList.get(i).getName());
                Gson gson = new Gson();
                jsonObject.addProperty("commodity", gson.toJson(commodityList.get(i)));
                jsonObject.addProperty("number", numberList.get(i));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray.toString();
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
        Commodity commodity = commodityList.get(position);
        // 图片
        Glide.with(context).load(Urls.HOST + commodity.getImgURL()).into(holder.iv_commodity);
        // name
        holder.tv_name.setText(commodity.getName());
        // detail
        holder.tv_detail.setText(commodity.getDetail());
        // price
        holder.tv_price.setText(String.valueOf(commodity.getPrice()));
        // checkBox
        holder.cb_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                int n = Integer.valueOf(holder.tv_number.getText().toString());
                numberList.set(position, n);
            } else {
                numberList.set(position, 0);
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
                holder.tv_number.setText("" + n);
                if (holder.cb_check.isChecked()) {
                    numberList.set(position, n);
                }
            }
        });
        // add button
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.valueOf(holder.tv_number.getText().toString());
                n++;
                holder.tv_number.setText("" + n);
                if (holder.cb_check.isChecked()) {
                    numberList.set(position, n);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


    static class ViewHolodr extends RecyclerView.ViewHolder{
        // 选中按钮
        @BindView(R.id.cb_check) CheckBox cb_check;
        // 图片
        @BindView(R.id.iv_commodity) ImageView iv_commodity;
        // 商品名字
        @BindView(R.id.tv_name__comment) TextView tv_name;
        // 商品描述
        @BindView(R.id.tv_detail) TextView tv_detail;
        // 单价
        @BindView(R.id.tv_price) TextView tv_price;
        // 减少数量按钮
        @BindView(R.id.ib_reduce) ImageButton ib_reduce;
        // 数量
        @BindView(R.id.tv_number) TextView tv_number;
        // 增加
        @BindView(R.id.ib_add) ImageButton ib_add;

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
