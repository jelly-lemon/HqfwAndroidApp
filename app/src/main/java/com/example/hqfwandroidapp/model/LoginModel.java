package com.example.hqfwandroidapp.model;

import com.example.hqfwandroidapp.interfaces.ILoginPresenter;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginModel {
    private ILoginPresenter mILoginPresenter;


    public LoginModel(ILoginPresenter ILoginPresenter) {
        mILoginPresenter = ILoginPresenter;
    }


    public void checkAccount(String phone, String password) {
        OkGo.<String>post(Urls.LoginServlet())
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            // 在这里判断服务器返回的消息
                            // 0 == 找不到该电话号码
                            // 1 == 密码错误
                            // 2 == 正确，允许登录
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");


                            switch (code) {
                                case 0:
                                case 1:
                                    mILoginPresenter.showToast(msg);
                                    break;
                                case 2:
                                    // 保存 user 相关属性到对象里
                                    // TODO 以后 user 属性可能更新
                                    String name = jsonObject.getString("name");
                                    String student_id = jsonObject.getString("student_id");
                                    String head = jsonObject.getString("head");
                                    String gender = jsonObject.getString("gender");
                                    App.getUser().setPhone(phone);
                                    App.getUser().setPassword(password);
                                    App.getUser().setName(name);
                                    App.getUser().setStudent_id(student_id);
                                    App.getUser().setHead(head);
                                    App.getUser().setGender(gender);

                                    mILoginPresenter.showToast(msg);
                                    // 跳转页面之前存储密码
                                    mILoginPresenter.saveAccount(phone, password);
                                    mILoginPresenter.goToMainActivity();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        // TODO 无法连接到服务器时返回代码是什么？
                        switch (response.code()) {
                            case -1:
                                mILoginPresenter.showToast("找不到服务器");
                                break;
                        }
                        // 测试使用，跳转到 MainActivity
                        mILoginPresenter.showToast("测试跳转，未登录");
                        mILoginPresenter.goToMainActivity();
                    }


                });
    }
}
