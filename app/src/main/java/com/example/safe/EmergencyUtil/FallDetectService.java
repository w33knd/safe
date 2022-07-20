package com.example.safe.EmergencyUtil;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class FallDetectService extends IntentService {
    private Activity currentActivity;
    public String userState=null;

    public FallDetectService() {
        super("FallDetectService");
    }



    public static void getCurrentState(Context context, String param1, String param2) {
        Intent intent = new Intent(context, FallDetectService.class);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
//        }

        fallDetect userState=new fallDetect();
        currentActivity=(Activity) intent.getExtras().getSerializable("currentActivity");
        userState.initialize(currentActivity);
    }
}