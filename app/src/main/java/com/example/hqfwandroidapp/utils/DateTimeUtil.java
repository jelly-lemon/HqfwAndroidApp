package com.example.hqfwandroidapp.utils;

import com.blankj.utilcode.util.TimeUtils;
import com.example.hqfwandroidapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String getFormatDateTime(String dateTime) {
        // 时间
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
            return new SimpleDateFormat("MM-dd HH:mm").format(date);
        } catch(Exception px) {
            px.printStackTrace();
        }
        return null;
    }
}
