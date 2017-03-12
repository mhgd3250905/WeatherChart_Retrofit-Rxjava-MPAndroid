
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pm25 {

    @SerializedName("advice")
    @Expose
    private String advice;
    @SerializedName("aqi")
    @Expose
    private String aqi;
    @SerializedName("citycount")
    @Expose
    private long citycount;
    @SerializedName("cityrank")
    @Expose
    private long cityrank;
    @SerializedName("co")
    @Expose
    private String co;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("no2")
    @Expose
    private String no2;
    @SerializedName("o3")
    @Expose
    private String o3;
    @SerializedName("pm10")
    @Expose
    private String pm10;
    @SerializedName("pm25")
    @Expose
    private String pm25;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("so2")
    @Expose
    private String so2;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("upDateTime")
    @Expose
    private String upDateTime;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Pm25 withAdvice(String advice) {
        this.advice = advice;
        return this;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public Pm25 withAqi(String aqi) {
        this.aqi = aqi;
        return this;
    }

    public long getCitycount() {
        return citycount;
    }

    public void setCitycount(long citycount) {
        this.citycount = citycount;
    }

    public Pm25 withCitycount(long citycount) {
        this.citycount = citycount;
        return this;
    }

    public long getCityrank() {
        return cityrank;
    }

    public void setCityrank(long cityrank) {
        this.cityrank = cityrank;
    }

    public Pm25 withCityrank(long cityrank) {
        this.cityrank = cityrank;
        return this;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public Pm25 withCo(String co) {
        this.co = co;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Pm25 withColor(String color) {
        this.color = color;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Pm25 withLevel(String level) {
        this.level = level;
        return this;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public Pm25 withNo2(String no2) {
        this.no2 = no2;
        return this;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public Pm25 withO3(String o3) {
        this.o3 = o3;
        return this;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public Pm25 withPm10(String pm10) {
        this.pm10 = pm10;
        return this;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public Pm25 withPm25(String pm25) {
        this.pm25 = pm25;
        return this;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Pm25 withQuality(String quality) {
        this.quality = quality;
        return this;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public Pm25 withSo2(String so2) {
        this.so2 = so2;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Pm25 withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(String upDateTime) {
        this.upDateTime = upDateTime;
    }

    public Pm25 withUpDateTime(String upDateTime) {
        this.upDateTime = upDateTime;
        return this;
    }

}
