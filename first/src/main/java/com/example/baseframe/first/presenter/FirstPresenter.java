package com.example.baseframe.first.presenter;

import com.example.BaseFrame.common.Config;
import com.example.baseframe.basemvp.BaseView;
import com.example.baseframe.basemvp.Result;
import com.example.baseframe.business.RxPresenter;
import com.example.baseframe.business.api.ApiProxy;
import com.example.baseframe.business.api.NormalSubscriber;
import com.example.baseframe.first.api.FirstApi;
import com.example.baseframe.first.contract.FirstContract;

import java.util.concurrent.Flow;

import io.reactivex.Flowable;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class FirstPresenter extends RxPresenter<FirstContract.View> implements FirstContract.Presenter {
    private FirstContract.Model mModel;

    @Override
    public void attachView(FirstContract.View view) {
        mModel = new LocalModel();
    }

    @Override
    public void getSomeData() {
        observe(mModel.getSomeData())
                .safeSubscribe(new NormalSubscriber<Result<String>>() {
                    @Override
                    public void onNext(Result<String> result) {
                        mView.onSomeDataGot(result.getCode());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                        mView.onSomeDataGot(code);
                    }
                });
    }

    private static class LocalModel implements FirstContract.Model {

        @Override
        public Flowable<Result<String>> getSomeData() {
            Result<String> result = new Result<>();
            result.setData("model data");
            result.setCode(Config.API_SUCCEED_CODE);
            return Flowable.just(result);
        }
    }

    private static class ServerModel implements FirstContract.Model {

        @Override
        public Flowable<Result<String>> getSomeData() {
            return ApiProxy.getApi(FirstApi.class)
                    .getSomeData();
        }
    }
}
