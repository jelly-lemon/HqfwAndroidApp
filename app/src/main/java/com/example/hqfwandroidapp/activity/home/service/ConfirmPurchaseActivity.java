package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.CommodityAdapter;
import com.example.hqfwandroidapp.adapter.ShoppingListAdapter;
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.List;


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
    private ShoppingListAdapter shoppingListAdapter;
    // total price
    @BindView(R.id.tv_total_price) TextView tv_total_price;
    // submit button
    @OnClick(R.id.btn_submit) void onSubmit() {
        showToast("暂未实现");
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
        tv_title.setText("确认订单");
        // get and set basic information
        et_receive_name.setText(App.getUser().getName());
        et_receive_phone.setText(App.getUser().getPhone());
        ed_receive_address.setText("地址暂未实现");
        // shopping list
        String shoppingList = getIntent().getStringExtra("shoppingList");
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(shoppingList, JsonArray.class);
        // adapter
        initAdapter(jsonArray);
        // recycler view
        initRecyclerView();
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 给 Adapter 设置数据集
     * @param jsonArray
     */
    void initAdapter(JsonArray jsonArray) {
        shoppingListAdapter = new ShoppingListAdapter(this, jsonArray);
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
        rv_shopping_list.setAdapter(shoppingListAdapter);
    }
}
