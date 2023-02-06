package com.example.safe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.example.safe.testing.toast;

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
    }
}