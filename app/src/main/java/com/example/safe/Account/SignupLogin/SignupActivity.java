package com.example.safe.Account.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.safe.Account.userClass;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SignupActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fragmentManager.beginTransaction().replace(R.id.signupStepLayout, welcomeSignupFragment.class, null).commit();
//        nextStepButton.setOnClickListener();
        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setProgress(0);
    }
    @Override
    public void onBackPressed() {
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        userPreferences.edit().clear().commit();
        super.onBackPressed();
        return;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult){
        final int PERMISSION_REQUEST_CODE=1;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResult.length > 0) {

                    boolean locationAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {
//                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        new toast().handle_error("permission granted", getApplicationContext());
                        fragmentManager.beginTransaction().replace(R.id.signupStepLayout, addContactSignupFragment.class, null).commit();
                    }
                    else {
                        new toast().handle_error("Permission Refused, Enable in settings",getApplicationContext());

//                        Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
                    }
                }


                break;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
    }
}