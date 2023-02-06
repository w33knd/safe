package com.example.safe.Account.SignupLogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.safe.Account.userClass;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.loginUserRequest;
import com.example.safe.HttpRequest.pojo.loginUserResponse;
import com.example.safe.HttpRequest.pojo.signupUserRequest;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class emailSignupFragment extends Fragment {
    static boolean accountCreateSuccess=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_email_signup, container, false);
        v.findViewById(R.id.otpConstraintLayout).setVisibility(View.INVISIBLE);
        v.findViewById(R.id.emailEditText).setVisibility(View.INVISIBLE);
        Button nextStepButton= v.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailPhoneTextView=v.findViewById(R.id.phoneEditText);
                String emailPhone=emailPhoneTextView.getText().toString();
                TextView passwordTextView=v.findViewById(R.id.passwordSignupEditText);
                String password=passwordTextView.getText().toString();
//                if(adduser(emailPhone,password)){
                    //                }else{
//                    new toast().handle_error("Some error occurred",getContext());
//                }
                Gson gson=new Gson();
                SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
                String userString=userPreferences.getString("user","");
                userClass user = gson.fromJson(userString,userClass.class);
                if(emailPhone !=null && !emailPhone.trim().isEmpty()) {
                    if(password!=null && !password.trim().isEmpty()){
                        Pattern VALID_EMAIL_ADDRESS_REGEX =Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        Pattern VALID_PHONE_REGEX =Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);
                        Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailPhone);
                        Matcher phoneMatcher = VALID_PHONE_REGEX.matcher(emailPhone);
                        if(emailMatcher.find()){
                            new toast().handle_error("Email signup is not supported right now.",getContext());
                            //call email login
                        } else if(phoneMatcher.find()) {
                            //call phone login
                            APIInterface apiInterface;
                            apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
                            signupUserRequest signupUser=new signupUserRequest(emailPhone,password,password);
                            Call<Success> call1 = apiInterface.createUser(signupUser);
                            call1.enqueue(new Callback<Success>() {
                                @Override
                                public void onResponse(Call<Success> call, Response<Success> response) {
                                    Success res=response.body();
                                    if(res.success==false){
                                        new toast().handle_error(res.msg,getContext());
                                        return;
                                    }else{

                                /*
                                login new user
                                 */
                                        loginUserRequest loginUser=new loginUserRequest(emailPhone,password);
                                        Call<loginUserResponse> call1 = apiInterface.loginUser(loginUser);
                                        call1.enqueue(new Callback<loginUserResponse>() {
                                            @Override
                                            public void onResponse(Call<loginUserResponse> call, Response<loginUserResponse> response) {
                                                loginUserResponse loginUser=response.body();
                                                if(loginUser.success==false){
                                                    new toast().handle_error(loginUser.msg,getContext());
                                                }else{
                                                    user.setProfileId(loginUser.uid);
                                                    user.setSession(loginUser.BearerToken);
                                                    user.setPhoneNumber(emailPhone);
                                                    user.updateSharedPreferences(getContext());
                                                    new toast().handle_error("Account created successffully",getContext());
                                                    ProgressBar progress=getActivity().findViewById(R.id.progressBar);
                                                    progress.setProgress(75);
                                                    getParentFragmentManager().beginTransaction().replace(R.id.signupStepLayout, addContactSignupFragment.class, null).commit();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<loginUserResponse> call, Throwable t) {
                                                call.cancel();
                                            }
                                        });

                                    }
                                }
                                @Override
                                public void onFailure(Call<Success> call, Throwable t) {
                                    call.cancel();
                                }
                            });
                        } else{
                            new toast().handle_error("Enter valid phone",getContext());
                        }
                    }else{
                        new toast().handle_error("Please enter password",getContext());
                    }
                }else{
                    new toast().handle_error("Please Enter your Phone Number",getContext());
                }
            }
        });
        return v;
    }

//    public boolean adduser(String emailPhone,String password){
//
//        new toast().handle_error("Check ",getContext());
//        if(!user.getSession().isEmpty()){
//            return true;
//        }
//        return false;
//    }
}