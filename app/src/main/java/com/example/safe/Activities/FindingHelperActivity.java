package com.example.safe.Activities;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.safe.Fragments.mapFragment;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;

public class FindingHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_helper);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mapFragment= new mapFragment();
        locationUtil forMap=new locationUtil();
        Location currentLocation=forMap.getCurrentLocation(this);
        if(currentLocation!=null){
            Bundle mapBundle= new Bundle();
            mapBundle.putParcelable("currentLocation",currentLocation);
            mapFragment.setArguments(mapBundle);
        }

        fragmentManager.beginTransaction().replace(R.id.mapFrameLayout,mapFragment).commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(this);

        BackAlertDialog.setTitle("Activity Exit Alert");

        BackAlertDialog.setMessage("Are you sure want to exit ?");


        BackAlertDialog.setPositiveButton("NO",
                (dialog, which) -> {
                    //Cancel alert dialog box .
                    dialog.cancel();
                });

        BackAlertDialog.setNegativeButton("YES",
                (dialog, which) -> {
                    //Exit from activity.
                    finish();
                });
        BackAlertDialog.show();
    }
}