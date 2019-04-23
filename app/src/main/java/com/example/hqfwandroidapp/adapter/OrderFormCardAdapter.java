package com.example.hqfwandroidapp.adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

public class OrderFormCardAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {


    public OrderFormCardAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, JsonObject orderFormCard) {


        // id
        helper.setText(R.id.tv_orderFormID, orderFormCard.get("orderFormID").getAsString());
        // dateTime
        helper.setText(R.id.tv_dateTime, orderFormCard.get("dateTime").getAsString());
        // total price
        helper.setText(R.id.tv_totalPrice, orderFormCard.get("totalPrice").getAsString());
        // name
        helper.setText(R.id.tv_receiveName, orderFormCard.get("receiveName").getAsString());
        // phone
        helper.setText(R.id.tv_receivePhone, orderFormCard.get("receivePhone").getAsString());
        // address
        helper.setText(R.id.tv_receiveAddress, orderFormCard.get("receiveAddress").getAsString());
        // status
        switch (orderFormCard.get("orderFormStatus").getAsString()) {
            case "等待付款": {
                helper.setTextColor(R.id.tv_orderFormStatus, ColorUtils.getColor(R.color.等待付款));
                break;
            }
            case "交易完成": {
                helper.setTextColor(R.id.tv_orderFormStatus, ColorUtils.getColor(R.color.交易完成));
                break;
            }
        }
        helper.setText(R.id.tv_orderFormStatus, orderFormCard.get("orderFormStatus").getAsString());


        // 购买的物品信息
        RecyclerView rv_purchasedItemCard_orderFormCard = (RecyclerView)helper.getView(R.id.rv_purchasedItem);
        rv_purchasedItemCard_orderFormCard.setLayoutManager(new LinearLayoutManager(mContext));
        rv_purchasedItemCard_orderFormCard.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rv_purchasedItemCard_orderFormCard.setNestedScrollingEnabled(false);

        NPurchasedItemCardAdapter purchasedItemCardAdapter = new NPurchasedItemCardAdapter(R.layout.item_purchased_item);
        purchasedItemCardAdapter.bindToRecyclerView(rv_purchasedItemCard_orderFormCard);

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
