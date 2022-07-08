package com.example.safe.Fragments;

import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.safe.testing.toast;
import com.example.safe.Location.locationUtil;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class mapFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map, container, false);
        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapCurrentLocationID);

        GoogleMapOptions options = new GoogleMapOptions();

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);


        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
//                if(getArguments()!=null){
//                    Location currentLocation=getArguments().get
//                }
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
                googleMap.setMinZoomPreference(12.0f);
                googleMap.setBuildingsEnabled(true);
                Location currentLocation=null;
                if(getArguments()!=null){
                    currentLocation=getArguments().getParcelable("currentLocation");
                }
                LatLng current;
                if(currentLocation!=null){
                    current=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                }else{
                    current = new LatLng(-33.852, 151.211);
                }
//                LatLng current = new LatLng(-33.852, 151.211);
                googleMap.addMarker(new MarkerOptions()
                        .position(current)
                        .title("Current Location Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));

//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        // When clicked on map
//                        // Initialize marker options
//                        MarkerOptions markerOptions=new MarkerOptions();
//                        // Set position of marker
//                        markerOptions.position(latLng);
//                        // Set title of marker
//                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
//                        // Remove all marker
//                        googleMap.clear();
//                        // Animating to zoom the marker
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
////                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                        // Add marker on map
//                        googleMap.addMarker(markerOptions);
//
//                    }
//                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}