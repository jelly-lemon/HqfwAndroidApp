package com.example.hqfwandroidapp.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.DateTime;
import com.google.gson.JsonObject;


public class CommentCardAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    public CommentCardAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, JsonObject CommentCard) {
        helper.setText(R.id.tv_name_comment, CommentCard.get("senderName").getAsString());
        helper.setText(R.id.tv_date_time_comment, DateTime.getFormatDateTime(CommentCard.get("dateTime").getAsString()));
        helper.setText(R.id.tv_content_comment, CommentCard.get("content").getAsString());
    }
}
