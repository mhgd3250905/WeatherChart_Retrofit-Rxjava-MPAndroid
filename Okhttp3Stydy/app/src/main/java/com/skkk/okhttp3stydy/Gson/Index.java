
package com.skkk.okhttp3stydy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Index {

    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("name")
    @Expose
    private String name;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Index withAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Index withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Index withContent(String content) {
        this.content = content;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Index withLevel(String level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Index withName(String name) {
        this.name = name;
        return this;
    }

}
