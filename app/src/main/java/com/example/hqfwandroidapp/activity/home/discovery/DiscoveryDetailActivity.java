package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.adapter.CommentCardAdapter;
import com.example.hqfwandroidapp.entity.CommentCard;
import com.example.hqfwandroidapp.entity.Discovery;
import com.example.hqfwandroidapp.entity.DiscoveryCard;
import com.example.hqfwandroidapp.entity.User;
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
    // 头像
    @BindView(R.id.iv_head_discovery) ImageView iv_head_discovery;
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

    // 评论部分
    // 输入框
    @BindView(R.id.et_comment) EditText et_comment;
    // 发送评论
    @OnClick(R.id.btn_submit_comment) void submit() {

    }

    @BindView(R.id.rv_comment_card) RecyclerView rv_comment_card;
    CommentCardAdapter commentCardAdapter;


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

        DiscoveryCard discoveryCard = new Gson().fromJson(getIntent().getStringExtra("discoveryCard"), DiscoveryCard.class);

        // 头像
        Glide.with(this).load(Urls.HOST + discoveryCard.getUser().getHeadURL()).into(iv_head_discovery);
        // 名字
        tv_name_discovery.setText(discoveryCard.getUser().getName());

        tv_role_discovery.setText(discoveryCard.getUser().getRole());

        tv_date_time_discovery.setText(discoveryCard.getDiscovery().getDateTime().toString());

        tv_tag_discovery.setText(discoveryCard.getDiscovery().getTag());

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
        OkGo.<String>get(Urls.CommentCardServlet)
                .params("method", "refresh")
                .params("discoveryID", discoveryCard.getDiscovery().getDiscoveryID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<List<CommentCard>>(){}.getType();
                        commentCardAdapter.setList(new Gson().fromJson(response.body(), type));
                    }
                });
    }

}
