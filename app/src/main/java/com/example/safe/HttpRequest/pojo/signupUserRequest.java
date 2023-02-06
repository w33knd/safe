package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class signupUserRequest {
    @SerializedName("CountryCode")
    public String CountryCode;
    @SerializedName("Contact")
    public String Contact;
    @SerializedName("Password")
    public String Password;
    @SerializedName("ConfirmPassword")
    public String ConfirmPassword;

    public signupUserRequest(String Contact, String Password,String ConfirmPassword) {
        this.Contact = Contact;
        this.Password = Password;
        this.ConfirmPassword= ConfirmPassword;
        this.CountryCode= "+91";
    }

}
