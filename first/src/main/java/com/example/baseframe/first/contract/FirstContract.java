package com.example.baseframe.first.contract;

import com.example.baseframe.basemvp.BasePresenter;
import com.example.baseframe.basemvp.BaseView;
import com.example.baseframe.basemvp.Result;

import io.reactivex.Flowable;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public interface FirstContract {
    interface View extends BaseView {
        void onSomeDataGot(int code);
    }

    interface Presenter extends BasePresenter<View> {
        void getSomeData();
    }

    interface Model {
        Flowable<Result<String>> getSomeData();
    }
}
