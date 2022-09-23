package com.example.safe.EmergencyUtil;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.example.safe.testing.toast;

public class FallDetectService extends Service {
    int mStartMode;
    IBinder mBinder;
    boolean mAllowRebind;
    public FallDetectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toast t=new toast();
        t.handle_error("service is on",getApplicationContext());
        fallDetect user=new fallDetect();
        user.initialize(getApplicationContext());
        return mStartMode;
    }

    @Override
    public void onDestroy() {
        toast t=new toast();
        t.handle_error("service is dead",getApplicationContext());
    }
}