package com.example.hqfwandroidapp.presenter;

import com.example.hqfwandroidapp.interfaces.LoginCallBack;
import com.example.hqfwandroidapp.interfaces.LoginInterface;
import com.example.hqfwandroidapp.model.LoginModel;
import com.example.hqfwandroidapp.utils.SaveSharedPreference;

public class LoginPresenter implements LoginCallBack {
    private LoginInterface mLoginInterface;
    private LoginModel mLoginModel = new LoginModel(this);

    public LoginPresenter(LoginInterface loginInterface) {
        mLoginInterface = loginInterface;
    }

    public void checkAccount(String phone, String password) {
        mLoginModel.checkAccount(phone, password);
    }



    @Override
    public void showToast(String msg) {
        mLoginInterface.showToast(msg);
    }

    @Override
    public void goToMainActivity() {
        mLoginInterface.goToMainActivity();
    }

    @Override
    public void saveAccount(String phone, String password) {
        mLoginInterface.saveAccount(phone, password);
    }


}
