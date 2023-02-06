package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class loginUserResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("BearerToken")
    public String BearerToken;
    @SerializedName("uid")
    public String uid;
    @SerializedName("msg")
    public String msg;

}
