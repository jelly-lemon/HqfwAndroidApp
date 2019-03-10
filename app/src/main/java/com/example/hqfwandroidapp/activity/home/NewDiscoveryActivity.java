package com.example.hqfwandroidapp.activity.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.Urls;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class NewDiscoveryActivity extends AppCompatActivity {
    static final int IMAGE_PICKER = 1;
    // 返回按钮
    @OnClick(R.id.iv_back) void back() {
        onBackPressed();
    }

    // 图片加载器
    @OnClick(R.id.iv_add) void selectImage() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        //startActivityForResult(intent, IMAGE_PICKER);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    @BindView(R.id.gv_nine) NineGridView gv_nine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discovery);
        ButterKnife.bind(this); // 绑定视图


    }

    void initView() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
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
        }
    }
}
