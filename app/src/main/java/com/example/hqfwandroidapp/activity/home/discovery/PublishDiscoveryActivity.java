package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.Discovery;
import com.example.hqfwandroidapp.utils.App;
import com.example.hqfwandroidapp.utils.GlideImageLoader;
import com.example.hqfwandroidapp.utils.ImagePickerLoader;
import com.example.hqfwandroidapp.utils.Urls;
import com.google.gson.Gson;
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


public class PublishDiscoveryActivity extends AppCompatActivity implements NineGridView.onItemClickListener{
    static final int IMAGE_PICKER = 1;
    static final int REQUEST_CODE_CHOOSE_IMAGE = 2;
    public static final int IMAGE_ITEM_ADD = -1;
    private final int REQUEST_CODE_PICKER = 100;    // ImagePicker 的请求代码

    // 标题
    @BindView(R.id.tv_title) TextView tv_title;
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
        List<NineGridBean> nineGridBeanList = mNineGridView.getDataList();
        List<File> fileList = new ArrayList<>();    // 图片数组
        for (NineGridBean nineGridBean : nineGridBeanList) {
            String path = nineGridBean.getThumbUrl();   // 图片路径
            File file = new File(path);
            fileList.add(file);
        }


        // 创建一个对象
        Discovery discovery = new Discovery();
        discovery.setPhone(App.getUser().getPhone());
        discovery.setContent(et_content.getText().toString());
        discovery.setTag(spinner.getSelectedItem().toString());
        discovery.setContactQQ(et_contact_qq.getText().toString());
        discovery.setContactPhone(et_contact_phone.getText().toString());

        // 上传
        OkGo.<String>post(Urls.PublishDiscovery())
                .isMultipart(true)  // 强制为 Multipart 格式
                .params("method", "insert")
                .params("discovery", new Gson().toJson(discovery))
                .addFileParams("fileList", fileList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 广播消息
                        EventBus.getDefault().post("PublishDiscoveryActivity:success");
                    }
                });

        // 返回
        onBackPressed();

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


        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new GlideImageLoader());
        //设置显示列数，默认3列
        //mNineGridView.setColumnCount(4);
        //设置是否为编辑模式，默认为false
        mNineGridView.setIsEditMode(true);
        //设置单张图片显示时的尺寸，默认100dp
        //mNineGridView.setSingleImageSize(150);
        //设置单张图片显示时的宽高比，默认1.0f
        //mNineGridView.setSingleImageRatio(0.8f);
        //设置最大显示数量，默认9张
        mNineGridView.setMaxNum(6);
        //设置图片显示间隔大小，默认3dp
        //mNineGridView.setSpcaeSize(4);
        //设置删除图片
        //mNineGridView.setIcDeleteResId(R.drawable.ic_delete);
        //设置删除图片与父视图的大小比例，默认0.35f
        //mNineGridView.setRatioOfDeleteIcon(0.4f);
        ////设置“+”号的图片
        //mNineGridView.setIcAddMoreResId(R.drawable.ic_ninegrid_addmore);
        //设置各类点击监听
        mNineGridView.setOnItemClickListener(this);


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



    // 显示气泡
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击 + 号按钮
     * @param cha 差值，还能够选择的图片
     */
    @Override
    public void onNineGirdAddMoreClick(int cha) {
        //编辑模式下，图片展示数量尚未达到最大数量时，会显示一个“+”号，点击后回调这里
        new ImagePicker()
                .cachePath(Environment.getExternalStorageDirectory().getAbsolutePath())
                .pickType(ImagePickType.MULTI)
                .displayer(new ImagePickerLoader())
                .maxNum(cha)
                .start(this, REQUEST_CODE_PICKER);
    }

    /**
     * 点击九宫格某张图片时
     * @param position
     * @param gridBean
     * @param imageContainer
     */
    @Override
    public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

    }

    /**
     * 删除某张图片后
     * @param position
     * @param gridBean
     * @param imageContainer
     */
    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

    }
}
