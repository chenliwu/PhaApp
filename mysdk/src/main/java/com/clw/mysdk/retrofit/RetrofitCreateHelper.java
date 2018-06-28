package com.clw.mysdk.retrofit;

import com.clw.mysdk.retrofit.converter.FastJsonConverterFactory;
import com.clw.mysdk.retrofit.okhttp.CacheInterceptor;
import com.clw.mysdk.retrofit.okhttp.TrustManager;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * 初始化Retrofit框架，进行配置，提供retrofit的操作
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 */

public class RetrofitCreateHelper {
    /**
     * 读写超时时间
     */
    private static final int TIMEOUT_READ = 20;
    /**
     * 连接超时时间
     */
    private static final int TIMEOUT_CONNECTION = 10;
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();

    /**
     * OkHttp对象
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //打印日志
            //.addInterceptor(interceptor)
            //设置Cache拦截器
            //.addNetworkInterceptor(cacheInterceptor)
            //.addInterceptor(cacheInterceptor)
            //.cache(HttpCache.getCache())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();
    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 构建API接口，发出请求
     * @param clazz
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())   //添加GSON解析：GsonConverterFactory.create()
                .addConverterFactory(FastJsonConverterFactory.create())   //添加GSON解析：GsonConverterFactory.create()
                .build();
        return retrofit.create(clazz);
    }
}

