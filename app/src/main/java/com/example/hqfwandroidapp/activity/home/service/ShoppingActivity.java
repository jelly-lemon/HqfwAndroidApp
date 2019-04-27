package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.table.CommodityAdapter;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ShoppingActivity extends AppCompatActivity {
    // 标题
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;
    // RecyclerView
    @BindView(R.id.rv_commodity) RecyclerView rv_commodity;
    // Adapter
    CommodityAdapter commodityAdapter;
    // 返回
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    // 购买
    @OnClick(R.id.btn_submit) void onSubmit() {
        //JsonArray purchasedItemCardJsonArray = commodityAdapter.getPurchasedItemCardJsonArray();
        List<JsonObject> purchasedItemCardList = commodityAdapter.getPurchasedItemCardList();
        // 检查是否选中了商品
        if (purchasedItemCardList.isEmpty()) {
            ToastUtils.showShort("请选择商品");
            return;
        }

        Intent intent = new Intent(this, ConfirmPurchaseActivity.class);
        intent.putExtra("purchasedItemCardList", GsonUtils.toJson(purchasedItemCardList));
        startActivity(intent);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        // 绑定视图
        ButterKnife.bind(this);
        // 初始化界面
        initView();
    }



    void initView() {
        // title
        tv_title_toolbar.setText("购买");

        // 初始化 rv_commodity
        rv_commodity.setLayoutManager(new LinearLayoutManager(this));                                      // 设置布局管理器
        rv_commodity.addItemDecoration(new SpacesItemDecoration(24));                              // 添加各单元之间的间距

        commodityAdapter = new CommodityAdapter(R.layout.card_commodity_shopping);
        commodityAdapter.bindToRecyclerView(rv_commodity);
        commodityAdapter.setEmptyView(R.layout.widget_empty_no_data);


        // 请求数据
        OkGo.<String>get(Urls.CommodityServlet)
                .params("method", "refresh")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //commodityAdapter = new CommodityAdapter(getContext(), GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));

                        commodityAdapter.setNewData(GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));
                    }
                });
    }

    private Context getContext() {
        return this;
    }







    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(String msg) {
        if (msg.equals("ConfirmPurchaseActivity:close")) {
            // 关闭该 activity
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 注册 event bus
        if ( !EventBus.getDefault().isRegistered(this) ) {
            EventBus.getDefault().register(this);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 取消 event bus
        EventBus.getDefault().unregister(this);
    }
}
