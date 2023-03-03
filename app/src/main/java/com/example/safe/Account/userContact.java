package com.example.safe.Account;

public class userContact{
    public String contactName;
    public String mobileNo;
    public int age;
    public String relation;
    public String UID;
    public String ContactID;
    //create local contacts without safe account
    public userContact(String name, String email, String mobile, String relation, int age, String ContactID){
        this.ContactID=ContactID;
        this.age=age;
        this.contactName=name;
        this.mobileNo=mobile;
        this.relation=relation;
    }
    //create connection with safe id
    public userContact(String name, String mobile, String UID){
        this.UID=UID;
        this.contactName=name;
        this.mobileNo=mobile;
    }
}
