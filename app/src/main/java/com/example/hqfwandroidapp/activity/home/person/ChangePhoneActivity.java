package com.example.hqfwandroidapp.activity.home.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends AppCompatActivity {

    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    @BindView(R.id.tv_title_toolbar)
    TextView tv_title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title_toolbar.setText("更改电话");



    }
}
