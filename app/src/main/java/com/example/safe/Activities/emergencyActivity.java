package com.example.safe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safe.EmergencyUtil.fallDetect;
import com.example.safe.Fragments.mapFragment;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.EmergencyUtil.sendSosMessage;
import com.example.safe.Location.locationUtil;
import com.example.safe.R;
import com.example.safe.testing.toast;

public class emergencyActivity extends AppCompatActivity {
//    private Timer timer;
    private Handler handler;
    private int noOfClicks=0;
    private int emergencyStep=0;
    private void changeEmergencyNextStep(TextView nextStepInfo){
        switch(this.emergencyStep){
            case 0:
                emergencyStep++;
                changeImageAnimation((ImageView) findViewById(R.id.sosEmergencyImage));
                nextStepInfo.setText("Connect To Safe Network");
                sendMessage();
                break;
            case 1:
                emergencyStep++;
                changeImageAnimation((ImageView) findViewById(R.id.connectEmegencyImage));
                nextStepInfo.setText("Searching for Helper");
                Intent findingHelper = new Intent(emergencyActivity.this,FindingHelperActivity.class);
                startActivity(findingHelper);
//                connectToSafe();
        }
    }
    private void changeImageAnimation(ImageView v){
        v.setImageResource(R.drawable.emergency_circle_green);
    }
    private void sendMessage(){
        sendSosMessage sosMessage=new sendSosMessage();
        sosMessage.sendAll(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        //start transition
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        ImageButton emergencyButton=(ImageButton) findViewById(R.id.mainEmergencyButton);
        emergencyButton.startAnimation(shake);
        TextView temp=findViewById(R.id.textView8);
        fallDetect userState=new fallDetect();
        userState.initialize(this);
        temp.setText(userState.curr_state);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                temp.setText(Integer.toString(noOfClicks));
                if(handler!=null){
//                    temp.setText(Integer.toString(noOfClicks));
                    emergencyButton.setMaxWidth(210);
                    if(noOfClicks++>=2){
                        changeEmergencyNextStep(findViewById(R.id.nextStepInfoText));
                        noOfClicks=0;
                    }
                }else{
                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            noOfClicks=0;
                            handler=null;
                        }
                    }, 4000);
                }
            }
        });
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



                        //Exit from activity.
                        finish();
                    }
                });
        BackAlertDialog.show();
//        super.onBackPressed();
        return;
    }
}