package com.example.hqfwandroidapp.activity.home.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cuit.pswkeyboard.OnPasswordInputFinish;
import com.cuit.pswkeyboard.widget.EnterPasswordPopupWindow;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.table.HydropowerBillAdapter;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class AllHydropowerBillFragment extends SupportFragment {

    @BindView(R.id.rv_hydropower_bill) RecyclerView rv_hydropower_bill;
    @BindView(R.id.smartRefreshLayout) SmartRefreshLayout smartRefreshLayout;
    private HydropowerBillAdapter hydropowerBillAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_hydropower_bill, container, false);

        ButterKnife.bind(this, view);

        initView(view);

        return view;
    }

    private void initView(View view) {
        hydropowerBillAdapter = new HydropowerBillAdapter(R.layout.card_hydropower_bill, new ArrayList<>());
        hydropowerBillAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JsonObject hydropowerBill = (JsonObject)adapter.getItem(position);

                switch (hydropowerBill.get("status").getAsString()) {
                    case "等待付款": {
                        // 付款
                        pay(hydropowerBill);
                        break;
                    }
                    case "交易完成": {
                        // 去详情页面
                        startHydropowerBillDetailActivity(hydropowerBill);
                        break;
                    }
                }
            }
        });

        rv_hydropower_bill.setLayoutManager(new LinearLayoutManager(_mActivity));
        rv_hydropower_bill.addItemDecoration( new SpacesItemDecoration(24));
        rv_hydropower_bill.setAdapter(hydropowerBillAdapter);

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                OkGo.<String>get(Urls.HydropowerBillServlet)
                        .params("method", "refresh")
                        .params("room", App.user.getBuilding()+"-"+App.user.getRoomNumber())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                List<JsonObject> hydropowerBillList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                                // 设置数据集
                                hydropowerBillAdapter.setNewData(hydropowerBillList);
                                // 结束刷新
                                smartRefreshLayout.finishRefresh();
                                // 如果为空，显示指定页面
                                if (hydropowerBillList.isEmpty()) {
                                    View emptyView = getLayoutInflater().inflate(R.layout.view_empty_no_data, (ViewGroup)rv_hydropower_bill.getParent(), false);
                                    hydropowerBillAdapter.setEmptyView(emptyView);
                                }
                            }
                        });

            }
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                OkGo.<String>get(Urls.HydropowerBillServlet)
                        .params("method", "loadMore")
                        .params("start", hydropowerBillAdapter.getItemCount())
                        .params("room", App.user.getBuilding()+"-"+App.user.getRoomNumber())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                // 得到数据集
                                List<JsonObject> jsonObjectList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                                if (jsonObjectList.isEmpty()) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    hydropowerBillAdapter.addData(jsonObjectList);
                                    smartRefreshLayout.finishLoadMore();
                                }
                            }
                        });
            }


        });
        smartRefreshLayout.autoRefresh();
    }

    /**
     * 创建实例
     * @return 一个实例
     */
    public static AllHydropowerBillFragment newInstance() {
        AllHydropowerBillFragment allHydropowerBillFragment = new AllHydropowerBillFragment();
        return allHydropowerBillFragment;
    }

    private void startHydropowerBillDetailActivity(JsonObject hydropowerBill) {
        // 调转到详情页
        Intent intent = new Intent(getContext(), HydropowerBillDetailActivity.class);
        intent.putExtra("hydropowerBill", GsonUtils.toJson(hydropowerBill));
        startActivity(intent);
    }

    private void pay(JsonObject hydropowerBill) {
        // 调用支付窗口
        EnterPasswordPopupWindow enterPasswordPopupWindow = new EnterPasswordPopupWindow(getContext());// 输入密码窗口
        enterPasswordPopupWindow.setMoney(hydropowerBill.get("totalCost").getAsFloat());// 金额
        Glide.with(_mActivity).load(Urls.HOST + App.user.getHeadURL()).into(enterPasswordPopupWindow.getImgHead());// 头像
        // 输入密码回调
        enterPasswordPopupWindow.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                if (password.equals("123456")) {
                    OkGo.<String>post(Urls.HydropowerBillServlet)
                            .params("method", "paySuccess")
                            .params("month", hydropowerBill.get("month").getAsString())
                            .params("room", hydropowerBill.get("room").getAsString())
                            .params("buyerPhone", App.user.getPhone())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    ToastUtils.showShort("支付成功");
                                    // 刷新内容
                                    smartRefreshLayout.autoRefresh();
                                    // 支付窗口消失
                                    enterPasswordPopupWindow.dismiss();
                                    // 去详情页
                                    //startHydropowerBillDetailActivity(hydropowerBill);
                                }
                            });
                } else {
                    ToastUtils.showShort("密码错误，请重新输入");
                }
            }
        });
        // 显示支付窗口
        enterPasswordPopupWindow.show(_mActivity.findViewById(R.id.fl_container));
    }
}
