package com.example.baseframe.business.api;

import com.example.BaseFrame.common.AppLog;
import com.example.BaseFrame.common.AppUtil;
import com.example.BaseFrame.common.Config;
import com.example.BaseFrame.common.MyApp;
import com.example.BaseFrame.common.ToastUtil;
import com.example.BaseFrame.common.UiHandler;
import com.example.baseframe.business.api.fail.ResponseException;
import com.google.gson.JsonParseException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 * @desc
 */
public class NormalSubscriber<T> extends DisposableSubscriber<T> {

    public static final int STATUS_TOKEN_INVALID = 403;
    public static final int STATUS_USER_FROZEN = 410;
    public static final int JSON_PARSE_FAILED = -1000;
    public static final int UNKNOWN = -1001;

    private boolean useCacheIfOffline;

    public NormalSubscriber() {
        this(true);
    }

    public NormalSubscriber(boolean useCacheIfNeed) {
        useCacheIfOffline = useCacheIfNeed;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!AppUtil.isNetWorkAvailable(MyApp.getAppContext())) {
//            final String netError = MyApp.getAppContext().getString(R.string.business_net_error);
            final String netError = "net wrong";
            UiHandler.post(() -> {
                ToastUtil.showToastShort(netError);
            });
            if (!isDisposed() && !useCacheIfOffline) {
                dispose();
                onError(Config.API_NET_LOST, netError);
            }
        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        AppLog.e("onError:" + e.toString());
        if (e instanceof ResponseException) {
            int errCode = ((ResponseException) e).getCode();
            if (errCode == STATUS_USER_FROZEN) {
                triggerFrozen(e.getMessage());
            } else if (((ResponseException) e).getCode() == STATUS_TOKEN_INVALID) {
//                TODO
//                LocalAccountManager.getInstance().clearLoginUser();
//                ToastUtil.showToastShort("请重新登录");
                triggerLogin();
            }
            onError(((ResponseException) e).getCode(), e.getMessage());
        } else if (e instanceof HttpException) {
            onError(((HttpException) e).code(), "");
        } else if (e instanceof JsonParseException) {
            onError(JSON_PARSE_FAILED, e.getMessage());
        } else {
//            onError(UNKNOWN, CalligraphyApp.getAPPContext().getString(R.string.business_unknown));
            onError(UNKNOWN, "");
        }
    }

    private void triggerLogin() {

    }

    private void triggerFrozen(String msg) {

    }

    public void onError(int code, String msg) {
        UiHandler.postDelayed(() -> {
            ToastUtil.showToastShort(msg);
        }, 10);
    }

    @Override
    public void onComplete() {

    }
}
