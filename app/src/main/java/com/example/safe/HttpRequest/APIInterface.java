package com.example.safe.HttpRequest;

import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.addContactRequest;
import com.example.safe.HttpRequest.pojo.getConnectionLocationResponse;
import com.example.safe.HttpRequest.pojo.getConnectionsResponse;
import com.example.safe.HttpRequest.pojo.getContactsResponse;
import com.example.safe.HttpRequest.pojo.getHelperResponse;
import com.example.safe.HttpRequest.pojo.getPendingRequestResponse;
import com.example.safe.HttpRequest.pojo.getUserResponse;
import com.example.safe.HttpRequest.pojo.locationFormat;
import com.example.safe.HttpRequest.pojo.loginUserRequest;
import com.example.safe.HttpRequest.pojo.loginUserResponse;
import com.example.safe.HttpRequest.pojo.signupUserRequest;
import com.example.safe.HttpRequest.pojo.userProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    //Authorized calls with Authorization header
    @GET("/profile/get/{uid}")
    Call<getUserResponse> getUser(@Header("Authorization") String bearer, @Path("uid") String uid);

    @GET("/profile/update-fcm-token/{uid}/{fcm_token}")
    Call<Success> updateFcmToken(@Header("Authorization") String bearer, @Path("uid") String uid, @Path("fcm_token") String fcm_token);

    @GET("/contact/get-contacts/{uid}")
    Call<getContactsResponse> getContacts(@Header("Authorization") String bearer, @Path("uid") String uid);

    @GET("/contact/get-pending-requests/{uid}")
    Call<getPendingRequestResponse> getPendingRequests(@Header("Authorization") String bearer, @Path("uid") String uid);

    @GET("/contact/accept-request/{uid}/{RequestID}")
    Call<Success> acceptRequest(@Header("Authorization") String bearer, @Path("uid") String uid, @Path("RequestID") String RequestID);

    @GET("/contact/delete-request/{uid}/{RequestID}")
    Call<Success> deleteRequest(@Header("Authorization") String bearer, @Path("uid") String uid, @Path("RequestID") String RequestID);

    @GET("/contact/delete-contact/{uid}/{ContactID}")
    Call<Success> deleteContact(@Header("Authorization") String bearer, @Path("uid") String uid, @Path("ContactID") String ContactID);

    @GET("/contact/get-connections/{uid}")
    Call<getConnectionsResponse> getConnections(@Header("Authorization") String bearer, @Path("uid") String uid);

    @POST("/profile/complete/{uid}")
    Call<Success> updateUser(@Header("Authorization") String bearer,@Path("uid") String uid,@Body userProfile profile);

    @GET("/contact/add-connection/{uid}/{phone}")
    Call<Success> addConnection(@Header("Authorization") String bearer, @Path("uid") String uid,@Path("phone") String phone);

    @POST("/contact/add-contact/{uid}")
    Call<Success> addContact(@Header("Authorization") String bearer, @Path("uid") String uid, @Body addContactRequest contact);

    @POST("/sos/update-location/{uid}")
    Call<Success> updateLocation(@Header("Authorization") String bearer,@Path("uid") String uid,@Body locationFormat location);

    @GET("/sos/get-connection-location/{uid}/{ConnectionID}")
    Call<getConnectionLocationResponse> getConnectionLocation(@Header("Authorization") String bearer, @Path("uid") String uid, @Path("ConnectionID") String ConnectionID);

    @POST("/sos/connect-safe/{uid}")
    Call<getHelperResponse> connectSafe(@Header("Authorization") String bearer, @Path("uid") String uid,@Body locationFormat userLocation);


    //Unauthorized calls
    @GET("/auth/ping")
    Call<Success> ping();

    @POST("/auth/login")
    Call<loginUserResponse> loginUser(@Body loginUserRequest user);

    @POST("/auth/simple-signup")
    Call<Success> createUser(@Body signupUserRequest signupUser);

//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
