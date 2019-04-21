package com.example.hqfwandroidapp.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<JsonObject, BaseViewHolder> {
    public TestAdapter(int layoutResId, @Nullable List<JsonObject> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JsonObject item) {

    }
}
