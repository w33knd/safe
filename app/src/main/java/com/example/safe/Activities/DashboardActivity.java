package com.example.safe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.safe.Fragments.mapFragment;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;
import com.example.safe.testing.toast;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Fragment mapFragment= new mapFragment();
        locationUtil forMap=new locationUtil();
        Location currentLocation=forMap.getCurrentLocation(this);
        if(currentLocation!=null){
            Bundle mapBundle= new Bundle();
            mapBundle.putParcelable("currentLocation",currentLocation);
            mapFragment.setArguments(mapBundle);
        }

        fragmentManager.beginTransaction().replace(R.id.mapFrameLayout,mapFragment).commit();
        ImageButton emergencyClick=(ImageButton) findViewById(R.id.buttonEmergency);
        emergencyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,emergencyActivity.class);
                startActivity(i);

            }
        });
        Button current=findViewById(R.id.button2);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}