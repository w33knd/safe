package com.example.safe.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safe.EmergencyUtil.sendSosMessage;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;

import java.util.Timer;
import java.util.TimerTask;

public class DetectedEmergencyActivity extends AppCompatActivity {
    private int emergencyStep=0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeEmergencyNextStep(TextView nextStepInfo){
        switch(this.emergencyStep){
            case 0:
                emergencyStep++;
                changeImageAnimation((ImageView) findViewById(R.id.sosEmergencyImage2));
                nextStepInfo.setText("Connect To Safe Network");
                sendMessage();
                break;
            case 1:
                emergencyStep++;
                changeImageAnimation((ImageView) findViewById(R.id.connectEmegencyImage2));
                nextStepInfo.setText("Searching for Helper");
                Intent findingHelper = new Intent(DetectedEmergencyActivity.this,FindingHelperActivity.class);
                startActivity(findingHelper);
//                connectToSafe();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendMessage(){
        sendSosMessage sosMessage=new sendSosMessage();
        sosMessage.sendAll(this);
    }
    private void changeImageAnimation(ImageView v){
        v.setImageResource(R.drawable.emergency_circle_green);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_emergency);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        TextView countdownText= findViewById(R.id.countDownTextView);
        final Handler lHandler = new Handler();
        Runnable lRunnable = new Runnable() {
            int countdown=15;
            @Override
            public void run() {
                countdownText.setText(Integer.toString(countdown));
                if(countdown--==0){
                    changeEmergencyNextStep(findViewById(R.id.nextStepInfoDetected));
                    countdown=10;
                }
                lHandler.postDelayed(this, 1000);
            }
        };
        lHandler.post(lRunnable);
        Button okEventButton= findViewById(R.id.okButton);
        okEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lHandler.removeCallbacks((lRunnable));
            }
        });
    }
    @Override
    /*
        todo: make onpause end activity if activity is in background
     */
    protected void onPause() {
        super.onPause();
        finish();
    }

}