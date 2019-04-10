package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.OrderForm;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllOrderFormAdapter extends RecyclerView.Adapter<AllOrderFormAdapter.ViewHolder>{
    // context
    private Context context;
    // data list
    private List<OrderForm> orderFormList;

    public AllOrderFormAdapter(Context context, List<OrderForm> orderFormList) {
        this.context = context;
        this.orderFormList = orderFormList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_order_form, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderForm orderForm = orderFormList.get(position);

        // 确认购物清单
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(orderForm.getShoppingList(), JsonArray.class);
        holder.initAdapter(context, jsonArray);
        // id
        holder.tv_order_form_id.setText(String.valueOf(orderForm.getOrderFormID()));
        // dateTime
        holder.tv_date_time.setText(orderForm.getDateTime().toString());
        // total price
        holder.tv_total_price.setText(String.valueOf(orderForm.getTotalPrice()));
        // name
        holder.tv_receive_name.setText(orderForm.getReceiveName());
        // phone
        holder.tv_receive_phone.setText(orderForm.getReceivePhone());
        // address
        holder.tv_receive_address.setText(orderForm.getReceiveAddress());
        // status
        holder.tv_order_form_status.setText(orderForm.getOrderFormStatus());

    }

    @Override
    public int getItemCount() {
        return orderFormList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // id
        @BindView(R.id.tv_order_form_id) TextView tv_order_form_id;
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
        @BindView(R.id.tv_receive_name) TextView tv_receive_name;
        // status
        @BindView(R.id.tv_order_form_status) TextView tv_order_form_status;
        // adapter
        private ConfirmPurchaseAdapter confirmPurchaseAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 绑定控件
            ButterKnife.bind(this, itemView);

            //initView();
        }

        void initView() {
            //initAdapter();
        }

        void initAdapter(Context context, JsonArray jsonArray) {
            confirmPurchaseAdapter = new ConfirmPurchaseAdapter(context, jsonArray);

            rv_commodity_confirm.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);   // 布局管理器
            rv_commodity_confirm.setLayoutManager(layoutManager);                                      // 设置布局管理器
            //SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
            //rv_commodity_confirm.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距

            rv_commodity_confirm.setAdapter(confirmPurchaseAdapter);
        }

    }
}
