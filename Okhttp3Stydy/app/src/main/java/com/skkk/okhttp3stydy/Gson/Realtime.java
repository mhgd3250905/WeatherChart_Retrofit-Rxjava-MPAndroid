
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Realtime {

    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("sD")
    @Expose
    private String sD;
    @SerializedName("sendibleTemp")
    @Expose
    private String sendibleTemp;
    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("wD")
    @Expose
    private String wD;
    @SerializedName("wS")
    @Expose
    private String wS;
    @SerializedName("weather")
    @Expose
    private String weather;
    @SerializedName("ziwaixian")
    @Expose
    private String ziwaixian;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Realtime withImg(String img) {
        this.img = img;
        return this;
    }

    public String getSD() {
        return sD;
    }

    public void setSD(String sD) {
        this.sD = sD;
    }

    public Realtime withSD(String sD) {
        this.sD = sD;
        return this;
    }

    public String getSendibleTemp() {
        return sendibleTemp;
    }

    public void setSendibleTemp(String sendibleTemp) {
        this.sendibleTemp = sendibleTemp;
    }

    public Realtime withSendibleTemp(String sendibleTemp) {
        this.sendibleTemp = sendibleTemp;
        return this;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Realtime withTemp(String temp) {
        this.temp = temp;
        return this;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Realtime withTime(String time) {
        this.time = time;
        return this;
    }

    public String getWD() {
        return wD;
    }

    public void setWD(String wD) {
        this.wD = wD;
    }

    public Realtime withWD(String wD) {
        this.wD = wD;
        return this;
    }

    public String getWS() {
        return wS;
    }

    public void setWS(String wS) {
        this.wS = wS;
    }

    public Realtime withWS(String wS) {
        this.wS = wS;
        return this;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Realtime withWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public String getZiwaixian() {
        return ziwaixian;
    }

    public void setZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
    }

    public Realtime withZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
        return this;
    }

}
