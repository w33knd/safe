package com.example.safe.Account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import com.example.safe.HttpRequest.postRequest;
import com.google.gson.Gson;

public class userClass {
    public String userName="";
    public String phoneExtension;
    private String phoneNumber="";
    private String email="";
    private ArrayList<userContact> contacts=new ArrayList<>();
    private String userSession;
    private String profileId;
    private String postalCode;

    public void addEmergencyContact(String name,String mobile, String relation,Context context){
        userContact n=new userContact(name,mobile,relation);
        contacts.add(n);
        updateSharedPreferences(context);
        postRequest sendNewContact=new postRequest();
//        sendNewContact.get("/auth/ping",context,this.userSession);
//        String json_body='{"CountryCode":"+91","Contact":"9909402670","Password":"password","Platform":"mobile"}'
//        sendNewContact
        sendNewContact.get("/profile/get/f2b24141ac63f6a2bd79c7d9f05ee553","",context,"dc6099ebfd03e5e04c7e5bf33f416b2ebb060bca7e2dd6f16523de305b464649");
    }
    public boolean removeEmergencyContact(userContact toDelete,Context context){
        for(int i=0;i<contacts.size();i++){
            if(toDelete.contactName.equals(contacts.get(i).contactName)){
                contacts.remove(i);
                this.updateSharedPreferences(context);
                return true;
            }
        }
        return false;
    }
    public void updateSharedPreferences(Context context){
        SharedPreferences userPreferences=context.getSharedPreferences("context",MODE_PRIVATE);
        SharedPreferences.Editor  editUser=userPreferences.edit();
        Gson gson=new Gson();
        String userString=gson.toJson(this);
        editUser.putString("user",userString);
        editUser.commit();
    }
    /*
    Getters
     */
    public ArrayList<userContact> getContacts(){
        return contacts;
    }
    public String getSession(){
        return (userSession==null) ? new String() : userSession;
    }
    public String getProfileId(){
        return (profileId==null) ? new String() : profileId;
    }
    public String getEmail(){
        return (email==null) ? new String() : email;
    }
    public String getPhoneNumber(){
        return (phoneNumber==null) ? new String() : phoneNumber;
    }
    public String getUserName(){
        return (userName==null) ? new String(): userName;
    }
    public String getPostalCode(){
        return (postalCode==null) ? new String(): postalCode;
    }
    /*
    Setters
     */
    public void setPostalCode(String postalCode){
        this.postalCode=postalCode;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    public void setProfileId(String profileId){
        this.profileId=profileId;
    }
    public void setSession(String userSession){
        this.userSession=userSession;
    }
}
