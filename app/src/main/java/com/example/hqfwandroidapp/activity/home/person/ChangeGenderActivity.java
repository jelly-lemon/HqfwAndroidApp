package com.example.hqfwandroidapp.activity.home.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeGenderActivity extends AppCompatActivity {
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;

    @BindView(R.id.spinner_gender_user) Spinner spinner_gender_user;

    @OnClick(R.id.tv_save_toolbar) void submit() {
        String gender = spinner_gender_user.getSelectedItem().toString();
        OkGo.<String>post(Urls.UsersServlet)
                .params("method", "changeGender")
                .params("phone", App.user.getPhone())
                .params("gender", gender)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        EventBus.getDefault().post("Change:success");

                        onBackPressed();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_gender);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tv_title_toolbar.setText("更改性别");



    }
}
