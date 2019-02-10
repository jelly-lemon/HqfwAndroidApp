package com.example.hqfwandroidapp.fragment;


import android.os.Bundle;

import com.example.hqfwandroidapp.R;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportActivity;


public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }


}
