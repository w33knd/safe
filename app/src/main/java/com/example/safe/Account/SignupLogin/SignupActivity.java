package com.example.safe.Account.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.safe.Account.userClass;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FragmentManager fragmentManager = getSupportFragmentManager();
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
}