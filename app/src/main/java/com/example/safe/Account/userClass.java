package com.example.safe.Account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import com.example.safe.Activities.DashboardActivity;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.addContactRequest;
import com.example.safe.HttpRequest.pojo.connectionPojo;
import com.example.safe.HttpRequest.pojo.contactPojo;
import com.example.safe.HttpRequest.pojo.getConnectionsResponse;
import com.example.safe.HttpRequest.pojo.getContactsResponse;
import com.example.safe.HttpRequest.pojo.userProfile;
import com.example.safe.HttpRequest.postRequest;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userClass {
    public String userName;
    public String firstName;
    public String phoneExtension="+91";
    private String phoneNumber;
    private String email;
    private ArrayList<userContact> contacts=new ArrayList<>();
    private ArrayList<userContact> connections=new ArrayList<>();
    private String userSession;
    private String profileId;
    private String postalCode;
    private String fcmToken;

    public void addEmergencyContact(String name,String mobile, String relation, int age, Context context){
        updateSharedPreferences(context);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        addContactRequest contact=new addContactRequest(name,"temp@temp.com",mobile,relation,age);
        Call<Success> call1 = apiInterface.addContact("Bearer "+getSession(),getProfileId(),contact);
        call1.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Success res=response.body();
                if(!res.success){
                    new toast().handle_error(res.msg,context);
                }else{
                    new toast().handle_error("success added contact",context);
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
        updateContacts(context);
    }

    public void updateContacts(Context context){
        updateSharedPreferences(context);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        Call<getContactsResponse> call1 = apiInterface.getContacts("Bearer "+getSession(),getProfileId());
        call1.enqueue(new Callback<getContactsResponse>() {
            @Override
            public void onResponse(Call<getContactsResponse> call, Response<getContactsResponse> response) {
                getContactsResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,context);
                }else{
//                    new toast().handle_error("success" + res.data.size() + "contact",context);
                    contacts.clear();
                    for(contactPojo i: res.data){
                        userContact newContact=new userContact(i.name,i.email,i.contact,i.relation,i.age,i.ContactID);
//                        Log.d("debug",newContact.);
                        contacts.add(newContact);
                    }

                    updateSharedPreferences(context);
                }
            }

            @Override
            public void onFailure(Call<getContactsResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
    }

    public void updateConnections(Context context){
        updateSharedPreferences(context);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        Call<getConnectionsResponse> call1 = apiInterface.getConnections("Bearer "+getSession(),getProfileId());
        call1.enqueue(new Callback<getConnectionsResponse>() {
            @Override
            public void onResponse(Call<getConnectionsResponse> call, Response<getConnectionsResponse> response) {
                getConnectionsResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,context);
                }else{
//                    new toast().handle_error("success"+ res.data.size()+"connections",context);
                    connections.clear();
                    for(connectionPojo i: res.data){
                        userContact newContact=new userContact(i.name,i.contact,i.uid);
//                        Log.d("debug",newContact.contactName);
                        connections.add(newContact);
                    }
                    updateSharedPreferences(context);
                }
            }

            @Override
            public void onFailure(Call<getConnectionsResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
    }

    public void removeEmergencyContact(String ContactID,Context context){
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
//        addContactRequest contact=new addContactRequest(name,"temp@temp.com",mobile,relation,age);
        Call<Success> call1 = apiInterface.deleteContact("Bearer "+getSession(),getProfileId(),ContactID);
        call1.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Success res=response.body();
                if(!res.success){
                    new toast().handle_error(res.msg,context);
                }else{
                    new toast().handle_error("Success contact deleted",context);
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
        updateContacts(context);
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
    public ArrayList<userContact> getConnections(){return connections; }
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
    public String getFcmToken(){ return fcmToken;}
    /*
    Setters
     */
    public void setFcmToken(String fcmToken,Context context){
        this.fcmToken=fcmToken;
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        Call<Success> call1 = apiInterface.updateFcmToken("Bearer "+userSession,profileId,fcmToken);
        call1.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Success res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,context);
                }
            }
            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                call.cancel();
            }
        });
        updateSharedPreferences(context);
    }
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
