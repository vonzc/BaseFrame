package com.example.BaseFrame.common;

import java.util.concurrent.TimeUnit;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class Config {
    public static final int CONNECT_TIMEOUT = 60;
    public static final int READ_TIMEOUT = 60;
    public static final int WRITE_TIMEOUT = 60;
    public static final TimeUnit NET_TIME_OUT_UNIT = TimeUnit.SECONDS;

    public static final int API_SUCCEED_CODE = 0;
    public static final int API_NET_LOST = -400;
}
