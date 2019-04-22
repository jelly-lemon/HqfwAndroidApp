package com.example.hqfwandroidapp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        // 获取该 view 在 RecyclerView 中的位置，如果是第一个，上边要留空
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
    }




}
