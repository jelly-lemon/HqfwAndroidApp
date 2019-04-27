package com.example.hqfwandroidapp.adapter.view;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;


public class PurchasedItemCardAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    public PurchasedItemCardAdapter(int layoutResId) {
        super(layoutResId);
    }



    @Override
    protected void convert(BaseViewHolder helper, JsonObject purchasedItemCard) {

        Glide.with(mContext).load(Urls.HOST + purchasedItemCard.get("imgURL").getAsString()).into((ImageView) helper.getView(R.id.iv_commodity));
        helper.setText(R.id.tv_name_commodity, purchasedItemCard.get("name").getAsString());
        helper.setText(R.id.tv_price_commodity, purchasedItemCard.get("price").getAsString());
        helper.setText(R.id.tv_number_commodity, purchasedItemCard.get("number").getAsString());
    }
}
