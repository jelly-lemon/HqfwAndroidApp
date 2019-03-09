package com.example.hqfwandroidapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.MainActivity;
import com.example.hqfwandroidapp.presenter.LoginPresenter;
import com.example.hqfwandroidapp.utils.SaveSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements com.example.hqfwandroidapp.interfaces.ILoginActivity {


    @BindView(R.id.et_phone_login) EditText etPhone;
    @BindView(R.id.et_password_login) EditText etPassword;
    @BindView(R.id.tv_find_password) TextView tvFindPassword;
    @BindView(R.id.tv_register) TextView tvRegister;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this);


    // 测试登录
    @OnClick(R.id.btn_login_test) void submitTest() {
        goToMainActivity();
    }

    // 登录
    @OnClick(R.id.btn_login) void submit() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        mLoginPresenter.checkAccount(phone, password);
    }

    // 注册
    @OnClick(R.id.tv_register) void goRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // 找回密码
    @OnClick(R.id.tv_find_password) void goFindPassword() {
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ButterKnife 绑定生效
        ButterKnife.bind(this);


        // 自动登录
        autoLogin();
    }


    @Override
    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    // 保存账号密码
    @Override
    public void saveAccount(String phone, String password) {
        SaveSharedPreference.saveAccount(this, phone, password);
    }

    // 获取账号密码并自动登录
    public void autoLogin() {
        if (SaveSharedPreference.getPhone(this).length() != 0) {
            String phone = SaveSharedPreference.getPhone(this);
            String password = SaveSharedPreference.getPassword(this);
            mLoginPresenter.checkAccount(phone, password);
        }
    }
}
