package com.example.hqfwandroidapp.entity;

import java.sql.Timestamp;

public class OrderForm {
    private String orderFormID;
    private Timestamp dateTime;
    private String shoppingList;
    private String buyerPhone;
    private String receiveName;
    private String receivePhone;
    private String receiveAdress;
    private String status;

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

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

    public String getReceiveAdress() {
        return receiveAdress;
    }

    public void setReceiveAdress(String receiveAdress) {
        this.receiveAdress = receiveAdress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
