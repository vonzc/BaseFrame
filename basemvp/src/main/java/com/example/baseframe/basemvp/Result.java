package com.example.baseframe.basemvp;

import com.google.gson.annotations.SerializedName;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class Result<T> {
    public static final int SUCCESS_CODE = 0;

    @SerializedName("responseCode")
    private int code;
    private String message;
    @SerializedName("content")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}