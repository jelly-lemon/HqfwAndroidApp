package com.example.hqfwandroidapp.entity;

import java.sql.Timestamp;

public class OrderForm {
    private String orderFormID;
    private Timestamp dateTime;
    private String shoppingList;
    private String buyerPhone;
    private String recevieAdress;
    private String status;

    public String getOrderFormID() {
        return orderFormID;
    }

    public void setOrderFormID(String orderFormID) {
        this.orderFormID = orderFormID;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getRecevieAdress() {
        return recevieAdress;
    }

    public void setRecevieAdress(String recevieAdress) {
        this.recevieAdress = recevieAdress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
