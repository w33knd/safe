package com.example.safe.HttpRequest.pojo;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Success {
    @SerializedName("success")
    public boolean success;
    @SerializedName("msg")
    public String msg;
}

//    Call<Success> call2=apiInterface.ping();
//                            call2.enqueue(new Callback<Success>(){
//@Override
//public void onResponse(Call<Success> call, Response<Success> response) {
////                                    User user1 = response.body();
//        Log.d("debug",response.code()+"ping");
//        Success loginUser=response.body();
//        Log.d("debug", loginUser.success + "hello");
//
////                                    if(loginUser.Success){
////                                        Log.d("debug", "true");
////                                    }else{
////                                        Log.d("debug", "false");
////                                    }
//
//        }
//
//@Override
//public void onFailure(Call<Success> call, Throwable t) {
//        call.cancel();
//        }
//        });
