package com.example.baseframe.basemvp;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.BaseFrame.common.MyApp;
import com.hjq.toast.ToastUtils;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.initContext(this);
        //初始化ARouter
        initARouter();
        //初始化toast框架
        ToastUtils.init(this);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(MyApplication.this);
    }
}
