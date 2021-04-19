package com.example.baseframe.business.api.fail;

import com.example.BaseFrame.common.AppLog;
import com.example.baseframe.basemvp.Result;
import com.example.baseframe.business.api.retry.RetryConfig;

import java.net.ConnectException;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class GlobalErrorUtil {

    private static final int SUCCESS_CODE = 0;

    public static <T> GlobalErrorTransformer<T> handleGlobalError() {
        return new GlobalErrorTransformer<T> (
                (next) -> {
                    if (next instanceof Result) {
                        Result result = (Result) next;
                        if (result.getCode() != SUCCESS_CODE) {
                            return Observable.error(
                                    new ResponseException(result.getMessage(), result.getCode())
                            );
                        }
                    }
                    return Observable.just(next);
                },
                (error) -> {
                    if (error instanceof ConnectException) {
                        return Observable.error(new ConnectFailedException());
                    }
                    if (error instanceof NullPointerException) {
                        // return Observable.empty();
                    }
                    return Observable.error(error);
                },
                (retry) -> {
                    if (retry instanceof ConnectFailedException) {
                        return new RetryConfig(() -> Single.just(true));
                    }
                    return new RetryConfig(0);
                },
                (throwable) -> {
                    AppLog.e("Exception:" + throwable.toString());
                }
        );
    }
}