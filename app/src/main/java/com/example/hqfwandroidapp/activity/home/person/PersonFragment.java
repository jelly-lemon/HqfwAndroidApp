package com.example.hqfwandroidapp.activity.home.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.login.LoginActivity;
import com.example.hqfwandroidapp.entity.User;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.ImagePickerLoader;
import com.example.hqfwandroidapp.utils.SaveSharedPreference;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.JsonObject;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class PersonFragment extends SupportFragment {
    private int REQUEST_CODE_PICKER = 100;

    @BindView(R.id.tv_title_toolbar) TextView tv_title_toolbar;

    // role
    @BindView(R.id.tv_role_user) TextView tv_role_user;
    // 头像
    @BindView(R.id.iv_head_user) ImageView iv_head_user;


    @OnClick(R.id.iv_setting_toolbar) void startSettingActivity() {
        ToastUtils.showShort("暂未实现");
    }
    // 名字
    @BindView(R.id.tv_name_user) TextView tv_name_user;
    @OnClick(R.id.tv_name_user) void startChangeNameActivity() {
        Intent intent = new Intent(getContext(), ChangeNameActivity.class);
        startActivity(intent);
    }
    // 电话
    @BindView(R.id.tv_phone_user) TextView tv_phone_user;
    @OnClick(R.id.cl_phone_user) void startChangePhoneActivity() {
        Intent intent = new Intent(getContext(), ChangePhoneActivity.class);
        startActivity(intent);
    }
    // studentID
    @BindView(R.id.tv_studentID_user)TextView tv_studentID_user;
    @OnClick(R.id.cl_studentID_user) void startChangdeStudentIDActivity() {
        Intent intent = new Intent(getContext(), ChangeStudentIDActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.tv_address_user) TextView tv_address_user;
    @OnClick(R.id.cl_address_user) void startChangeAddressActivity() {
        Intent intent = new Intent(getContext(), ChangeAddressActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.tv_gender_user) TextView tv_gender_user;
    @OnClick(R.id.cl_gender_user) void startChangeGender() {
        Intent intent = new Intent(getContext(), ChangeGenderActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_head_user) void startImagePicker() {
        new ImagePicker()
                .cachePath(Environment.getExternalStorageDirectory().getAbsolutePath())
                .pickType(ImagePickType.SINGLE)
                .displayer(new ImagePickerLoader())
                .start(this, REQUEST_CODE_PICKER);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null)
        {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            ImageBean imageBean = list.get(0);
            File headImg = new File(imageBean.getImagePath());




            OkGo.<String>post(Urls.UsersServlet)
                    .params("phone", App.user.getPhone())
                    .params("headImg", headImg)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            refresh();
                        }
                    });
        }
    }



    /**
     * 注销登录
     */
    @OnClick(R.id.btn_logout_person) void logout() {
        // 销毁 user 对象
        App.user = null;
        // 密码置为空
        SaveSharedPreference.setPassword(getContext(), "");
        // 转到登录页面
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

        // 绑定视图
        ButterKnife.bind(this, view);
        // 初始化视图
        initView();


        return view;


    }


    public void initView() {
        tv_title_toolbar.setText("个人");


        Glide.with(this).load(Urls.HOST + App.user.getHeadURL())
                .placeholder(R.drawable.ic_default_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(iv_head_user);  // 加载头像

        // name
        tv_name_user.setText(App.user.getName());
        // role
        tv_role_user.setText(App.user.getRole());

        tv_gender_user.setText(App.user.getGender());
        // phone
        tv_phone_user.setText(App.user.getPhone());
        // studentID
        tv_studentID_user.setText(App.user.getStudentID());

        tv_address_user.setText(App.user.getBuilding() + "栋" + App.user.getRoomNumber() + "房间");
    }

    private void refresh() {
        OkGo.<String>get(Urls.UsersServlet)
                .params("method", "get")
                .params("phone", App.user.getPhone())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JsonObject result = GsonUtils.fromJson(response.body(), JsonObject.class);

                        User user = new User();
                        user.setName(result.get("name").getAsString());
                        user.setBuilding(result.get("building").getAsString());
                        user.setGender(result.get("gender").getAsString());
                        user.setHeadURL(result.get("headURL").getAsString());
                        user.setPassword(result.get("password").getAsString());
                        user.setPhone(result.get("phone").getAsString());
                        user.setRole(result.get("role").getAsString());
                        user.setRoomNumber(result.get("roomNumber").getAsString());
                        user.setStudentID(result.get("studentID").getAsString());

                        App.user = user;

                        initView();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册广播
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取关广播
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String msg) {
        if (msg.equals("Change:success")) {

            refresh();
        }
    }


}
