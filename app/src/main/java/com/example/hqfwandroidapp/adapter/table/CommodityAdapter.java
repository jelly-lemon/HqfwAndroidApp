package com.example.hqfwandroidapp.adapter.table;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CommodityAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    // 记录选中状态
    private List<JsonObject> purchasedItemCardList = new ArrayList<>();


    public CommodityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, JsonObject commodity) {
        Glide.with(mContext).load(Urls.HOST + commodity.get("imgURL").getAsString()).into((ImageView) helper.getView(R.id.iv_commodity));
        helper.setText(R.id.tv_name_commodity, commodity.get("name").getAsString());
        helper.setText(R.id.tv_price_commodity, commodity.get("price").getAsString());


        TextView tv_number = helper.getView(R.id.tv_number);
        CheckBox cb_check = helper.getView(R.id.cb_check);
        ImageButton ib_reduce = helper.getView(R.id.ib_reduce);
        ImageButton ib_add = helper.getView(R.id.ib_add);

        // 记录购买项
        JsonObject purchasedItem = commodity.deepCopy();// 把商品信息复制过来
        purchasedItem.addProperty("number", tv_number.getText().toString());
        // checkBox
        cb_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                purchasedItemCardList.add(purchasedItem);
            } else {
                purchasedItemCardList.remove(purchasedItem);
            }
        });
        // reduce button
        ib_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.valueOf(tv_number.getText().toString());
                if (n >= 2) {
                    n--;
                }
                tv_number.setText(String.valueOf(n));
                // 记录数量
                purchasedItem.addProperty("number", n);
            }
        });
        // add button
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.valueOf(tv_number.getText().toString());
                n++;
                tv_number.setText(String.valueOf(n));
                // 记录数量
                purchasedItem.addProperty("number", n);
            }
        });
    }

    public List<JsonObject> getPurchasedItemCardList() {
        return purchasedItemCardList;
    }
}
