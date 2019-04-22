package com.example.hqfwandroidapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.tv_title_discovery) TextView tv_title; // 标题

    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();    // 按下返回键
    }

    // 获取验证码
    @OnClick(R.id.btn_get_code) void getCode() {
        // TODO 获取验证码
        showToast("暂未实现");
    }

    // 提交注册
    @OnClick(R.id.btn_submit) void submit() {
        // TODO 提交注册
        showToast("暂未实现");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this); // 绑定视图
        initUI();   // 初始化界面
    }

    // 初始化界面
    private void initUI() {
        /*ActionBar actionBar = getSupportActionBar();
        // 显示返回箭头 <--
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        tv_title.setText("注册");
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 返回键
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    // 显示气泡
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
