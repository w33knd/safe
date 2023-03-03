package com.example.safe.Activities;

import static java.lang.Double.parseDouble;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.safe.Account.userClass;
import com.example.safe.Account.userContact;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.contactPojo;
import com.example.safe.HttpRequest.pojo.getContactsResponse;
import com.example.safe.HttpRequest.pojo.getHelperResponse;
import com.example.safe.HttpRequest.pojo.helperProfile;
import com.example.safe.HttpRequest.pojo.locationFormat;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.MapUtil.mapFragment;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindingHelperActivity extends AppCompatActivity implements mapFragment.OnFoundHelperListener{
    public void getCountHelper(int count,getHelperResponse res){
        TextView processText=findViewById(R.id.processTitleText);
        processText.setText("Found "+count+" Helper");
        TextView processDescText=findViewById(R.id.processDescriptionText);
        processDescText.setText("Attempting to connect them with you!!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_helper);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Fragment mapFragment= new mapFragment();

        Bundle mapBundle = new Bundle();
        mapBundle.putString("context", "helper");
        mapFragment.setArguments(mapBundle);
        fragmentManager.beginTransaction().replace(R.id.mapFrameLayout,mapFragment).commit();

        locationUtil forMap=new locationUtil();
        Location currentLocation=forMap.getCurrentLocation(this);
        if(currentLocation==null){
            new toast().handle_error("Cannot get current location",getApplicationContext());
        }else {
        }

    }



    //overriding back button
    @Override
    public void onBackPressed() {
        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(this);
        BackAlertDialog.setTitle("Activity Exit Alert");
        BackAlertDialog.setMessage("Are you sure want to exit ?");
        BackAlertDialog.setPositiveButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Cancel alert dialog box .
                        dialog.cancel();
                    }
                });
        BackAlertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        BackAlertDialog.show();
        return;
    }
}