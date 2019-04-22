package com.example.hqfwandroidapp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    public static final int BiliBili = 0;
    private int style;
    private int space = 24;


    public ItemDecoration(int style) {
        this.style = style;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch (style) {
            case BiliBili: {
                outRect.bottom = space;
                break;
            }
        }
    }
}
