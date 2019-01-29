package com.example.hqfwandroidapp.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.hqfwandroidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_discovery:
                    mTextMessage.setText(R.string.title_discovery);
                    return true;
                case R.id.navigation_service:
                    mTextMessage.setText(R.string.title_service);
                    return true;
                case R.id.navigation_person:
                    mTextMessage.setText(R.string.title_person);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initUI();
    }


    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
