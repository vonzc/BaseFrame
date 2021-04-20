package com.example.baseframe.business.api;

import android.text.TextUtils;

import com.example.BaseFrame.common.AppLog;
import com.example.BaseFrame.common.AppUtil;
import com.example.BaseFrame.common.Config;
import com.example.BaseFrame.common.MyApp;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class ApiProxy {

    public static final String HEADER_TOKEN = "token";
    public static final String HEADER_UID = "userId";
    public static final String HEADER_APP_ID = "appid";
    public static final String HEADER_DEVICE = "deviceType";
    public static final String KEY_PRIVACY_URL = "PRIVACY_URL";

    private static final boolean PRINT_LOG = AppLog.isEnableDebug();
    public static final String KEY_CHANNEL = "APP_CHANNEL";
    private static final String KEY_SERVER_URL = "SERVER_URL";
    private static final String CHANNEL_TEST = "Test";
    private static final int APP_ID = 3001;
    private static final int HEADER_DEVICE_ANDROID = 0;

    private static String DOMAIN_URL;

    private static Retrofit sRetrofit = null;

    static {
        //todo
        DOMAIN_URL = AppUtil.getMetaDataByKey(MyApp.getAppContext(), KEY_SERVER_URL);
    }

    public static final String API_URL = DOMAIN_URL + "/";

    static class InstanceHolder {
        final static ApiProxy sInstance = new ApiProxy();
    }

    public static ApiProxy getInstance() {
        return ApiProxy.InstanceHolder.sInstance;
    }

    private ApiProxy() {
        sRetrofit = initRetrofit(API_URL);
    }

    private Retrofit initRetrofit(final String baseUri) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor((message) -> {
            AppLog.v("retrofit = " + message);
        });

        if (PRINT_LOG) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        Interceptor baseInterceptor = chain -> {
            Request request = chain.request();
            if (!AppUtil.netWorkAvailable()) {
                /*
                 * read the last 5 days http response when offline
                 */
                int maxStale = 60 * 60 * 24 * 5;
                CacheControl tempCacheControl = new CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(maxStale, TimeUnit.SECONDS)
                        .build();
                request = request.newBuilder()
                        .cacheControl(tempCacheControl)
                        .build();
                AppLog.i("intercept:no network ");
            }
            return chain.proceed(request);
        };

        //添加请求头
        Interceptor headerInterceptor = chain -> {
            //todo  打印本地用户id与token等信息
//            String authorization = LocalAccountManager.getInstance().getAuthorization();
            Request.Builder builder = chain.request().newBuilder();
//            AppLog.v(authorization);
//            if (!TextUtils.isEmpty(authorization)) {
//                long uid = LocalAccountManager.getInstance().getUid();
//                String token = LocalAccountManager.getInstance().getToken();
//                builder.addHeader(HEADER_TOKEN, token);
////                builder.addHeader(HEADER_UID, String.valueOf(uid));
//            }

            builder.addHeader(HEADER_APP_ID, String.valueOf(APP_ID));
            builder.addHeader(HEADER_DEVICE, String.valueOf(HEADER_DEVICE_ANDROID));

            Response response = chain.proceed(builder.build());
            return response;
        };

        // Note for the base retry interceptor {@link okhttp3.RealCall#retryAndFollowUpInterceptor}
        Interceptor rewriteCacheControlInterceptor = chain -> {
            Request request = chain.request();

            Response originalResponse = chain.proceed(request);
            // 3s, online cache
            int maxAge = 3;
            // origin request cache time.
            String reqCache = request.header("Cache-Control");
            if (!TextUtils.isEmpty(reqCache) && reqCache.contains("=")) {
                String age = reqCache.split("=")[1];
                maxAge = Integer.parseInt(age);
            }
            // response request cache time.
            Headers headers = originalResponse.headers();
            String maxAgeStr = headers.get("Cache-Control");
            if (!TextUtils.isEmpty(maxAgeStr) && maxAgeStr.contains("=")) {
                String age = maxAgeStr.split("=")[1];
                maxAge = Integer.parseInt(age);
            }
            return originalResponse.newBuilder()
                    /* Clear the header information, it will return some interference
                     * information if the server doesn't support.
                     * */
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        };

        // http cache, internal file system
        File httpCacheDirectory = new File(MyApp.getAppContext().getCacheDir(), "responses");
        /// external file system
        // File httpCacheExDirectory = new File(MyApp.getAPPContext().getExternalCacheDir(), "responses");
        // max cache size: 100MB
        int cacheSize = 100 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(Config.CONNECT_TIMEOUT, Config.NET_TIME_OUT_UNIT)
                .readTimeout(Config.READ_TIMEOUT, Config.NET_TIME_OUT_UNIT)
                .writeTimeout(Config.WRITE_TIMEOUT, Config.NET_TIME_OUT_UNIT)
                .addInterceptor(logInterceptor)
                .addInterceptor(baseInterceptor)
                .addInterceptor(headerInterceptor)
                .addNetworkInterceptor(rewriteCacheControlInterceptor);

        OkHttpClient okHttpClient = builder.build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(new EmptyConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUri)
                .build();
    }

    /**
     * @param apiService api interface
     * @param <T>        the interface class-type
     * @return mostly are flowable
     */
    public static <T> T getApi(final Class<T> apiService) {
        return getInstance().getInstanceApi(apiService);
    }

    public <T> T getInstanceApi(final Class<T> service) {
        return sRetrofit.create(service);
    }
}