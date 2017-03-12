
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherGson {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("redirect")
    @Expose
    private String redirect;
    @SerializedName("value")
    @Expose
    private List<Value> value = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public WeatherGson withCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WeatherGson withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public WeatherGson withRedirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public WeatherGson withValue(List<Value> value) {
        this.value = value;
        return this;
    }

}
