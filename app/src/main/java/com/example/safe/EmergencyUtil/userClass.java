package com.example.safe.EmergencyUtil;

import java.util.ArrayList;
import com.example.safe.EmergencyUtil.userContact;

public class userClass {
    public String userName="hello";
    private ArrayList<userContact> contacts=new ArrayList<>();
    private String userSession;
    public void addEmergencyContact(String name,String mobile, String relation){
        userContact n=new userContact(name,mobile,relation);
        contacts.add(n);
    }
    public ArrayList<userContact> getContacts(){
        return contacts;
    }
    public String getSession(){
        return userSession;
    }
}
