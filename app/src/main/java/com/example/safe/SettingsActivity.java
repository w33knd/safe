package com.example.safe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.safe.Account.userClass;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        ConstraintLayout privacyLayout=findViewById(R.id.privacySettingLayout);
        ConstraintLayout accountLayout=findViewById(R.id.accountSettingLayout);
        ConstraintLayout notificationLayout=findViewById(R.id.notificationSettingLayout);
        ConstraintLayout logoutLayout=findViewById(R.id.logoutSettingLayout);
        Gson gson=new Gson();
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        TextView i=findViewById(R.id.contactNoText);
        i.setText(user.getPhoneNumber());
        privacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("privacy",getApplicationContext());
            }
        });
        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("account",getApplicationContext());
            }
        });
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("notification",getApplicationContext());
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("Logging out",getApplicationContext());
                SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
                userPreferences.edit().clear().commit();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }
}