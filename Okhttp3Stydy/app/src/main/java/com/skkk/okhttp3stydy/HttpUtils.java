package com.skkk.okhttp3stydy;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/3/15.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2017/3/15$ 22:40$.
*/
public class HttpUtils {
    public volatile static HttpUtils instance;
    private Retrofit retrofit;
    private WeatherInterface weatherInterface;

    public HttpUtils() {
        String baseUrl = "http://aider.meizu.com/";
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public WeatherInterface getWeatherInterface() {
        return retrofit.create(WeatherInterface.class);
    }
}
