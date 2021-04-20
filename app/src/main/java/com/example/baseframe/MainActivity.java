package com.example.baseframe;

import android.widget.Button;

import com.example.BaseFrame.common.Constants;
import com.example.baseframe.basemvp.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class MainActivity extends BaseActivity {

    @BindView(R2.id.btn_to_first)
    Button mBtnToFirst;
    @BindView(R2.id.btn_to_kotlin_test)
    Button mBtnToKotlin1;

    @Override
    protected int contentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        //ButterKnife在Activity中使用时，在setContentView()之后调用bind方法
        //并且在Activity中bind时，不需要解绑，而在fragment中需要解绑
        //因为不确定是否每个页面都是要ButterKnife，没有放到Bases中
        ButterKnife.bind(this);
        mBtnToFirst.setOnClickListener(v -> toFirst());
        mBtnToKotlin1.setOnClickListener(v -> toKotlin1());
    }

    private void toFirst() {
        navigate(Constants.First.Route.FIRST).navigation(this);
    }

    private void toKotlin1() {
        navigate(Constants.Kotlin1.Route.KOTLIN1)
                .withString(Constants.Kotlin1.KEY_TO_KOTLIN, "参数123")
                .navigation(this);
    }
}