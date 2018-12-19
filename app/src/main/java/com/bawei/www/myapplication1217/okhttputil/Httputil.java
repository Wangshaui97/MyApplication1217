package com.bawei.www.myapplication1217.okhttputil;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Httputil {

    private static volatile Httputil mInstance;

    private OkHttpClient mclient;

    private Handler mhandler = new Handler(Looper.getMainLooper());

    public static Httputil getInstance() {
        if (mInstance == null) {
            synchronized (Httputil.class) {
                if (null == mInstance) {
                    mInstance = new Httputil();
                }
            }
        }
        return mInstance;
    }

    private Httputil() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mclient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void getEnqueue(String url, final Class clazz, final ICallBack iCallBack) {

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = mclient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reuslt = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(reuslt, clazz);
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallBack.success(o);
                    }
                });
            }
        });

    }
}
