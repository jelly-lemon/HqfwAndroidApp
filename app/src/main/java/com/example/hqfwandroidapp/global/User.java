package com.example.hqfwandroidapp.global;

import android.content.Context;

import com.example.hqfwandroidapp.utils.SaveSharedPreference;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String phone;
    private String password;
    private String name;
    private String student_id;
    private String portraitURL;
    private char gender;


    public void autoLogin(Context ctx) {
        if (SaveSharedPreference.getPhone(ctx).length() != 0) {
            String phone = SaveSharedPreference.getPhone(ctx);
            String password = SaveSharedPreference.getPassword(ctx);
            login(phone, password);
        }
    }

    public void login(String phone, String password) {
        OkGo.<String>post(Urls.getLogin())
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // 通知
                        //showToast(response.body());
                        //showToast("登录成功");
                        //goMainActivity();
                        // TODO 发送“结束加载”消息，更新界面

                    }

                    @Override
                    public void onError(Response<String> response) {
                        // TODO 发送“故障红色状态栏”消息，更新界面
                        super.onError(response);
                        if (response.code() == -1) {
                            //showToast("找不到服务器");
                        } else {
                            //showToast("服务器故障：code:" + response.code() + " message:" + response.message());
                        }
                    }


                });


    }

    public void saveUser(Context ctx, String phone, String password) {
        SaveSharedPreference.setPhone(ctx, phone);
        SaveSharedPreference.setPassword(ctx, password);
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPortraitURL() {
        return portraitURL;
    }

    public void setPortraitURL(String portraitURL) {
        this.portraitURL = portraitURL;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
