package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.cuit.pswkeyboard.OnPasswordInputFinish;
import com.cuit.pswkeyboard.widget.EnterPasswordPopupWindow;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.view.PurchasedItemCardAdapter;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class ConfirmPurchaseActivity extends AppCompatActivity {
    // title
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;
    // 返回按钮
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    // receive name
    @BindView(R.id.et_receive_name) EditText et_receive_name;
    // receive phone
    @BindView(R.id.et_receive_phone) EditText et_receive_phone;
    // receive address
    @BindView(R.id.ed_receive_address) EditText ed_receive_address;
    // recycler view
    @BindView(R.id.rv_shopping_list) RecyclerView rv_shopping_list;
    // adapter
    //private PurchasedItemCardAdapter purchasedItemCardAdapter;
    private PurchasedItemCardAdapter purchasedItemCardAdapter;
    // total price
    @BindView(R.id.tv_total_price) TextView tv_total_price;


    // submit button, 提交订单
    @OnClick(R.id.btn_submit) void onSubmit() {
        // 关闭键盘
        KeyboardUtils.hideSoftInput(this);

        // orderFrom
        JsonObject orderForm = new JsonObject();
        orderForm.addProperty("buyerPhone", App.user.getPhone());
        orderForm.addProperty("receivePhone", et_receive_phone.getText().toString());
        orderForm.addProperty("receiveName", et_receive_name.getText().toString());
        orderForm.addProperty("receiveAddress", ed_receive_address.getText().toString());
        orderForm.addProperty("totalPrice", tv_total_price.getText().toString());
        orderForm.addProperty("orderFormStatus", "等待付款");

        // 购买项记录
        List<JsonObject> purchasedItemList = new ArrayList<>();
        for (JsonObject jsonObject : purchasedItemCardAdapter.getData()) {
            JsonObject purchasedItemCard = jsonObject.getAsJsonObject();

            JsonObject purchasedItem = new JsonObject();
            purchasedItem.addProperty("commodityID", purchasedItemCard.get("commodityID").getAsString());
            purchasedItem.addProperty("number", purchasedItemCard.get("number").getAsString());
            purchasedItemList.add(purchasedItem);
        }

        // submit，创建一个订单
        OkGo.<String>post(Urls.OrderFormServlet)
                .params("method", "insert")
                .params("orderForm", orderForm.toString())
                .params("purchasedItemList", purchasedItemList.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 得到 orderFormID
                        JsonObject jsonObject = GsonUtils.fromJson(response.body(), JsonObject.class);
                        String orderFormID = jsonObject.get("orderFormID").getAsString();

                        // 输入密码窗口
                        EnterPasswordPopupWindow enterPasswordPopupWindow = new EnterPasswordPopupWindow(getActivityContext());
                        // 金额
                        enterPasswordPopupWindow.setMoney(orderForm.get("totalPrice").getAsFloat());
                        // 头像
                        Glide.with(getActivityContext()).load(Urls.HOST + App.user.getHeadURL()).into(enterPasswordPopupWindow.getImgHead());
                        // 输入密码回调
                        enterPasswordPopupWindow.setOnFinishInput(new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                if (password.equals("123456")) {
                                    OkGo.<String>post(Urls.OrderFormServlet)
                                            .params("method", "paySuccess")
                                            .params("orderFormID", orderFormID)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {
                                                    ToastUtils.showShort("支付成功");
                                                    // 广播消息
                                                    EventBus.getDefault().post("ConfirmPurchaseActivity:close");
                                                    // 关闭支付键盘窗口
                                                    enterPasswordPopupWindow.dismiss();
                                                    // 结束当前 activity
                                                    onActivityFinish();
                                                }
                                            });
                                } else {
                                    ToastUtils.showShort("密码错误，请重新输入");
                                }
                            }
                        });
                        // 显示支付窗口
                        enterPasswordPopupWindow.show(getActivityContext().findViewById(R.id.layoutContent)); // 设置layout在PopupWindow中显示的位置
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        // 绑定视图
        ButterKnife.bind(this);
        // init view
        initView();

    }

    // 初始化视图
    void initView() {
        // title
        tv_title_toolbar.setText("确认订单");
        // get and set basic information
        et_receive_name.setText(App.user.getName());
        et_receive_phone.setText(App.user.getPhone());
        ed_receive_address.setText(App.user.getBuilding() + "栋" + App.user.getRoomNumber() + "房间");




        rv_shopping_list.setLayoutManager(new LinearLayoutManager(this));                                      // 设置布局管理器
        rv_shopping_list.addItemDecoration(new SpacesItemDecoration(24));                              // 添加各单元之间的间距
        rv_shopping_list.setOverScrollMode(View.OVER_SCROLL_NEVER);


        List<JsonObject> jsonObjectList = GsonUtils.fromJson(getIntent().getStringExtra("purchasedItemCardList"), GsonUtils.getListType(JsonObject.class));
        purchasedItemCardAdapter = new PurchasedItemCardAdapter(R.layout.card_commodity_confirm);
        purchasedItemCardAdapter.setNewData(jsonObjectList);
        purchasedItemCardAdapter.bindToRecyclerView(rv_shopping_list);


        // 总金额
        float totalPrice = 0f;
        for (JsonObject purchasedItem : jsonObjectList) {
            int number = purchasedItem.get("number").getAsInt();
            float price = purchasedItem.get("price").getAsFloat();
            totalPrice += number * price;
        }
        tv_total_price.setText(String.valueOf(totalPrice));

    }

    Activity getActivityContext() {
        return this;
    }


    void onActivityFinish() {
        finish();
    }


}
