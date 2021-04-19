package com.example.baseframe.business.api.fail;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class ResponseException extends Exception {
    private int code;

    public ResponseException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
