package com.example.safe.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.safe.Account.userClass;
import com.example.safe.MapUtil.mapFragment;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Gson gson=new Gson();
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);

        if(user.getFcmToken()==null){
            Task<String> t= FirebaseMessaging.getInstance().getToken();
//                    token=t.getResult();
            t.addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    String token=task.getResult();
                    user.setFcmToken(token,getApplicationContext());
                }
            });
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Fragment mapFragment= new mapFragment();
//        locationUtil forMap=new locationUtil();

        Bundle mapBundle = new Bundle();
        mapBundle.putString("context", "dashboard");
        mapFragment.setArguments(mapBundle);
//        Location currentLocation=forMap.getCurrentLocation(this);
//        if(currentLocation!=null){
//            Bundle mapBundle= new Bundle();
//            mapBundle.putParcelable("currentLocation",currentLocation);
//            mapFragment.setArguments(mapBundle);
//        }
        fragmentManager.beginTransaction().replace(R.id.mapFrameLayout,mapFragment).commit();
//        mapFragment x=(mapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrameLayout);
//        x.placeConnections();
        Button detected=(Button) findViewById(R.id.button2);
        detected.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(DashboardActivity.this,DetectedEmergencyActivity.class);
                startActivity(i);
            }
        });
        ImageButton emergencyClick=(ImageButton) findViewById(R.id.buttonEmergency);
        emergencyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,emergencyActivity.class);
                startActivity(i);
            }
        });
    }
}