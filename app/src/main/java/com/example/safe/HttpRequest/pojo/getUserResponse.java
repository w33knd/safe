package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;



public class getUserResponse{
    @SerializedName("success")
    public boolean success;
    @SerializedName("data")
    public Data data;

    class Data{
        @SerializedName("FirstName")
        public String firstName;
        @SerializedName("LastName")
        public String lastName;
        @SerializedName("Aadhar")
        public int aadhar;
        @SerializedName("Gender")
        public String gender;
        @SerializedName("HomeAddress")
        public HomeAddress homeAddress;
    }

    class Geojson{
        @SerializedName("lat")
        public int lat;
        @SerializedName("lng")
        public int lng;
        @SerializedName("name")
        public String name;
        @SerializedName("street")
        public String street;
        @SerializedName("category")
        public String category;
    }

    class HomeAddress{
        @SerializedName("area")
        public String area;
        @SerializedName("city")
        public String city;
        @SerializedName("state")
        public String state;
        @SerializedName("geojson")
        public Geojson geojson;
        @SerializedName("pincode")
        public String pincode;
    }
}
