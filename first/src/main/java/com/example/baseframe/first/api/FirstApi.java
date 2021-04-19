package com.example.baseframe.first.api;

import com.example.baseframe.basemvp.Result;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public interface FirstApi {

    @GET("http")
    Flowable<Result<String>> getSomeData();
}
