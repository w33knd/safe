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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.example.safe.testing.toast;

import org.w3c.dom.Text;

public class emergencyActivity extends AppCompatActivity {
//    private Timer timer;
    private Handler handler;
    private int emergencyStep=0;
    GestureDetector gestureDetector;
    boolean tapped;
    ImageView imageView;

// inside onCreate of Activity or Fragment
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

        gestureDetector = new GestureDetector(getApplicationContext(),new GestureListener());
        emergencyButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }
    int count=0;
    long t1=0,t2=0;
    class GestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
//            Log.d("debug","ondown");
            if(count == 0) {
                t1 = System.currentTimeMillis();
            }
            if(count == 1){
                t2 = System.currentTimeMillis();
            }
            count++;
            if(count > 1) count = 0;
            if(Math.abs(t2 - t1) < 900){
                t1 = t2 = 0; count = 0;
                // On double tap here. Do stuff
                changeEmergencyNextStep(findViewById(R.id.nextStepInfoText));
            }
            return true;
        }
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