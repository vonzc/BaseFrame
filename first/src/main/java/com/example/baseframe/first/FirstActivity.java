package com.example.baseframe.first;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.BaseFrame.common.AppLog;
import com.example.BaseFrame.common.Config;
import com.example.BaseFrame.common.Constants;
import com.example.baseframe.basemvp.BaseMvpActivity;
import com.example.baseframe.first.contract.FirstContract;
import com.example.baseframe.first.presenter.FirstPresenter;

import butterknife.ButterKnife;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
@Route(path = Constants.First.Route.FIRST)
public class FirstActivity extends BaseMvpActivity<FirstPresenter> implements FirstContract.View {

    @Override
    protected int contentLayout() {
        return R.layout.activity_first;
    }

    @Override
    protected void attachViewToPresenter() {
        mPresenter.attachView(this);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        mPresenter.getSomeData();
    }

    @Override
    public void onSomeDataGot(int code) {
        if (code != Config.API_SUCCEED_CODE) {
            return;
        }
        AppLog.d("请求成功！");
        setResultToFirst();
    }

    private void setResultToFirst() {
        Intent intent = new Intent();
        intent.putExtra(Constants.First.EXTRA_TEXT, "123456");
        setResult(RESULT_OK, intent);
    }
}