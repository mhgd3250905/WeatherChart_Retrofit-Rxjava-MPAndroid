
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("sun_down_time")
    @Expose
    private String sunDownTime;
    @SerializedName("sun_rise_time")
    @Expose
    private String sunRiseTime;
    @SerializedName("temp_day_c")
    @Expose
    private String tempDayC;
    @SerializedName("temp_day_f")
    @Expose
    private String tempDayF;
    @SerializedName("temp_night_c")
    @Expose
    private String tempNightC;
    @SerializedName("temp_night_f")
    @Expose
    private String tempNightF;
    @SerializedName("wd")
    @Expose
    private String wd;
    @SerializedName("weather")
    @Expose
    private String weather;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("ws")
    @Expose
    private String ws;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Weather withDate(String date) {
        this.date = date;
        return this;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Weather withImg(String img) {
        this.img = img;
        return this;
    }

    public String getSunDownTime() {
        return sunDownTime;
    }

    public void setSunDownTime(String sunDownTime) {
        this.sunDownTime = sunDownTime;
    }

    public Weather withSunDownTime(String sunDownTime) {
        this.sunDownTime = sunDownTime;
        return this;
    }

    public String getSunRiseTime() {
        return sunRiseTime;
    }

    public void setSunRiseTime(String sunRiseTime) {
        this.sunRiseTime = sunRiseTime;
    }

    public Weather withSunRiseTime(String sunRiseTime) {
        this.sunRiseTime = sunRiseTime;
        return this;
    }

    public String getTempDayC() {
        return tempDayC;
    }

    public void setTempDayC(String tempDayC) {
        this.tempDayC = tempDayC;
    }

    public Weather withTempDayC(String tempDayC) {
        this.tempDayC = tempDayC;
        return this;
    }

    public String getTempDayF() {
        return tempDayF;
    }

    public void setTempDayF(String tempDayF) {
        this.tempDayF = tempDayF;
    }

    public Weather withTempDayF(String tempDayF) {
        this.tempDayF = tempDayF;
        return this;
    }

    public String getTempNightC() {
        return tempNightC;
    }

    public void setTempNightC(String tempNightC) {
        this.tempNightC = tempNightC;
    }

    public Weather withTempNightC(String tempNightC) {
        this.tempNightC = tempNightC;
        return this;
    }

    public String getTempNightF() {
        return tempNightF;
    }

    public void setTempNightF(String tempNightF) {
        this.tempNightF = tempNightF;
    }

    public Weather withTempNightF(String tempNightF) {
        this.tempNightF = tempNightF;
        return this;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public Weather withWd(String wd) {
        this.wd = wd;
        return this;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Weather withWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Weather withWeek(String week) {
        this.week = week;
        return this;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public Weather withWs(String ws) {
        this.ws = ws;
        return this;
    }

}
