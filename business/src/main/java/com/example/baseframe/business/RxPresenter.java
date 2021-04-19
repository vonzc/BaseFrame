package com.example.baseframe.business;

import com.example.baseframe.basemvp.BasePresenter;
import com.example.baseframe.basemvp.BaseView;
import com.example.baseframe.business.api.fail.GlobalErrorUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected String TAG = this.getClass().getSimpleName();

    private WeakReference<T> mViewReference;

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mViewReference = new WeakReference<>(view);
        // like the null check, if (mView != null) {}
        Class<?> clz = view.getClass();
        mView = (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(),
                (proxy, method, args) -> {
                    if (mViewReference == null || mViewReference.get() == null) {
                        return null;
                    } else {
                        return method.invoke(mViewReference.get(), args);
                    }
                });
        registerEvent();
    }

    protected void registerEvent() {
    }

    @Override
    public void detachView() {
        this.mViewReference = null;
    }

    protected <T> Flowable<T> observe(Flowable<T> observable) {
        return observable
                .compose(GlobalErrorUtil.handleGlobalError())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
