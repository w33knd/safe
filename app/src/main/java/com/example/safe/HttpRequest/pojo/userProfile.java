package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class userProfile {
    @SerializedName("FirstName")
    public String firstName;
    @SerializedName("LastName")
    public String lastName;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("BirthDate")
    public String birthDate;
    @SerializedName("Aadhar")
    public String aadhar;
    @SerializedName("HomeAddress")
    public HomeAddress homeAddress;

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

    public class HomeAddress{
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
        HomeAddress(String pincode){
            this.pincode=pincode;
        }
    }
    public userProfile(String firstName,String lastName,String gender,String birthDate,String aadhar,String pincode){
        this.firstName=firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.aadhar=aadhar;
        this.gender=gender;
        this.homeAddress=new HomeAddress(pincode);
    }
}
