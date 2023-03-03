package com.example.safe.MapUtil;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import static java.lang.Double.parseDouble;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safe.Account.userClass;
import com.example.safe.Account.userContact;
import com.example.safe.Activities.FindingHelperActivity;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.addContactRequest;
import com.example.safe.HttpRequest.pojo.getConnectionLocationResponse;
import com.example.safe.HttpRequest.pojo.getHelperResponse;
import com.example.safe.HttpRequest.pojo.helperProfile;
import com.example.safe.HttpRequest.pojo.locationFormat;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.Location.locationHTTP;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//https://stackoverflow.com/questions/35670461/update-location-in-googlemap-android advanced google map manipulation
public class mapFragment extends Fragment implements LocationListener, OnMapReadyCallback {
    public GoogleMap mMap;
    Marker selfMarker;
    SupportMapFragment supportMapFragment;
    public interface OnFoundHelperListener {
        public void getCountHelper(int count,getHelperResponse res);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity().getApplicationContext(), R.raw.map_style));

//                    if (!success) {
//                    }
        } catch (Resources.NotFoundException e) {
//                    Log.e(TAG, "Can't find style. Error: ", e);
        }
//                JSONObject geoJsonData=(JSONObject) getResources().openRawResource(R.raw.sample_geojson);
//                GeoJsonLayer layer;
//                try{
//                    layer = new GeoJsonLayer(googleMap, R.raw.sample_geojson, getActivity().getApplicationContext());
//                }
//                catch(Exception j){
//                    layer=null;
//                }
//                layer.addLayerToMap();
        locationTracker mGPS = new locationTracker(getContext());
        int LOCATION_REFRESH_TIME = 5000; // 15 seconds to update
        int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update

        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
        mGPS.getLocation();
        LatLng current=new LatLng(mGPS.getLatitude(),mGPS.getLongitude());
        googleMap.setMinZoomPreference(16.0f);
        googleMap.setBuildingsEnabled(true);
        googleMap.setMaxZoomPreference(16.0f);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));

        if(getArguments().getString("context").equals("helper")){
            locationUtil forMap=new locationUtil();
            Location currentLocation=forMap.getCurrentLocation(getActivity());

            Gson gson=new Gson();
            SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
            String userString=userPreferences.getString("user","");
            userClass user = gson.fromJson(userString,userClass.class);
            APIInterface apiInterface;
            apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
            locationFormat locationRequest=new locationFormat(String.valueOf(currentLocation.getLatitude()),String.valueOf(currentLocation.getLongitude()));
            Call<getHelperResponse> call1 = apiInterface.connectSafe("Bearer "+user.getSession(),user.getProfileId(),locationRequest);
            call1.enqueue(new Callback<getHelperResponse>() {
                @Override
                public void onResponse(Call<getHelperResponse> call, Response<getHelperResponse> response) {
                    getHelperResponse res=response.body();
                    if(res.success==false){
                        new toast().handle_error(res.msg,getContext());
                    }else{
                        for(helperProfile i:res.data){
                            String displayInfo=i.firstName+","+i.age+","+i.gender;
                            LatLng helperLocation=new LatLng(parseDouble(i.lastActiveLocation.Lat),parseDouble(i.lastActiveLocation.Lng));

                            placeMarker(displayInfo,R.drawable.material_icons_location_black,helperLocation);
                        }
                        FindingHelperActivity a=(FindingHelperActivity)getActivity();
                        a.getCountHelper(res.data.size(),res);
                    }
                }

                @Override
                public void onFailure(Call<getHelperResponse> call, Throwable t) {
                    new toast().handle_error("Connection Error", getContext());
                    call.cancel();
                }
            });
        }else{
//            placeConnections();

            Gson gson=new Gson();
            SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
            String userString=userPreferences.getString("user","");
            userClass user = gson.fromJson(userString,userClass.class);
            user.updateConnections(getContext());
            for(userContact i:user.getConnections()){
                APIInterface apiInterface;
                apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
                Call<getConnectionLocationResponse> call1 = apiInterface.getConnectionLocation("Bearer "+user.getSession(),user.getProfileId(),i.UID);
                call1.enqueue(new Callback<getConnectionLocationResponse>() {
                    @Override
                    public void onResponse(Call<getConnectionLocationResponse> call, Response<getConnectionLocationResponse> response) {
                        getConnectionLocationResponse res=response.body();
                        if(!res.success){
                            new toast().handle_error(res.msg,getContext());
                        }else{
//                        new toast().handle_error("got location"+ i.contactName,getContext());
                            LatLng loc = new LatLng(parseDouble(res.data.Lat), parseDouble(res.data.Lng));
                            placeMarker(i.contactName+"'s Location",R.drawable.material_icons_location_blue,loc);
                        }
//                    mMap.addMarker(new MarkerOptions().position(loc).title(i.contactName+"'s Location").icon(BitmapFromVector(getContext(),R.drawable.material_icons_location_blue  )));
                    }

                    @Override
                    public void onFailure(Call<getConnectionLocationResponse> call, Throwable t) {
                        new toast().handle_error("Connection Error", getContext());
                        call.cancel();
                    }
                });
            }
        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Initialize map fragment
        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapCurrentLocationID);

        GoogleMapOptions options = new GoogleMapOptions();

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        supportMapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
//        Log.d("debug","changed");
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        if(selfMarker!=null) {
            selfMarker.remove();
        }
        selfMarker= mMap.addMarker(new MarkerOptions().position(loc).title("My Location").icon(BitmapFromVector(getContext(),R.drawable.material_icons_location_red)));
//        placeMarker("My Location",R.drawable.material_icons_location_red,loc);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        new locationHTTP().sendLocationToSafe(getActivity(),location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void placeMarker(String title,int resource,LatLng latlng){
        mMap.addMarker(new MarkerOptions().position(latlng).title(title).icon(BitmapFromVector(getContext(),resource)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    public void placeHelper(){

        locationUtil forMap=new locationUtil();
        Location currentLocation=forMap.getCurrentLocation(getActivity());

        Gson gson=new Gson();
        SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
        locationFormat locationRequest=new locationFormat(String.valueOf(currentLocation.getLatitude()),String.valueOf(currentLocation.getLongitude()));
        Call<getHelperResponse> call1 = apiInterface.connectSafe("Bearer "+user.getSession(),user.getProfileId(),locationRequest);
        call1.enqueue(new Callback<getHelperResponse>() {
            @Override
            public void onResponse(Call<getHelperResponse> call, Response<getHelperResponse> response) {
                getHelperResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,getContext());
                }else{
                    for(helperProfile i:res.data){
                        String displayInfo=i.firstName+","+i.age+","+i.gender;
                        LatLng helperLocation=new LatLng(parseDouble(i.lastActiveLocation.Lat),parseDouble(i.lastActiveLocation.Lng));

                        placeMarker(displayInfo,R.drawable.material_icons_location_black,helperLocation);
                    }
                }
            }

            @Override
            public void onFailure(Call<getHelperResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", getContext());
                call.cancel();
            }
        });
    }

    public void placeConnections(){
        Gson gson=new Gson();
        SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        for(userContact i:user.getConnections()){
            APIInterface apiInterface;
            apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
            Call<getConnectionLocationResponse> call1 = apiInterface.getConnectionLocation("Bearer "+user.getSession(),user.getProfileId(),i.UID);
            call1.enqueue(new Callback<getConnectionLocationResponse>() {
                @Override
                public void onResponse(Call<getConnectionLocationResponse> call, Response<getConnectionLocationResponse> response) {
                    getConnectionLocationResponse res=response.body();
                    if(!res.success){
                        new toast().handle_error(res.msg,getContext());
                    }else{
//                        new toast().handle_error("got location"+ i.contactName,getContext());
                        LatLng loc = new LatLng(parseDouble(res.data.Lat), parseDouble(res.data.Lng));
                        placeMarker(i.contactName+"'s Location",R.drawable.material_icons_location_blue,loc);
                    }
//                    mMap.addMarker(new MarkerOptions().position(loc).title(i.contactName+"'s Location").icon(BitmapFromVector(getContext(),R.drawable.material_icons_location_blue  )));
                }

                @Override
                public void onFailure(Call<getConnectionLocationResponse> call, Throwable t) {
                    new toast().handle_error("Connection Error", getContext());
                    call.cancel();
                }
            });
        }
    }


    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}