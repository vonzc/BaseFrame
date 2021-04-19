package com.example.BaseFrame.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hjq.toast.ToastStrategy;
import com.hjq.toast.ToastUtils;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 *
 * 对第三方的ToastUtils的使用的整理
 */
public class ToastUtil {
    private ToastUtil() {
    }

    private static Toast mToast;

    private static ShortStrategy sShortStrategy = new ShortStrategy();
    private static LongStrategy sLongStrategy = new LongStrategy();

    public static void showToastShort(@NonNull String text) {
        // custom(text, Toast.LENGTH_SHORT).show();
        ToastUtils.setToastStrategy(sShortStrategy);
        ToastUtils.show(text);
    }

    public static void showToastLong(@NonNull String text) {
        // custom(text, Toast.LENGTH_LONG).show();
        ToastUtils.setToastStrategy(sLongStrategy);
        ToastUtils.show(text);
    }

    /**
     * 这里reset是在UI基类里做的，并不是所有的Activity都继承了UI.但是mToast本身占用内存很小，暂时不处理。
     * 这里的context为全局，不会出现内存泄露情况
     */
    public static void resetToast() {
        if (mToast != null) {
            mToast = null;
        }
    }

    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    private static int parseInt(String code) {
        int ret;
        try {
            ret = Integer.parseInt(code);
        } catch (Exception e) {
            ret = 0;
        }
        return ret;
    }

    private static class ShortStrategy extends ToastStrategy {
        @Override
        public int getToastDuration(CharSequence text) {
            return SHORT_DURATION_TIMEOUT;
        }

        @Override
        public void bind(Toast toast) {
            super.bind(toast);
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        @Override
        public void show(CharSequence text) {
            super.show(text);
            if (hasMessages(1)) {
                removeMessages(1);
                sendEmptyMessage(1);
            }
        }
    }

    private static class LongStrategy extends ToastStrategy {
        @Override
        public int getToastDuration(CharSequence text) {
            return LONG_DURATION_TIMEOUT;
        }

        @Override
        public void bind(Toast toast) {
            super.bind(toast);
            toast.setDuration(Toast.LENGTH_LONG);
        }

        @Override
        public void show(CharSequence text) {
            super.show(text);
            if (hasMessages(1)) {
                removeMessages(1);
                sendEmptyMessage(1);
            }
        }
    }
}
