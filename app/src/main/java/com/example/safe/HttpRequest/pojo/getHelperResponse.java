package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class getHelperResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<helperProfile> data;
    @SerializedName("msg")
    public String msg;
}
