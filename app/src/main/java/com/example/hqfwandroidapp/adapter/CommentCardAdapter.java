package com.example.hqfwandroidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.activity.home.discovery.PersonDetailActivity;
import com.example.hqfwandroidapp.entity.CommentCard;
import com.example.hqfwandroidapp.utils.Urls;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.ViewHolder>{
    // 上下文
    private Context context;
    // 数据集
    private List<CommentCard> commentCardList;


    public CommentCardAdapter(Context context, List<CommentCard> commentCardList) {
        this.context = context;
        this.commentCardList = commentCardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载视图
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        // 返回 ViewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 一个对象
        CommentCard commentCard = commentCardList.get(position);
        // 头像
       // Glide.with(context).load(Urls.HOST + commentCard.getUser().getHeadURL()).into(holder.iv_head_comment);
        // 名字
        holder.tv_name_comment.setText(commentCard.getUser().getName());
        // 点击名字进入个人资料详情页
        holder.tv_name_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonDetailActivity.class);
                intent.putExtra("user", GsonUtils.toJson(commentCard.getUser()));
                context.startActivity(intent);
            }
        });
        // 时间
        holder.tv_date_time_comment.setText(commentCard.getComment().getDateTime().toString());
        // 角色
        //holder.tv_role_comment.setText(commentCard.getUser().getRole());
        // 内容
        holder.tv_content_comment.setText(commentCard.getComment().getContent());
    }

    @Override
    public int getItemCount() {
        return commentCardList.size();
    }



    public void setList(List<CommentCard> commentCardList) {
        this.commentCardList = commentCardList;
        notifyDataSetChanged();
    }

    public void addList(List<CommentCard> commentCardList) {
        this.commentCardList.addAll(commentCardList);
        notifyDataSetChanged();
    }

    public void addData(CommentCard commentCard) {
        commentCardList.add(commentCard);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // 头像
        //@BindView(R.id.iv_head_comment) ImageView iv_head_comment;
        // name
        @BindView(R.id.tv_name_comment) TextView tv_name_comment;

        // dateTime
        @BindView(R.id.tv_date_time_comment) TextView tv_date_time_comment;
        // 角色
        //@BindView(R.id.tv_role_comment) TextView tv_role_comment;
        // content
        @BindView(R.id.tv_content_comment) TextView tv_content_comment;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 绑定 view
            ButterKnife.bind(this, itemView);
        }
    }
}
