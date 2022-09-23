package com.example.safe.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safe.EmergencyUtil.fallDetect;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.EmergencyUtil.sendSosMessage;
import com.example.safe.R;

import org.w3c.dom.Text;

public class emergencyActivity extends AppCompatActivity {
//    private Timer timer;
    private Handler handler;
    private int emergencyStep=0;
    @RequiresApi(api = Build.VERSION_CODES.M)
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
    @RequiresApi(api = Build.VERSION_CODES.M)
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
        fallDetect userState=new fallDetect();
        userState.initialize(this);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            Double lastClickTime=0.0;
            final Double DOUBLE_CLICK_TIME_DELTA=1000.0;
            boolean firstClick=false;
            boolean secondClick=false;
            @Override
            public void onClick(View view){
                double clickTime = System.currentTimeMillis();
                if(firstClick==false){
                    firstClick=true;
                    lastClickTime = clickTime;
                    return;
                }
                if(secondClick==false){
                    secondClick=true;
                    return;
                }
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    changeEmergencyNextStep(findViewById(R.id.nextStepInfoText));
                }else{
                    firstClick=secondClick=false;
                }
                lastClickTime = clickTime;
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
                        finish();
                    }
                });
        BackAlertDialog.show();
        return;
    }
}