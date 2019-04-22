package com.example.hqfwandroidapp.activity.home;


import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;

import java.util.HashMap;

import androidx.annotation.Nullable;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import me.yokeyword.fragmentation.SupportActivity;


public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }



    }








    // 使用 Mob 提供的短信验证 UI
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
                } else{
                    // TODO 处理错误的结果
                    Toast.makeText(context, "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        page.show(context);
    }
}
