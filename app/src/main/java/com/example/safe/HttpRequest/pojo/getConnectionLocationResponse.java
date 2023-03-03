package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class getConnectionLocationResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("msg")
    public String msg;
//    @SerializedName("data")
//    public ArrayList<connectionPojo> data;

    @SerializedName("data")
    public Datum data;

    public class Datum {
        @SerializedName("Lat")
        public String Lat;
        @SerializedName("Lng")
        public String Lng;
    }
}
