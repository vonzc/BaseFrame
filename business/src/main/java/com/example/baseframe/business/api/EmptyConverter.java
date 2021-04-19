package com.example.baseframe.business.api;


import com.example.BaseFrame.common.AppLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.example.baseframe.basemvp.Result.SUCCESS_CODE;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class EmptyConverter extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            if (body.contentLength() == 0) {
                JSONObject json = new JSONObject();
                try {
                    json.put("data", null);
                    json.put("code", SUCCESS_CODE);
                    json.put("message", "empty");
                } catch (JSONException e) {
                    AppLog.e("empty json response failed");
                }
                return json;
            }
            return delegate.convert(body);
        };
    }
}
