package com.example.hqfwandroidapp.activity.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.activity.HomeActivity;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.tool.SaveSharedPreference;
import com.example.hqfwandroidapp.tool.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_phone_login) EditText etPhone;
    @BindView(R.id.et_password_login) EditText etPassword;
    @BindView(R.id.tv_find_password) TextView tvFindPassword;
    @BindView(R.id.tv_register) TextView tvRegister;

    // 登录
    @OnClick(R.id.btn_login) void submit() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        saveUser(phone, password);
        //login(phone, password);
        // 测试，跳过登录
        goHomeActivity();
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


    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void goHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
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

                        //showToast(response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showToast(response.body());
                        showToast("登录成功");
                        goHomeActivity();


                        /*Log.d("lm", "onSuccess->body:" + response.body());
                        Log.d("lm", "onSuccess->message:" + response.message());
                        Log.d("lm", "onSuccess->code:" + response.code());*/
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (response.code() == -1) {
                            showToast("找不到服务器");
                        } else {
                            showToast("服务器故障：code:" + response.code() + " message:" + response.message());
                        }



                        /*Log.d("lm", "onError->body:" + response.body());
                        Log.d("lm", "onError->message:" + response.message());
                        Log.d("lm", "onError->code:" + response.code());*/

                    }


                });


    }

    private void saveUser(String phone, String password) {
        SaveSharedPreference.setPhone(this, phone);
        SaveSharedPreference.setPassword(this, password);
    }

}
