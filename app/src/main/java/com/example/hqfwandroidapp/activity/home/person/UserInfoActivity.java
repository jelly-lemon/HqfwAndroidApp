package com.example.hqfwandroidapp.activity.home.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;
    @BindView(R.id.iv_head_user) ImageView iv_head_user;
    @BindView(R.id.tv_name_user) TextView tv_name_user;
    @BindView(R.id.tv_role_user) TextView tv_role_user;
    @BindView(R.id.tv_gender_user) TextView tv_gender_user;


    @BindView(R.id.tv_studentID_user) TextView tv_studentID_user;
    @BindView(R.id.tv_address_user) TextView tv_address_user;
    @BindView(R.id.tv_phone_user) TextView tv_phone_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        tv_title_toolbar.setText("个人信息");

        JsonObject user = GsonUtils.fromJson(getIntent().getStringExtra("user"), JsonObject.class);
        Glide.with(this).load(Urls.HOST + user.get("headURL").getAsString()).into(iv_head_user);
        tv_name_user.setText(user.get("name").getAsString());
        tv_role_user.setText(user.get("role").getAsString());
        tv_gender_user.setText(user.get("gender").getAsString());
        tv_studentID_user.setText(user.get("studentID").getAsString().substring(0, 4) + "****" + user.get("studentID").getAsString().substring(8, 10));
        tv_address_user.setText("保密");
        tv_phone_user.setText(user.get("phone").getAsString().substring(0, 3) + "****" + user.get("phone").getAsString().substring(7, 11));

    }
}
