package com.example.safe.HttpRequest;

import com.example.safe.HttpRequest.pojo.MultipleResources;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.UserList;
import com.example.safe.HttpRequest.pojo.loginUserRequest;
import com.example.safe.HttpRequest.pojo.loginUserResponse;
import com.example.safe.HttpRequest.pojo.signupUserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/api/{uid}")
    Call<MultipleResources> getUser(@Path("uid") long uid);

    @GET("/auth/ping")
    Call<Success> ping();

    @POST("/auth/login")
    Call<loginUserResponse> loginUser(@Body loginUserRequest user);

    @POST("/auth/simple-signup")
    Call<Success> createUser(@Body signupUserRequest signupUser);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
