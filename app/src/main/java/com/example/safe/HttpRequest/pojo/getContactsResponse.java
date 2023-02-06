package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class getContactsResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public ArrayList<Datum> data;

    class Datum{
        @SerializedName("name")
        public String name;
        @SerializedName("email")
        public String email;
        @SerializedName("contact")
        public String contact;
        @SerializedName("relation")
        public String relation;
        @SerializedName("age")
        public int age;
    }
}
