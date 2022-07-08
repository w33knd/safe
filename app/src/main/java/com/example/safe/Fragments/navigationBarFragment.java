package com.example.safe.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.safe.R;
import com.example.safe.testing.toast;

public class navigationBarFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_navigation, container, false);
        ImageButton homeButton=(ImageButton) view.findViewById(R.id.homeNavbarButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("home clicked",getActivity().getApplicationContext());
            }
        });
        ImageButton settingsButton=(ImageButton) view.findViewById(R.id.settingsNavbarButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("settings clicked",getActivity().getApplicationContext());
            }
        });
        ImageButton familyButton=(ImageButton) view.findViewById(R.id.familyNavbarButton);
        familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast t=new toast();
                t.handle_error("family clicked",getActivity().getApplicationContext());
            }
        });
        return view;
    }
}