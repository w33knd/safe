package com.example.safe.EmergencyUtil;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.safe.testing.toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class sendSosMessage {
    private boolean smsSendingPermission=false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean sendAll(Activity currentActivity) {
        //get emergency contact from emergencyutil

        //check permissions
        Context currentContext=currentActivity.getApplicationContext();
        if (currentContext.checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            smsSendingPermission=true;
        }else if (!ActivityCompat.shouldShowRequestPermissionRationale(currentActivity, Manifest.permission.SEND_SMS)) {
            AlertDialog.Builder smsPermission = new AlertDialog.Builder(currentContext);
            smsPermission.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                    toast.makeText(this,"enter a text here",Toast.LENTH_SHORT).show();
                }
            });
            smsPermission.create();
        }else {
            requestPermissions(currentActivity, new String[]{Manifest.permission.SEND_SMS}, 2);
            smsSendingPermission=true;
        }
        if(!smsSendingPermission){
            new toast().handle_error("Permission Denied. Please enable in settings",currentContext);
            return false;
        }
//        String phoneNumber="9909402670";
        SharedPreferences userPreferences=currentActivity.getApplicationContext().getSharedPreferences("context",MODE_PRIVATE);
        String userClassString=userPreferences.getString("user","");
        JsonObject user=new Gson().fromJson(userClassString,JsonObject.class);
        JsonArray userContacts=user.get("contacts").getAsJsonArray();
        String message="emergency";


        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(currentContext, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(currentContext, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        currentContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(currentContext, "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(currentContext, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(currentContext, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(currentContext, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(currentContext, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        currentContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(currentContext, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(currentContext, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        for(int i=0;i<userContacts.size();i++){
            JsonObject userContact=new Gson().fromJson(userContacts.get(i).toString(),JsonObject.class);
            Log.d("debug",userContact.get("contactName").toString());
            String phoneNumber=userContact.get("mobileNo").toString();
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        }
        return true;
    }
}
