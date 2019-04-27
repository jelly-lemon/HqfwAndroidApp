package com.example.hqfwandroidapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;

public class FindPasswordActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;                 // 标题
    @BindView(R.id.edt_phone) EditText edt_phone;               // 电话号码
    @BindView(R.id.edt_code) EditText edt_code;                 // 验证码输入框
    @BindView(R.id.edt_new_password) EditText edt_new_password; // 输入新密码
    // 返回
    @OnClick(R.id.iv_back_toolbar) void goBack() {
        onBackPressed();    // 返回
    }
    // 提交
    @OnClick(R.id.btn_submit) void submit() {
        // TODO 提交到服务器
        showToast("暂未实现");
    }
    // 获取验证码
    @OnClick(R.id.btn_get_code) void getCode() {
        // TODO 获取验证码
        showToast("暂未实现");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        ButterKnife.bind(this); // 绑定视图
        initUI();   // 初始化界面

    }

    /**
     * 初始化界面操作
     */
    private void initUI() {
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        tv_title_toolbar.setText("找回密码"); // 设置标题
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/


    // 气泡显示
    //@Override
    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
