package com.example.safe.Location;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;

import com.example.safe.testing.toast;


public class locationUtil {
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
//    locationManager=
    public Location getCurrentLocation(Activity current) {
        ActivityCompat.requestPermissions( current,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager= (LocationManager) current.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        current.getSystemService(Context.LOCATION_SERVICE);
        Location locationCurrent=null;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS(current);
        } else {
            locationCurrent=getLocation(current);
        }
        return locationCurrent;
    }
    private void OnGPS(Activity current) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(current.getApplicationContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                current.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private Location getLocation(Activity current) {
        if (ActivityCompat.checkSelfPermission(
                current.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                current.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(current, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS == null) {
                toast t=new toast();
                toast.handle_error("Unable to find location.", current.getApplicationContext());
            }
            return locationGPS;
        }
        return null;
    }
}
