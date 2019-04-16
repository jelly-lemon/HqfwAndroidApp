package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cuit.pswkeyboard.OnPasswordInputFinish;
import com.cuit.pswkeyboard.widget.EnterPasswordPopupWindow;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.ConfirmPurchaseAdapter;
import com.example.hqfwandroidapp.entity.OrderForm;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;


public class ConfirmPurchaseActivity extends AppCompatActivity {
    // title
    @BindView(R.id.tv_title) TextView tv_title;
    // 返回按钮
    @OnClick(R.id.iv_back) void onBack() {
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
    private ConfirmPurchaseAdapter confirmPurchaseAdapter;
    // total price
    @BindView(R.id.tv_total_price) TextView tv_total_price;
    // submit button
    @OnClick(R.id.btn_submit) void onSubmit() {
        // 关闭键盘
        showKeyboard(false);

        // shoppingList
        String shoppingList = getIntent().getStringExtra("shoppingList");
        // orderFrom
        OrderForm orderForm = new OrderForm();
        orderForm.setShoppingList(shoppingList);
        orderForm.setBuyerPhone(App.getUser().getPhone());
        orderForm.setReceivePhone(et_receive_phone.getText().toString());
        orderForm.setReceiveName(et_receive_name.getText().toString());
        orderForm.setReceiveAddress(ed_receive_address.getText().toString());
        orderForm.setTotalPrice(confirmPurchaseAdapter.getTotalPrice());
        orderForm.setOrderFormStatus("等待付款");
        // convert orderFrom into json
        Gson gson = new Gson();
        String json = gson.toJson(orderForm);
        // submit
        OkGo.<String>post(Urls.OrderFormServlet)
                .params("method", "onInsertOrderForm")
                .params("orderForm", json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                        int orderFormID = jsonObject.get("orderFormID").getAsInt();

                        EnterPasswordPopupWindow enterPasswordPopupWindow = new EnterPasswordPopupWindow(getActivityContext());
                        // 金额
                        enterPasswordPopupWindow.setMoney(orderForm.getTotalPrice());
                        // 头像
                        Glide.with(getActivityContext()).load(Urls.HOST + App.getUser().getHeadURL()).into(enterPasswordPopupWindow.getImgHead());
                        // 输入密码回调
                        enterPasswordPopupWindow.setOnFinishInput(new OnPasswordInputFinish() {
                            @Override
                            public void inputFinish(String password) {
                                if (password.equals("123456")) {
                                    OkGo.<String>post(Urls.OrderFormServlet)
                                            .params("method", "onUpdateStatus")
                                            .params("orderFormID", orderFormID)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {
                                                    showToast("支付成功");
                                                    // 广播消息
                                                    EventBus.getDefault().post("ConfirmPurchaseActivity:close");
                                                    // 关闭支付键盘窗口
                                                    enterPasswordPopupWindow.dismiss();
                                                    // 结束当前 activity
                                                    onActivityFinish();
                                                }
                                            });
                                } else {
                                    showToast("密码错误，请重新输入");
                                }
                            }
                        });
                        // 显示支付窗口
                        enterPasswordPopupWindow.show(getActivityContext().findViewById(R.id.layoutContent)); // 设置layout在PopupWindow中显示的位置




                    }
                });
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (null == imm)
            return;

        if (isShow) {
            if (getCurrentFocus() != null) {
                //有焦点打开
                imm.showSoftInput(getCurrentFocus(), 0);
            } else {
                //无焦点打开
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                //有焦点关闭
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                //无焦点关闭
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }
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
        tv_title.setText("确认订单");
        // get and set basic information
        et_receive_name.setText(App.getUser().getName());
        et_receive_phone.setText(App.getUser().getPhone());
        ed_receive_address.setText(App.getUser().getBuilding() + "栋" + App.getUser().getRoomNumber() + "房间");
        // shopping list
        String shoppingList = getIntent().getStringExtra("shoppingList");
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(shoppingList, JsonArray.class);
        // adapter
        initAdapter(jsonArray);
        // recycler view
        initRecyclerView();
        // 总金额
        tv_total_price.setText(String.valueOf(confirmPurchaseAdapter.getTotalPrice()));

        //showKeyboard(false);
    }





    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 给 Adapter 设置数据集
     * @param jsonArray
     */
    void initAdapter(JsonArray jsonArray) {
        confirmPurchaseAdapter = new ConfirmPurchaseAdapter(this, jsonArray);
    }

    /**
     * 初始化 RecyclerView
     */
    void initRecyclerView() {
        rv_shopping_list.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);   // 布局管理器
        rv_shopping_list.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        rv_shopping_list.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距
        rv_shopping_list.setAdapter(confirmPurchaseAdapter);


    }

    Activity getActivityContext() {
        return this;
    }


    void onActivityFinish() {
        finish();
    }


}
