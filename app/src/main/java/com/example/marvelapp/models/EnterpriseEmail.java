package com.example.marvelapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnterpriseEmail {

    @SerializedName("num.backers")
    @Expose()
    private String numbackers;

    @SerializedName("by")
    @Expose()
    private String by;

    @SerializedName("title")
    @Expose()
    private String title;


    public EnterpriseEmail(String numbackers, String by, String title) {
        this.numbackers = numbackers;
        this.by = by;
        this.title = title;
    }

    public String getNumbackers() {
        return numbackers;
    }

    public void setNumbackers(String numbackers) {
        this.numbackers = numbackers;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
