
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather3HoursDetailsInfo {

    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("highestTemperature")
    @Expose
    private String highestTemperature;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("isRainFall")
    @Expose
    private String isRainFall;
    @SerializedName("lowerestTemperature")
    @Expose
    private String lowerestTemperature;
    @SerializedName("precipitation")
    @Expose
    private String precipitation;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("wd")
    @Expose
    private String wd;
    @SerializedName("weather")
    @Expose
    private String weather;
    @SerializedName("ws")
    @Expose
    private String ws;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Weather3HoursDetailsInfo withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(String highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public Weather3HoursDetailsInfo withHighestTemperature(String highestTemperature) {
        this.highestTemperature = highestTemperature;
        return this;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Weather3HoursDetailsInfo withImg(String img) {
        this.img = img;
        return this;
    }

    public String getIsRainFall() {
        return isRainFall;
    }

    public void setIsRainFall(String isRainFall) {
        this.isRainFall = isRainFall;
    }

    public Weather3HoursDetailsInfo withIsRainFall(String isRainFall) {
        this.isRainFall = isRainFall;
        return this;
    }

    public String getLowerestTemperature() {
        return lowerestTemperature;
    }

    public void setLowerestTemperature(String lowerestTemperature) {
        this.lowerestTemperature = lowerestTemperature;
    }

    public Weather3HoursDetailsInfo withLowerestTemperature(String lowerestTemperature) {
        this.lowerestTemperature = lowerestTemperature;
        return this;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public Weather3HoursDetailsInfo withPrecipitation(String precipitation) {
        this.precipitation = precipitation;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Weather3HoursDetailsInfo withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public Weather3HoursDetailsInfo withWd(String wd) {
        this.wd = wd;
        return this;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Weather3HoursDetailsInfo withWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public Weather3HoursDetailsInfo withWs(String ws) {
        this.ws = ws;
        return this;
    }

}
