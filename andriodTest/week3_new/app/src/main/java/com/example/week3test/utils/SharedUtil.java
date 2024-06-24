package com.example.week3test.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    private static SharedUtil mUtil;
    private SharedPreferences preferences;

    // 单例
    public static SharedUtil getInstance(Context cxt){
        if (mUtil == null){
            mUtil = new SharedUtil();
            mUtil.preferences = cxt.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }
        return mUtil;
    }

    // 快捷方法读和写
    public void writeBoolean(String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }




}
