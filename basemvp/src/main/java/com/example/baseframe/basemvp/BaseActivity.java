package com.example.baseframe.basemvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.BaseFrame.common.Constants;
import com.example.BaseFrame.common.ToastUtil;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = contentLayout();
        if (layout > 0) {
            setContentView(layout);
        }
        init();
    }

    /**
     * layout的id
     */
    protected abstract int contentLayout();

    /**
     * 初始化相关操作
     */
    protected void init() {
    }

    /**
     * ARouter跳转
     */
    protected Postcard navigate(String path) {
        return ARouter.getInstance().build(path);
    }

    /**
     * toast
     */
    protected void toast(String text) {
        ToastUtil.showToastShort(text);
    }
}
