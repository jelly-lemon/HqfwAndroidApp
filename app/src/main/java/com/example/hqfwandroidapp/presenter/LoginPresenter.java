package com.example.hqfwandroidapp.presenter;

import com.example.hqfwandroidapp.interfaces.ILoginActivity;
import com.example.hqfwandroidapp.interfaces.ILoginPresenter;
import com.example.hqfwandroidapp.model.LoginModel;

public class LoginPresenter implements ILoginPresenter {
    private ILoginActivity mILoginActivity;
    private LoginModel mLoginModel = new LoginModel(this);

    public LoginPresenter(ILoginActivity ILoginActivity) {
        mILoginActivity = ILoginActivity;
    }

    public void checkAccount(String phone, String password) {
        mLoginModel.checkAccount(phone, password);
    }



    @Override
    public void showToast(String msg) {
        mILoginActivity.showToast(msg);
    }

    @Override
    public void goToMainActivity() {
        mILoginActivity.goToMainActivity();
    }

    @Override
    public void saveAccount(String phone, String password) {
        mILoginActivity.saveAccount(phone, password);
    }


}
