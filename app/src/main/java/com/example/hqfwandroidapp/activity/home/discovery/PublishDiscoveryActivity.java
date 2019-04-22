package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.Discovery;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.GlideImageLoader;
import com.example.hqfwandroidapp.utils.ImagePickerLoader;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.widget.ninegridview.NineGirdImageContainer;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.widget.ninegridview.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PublishDiscoveryActivity extends AppCompatActivity{
    static final int IMAGE_PICKER = 1;
    static final int REQUEST_CODE_CHOOSE_IMAGE = 2;
    public static final int IMAGE_ITEM_ADD = -1;
    private final int REQUEST_CODE_PICKER = 100;    // ImagePicker 的请求代码

    // 标题
    @BindView(R.id.tv_title_discovery) TextView tv_title;
    // 九宫格图片选择器
    @BindView(R.id.nineGridView) NineGridView mNineGridView;
    // QQ
    @BindView(R.id.et_contact_qq) EditText et_contact_qq;
    // phone
    @BindView(R.id.et_contact_phone) EditText et_contact_phone;
    // 文字内容
    @BindView(R.id.et_content) EditText et_content;
    // spinner
    @BindView(R.id.spinner) Spinner spinner;


    // 返回按钮，点击返回
    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();
    }

    // 提交
    @OnClick(R.id.btn_submit) void submit() {
        // 提取照片
        List<NineGridBean> nineGridBeanList = mNineGridView.getDataList();
        List<File> fileList = new ArrayList<>();    // 图片数组
        for (NineGridBean nineGridBean : nineGridBeanList) {
            String path = nineGridBean.getThumbUrl();   // 图片路径
            File file = new File(path);
            fileList.add(file);
        }

        // 检查
        if (et_content.getText().toString().length() < 10) {
            ToastUtils.showShort("至少输入10个字");
            return;
        }

        // 创建一个对象
        JsonObject discovery = new JsonObject();
        discovery.addProperty("phone", App.getUser().getPhone());
        discovery.addProperty("content", et_content.getText().toString());
        discovery.addProperty("tag", spinner.getSelectedItem().toString());
        discovery.addProperty("contactQQ", et_contact_qq.getText().toString());
        discovery.addProperty("contactPhone", et_contact_phone.getText().toString());

        // 进度框
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // 上传
        OkGo.<String>post(Urls.DiscoveryServlet)
                .isMultipart(true)  // 强制为 Multipart 格式
                .params("discovery", GsonUtils.toJson(discovery))
                .addFileParams("fileList", fileList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 广播消息
                        EventBus.getDefault().post("PublishDiscoveryActivity:success");
                        // 关闭进度框
                        progressDialog.dismiss();
                        // 返回
                        onBackPressed();
                    }
                });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_discovery);
        ButterKnife.bind(this);      // 绑定视图
        initView();                         // 初始化界面

    }


    /**
     * 初始化界面
     */
    void initView() {
        tv_title.setText("发布信息");   // 标题

        // 九宫格图片
        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new GlideImageLoader());
        //设置是否为编辑模式，默认为false
        mNineGridView.setIsEditMode(true);
        //设置最大显示数量，默认9张
        mNineGridView.setMaxNum(6);
        //设置各类点击监听
        //mNineGridView.setOnItemClickListener(this);
        mNineGridView.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha) {
                //编辑模式下，图片展示数量尚未达到最大数量时，会显示一个“+”号，点击后回调这里
                new ImagePicker()
                        .cachePath(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .pickType(ImagePickType.MULTI)
                        .displayer(new ImagePickerLoader())
                        .maxNum(cha)
                        .start(getActivity(), REQUEST_CODE_PICKER);
            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }
        });
    }


    /**
     * ImagePicker Activity 返回的结果
     * @param requestCode   请求代码
     * @param resultCode    结果代码
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null)
        {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            List<NineGridBean> resultList = new ArrayList<>();
            for (ImageBean imageBean : list)
            {
                NineGridBean nineGirdData = new NineGridBean(imageBean.getImagePath(), imageBean.getImagePath(), imageBean);
                resultList.add(nineGirdData);
            }
            mNineGridView.addDataList(resultList);
        }
    }

    private Activity getActivity() {
        return this;
    }
}
