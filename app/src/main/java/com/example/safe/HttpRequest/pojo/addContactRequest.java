package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

public class addContactRequest {
    @SerializedName("Name")
    public String Name;
    @SerializedName("Email")
    public String Email;
    @SerializedName("Contact")
    public String Contact;
    @SerializedName("Relation")
    public String Relation;
    @SerializedName("Age")
    public int Age;

    public addContactRequest(String Name,String Email,String Contact, String Relation, int Age) {
        this.Name = Name;
        this.Email=Email;
        this.Contact=Contact;
        this.Relation=Relation;
        this.Age=Age;
    }
}
