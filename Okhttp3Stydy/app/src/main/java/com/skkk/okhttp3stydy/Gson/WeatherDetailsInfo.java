
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetailsInfo {

    @SerializedName("publishTime")
    @Expose
    private String publishTime;
    @SerializedName("weather3HoursDetailsInfos")
    @Expose
    private List<Weather3HoursDetailsInfo> weather3HoursDetailsInfos = null;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public WeatherDetailsInfo withPublishTime(String publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public List<Weather3HoursDetailsInfo> getWeather3HoursDetailsInfos() {
        return weather3HoursDetailsInfos;
    }

    public void setWeather3HoursDetailsInfos(List<Weather3HoursDetailsInfo> weather3HoursDetailsInfos) {
        this.weather3HoursDetailsInfos = weather3HoursDetailsInfos;
    }

    public WeatherDetailsInfo withWeather3HoursDetailsInfos(List<Weather3HoursDetailsInfo> weather3HoursDetailsInfos) {
        this.weather3HoursDetailsInfos = weather3HoursDetailsInfos;
        return this;
    }

}
