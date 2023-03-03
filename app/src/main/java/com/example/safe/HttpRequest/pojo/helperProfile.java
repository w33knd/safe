package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class helperProfile {
    @SerializedName("uid")
    public String uid;
    @SerializedName("FirstName")
    public String firstName;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("Age")
    public int age;
    @SerializedName("Last_Location")
    public locationFormat lastActiveLocation;

//    public helperProfile(String firstName, String lastName, String gender, String birthDate, String aadhar, String pincode){
//        this.firstName=firstName;
//        this.lastName=lastName;
//        this.birthDate=birthDate;
//        this.aadhar=aadhar;
//        this.gender=gender;
//        this.homeAddress=new HomeAddress(pincode);
//    }
}
