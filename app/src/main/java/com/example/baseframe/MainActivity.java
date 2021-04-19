package com.example.baseframe;

import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.baseframe.basemvp.BaseActivity;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int contentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        findViewById(R.id.tv_test).setOnClickListener(v -> toFirst());
    }

    private void toFirst() {
        ARouter.getInstance().build("/first/main")
                .navigation(this);
    }
}