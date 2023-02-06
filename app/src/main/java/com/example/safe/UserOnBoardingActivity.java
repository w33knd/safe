package com.example.safe;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.safe.R;
//
//public class UserOnBoardingActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_on_boarding);
//    }
//}
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.safe.Account.SignupLogin.LoginActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.example.safe.R;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class UserOnBoardingActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_on_boarding);

        fragmentManager = getSupportFragmentManager();

        // new instance is created and data is took from an
        // array list known as getDataonborading
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // fragmentTransaction method is used
        // do all the transactions or changes
        // between different fragments
        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment);

        // all the changes are committed
        fragmentTransaction.commit();
        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        // the first string is to show the main title ,
        // second is to show the message below the
        // title, then color of background is passed ,
        // then the image to show on the screen is passed
        // and at last icon to navigate from one screen to other
        PaperOnboardingPage source = new PaperOnboardingPage("Safe Family", "Add your family contacts to stay connected with the network.", Color.parseColor("#ffb174"),R.drawable.ic_baseline_family_restroom_24, R.drawable.emergency_circle);
        PaperOnboardingPage source1 = new PaperOnboardingPage("Seek help", "Ask for help from safe network or send distress signal to your emergency contacts.", Color.parseColor("#22eaaa"),R.drawable.ic_baseline_home_24, R.drawable.emergency_circle);
        PaperOnboardingPage source2 = new PaperOnboardingPage("Notify", "Get notified when a person around you or your family seems to be in danger.", R.color.app_beau_blue   ,R.drawable.ic_baseline_notifications_active_24, R.drawable.emergency_circle);

        // array list is used to store
        // data of onbaording screen
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        // all the sources(data to show on screens)
        // are added to array list
        elements.add(source);
        elements.add(source1);
        elements.add(source2);
        return elements;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return;
    }
}
