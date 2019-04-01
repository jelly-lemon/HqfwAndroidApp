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
import com.example.hqfwandroidapp.utils.GlideImageLoader;
import com.lwkandroid.widget.ninegridview.NineGirdImageContainer;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.widget.ninegridview.NineGridView;
/*import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.activity.ImageGridActivity;
import com.lzy.imagepicker.activity.ImagePreviewDelActivity;
import com.lzy.imagepicker.adapter.ImagePickerAdapter;
import com.lzy.imagepicker.bean.ImageItem;*/


public class PublishDiscoveryActivity extends AppCompatActivity {
    static final int IMAGE_PICKER = 1;
    static final int REQUEST_CODE_CHOOSE_IMAGE = 2;
    public static final int IMAGE_ITEM_ADD = -1;

    @BindView(R.id.tv_title)
    TextView tv_title; // 标题
    @BindView(R.id.nineGridView)
    NineGridView mNineGridView; // 九宫格图片选择器


    //@BindView(R.id.gv_nine) NineGridView gv_nine;   // 九宫格图片显示器控件

    // 返回按钮，点击返回
    @OnClick(R.id.iv_back)
    void onBack() {
        onBackPressed();
    }

    // 提交
    @OnClick(R.id.btn_submit)
    void submit() {
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


        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new GlideImageLoader());
//设置显示列数，默认3列
        mNineGridView.setColumnCount(4);
//设置是否为编辑模式，默认为false
        mNineGridView.setIsEditMode(true);
//设置单张图片显示时的尺寸，默认100dp
        mNineGridView.setSingleImageSize(150);
//设置单张图片显示时的宽高比，默认1.0f
        mNineGridView.setSingleImageRatio(0.8f);
//设置最大显示数量，默认9张
        mNineGridView.setMaxNum(16);
//设置图片显示间隔大小，默认3dp
        mNineGridView.setSpcaeSize(4);
//设置删除图片
        mNineGridView.setIcDeleteResId(R.drawable.ic_delete);
//设置删除图片与父视图的大小比例，默认0.35f
        mNineGridView.setRatioOfDeleteIcon(0.4f);
//设置“+”号的图片
        mNineGridView.setIcAddMoreResId(R.drawable.ic_ninegrid_addmore);
//设置各类点击监听
        mNineGridView.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha) {
                //编辑模式下，图片展示数量尚未达到最大数量时，会显示一个“+”号，点击后回调这里
            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }

            /*@Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                //点击图片的监听
            }*/

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
                //编辑模式下，某张图片被删除后回调这里
            }
        });

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
