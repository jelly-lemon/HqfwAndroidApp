package com.example.hqfwandroidapp.interfaces;

public interface ILoginPresenter {
    public void showToast(String msg);
    public void goToMainActivity();
    public void saveAccount(String phone, String password);
}
