package com.example.baseframe.first;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.BaseFrame.common.Config;
import com.example.BaseFrame.common.ToastUtil;
import com.example.baseframe.basemvp.BaseMvpActivity;
import com.example.baseframe.first.contract.FirstContract;
import com.example.baseframe.first.presenter.FirstPresenter;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
@Route(path = "/first/main")
public class FirstActivity extends BaseMvpActivity<FirstPresenter> implements FirstContract.View {

    @Override
    protected int contentLayout() {
        return R.layout.activity_first;
    }

    @Override
    protected void attachViewToPresenter(FirstPresenter presenter) {
        presenter.attachView(this);
    }

    @Override
    protected void init() {
        super.init();
        mPresenter.getSomeData();
    }

    @Override
    public void onSomeDataGot(int code) {
        if (code != Config.API_SUCCEED_CODE) {
            return;
        }
        ToastUtil.showToastShort("请求成功！");
    }
}