package com.example.baseframe.basemvp;

import androidx.annotation.NonNull;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    protected T mPresenter;

    /**
     * 将View传给Presenter
     * @param presenter p
     */
    protected abstract void attachViewToPresenter(T presenter);

    public BaseMvpActivity() {
        mPresenter = PresenterUtils.getBasePresenter(this.getClass());
        if (mPresenter == null) {
            throw new IllegalArgumentException("MVP activity must has presenter");
        }
    }

    @Override
    protected void init() {
        super.init();
        attachViewToPresenter(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
