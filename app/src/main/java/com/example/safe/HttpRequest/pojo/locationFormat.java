package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class locationFormat {
    @SerializedName("Lat")
    public String Lat;
    @SerializedName("Lng")
    public String Lng;

    public locationFormat(String Lat, String Lng) {
        this.Lat = Lat;
        this.Lng = Lng;
    }

}
