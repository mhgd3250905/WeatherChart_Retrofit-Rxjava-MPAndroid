package com.skkk.okhttp3stydy;

import com.skkk.okhttp3stydy.Gson.WeatherGson;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/3/12.
 */

public interface WeatherInterface {

    //http://aider.meizu.com/app/weather/listWeather?cityIds=101010100
    @GET("app/weather/listWeather")
    Observable<WeatherGson> getWeather(@Query("cityIds")String cityIds);
}
