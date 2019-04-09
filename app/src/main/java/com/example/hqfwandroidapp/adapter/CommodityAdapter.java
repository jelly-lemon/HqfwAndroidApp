package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.entity.Commodity;
import com.example.hqfwandroidapp.utils.Urls;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.ViewHolodr> {
    // 上下文
    private Context context;
    // 数据集
    private List<Commodity> commodityList;


    public CommodityAdapter(Context context, List<Commodity> commodityList) {
        this.context = context;
        this.commodityList = commodityList;
    }

    @NonNull
    @Override
    public ViewHolodr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_commodity, parent, false);
        return new ViewHolodr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolodr holder, int position) {
        Commodity commodity = commodityList.get(position);
        // 图片
        Glide.with(context).load(Urls.HOST + commodity.getImgURL()).into(holder.iv_commodity);
        // name
        holder.tv_name.setText(commodity.getName());
        // detail
        holder.tv_detail.setText(commodity.getDetail());
        // price
        holder.tv_price.setText(String.valueOf(commodity.getPrice()));
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


    static class ViewHolodr extends RecyclerView.ViewHolder{
        // 选中按钮
        @BindView(R.id.cb_check) CheckBox cb_check;
        // 图片
        @BindView(R.id.iv_commodity) ImageView iv_commodity;
        // 商品名字
        @BindView(R.id.tv_name) TextView tv_name;
        // 商品描述
        @BindView(R.id.tv_detail) TextView tv_detail;
        // 单价
        @BindView(R.id.tv_price) TextView tv_price;
        // 减少数量按钮
        @OnClick(R.id.ib_reduce) void onReduce() {
            int number = Integer.valueOf(tv_number.getText().toString());
            if (number >= 2) {
                number--;
            }

            tv_number.setText("" + number);
        }

        // 数量
        @BindView(R.id.tv_number) TextView tv_number;


        // 增加
        @OnClick(R.id.ib_add) void onAdd() {
            int number = Integer.valueOf(tv_number.getText().toString());
            number++;
            tv_number.setText("" + number);
        }

        public ViewHolodr(@NonNull View itemView) {
            super(itemView);
            // 绑定视图
            ButterKnife.bind(this, itemView);

            initView();
        }

        void initView() {

        }
    }
}
