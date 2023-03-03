package com.example.safe.Account.SignupLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safe.Account.userClass;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.getUserResponse;
import com.example.safe.HttpRequest.pojo.loginUserRequest;
import com.example.safe.HttpRequest.pojo.loginUserResponse;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.R;
import com.example.safe.testing.toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        TextView signupTextButton=findViewById(R.id.signupTextButton);
        signupTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            }
        });
        Button loginButton= findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailPhoneTextView=findViewById(R.id.emailEditText);
                String emailPhone=emailPhoneTextView.getText().toString();
                TextView passwordTextView=findViewById(R.id.passwordLoginEditText);
                String password=passwordTextView.getText().toString();
                if(emailPhone !=null && !emailPhone.trim().isEmpty()) {
                    if(password!=null && !password.trim().isEmpty()){
                        Pattern VALID_EMAIL_ADDRESS_REGEX =Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        Pattern VALID_PHONE_REGEX =Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);
                        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailPhone);
                        Matcher phoneMatcher = VALID_PHONE_REGEX.matcher(emailPhone);
                        if(emailMatcher.find()){
                            new toast().handle_error("Email login is not supported right now.",getApplicationContext());
                            //call email login
                        } else if(phoneMatcher.find()) {
                            //call phone login
                            APIInterface apiInterface;
                            apiInterface = retrofitClass.getClient(getApplicationContext()).create(APIInterface.class);
                            loginUserRequest loginUser=new loginUserRequest(emailPhone,password);
                            Call<loginUserResponse> call1 = apiInterface.loginUser(loginUser);
                            call1.enqueue(new Callback<loginUserResponse>() {
                                @Override
                                public void onResponse(Call<loginUserResponse> call, Response<loginUserResponse> response) {
                                    loginUserResponse loginUser=response.body();
                                    if(loginUser.success==false){
                                        new toast().handle_error(loginUser.msg,getApplicationContext());
                                    }else{
                                        userClass user=new userClass();
                                        user.setProfileId(loginUser.uid);
                                        user.setSession(loginUser.BearerToken);
                                        user.setPhoneNumber(emailPhone);
                                        user.updateSharedPreferences(getApplicationContext());
                                        Call<getUserResponse> call2=apiInterface.getUser("Bearer "+user.getSession(),user.getProfileId());
                                        call2.enqueue(new Callback<getUserResponse>() {
                                            @Override
                                            public void onResponse(Call<getUserResponse> call, Response<getUserResponse> response) {
                                                getUserResponse res=response.body();
                                                if(res.success==false){
                                                    new toast().handle_error(res.msg,getApplicationContext());
                                                }else{
                                                    user.setUserName(res.data.firstName+" "+res.data.lastName);
                                                    user.firstName=res.data.firstName;
                                                    user.setPostalCode(res.data.homeAddress.pincode);
                                                    user.updateSharedPreferences(getApplicationContext());
                                                    Intent i=new Intent(view.getContext(), DashboardActivity.class);
                                                    startActivity(i);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<getUserResponse> call, Throwable t) {
                                                call.cancel();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure(Call<loginUserResponse> call, Throwable t) {
                                    call.cancel();
                                }
                            });
                        } else{
                            new toast().handle_error("Enter valid email or phone",getApplicationContext());
                        }
                    }else{
                        new toast().handle_error("Please enter password",getApplicationContext());
                    }
                }else{
                    new toast().handle_error("Please Enter email",getApplicationContext());
                }
            }
        });

    }

}