package com.example.hqfwandroidapp.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hqfwandroidapp.R;
import com.example.hqfwandroidapp.utils.DateTime;
import com.google.gson.JsonObject;

import java.util.List;

public class NCommentCardAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    public NCommentCardAdapter(int layoutResId) {
        super(layoutResId);
    }

    /*public NCommentCardAdapter(int layoutResId, @Nullable List<JsonObject> data) {
        super(layoutResId, data);
    }*/

    @Override
    protected void convert(BaseViewHolder helper, JsonObject CommentCard) {
        helper.setText(R.id.tv_name_comment, CommentCard.get("senderName").getAsString());
        helper.setText(R.id.tv_date_time_comment, DateTime.getFormatDateTime(CommentCard.get("dateTime").getAsString()));
        helper.setText(R.id.tv_content_comment, CommentCard.get("content").getAsString());
    }
}
