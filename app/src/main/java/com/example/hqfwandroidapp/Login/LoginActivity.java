package com.example.hqfwandroidapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
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
    //@BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.tv_find_password) TextView tvFindPassword;
    @BindView(R.id.tv_register) TextView tvRegister;

    @OnClick(R.id.btn_login) void submit() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        OkGo.<String>post(Urls.getLogin())
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "onError"+response.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(), "onFinish", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 绑定生效
        ButterKnife.bind(this);



    }



}