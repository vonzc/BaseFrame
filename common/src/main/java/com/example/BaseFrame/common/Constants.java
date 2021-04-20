package com.example.BaseFrame.common;

/**
 * @author fengzhongcheng
 * @since 2021/4/20
 */
public class Constants {
    private Constants() {
    }

    public static final class First {
        public static final class Route {
            /**
             * ARouter使用的path字符串要求至少包含两个'/'
             */
            public static final String FIRST = "/first/first";
        }

        public static final int FIRST_REQ_CODE = 1;
        public static final String EXTRA_TEXT = "extra_text";
    }

    public static final class Kotlin1 {
        public static final class Route {
            public static final String KOTLIN1 = "/kotlin1/kotlin1";
        }

        public static final String KEY_TO_KOTLIN = "key_to_kotlin";
    }
}
