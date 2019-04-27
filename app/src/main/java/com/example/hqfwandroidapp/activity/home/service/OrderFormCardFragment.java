package com.example.hqfwandroidapp.activity.home.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cuit.pswkeyboard.OnPasswordInputFinish;
import com.cuit.pswkeyboard.widget.EnterPasswordPopupWindow;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.view.OrderFormCardAdapter;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class OrderFormCardFragment extends SupportFragment {
    // recycler view
    @BindView(R.id.rv_allOrderForm) RecyclerView rv_allOrderForm;
    // adapter
    private OrderFormCardAdapter orderFormCardAdapter;
    // refresh layout
    @BindView(R.id.rl_allOrderForm) SwipeRefreshLayout rl_allOrderForm;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载视图
        View view = inflater.inflate(R.layout.fragment_order_form_card, container, false);
        // 绑定变量
        ButterKnife.bind(this, view);
        // 初始化视图
        initView(view);

        return view;
    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        // 初始化 rv_allOrderForm
        rv_allOrderForm.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_allOrderForm.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rv_allOrderForm.addItemDecoration(new SpacesItemDecoration(24));

        // 适配器
        orderFormCardAdapter = new OrderFormCardAdapter(R.layout.card_orderform);
        orderFormCardAdapter.bindToRecyclerView(rv_allOrderForm);
        orderFormCardAdapter.setEmptyView(R.layout.widget_empty_no_data);
        orderFormCardAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rv_allOrderForm);
        orderFormCardAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JsonObject orderFormCard = (JsonObject)adapter.getItem(position);
                switch (orderFormCard.get("orderFormStatus").getAsString()) {
                    case "等待付款": {
                        // 调用支付窗口
                        pay(orderFormCard);
                        break;
                    }
                    case "交易完成": {
                        // 进入详情页面
                        Intent intent = new Intent(getContext(), OrderFormCardDetailAty.class);
                        intent.putExtra("orderFormCard", orderFormCard.toString());
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

        // refresh layout
        rl_allOrderForm.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        rl_allOrderForm.setRefreshing(true);
        refresh();
    }


    private void refresh() {
        // 获取数据
        OkGo.<String>get(Urls.OrderFormCardServlet)
                .params("method", "refresh")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 设置数据集
                        orderFormCardAdapter.setNewData(GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class)));
                        // finish
                        rl_allOrderForm.setRefreshing(false);
                    }
                });
    }

    private void loadMore() {
        // 获取数据
        OkGo.<String>get(Urls.OrderFormCardServlet)
                .params("method", "loadMore")
                .params("start", orderFormCardAdapter.getData().size())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<JsonObject> orderFormCardList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));
                        if (orderFormCardList.isEmpty()) {
                            orderFormCardAdapter.loadMoreEnd();
                        } else {
                            // 添加数据
                            orderFormCardAdapter.addData(orderFormCardList);
                            orderFormCardAdapter.loadMoreComplete();
                        }
                    }
                });
    }



    /**
     * 创建实例
     * @return 一个实例
     */
    public static OrderFormCardFragment newInstance() {
        OrderFormCardFragment orderFormCardFragment = new OrderFormCardFragment();
        return orderFormCardFragment;
    }



    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEvent(String msg) {
        if (msg.equals("ConfirmPurchaseActivity:close")) {
            rl_allOrderForm.setRefreshing(true);
            refresh();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // 订阅消息
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // 取消订阅
        EventBus.getDefault().unregister(this);
    }

    private void pay(JsonObject orderFormCard) {
        // 调用支付窗口
        EnterPasswordPopupWindow enterPasswordPopupWindow = new EnterPasswordPopupWindow(getContext());// 输入密码窗口
        enterPasswordPopupWindow.setMoney(orderFormCard.get("totalPrice").getAsFloat());// 金额
        Glide.with(_mActivity).load(Urls.HOST + App.user.getHeadURL()).into(enterPasswordPopupWindow.getImgHead());// 头像
        // 输入密码回调
        enterPasswordPopupWindow.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String password) {
                if (password.equals("123456")) {
                    OkGo.<String>post(Urls.OrderFormServlet)
                            .params("method", "paySuccess")
                            .params("orderFormID", orderFormCard.get("orderFormID").getAsFloat())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    ToastUtils.showShort("支付成功");
                                    // 刷新内容
                                    rl_allOrderForm.setRefreshing(true);
                                    refresh();
                                    // 支付窗口消失
                                    enterPasswordPopupWindow.dismiss();
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
