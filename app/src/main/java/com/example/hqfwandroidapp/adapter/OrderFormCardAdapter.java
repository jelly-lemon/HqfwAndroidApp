package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.service.OrderFormDetailActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFormCardAdapter extends RecyclerView.Adapter<OrderFormCardAdapter.ViewHolder> {
    // 上下文
    private Context context;
    // 数据集
    private JsonArray jsonArray;

    public OrderFormCardAdapter(Context context, JsonArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_order_form, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonObject jsonObject = jsonArray.get(position).getAsJsonObject();

        // 确认购物清单
        // id
        holder.tv_order_form_id.setText(jsonObject.get("orderFormID").getAsString());
        // dateTime
        holder.tv_date_time.setText(jsonObject.get("dateTime").getAsString());
        // total price
        float totalPrice = jsonObject.get("totalPrice").getAsFloat();
        holder.tv_total_price.setText(String.valueOf(totalPrice));
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


        /*// 点击该卡片时，进入详情页面
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderFormDetailActivity.class);
                intent.putExtra("orderFormID", orderForm.getOrderFormID());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return jsonArray.size();
    }

    public void setData(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        // id
        @BindView(R.id.tv_order_form_id)
        TextView tv_order_form_id;
        // date time
        @BindView(R.id.tv_date_time) TextView tv_date_time;
        // recycler view
        @BindView(R.id.rv_commodity_confirm) RecyclerView rv_commodity_confirm;
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
        // adapter
        private ConfirmPurchaseAdapter confirmPurchaseAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 绑定控件
            ButterKnife.bind(this, itemView);

            initView(itemView);
        }

        void initView(View view) {

        }
    }
}
