package com.example.safe.Location;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import com.example.safe.Account.userClass;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.getUserResponse;
import com.example.safe.HttpRequest.pojo.locationFormat;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class locationHTTP {
    public void sendLocationToSafe(Activity mActivity, Location location){
        Gson gson=new Gson();
        SharedPreferences userPreferences=mActivity.getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(mActivity.getApplicationContext()).create(APIInterface.class);
        locationFormat locationBody=new locationFormat(""+location.getLatitude(),""+location.getLongitude());
        Call<Success> call2=apiInterface.updateLocation("Bearer "+user.getSession(),user.getProfileId(),locationBody);
        call2.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Success res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,mActivity.getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void getLocation(Activity mActivity, Location location, String UID){
        Gson gson=new Gson();
        SharedPreferences userPreferences=mActivity.getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(mActivity.getApplicationContext()).create(APIInterface.class);
        locationFormat locationBody=new locationFormat(""+location.getLatitude(),""+location.getLongitude());
        Call<Success> call2=apiInterface.updateLocation("Bearer "+user.getSession(),user.getProfileId(),locationBody);
        call2.enqueue(new Callback<Success>() {
            @Override
            public void onResponse(Call<Success> call, Response<Success> response) {
                Success res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,mActivity.getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Success> call, Throwable t) {
                call.cancel();
            }
        });
    }

}
