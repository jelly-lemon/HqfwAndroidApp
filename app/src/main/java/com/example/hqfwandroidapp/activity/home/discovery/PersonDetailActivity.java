package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.User;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;

/**
 * 查看个人资料页面
 */
public class PersonDetailActivity extends AppCompatActivity {
    // 标题
    @BindView(R.id.tv_title) TextView tv_title;
    // 头像
    @BindView(R.id.iv_head) ImageView iv_head;
    // 名字
    @BindView(R.id.tv_receive_name) TextView tv_name;
    // 用户
    User user;


    /**
     * 点击返回
     */
    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        // 绑定视图
        ButterKnife.bind(this);
        // 初始化视图
        initView();
    }

    void initView() {
        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);

        tv_title.setText("个人资料");
        Glide.with(this).load(Urls.getHOST() + user.getHeadURL()).into(iv_head);
        tv_name.setText(user.getName());



    }
}
