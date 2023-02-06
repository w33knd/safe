package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class loginUserRequest {
    @SerializedName("CountryCode")
    public String CountryCode;
    @SerializedName("Contact")
    public String Contact;
    @SerializedName("Password")
    public String Password;
    @SerializedName("Platform")
    public String Platform;

    public loginUserRequest(String Contact, String Password) {
        this.Contact = Contact;
        this.Password = Password;
        this.Platform="mobile";
        this.CountryCode="+91";
    }

}
