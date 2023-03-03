package com.example.safe;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.safe.Account.userClass;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.EmergencyUtil.FallDetectService;
import com.example.safe.testing.toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //create shared preferences for emergency contact
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String data = userPreferences.getString("user",null);


        /**
         * temp user creation
         */
//        userClass user=new userClass();
//        user.addEmergencyContact("Trilok","9909402670","Son",getApplicationContext());
//        user.addEmergencyContact("Anil","8758912263","brother",getApplicationContext());
//        user.updateSharedPreferences(getApplicationContext());
        if(data!=null){
            Gson gson=new Gson();
            userClass user = gson.fromJson(data,userClass.class);
            if(!user.getSession().isEmpty()){
                //verify session and uid before starting and verifying
                Log.d("debug","fcm"+user.getFcmToken());


                startFallDetectService();
                switchActivity();
            }else{
                switchOnboarding();
            }
        }else{
            switchOnboarding();
        }
    }

    //start user fall detect service
    public void startFallDetectService(){
        Intent fallIntent = new Intent(this, FallDetectService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("currentActivity",this.getClass());
//        fallIntent.putExtras(bundle);
//        fallIntent.putExtra(FallDetectService.context, getApplicationContext());
        startService(fallIntent);
    }
    public void switchActivity(){
        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(i);
    }

    public void switchOnboarding(){
        Intent i=new Intent(getApplicationContext(), UserOnBoardingActivity.class);
        startActivity(i);
    }


    public void showmap(View v){
//        Intent i = new Intent(MainActivity.this, MapsActivity.class);
//        startActivity(i);
    }
}