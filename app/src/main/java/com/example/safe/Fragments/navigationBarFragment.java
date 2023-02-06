package com.example.safe.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.safe.Activities.DashboardActivity;
import com.example.safe.Activities.FamilyActivity;
import com.example.safe.SettingsActivity;
import com.example.safe.R;

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
//                toast t=new toast();
//                t.handle_error("home clicked",getActivity().getApplicationContext());
                if(view.getContext().getClass()==DashboardActivity.class){
                    return;
                }
                Intent i=new Intent(view.getContext(), DashboardActivity.class);
                startActivity(i);
            }
        });
        ImageButton settingsButton=(ImageButton) view.findViewById(R.id.settingsNavbarButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast t=new toast();
//                t.handle_error("settings clicked",getActivity().getApplicationContext());
                if(view.getContext().getClass()==SettingsActivity.class){
                    return;
                }
                Intent i=new Intent(view.getContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
        ImageButton familyButton=(ImageButton) view.findViewById(R.id.familyNavbarButton);
        familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast t=new toast();
//                t.handle_error("family clicked",getActivity().getApplicationContext());
                if(view.getContext().getClass()== FamilyActivity.class){
                    return;
                }
                Intent i=new Intent(view.getContext(), FamilyActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}