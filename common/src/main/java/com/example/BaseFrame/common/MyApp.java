package com.example.BaseFrame.common;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class MyApp {
    private static Context mAppContext;

    /**
     * 在Application中调用，获取context
     */
    public static void initContext(Context context) {
        mAppContext = context;
    }

    @NonNull
    public static Context getAppContext() {
        return mAppContext;
    }
}
