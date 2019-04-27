package com.example.hqfwandroidapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.MainActivity;
import com.example.hqfwandroidapp.entity.User;

import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SaveSharedPreference;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.et_phone_login) EditText et_phone_login;                // 电话
    @BindView(R.id.et_password_login) EditText et_password_login;          // 密码
    @BindView(R.id.tv_find_password) TextView tv_find_password;       // 找回密码
    @BindView(R.id.tv_register) TextView tv_register;                // 注册


    //private LoginPresenter mLoginPresenter = new LoginPresenter(this);  // 中间人


    // QQ 登录
    @OnClick(R.id.iv_qq) void goQQLogin() {
        // TODO QQ 登录
        showToast("暂未实现");
    }

    // 微信登录
    @OnClick(R.id.iv_wechat) void goWechatLogin() {
        // TODO 微信登录
        showToast("暂未实现");
    }



    // 登录
    @OnClick(R.id.btn_login) void submit() {
        String phone = et_phone_login.getText().toString();
        String password = et_password_login.getText().toString();

        if (phone.isEmpty()) {
            showToast("请输入账号");
            return;
        }
        if (password.isEmpty()) {
            showToast("请输入密码");
            return;
        }

        checkAccount(phone, password);
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


    // 气泡显示

    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 跳转到 MainActivity
     */

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    // 保存账号密码

    public void saveAccount(String phone, String password) {
        SaveSharedPreference.saveAccount(this, phone, password);
    }

    // 获取账号密码并自动登录
    public void autoLogin() {
        if (SaveSharedPreference.getPhone(this).length() != 0) {
            String phone = SaveSharedPreference.getPhone(this);
            String password = SaveSharedPreference.getPassword(this);
            et_phone_login.setText(phone);
            et_password_login.setText(password);
            if (phone.isEmpty()) {
                showToast("请输入账号");
                return;
            }
            if (password.isEmpty()) {
                showToast("请输入密码");
                return;
            }
            checkAccount(phone, password);
        }
    }

    /**
     * 检查账号密码
     * @param phone 号码
     * @param password  密码
     */
    public void checkAccount(String phone, String password) {
        OkGo.<String>get(Urls.UsersServlet)
                .params("method", "login")
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JsonObject result = GsonUtils.fromJson(response.body(), JsonObject.class);

                            // 在这里判断服务器返回的消息
                            String msg = result.get("msg").getAsString();
                            // 判断消息类型
                            switch (msg) {
                                // 1 == 正确，允许登录
                                case "登录成功": {
                                    User user = new User();
                                    user.setName(result.get("name").getAsString());
                                    user.setBuilding(result.get("building").getAsString());
                                    user.setGender(result.get("gender").getAsString());
                                    user.setHeadURL(result.get("headURL").getAsString());
                                    user.setPassword(result.get("password").getAsString());
                                    user.setPhone(result.get("phone").getAsString());
                                    user.setRole(result.get("role").getAsString());
                                    user.setRoomNumber(result.get("roomNumber").getAsString());
                                    user.setStudentID(result.get("studentID").getAsString());



                                    App.user = user;
                                    // 跳转页面之前存储密码
                                    saveAccount(phone, password);
                                    goToMainActivity();
                                    break;
                                }
                                default:{
                                    ToastUtils.showShort(msg);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("body:" + response.body() + " code:" + response.code());
                    }
                });
    }
}
