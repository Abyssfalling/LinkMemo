package com.example.linkmemo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.linkmemo.data.bean.UserInfo;
import com.example.linkmemo.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

public class App extends Application {

    /**
     * 用来保存用户状态
     */
    public static UserInfo userInfo;

    public Context appContext() {
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String userStr = SharedPreferencesUtil.get(this, "user", "").toString();
        if (!TextUtils.isEmpty(userStr)) {
            userInfo = new Gson().fromJson(userStr, UserInfo.class);
        }
    }
}
