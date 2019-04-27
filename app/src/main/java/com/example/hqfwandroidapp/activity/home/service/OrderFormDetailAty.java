package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.example.hqfwandroidapp.R;

import com.example.hqfwandroidapp.adapter.view.PurchasedItemCardAdapter;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFormDetailAty extends AppCompatActivity {
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    @BindView(R.id.rv_purchasedItem) RecyclerView rv_purchasedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form_detail);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        JsonObject orderFormCard = GsonUtils.fromJson(getIntent().getStringExtra("orderFormCard"), JsonObject.class);


        setText(R.id.tv_title_toolbar, "普通订单详情");

        rv_purchasedItem.setLayoutManager(new LinearLayoutManager(this));
        rv_purchasedItem.setOverScrollMode(View.OVER_SCROLL_NEVER);

        PurchasedItemCardAdapter purchasedItemCardAdapter = new PurchasedItemCardAdapter(R.layout.item_purchased_item);
        purchasedItemCardAdapter.bindToRecyclerView(rv_purchasedItem);

        refresh(purchasedItemCardAdapter, orderFormCard);


        setText(R.id.tv_totalPrice, orderFormCard.get("totalPrice").getAsString());
        setText(R.id.tv_receiveAddress, orderFormCard.get("receiveAddress").getAsString());
        setText(R.id.tv_receivePhone, orderFormCard.get("receivePhone").getAsString());
        setText(R.id.tv_receiveName, orderFormCard.get("receiveName").getAsString());

        setText(R.id.tv_buyerName, orderFormCard.get("buyerName").getAsString());
        setText(R.id.tv_buyerPhone, orderFormCard.get("buyerPhone").getAsString());

        setText(R.id.tv_orderFormID, orderFormCard.get("orderFormID").getAsString());
        setText(R.id.tv_wechatDealID, orderFormCard.get("wechatDealID").getAsString());
        setText(R.id.tv_createDateTime, orderFormCard.get("createDateTime").getAsString());
        setText(R.id.tv_dealDateTime, orderFormCard.get("dealDateTime").getAsString());
    }

    private void setText(int resId, String str) {
        TextView textView = findViewById(resId);
        textView.setText(str);
    }

    private void refresh(PurchasedItemCardAdapter purchasedItemCardAdapter, JsonObject orderFormCard) {
        OkGo.<String>get(Urls.PurchasedItemCardServlet)
                .params("method", "refresh")
                .params("orderFormID", orderFormCard.get("orderFormID").getAsString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<JsonObject> purchasedItemCardList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                        purchasedItemCardAdapter.setNewData(purchasedItemCardList);
                    }
                });
    }
}
