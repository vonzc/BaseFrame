package com.example.BaseFrame.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class AppUtil {
    private AppUtil() {
    }

    private static volatile ConcurrentHashMap<Network, Boolean> networkInfos = new ConcurrentHashMap<>();

    /**
     * use the new network API
     * Note: after android 5.1, api-21
     *
     * @return true if connected
     */
    public static boolean netWorkAvailable() {
        for (Boolean value : networkInfos.values()) {
            if (value) {
                return true;
            }
        }
        return false;
    }

    public static void updateNetState(Network network, boolean available) {
        AppLog.v("updateNetState:" + network + ", available:" + available);
        networkInfos.put(network, available);
    }

    public static boolean isNetWorkAvailable(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = null;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getMetaDataByKey(@NonNull Context context, String key) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        AppLog.e("getMetaDataByKey key:" + key + ", value:empty");
        return "";
    }

    @NonNull
    public static String getProjectVersionName(@NonNull Context context) {
        String versionName = null;
        try {
            PackageManager pkMgr = context.getPackageManager();
            if (pkMgr != null) {
                PackageInfo pkInfo = pkMgr.getPackageInfo(context.getPackageName(), 0);
                if (pkInfo != null) {
                    versionName = pkInfo.versionName;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //todo
        return TextUtils.isEmpty(versionName) ? "Constants.APP.DEFAULT_VERSION_NAME" : versionName;
    }

    @NonNull
    public static int getProjectVersionCode(@NonNull Context context) {
        int code = 0;
        try {
            PackageManager pkMgr = context.getPackageManager();
            if (pkMgr != null) {
                PackageInfo pkInfo = pkMgr.getPackageInfo(context.getPackageName(), 0);
                if (pkInfo != null) {
                    code = pkInfo.versionCode;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static boolean isInBackgroundThread() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    public static ThreadPoolExecutor getActivityBgWork(Activity activity) {
        return new ThreadPoolExecutor(1, 1,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger();

            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(r);
                t.setName(activity.getClass().getSimpleName() + index.getAndIncrement());
                return t;
            }
        });
    }

}
