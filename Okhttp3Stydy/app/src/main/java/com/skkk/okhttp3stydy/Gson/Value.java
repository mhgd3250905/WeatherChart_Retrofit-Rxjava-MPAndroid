
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Value {

    @SerializedName("alarms")
    @Expose
    private List<Object> alarms = null;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cityid")
    @Expose
    private long cityid;
    @SerializedName("indexes")
    @Expose
    private List<Index> indexes = null;
    @SerializedName("pm25")
    @Expose
    private Pm25 pm25;
    @SerializedName("provinceName")
    @Expose
    private String provinceName;
    @SerializedName("realtime")
    @Expose
    private Realtime realtime;
    @SerializedName("weatherDetailsInfo")
    @Expose
    private WeatherDetailsInfo weatherDetailsInfo;
    @SerializedName("weathers")
    @Expose
    private List<Weather> weathers = null;

    public List<Object> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Object> alarms) {
        this.alarms = alarms;
    }

    public Value withAlarms(List<Object> alarms) {
        this.alarms = alarms;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Value withCity(String city) {
        this.city = city;
        return this;
    }

    public long getCityid() {
        return cityid;
    }

    public void setCityid(long cityid) {
        this.cityid = cityid;
    }

    public Value withCityid(long cityid) {
        this.cityid = cityid;
        return this;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public Value withIndexes(List<Index> indexes) {
        this.indexes = indexes;
        return this;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public Value withPm25(Pm25 pm25) {
        this.pm25 = pm25;
        return this;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Value withProvinceName(String provinceName) {
        this.provinceName = provinceName;
        return this;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Value withRealtime(Realtime realtime) {
        this.realtime = realtime;
        return this;
    }

    public WeatherDetailsInfo getWeatherDetailsInfo() {
        return weatherDetailsInfo;
    }

    public void setWeatherDetailsInfo(WeatherDetailsInfo weatherDetailsInfo) {
        this.weatherDetailsInfo = weatherDetailsInfo;
    }

    public Value withWeatherDetailsInfo(WeatherDetailsInfo weatherDetailsInfo) {
        this.weatherDetailsInfo = weatherDetailsInfo;
        return this;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public Value withWeathers(List<Weather> weathers) {
        this.weathers = weathers;
        return this;
    }

}
