package com.example.hqfwandroidapp.model;

import com.example.hqfwandroidapp.entity.User;
import com.example.hqfwandroidapp.interfaces.ILoginPresenter;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/*import org.json.JSONException;
import org.json.JSONObject;*/

/**
 *  登录模型
 */
public class LoginModel {
    private ILoginPresenter mILoginPresenter;


    public LoginModel(ILoginPresenter ILoginPresenter) {
        mILoginPresenter = ILoginPresenter;
    }


    /**
     * 检查账号密码
     * @param phone 号码
     * @param password  密码
     */
    public void checkAccount(String phone, String password) {
        OkGo.<String>post(Urls.LoginServlet())
                .params("phone", phone)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            //
                            JsonObject jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();
                            // 在这里判断服务器返回的消息
                            int code = jsonObject.get("code").getAsInt();
                            String msg = jsonObject.get("msg").getAsString();
                            // 判断消息类型
                            switch (code) {
                                case 1: // 1 == 正确，允许登录
                                    String userJSON = jsonObject.get("user").getAsString();
                                    Gson gson = new Gson();
                                    User user = gson.fromJson(userJSON, User.class);
                                    App.setUser(user);
                                    // 跳转页面之前存储密码
                                    mILoginPresenter.saveAccount(phone, password);
                                    mILoginPresenter.goToMainActivity();
                                    break;
                                case 2: // 2 == 密码错误
                                case 3: // 3 == 账号错误
                                    break;

                            }
                            // 气泡显示
                            mILoginPresenter.showToast(msg);
                        } catch (Exception e) {
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
                        //mILoginPresenter.showToast("测试跳转，未登录");
                        //mILoginPresenter.goToMainActivity();
                    }


                });
    }
}
