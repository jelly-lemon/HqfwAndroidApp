package com.example.hqfwandroidapp.activity.home.service;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.example.hqfwandroidapp.R;
import com.google.gson.JsonObject;

public class HydropowerBillDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydropower_bill_detail);

        initView();

    }

    private void initView() {
        JsonObject hydropowerBill = GsonUtils.fromJson(getIntent().getStringExtra("hydropowerBill"), JsonObject.class);

        setText(R.id.tv_month, hydropowerBill.get("month").getAsString());
        setText(R.id.tv_room, hydropowerBill.get("room").getAsString());
        setText(R.id.tv_people, hydropowerBill.get("people").getAsString());
        setText(R.id.tv_eStart, hydropowerBill.get("eStart").getAsString());
        setText(R.id.tv_eEnd, hydropowerBill.get("eEnd").getAsString());
        setText(R.id.tv_eUsage, hydropowerBill.get("eUsage").getAsString());
        setText(R.id.tv_eSuperPlan, hydropowerBill.get("eSuperPlan").getAsString());
        setText(R.id.tv_eCost, hydropowerBill.get("eCost").getAsString());
        setText(R.id.tv_wStart, hydropowerBill.get("wStart").getAsString());
        setText(R.id.tv_wEnd, hydropowerBill.get("wEnd").getAsString());
        setText(R.id.tv_wUsage, hydropowerBill.get("wUsage").getAsString());
        setText(R.id.tv_wSuperPlan, hydropowerBill.get("wSuperPlan").getAsString());
        setText(R.id.tv_wCost, hydropowerBill.get("wCost").getAsString());
        setText(R.id.tv_totalCoast, hydropowerBill.get("totalCost").getAsString());
        setText(R.id.tv_status, hydropowerBill.get("status").getAsString());
        switch (hydropowerBill.get("status").getAsString()) {
            case "等待付款": {
                setTextColor(R.id.tv_status, ColorUtils.getColor(R.color.等待付款));
                break;
            }
            case "交易完成": {
                setTextColor(R.id.tv_status, ColorUtils.getColor(R.color.交易完成));
                break;
            }
        }

        setText(R.id.tv_name_buyer, "TODO");
        setText(R.id.tv_phone_buyer, hydropowerBill.get("buyerPhone").getAsString());
        setText(R.id.tv_bill_number, hydropowerBill.get("billNumber").getAsString());
        setText(R.id.tv_wechat_deal_number, hydropowerBill.get("wechatDealNumber").getAsString());
        setText(R.id.tv_create_date_time, hydropowerBill.get("createDateTime").getAsString());
        setText(R.id.tv_deal_date_time, hydropowerBill.get("dealDateTime").getAsString());

    }



    private void setText(int resId, String content) {
        TextView textView = findViewById(resId);
        textView.setText(content);
    }

    private void setTextColor(int resId, int color) {
        TextView textView = findViewById(resId);
        textView.setTextColor(color);
    }
}
