package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class getContactsResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public ArrayList<contactPojo> data;
}
