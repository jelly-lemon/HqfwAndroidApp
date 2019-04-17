package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.CommentCardAdapter;
import com.example.hqfwandroidapp.entity.Comment;
import com.example.hqfwandroidapp.entity.CommentCard;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.SpacesItemDecoration;
import com.example.hqfwandroidapp.utils.Urls;
import com.example.ninegridview.adapter.NineGridViewAdapter;
import com.example.ninegridview.entity.ImageInfo;
import com.example.ninegridview.ui.NineGridView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryDetailActivity extends AppCompatActivity {
    // 返回
    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();
    }
    // 标题
    @BindView(R.id.tv_title) TextView tv_title;

    // 消息部分
    DiscoveryCard discoveryCard;
    // 头像
    @BindView(R.id.iv_head_discovery) ImageView iv_head_discovery;
    // 点击头像，进入个人资料详情页
    @OnClick(R.id.iv_head_discovery) void startPersonDetailActivity() {
        Intent intent = new Intent(this, PersonDetailActivity.class);
        intent.putExtra("user", GsonUtils.toJson(discoveryCard.getUser()));
        startActivity(intent);
    }
    // 名字
    @BindView(R.id.tv_name_discovery) TextView tv_name_discovery;
    // 角色
    @BindView(R.id.tv_role_discovery) TextView tv_role_discovery;
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
    @BindView(R.id.smart_refresh_layout_comment) SmartRefreshLayout smart_refresh_layout_comment;
    @BindView(R.id.rv_comment_card) RecyclerView rv_comment_card;
    CommentCardAdapter commentCardAdapter;
    // 输入框
    @BindView(R.id.et_comment) EditText et_comment;
    // 发送评论
    @OnClick(R.id.btn_submit_comment) void submit() {
        if (et_comment.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入评论内容");
        }

        Comment comment = new Comment();
        comment.setDiscoveryID(discoveryCard.getDiscovery().getDiscoveryID());
        comment.setSenderPhone(App.getUser().getPhone());
        comment.setContent(et_comment.getText().toString());

        OkGo.<String>post(Urls.CommentServlet)
                .params("method", "insert")
                .params("comment", GsonUtils.toJson(comment))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        et_comment.setText("");
                        ToastUtils.showShort("评论成功");
                        KeyboardUtils.hideSoftInput(getActivity());
                        smart_refresh_layout_comment.autoLoadMore();
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
        tv_title.setText("详情");

        discoveryCard = new Gson().fromJson(getIntent().getStringExtra("discoveryCard"), DiscoveryCard.class);

        // 头像
        Glide.with(this).load(Urls.HOST + discoveryCard.getUser().getHeadURL()).into(iv_head_discovery);
        // 名字
        tv_name_discovery.setText(discoveryCard.getUser().getName());
        // 角色
        tv_role_discovery.setText(discoveryCard.getUser().getRole());
        // 时间
        tv_date_time_discovery.setText(discoveryCard.getDiscovery().getDateTime().toString());
        // 标签
        tv_tag_discovery.setText(discoveryCard.getDiscovery().getTag());
        // 内容
        tv_content_discovery.setText(discoveryCard.getDiscovery().getContent());
        // 九宫格图片
        List<ImageInfo> imageInfoList = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(discoveryCard.getDiscovery().getImgURL(), JsonArray.class);
        for (JsonElement jsonElement : jsonArray) {
            String url = Urls.getHOST() + jsonElement.getAsString();
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setThumbnailUrl(url);
            imageInfo.setOriginalImageUrl(url);
            // 添加到线性表中
            imageInfoList.add(imageInfo);
        }
        // 适配器
        NineGridViewAdapter nineGridViewAdapter = new NineGridViewAdapter(this, imageInfoList);
        // 九宫格图片加载器设置配置好了的适配器
        nine_grid_view_discovery.setAdapter(nineGridViewAdapter);
        // qq
        tv_contact_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_contact_qq.getPaint().setAntiAlias(true);//抗锯齿
        tv_contact_qq.setText(discoveryCard.getDiscovery().getContactQQ().isEmpty() ? "无" : discoveryCard.getDiscovery().getContactQQ());
        // 电话
        tv_contact_phone.setText(discoveryCard.getDiscovery().getContactPhone().isEmpty() ? "无" : discoveryCard.getDiscovery().getContactPhone());




        // 评论区
        commentCardAdapter = new CommentCardAdapter(this, new ArrayList<>());
        // 回收视图
        // 布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_comment_card.setLayoutManager(layoutManager);
        // 间距
        //SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(24);
        //rv_comment_card.addItemDecoration(spacesItemDecoration);
        rv_comment_card.setAdapter(commentCardAdapter);
        // 关闭滑动
        rv_comment_card.setNestedScrollingEnabled(false);


        smart_refresh_layout_comment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreComment();
            }
        });
        // 自动加载更多
        smart_refresh_layout_comment.autoLoadMore();



    }

    /**
     * 获取几条评论
     */
    void refreshComment() {
        OkGo.<String>get(Urls.CommentCardServlet)
                .params("method", "refresh")
                .params("discoveryID", discoveryCard.getDiscovery().getDiscoveryID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<List<CommentCard>>(){}.getType();
                        List<CommentCard> commentCardList = new Gson().fromJson(response.body(), type);

                        commentCardAdapter.addList(commentCardList);
                    }
                });
    }


    void loadMoreComment() {
        OkGo.<String>get(Urls.CommentCardServlet)
                .params("method", "loadMore")
                .params("discoveryID", discoveryCard.getDiscovery().getDiscoveryID())
                .params("start", commentCardAdapter.getItemCount())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<List<CommentCard>>(){}.getType();
                        List<CommentCard> commentCardList = new Gson().fromJson(response.body(), type);

                        if (commentCardList.isEmpty()) {
                            //smart_refresh_layout_comment.finishLoadMoreWithNoMoreData();
                            ToastUtils.showShort("没有更多");
                        } else {
                            commentCardAdapter.addList(commentCardList);
                        }
                        smart_refresh_layout_comment.finishLoadMore();
                    }
                });
    }


    Activity getActivity() {
        return this;
    }

}
