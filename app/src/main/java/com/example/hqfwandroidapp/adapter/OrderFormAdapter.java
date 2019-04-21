package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.service.OrderFormDetailActivity;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFormAdapter extends RecyclerView.Adapter<OrderFormAdapter.ViewHolder> {
    // 上下文
    private Context context;
    // 数据集
    //private JsonArray jsonArray;
    private List<JsonObject> jsonObjectList;

    public OrderFormAdapter(Context context, List<JsonObject> jsonObjectList) {
        this.context = context;
        this.jsonObjectList = jsonObjectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_order_form, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonObject jsonObject = jsonObjectList.get(position);

        // id
        holder.tv_order_form_id.setText(jsonObject.get("orderFormID").getAsString());
        // dateTime
        holder.tv_date_time.setText(jsonObject.get("dateTime").getAsString());
        // total price
        holder.tv_total_price.setText(jsonObject.get("totalPrice").getAsString());
        // name
        holder.tv_receive_name.setText(jsonObject.get("receiveName").getAsString());
        // phone
        holder.tv_receive_phone.setText(jsonObject.get("receivePhone").getAsString());
        // address
        holder.tv_receive_address.setText(jsonObject.get("receiveAddress").getAsString());
        // status
        switch (jsonObject.get("orderFormStatus").getAsString()) {
            case "等待付款": {
                holder.tv_order_form_status.setTextColor(context.getColor(R.color.等待付款));
                break;
            }
            case "交易完成": {
                holder.tv_order_form_status.setTextColor(context.getColor(R.color.交易完成));
                break;
            }
        }
        holder.tv_order_form_status.setText(jsonObject.get("orderFormStatus").getAsString());

        // 点击该卡片时，进入详情页面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderFormDetailActivity.class);
                intent.putExtra("orderFormID", jsonObject.get("orderFormID").getAsString());
                context.startActivity(intent);
            }
        });

        // 购买的物品信息
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.rv_purchased_item.setLayoutManager(layoutManager);
        holder.rv_purchased_item.setNestedScrollingEnabled(false);
        OkGo.<String>get(Urls.PurchasedItemCardServlet)
                .params("method", "refresh")
                .params("orderFormID", jsonObject.get("orderFormID").getAsString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        holder.purchasedItemCardAdapter = new PurchasedItemCardAdapter(context, GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));
                        holder.rv_purchased_item.setAdapter(holder.purchasedItemCardAdapter);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return jsonObjectList.size();
    }

    public void setData(List<JsonObject> jsonObjectList) {
        this.jsonObjectList = jsonObjectList;
        notifyDataSetChanged();
    }

    public void addDate(List<JsonObject> jsonObjectList) {
        this.jsonObjectList.addAll(jsonObjectList);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        // id
        @BindView(R.id.tv_bill_number)
        TextView tv_order_form_id;
        // date time
        @BindView(R.id.tv_date_time) TextView tv_date_time;
        // total price
        @BindView(R.id.tv_total_price) TextView tv_total_price;
        // receive address
        @BindView(R.id.tv_receive_address) TextView tv_receive_address;
        // receive phone
        @BindView(R.id.tv_receive_phone) TextView tv_receive_phone;
        // receive name
        @BindView(R.id.tv_name_discovery) TextView tv_receive_name;
        // status
        @BindView(R.id.tv_order_form_status) TextView tv_order_form_status;
        // 每一项商品的 recycler view
        @BindView(R.id.rv_purchased_item) RecyclerView rv_purchased_item;
        // adapter
        private PurchasedItemCardAdapter purchasedItemCardAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 绑定控件
            ButterKnife.bind(this, itemView);
        }
    }
}
