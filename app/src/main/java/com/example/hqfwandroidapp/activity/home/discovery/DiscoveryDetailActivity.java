package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.view.CommentCardAdapter;
import com.example.hqfwandroidapp.entity.Comment;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.DateTimeUtil;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryDetailActivity extends AppCompatActivity {
    // 返回
    @OnClick(R.id.iv_back_toolbar) void onBack() {
        onBackPressed();
    }
    // 标题
    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;
    // 消息部分
    JsonObject discoveryCard;
    // 头像
    @BindView(R.id.iv_head_discovery) ImageView iv_head_discovery;
    // 点击头像，进入个人资料详情页
    @OnClick(R.id.iv_head_discovery) void startPersonDetailActivity() {
        Intent intent = new Intent(this, PersonDetailActivity.class);
        intent.putExtra("user", GsonUtils.toJson(discoveryCard));
        startActivity(intent);
    }
    // 名字
    @BindView(R.id.tv_name_discovery) TextView tv_name_discovery;
    // 日期时间
    @BindView(R.id.tv_date_time_discovery) TextView tv_date_time_discovery;
    // 标签
    @BindView(R.id.tv_tag_discovery) TextView tv_tag_discovery;
    // 内容
    @BindView(R.id.tv_content_discovery) TextView tv_content_discovery;
    // 九宫格
    @BindView(R.id.nine_grid_view_discovery) NineGridView nine_grid_view_discovery;
    // 联系 QQ
    @BindView(R.id.tv_contact_qq) TextView tv_contact_qq;
    // 加好友
    @OnClick(R.id.tv_contact_qq) void startQQ() {
        try {
            //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + tv_contact_qq.getText().toString();//uin是发送过去的qq号码
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort("请检查是否安装QQ");
        }
    }
    // 联系电话
    @BindView(R.id.tv_contact_phone) TextView tv_contact_phone;

    // 评论部分
    // 评论列表
    @BindView(R.id.rv_comment) RecyclerView rv_comment;
    // 适配器
    CommentCardAdapter commentCardAdapter = new CommentCardAdapter(R.layout.item_comment);
    // 输入框
    @BindView(R.id.et_comment) EditText et_comment;
    // 发送评论
    @OnClick(R.id.tv_submit_comment) void submit() {
        if (et_comment.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入评论内容");
        }

        Comment comment = new Comment();
        comment.setDiscoveryID(discoveryCard.get("discoveryID").getAsInt());
        comment.setSenderPhone(App.user.getPhone());
        comment.setContent(et_comment.getText().toString());

        OkGo.<String>post(Urls.CommentServlet)
                .params("method", "insert")
                .params("comment", GsonUtils.toJson(comment))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        et_comment.setText("");
                        // 隐藏键盘
                        KeyboardUtils.hideSoftInput(getActivity());
                        // 加载新评论
                        commentCardAdapter.setEnableLoadMore(true);
                        loadMore();
                    }
                });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_detail);
        // 绑定视图
        ButterKnife.bind(this);

        initView();
    }

    void initView() {
        tv_title_toolbar.setText("详情");

        // 发现详情
        // 对象
        discoveryCard = GsonUtils.fromJson(getIntent().getStringExtra("discoveryCard"), JsonObject.class);
        // 头像
        Glide.with(this).load(Urls.HOST + discoveryCard.get("headURL").getAsString()).into(iv_head_discovery);
        // 名字
        tv_name_discovery.setText(discoveryCard.get("name").getAsString());
        // 时间
        tv_date_time_discovery.setText(DateTimeUtil.getFormatDateTime(discoveryCard.get("dateTime").getAsString()));
        // 标签
        tv_tag_discovery.setText(discoveryCard.get("tag").getAsString());
        // 内容
        tv_content_discovery.setText(discoveryCard.get("content").getAsString());
        // 九宫格图片
        List<ImageInfo> imageInfoList = new ArrayList<>();
        JsonArray imgURL = GsonUtils.fromJson(discoveryCard.get("imgURL").getAsString(), JsonArray.class);
        for (JsonElement jsonElement : imgURL) {
            String url = Urls.HOST + jsonElement.getAsString();
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(url);
            imageInfo.setOriginalImageUrl(url);
            // 添加到线性表中
            imageInfoList.add(imageInfo);
        }
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(this, imageInfoList);// 适配器
        nine_grid_view_discovery.setAdapter(nineGridViewAdapter);// 九宫格图片加载器设置配置好了的适配器
        // qq
        if (discoveryCard.get("contactQQ").getAsString().isEmpty()) {
            tv_contact_qq.setText("无");
            // 不能有点击事件
            tv_contact_qq.setClickable(false);
        } else {
            tv_contact_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            tv_contact_qq.getPaint().setAntiAlias(true);//抗锯齿
            tv_contact_qq.setText(discoveryCard.get("contactQQ").getAsString());
            tv_contact_qq.setTextColor(ColorUtils.getColor(R.color.blue));
            // 并且可以点击
            tv_contact_qq.setClickable(true);
        }
        // 电话
        tv_contact_phone.setText(discoveryCard.get("contactPhone").getAsString().isEmpty() ? "无" : discoveryCard.get("contactPhone").getAsString());


        // 评论详情
        // 评论列表
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 适配器
        commentCardAdapter.bindToRecyclerView(rv_comment);
        commentCardAdapter.setEmptyView(R.layout.view_no_comment);
        commentCardAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, rv_comment);

        // 首先获取几条数据
        loadMore();
    }



    void loadMore() {
        OkGo.<String>get(Urls.CommentCardServlet)
                .params("method", "loadMore")
                .params("discoveryID", discoveryCard.get("discoveryID").getAsString())
                .params("start", commentCardAdapter.getData().size())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<JsonObject> commentCardList = GsonUtils.fromJson(response.body(), GsonUtils.getListType(JsonObject.class));

                        if (commentCardList.isEmpty()) {
                            commentCardAdapter.loadMoreEnd();
                        } else {
                            commentCardAdapter.addData(commentCardList);
                            commentCardAdapter.loadMoreComplete();
                        }
                    }
                });
    }


    Activity getActivity() {
        return this;
    }

}
