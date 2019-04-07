package com.example.hqfwandroidapp.activity.home.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.login.LoginActivity;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.Urls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class PersonFragment extends SupportFragment {
    @BindView(R.id.iv_head) ImageView iv_head;  // 头像
    @BindView(R.id.tv_name) TextView tv_name;   // 名字
    @BindView(R.id.tv_phone) TextView tv_phone; // 电话
    //@BindView(R.id)


    /**
     * 注销登录
     */
    @OnClick(R.id.btn_logout) void logout() {
        App.setUser(null);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public static PersonFragment newInstance() {
        PersonFragment personFragment = new PersonFragment();
        return personFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        ButterKnife.bind(this, view);
        initView(view);


        return view;


    }


    public void initView(View view) {
        Glide.with(this).load(Urls.HeadPath() + App.getUser().getHeadURL())
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(iv_head);  // 加载头像

        tv_name.setText(App.getUser().getName());
        tv_phone.setText(App.getUser().getPhone());

    }


}
