package com.example.hqfwandroidapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.MainActivity;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.SaveSharedPreference;
import com.example.hqfwandroidapp.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_phone_login) EditText etPhone;
    @BindView(R.id.et_password_login) EditText etPassword;
    @BindView(R.id.tv_find_password) TextView tvFindPassword;
    @BindView(R.id.tv_register) TextView tvRegister;

    @OnClick(R.id.btn_login) void submit() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        saveUser(phone, password);
        login(phone, password);
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


    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void autoLogin() {
        if (SaveSharedPreference.getPhone(this).length() != 0) {
            String phone = SaveSharedPreference.getPhone(this);
            String password = SaveSharedPreference.getPassword(this);
            login(phone, password);
        }
    }

    private void login(String phone, String password) {
        OkGo.<String>post(Urls.getLogin())
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        showToast(response.body());
                        goMainActivity();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToast("网络故障，请检查网络");
                    }
                });
    }

    private void saveUser(String phone, String password) {
        SaveSharedPreference.setPhone(this, phone);
        SaveSharedPreference.setPassword(this, password);
    }

}
