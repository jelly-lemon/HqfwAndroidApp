package com.example.hqfwandroidapp.activity.home.discovery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
/*import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.activity.ImageGridActivity;
import com.lzy.imagepicker.activity.ImagePreviewDelActivity;
import com.lzy.imagepicker.adapter.ImagePickerAdapter;
import com.lzy.imagepicker.bean.ImageItem;*/


public class PublishDiscoveryActivity extends AppCompatActivity {
    static final int IMAGE_PICKER = 1;
    static final int REQUEST_CODE_CHOOSE_IMAGE = 2;
    public static final int IMAGE_ITEM_ADD = -1;

    @BindView(R.id.tv_title) TextView tv_title; // 标题


    //@BindView(R.id.gv_nine) NineGridView gv_nine;   // 九宫格图片显示器控件

    // 返回按钮，点击返回
    @OnClick(R.id.iv_back) void onBack() {
        onBackPressed();
    }

    // 提交
    @OnClick(R.id.btn_submit) void submit() {
        // TODO 提交
        showToast("暂未实现");
    }

    // RecyclerView
    /*@BindView(R.id.rv_image_picker) RecyclerView recyclerView;
    ImagePickerAdapter imagePickerAdapter;*/

    /*// 启动图片加载器
    @OnClick(R.id.iv_add) void selectImage() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);

    }*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_discovery);
        ButterKnife.bind(this); // 绑定视图
        initView(); // 初始化界面

    }

    void initView() {
        /*ArrayList<ImageItem> imageItemArrayList = new ArrayList<>();
        imagePickerAdapter = new ImagePickerAdapter(this, imageItemArrayList, 9);
        //imagePickerAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagePickerAdapter);*/
        tv_title.setText("发布信息");

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            Log.d("Matisse", "Uris: " + Matisse.obtainResult(data));
            Log.d("Matisse", "Paths: " + Matisse.obtainPathResult(data));
            Log.e("Matisse", "Use the selected photos with original: "+String.valueOf(Matisse.obtainOriginalState(data)));
        }*/

        /*if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {


                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //MyAdapter adapter = new MyAdapter(images);
                //gridView.setAdapter(adapter);

                // 加载到九宫格图片显示器
                ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    ImageInfo imageInfo = new ImageInfo();

                    //String path = "file://" + new File(images.get(i).path).getPath();
                    String path = images.get(i).path;
                    imageInfo.setThumbnailUrl(path);
                    imageInfo.setBigImageUrl(path);
                    imageInfoList.add(imageInfo);
                }

                gv_nine.setAdapter(new NineGridViewClickAdapter(this, imageInfoList));
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    /*private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }*/


    // 显示气泡
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
