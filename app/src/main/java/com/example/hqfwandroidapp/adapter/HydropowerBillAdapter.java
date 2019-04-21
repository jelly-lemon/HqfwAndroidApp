package com.example.hqfwandroidapp.adapter;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.google.gson.JsonObject;


import java.util.List;



public class HydropowerBillAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    public HydropowerBillAdapter(int layoutResId, @Nullable List<JsonObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JsonObject item) {
        helper.setText(R.id.tv_month, item.get("month").getAsString());
        helper.setText(R.id.tv_room, item.get("room").getAsString());
        helper.setText(R.id.tv_people, item.get("people").getAsString());
        helper.setText(R.id.tv_eStart, item.get("eStart").getAsString());
        helper.setText(R.id.tv_eEnd, item.get("eEnd").getAsString());
        helper.setText(R.id.tv_eUsage, item.get("eUsage").getAsString());
        helper.setText(R.id.tv_eSuperPlan, item.get("eSuperPlan").getAsString());
        helper.setText(R.id.tv_eCost, item.get("eCost").getAsString());
        helper.setText(R.id.tv_wStart, item.get("wStart").getAsString());
        helper.setText(R.id.tv_wEnd, item.get("wEnd").getAsString());
        helper.setText(R.id.tv_wUsage, item.get("wUsage").getAsString());
        helper.setText(R.id.tv_wSuperPlan, item.get("wSuperPlan").getAsString());
        helper.setText(R.id.tv_wCost, item.get("wCost").getAsString());
        helper.setText(R.id.tv_totalCoast, item.get("totalCost").getAsString());
        helper.setText(R.id.tv_status, item.get("status").getAsString());
        switch (item.get("status").getAsString()) {
            case "等待付款": {
                helper.setTextColor(R.id.tv_status, ColorUtils.getColor(R.color.等待付款));
                break;
            }
            case "交易完成": {
                helper.setTextColor(R.id.tv_status, ColorUtils.getColor(R.color.交易完成));
                break;
            }
        }
    }


}
