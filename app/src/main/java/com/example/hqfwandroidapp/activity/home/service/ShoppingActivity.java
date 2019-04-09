package com.example.hqfwandroidapp.activity.home.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.CommodityAdapter;
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {
    // 标题
    @BindView(R.id.tv_title) TextView tv_title;


    // RecyclerView
    @BindView(R.id.rv_commodity) RecyclerView rv_commodity;


    // 返回
    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();
    }

    // 购买
    @OnClick(R.id.btn_submit) void onSubmit() {
        showToast("暂未实现");
    }
    // Adapter
    CommodityAdapter commodityAdapter;


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
        tv_title.setText("购买");
        // 请求数据
        OkGo.<String>get(Urls.CommodityServlet)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<List<Commodity>>(){}.getType();
                        Gson gson = new Gson();
                        List<Commodity> commodityList = gson.fromJson(response.body(), type);
                        // 初始化 adapter
                        initAdapter(commodityList);

                        // 初始化 RecyclerView
                        initRecyclerView();
                    }
                });
    }

    /**
     * 给 Adapter 设置数据集
     * @param commodityList
     */
    void initAdapter(List<Commodity> commodityList) {
        // CommodityAdapter
        commodityAdapter = new CommodityAdapter(this, commodityList);
    }

    /**
     * 初始化 RecyclerView
     */
    void initRecyclerView() {
        // 初始化 rv_commodity
        rv_commodity.setHasFixedSize(true);                                                // RecyclerView 自适应尺寸
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);   // 布局管理器
        rv_commodity.setLayoutManager(layoutManager);                                      // 设置布局管理器
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);           // 间距
        rv_commodity.addItemDecoration(spacesItemDecoration);                              // 添加各单元之间的间距
        rv_commodity.setAdapter(commodityAdapter);
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
