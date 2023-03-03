package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class contactPojo {

    @SerializedName("Name")
    public String name;
    @SerializedName("Email")
    public String email;
    @SerializedName("Contact")
    public String contact;
    @SerializedName("Relation")
    public String relation;
    @SerializedName("Age")
    public int age;
    @SerializedName("ContactID")
    public String ContactID;
}
