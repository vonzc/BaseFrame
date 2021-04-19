package com.example.baseframe;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.BaseFrame.common.MyApp;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.initContext(this);
        initARouter(true);
    }

    private void initARouter(boolean isMainProcess) {
        if (!isMainProcess) {
            return;
        }
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
