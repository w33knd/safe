package com.example.safe.EmergencyUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.safe.Account.userClass;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.MainActivity;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//https://stackoverflow.com/questions/27033225/checking-server-response-periodically
public class sosBackgroundService extends FirebaseMessagingService {
    String TAG="debug";
//    String BearerToken;
//    public sosBackgroundService(userClass user) {
////        BearerToken=user.getSession();
//
//    }
    @Override
    public void onNewToken(@NonNull String token)
    {
        Gson gson=new Gson();
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        user.setFcmToken(token,getApplicationContext());

//        user.set
        Log.d(TAG, "Refreshed token: " + token);
    }

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
////                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
////                handleNow();
//            }
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//    }
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        Gson gson=new Gson();
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);

        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        if(remoteMessage.getData().size()>0){
            Log.d("debug","here remote message");
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }

        // Second case when notification payload is
        // received.
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly
            // from FCM, the title and the body can be
            // fetched directly as below.
            Log.d("debug","message received "+ remoteMessage.getNotification().getTitle());

            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    // Method to get the custom Design for the display of
    // notification.
    private RemoteViews getCustomDesign(String title,
                                        String message)
    {
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification_test);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
//        remoteViews.setImageViewResource(R.id.icon,
//                R.drawable.);
        return remoteViews;
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message)
    {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, MainActivity.class);
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        if(NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled()){

            NotificationCompat.Builder builder
                    = new NotificationCompat
                    .Builder(getApplicationContext(),
                    channel_id)
                    .setSmallIcon(R.drawable.emergency_circle)
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 1000, 1000, 1000,
                            1000, 1000 })
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager
                    = (NotificationManager)getSystemService(
                    Context.NOTIFICATION_SERVICE);
            // Check if the Android Version is greater than Oreo
            if (Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel
                        = new NotificationChannel(
                        channel_id, "web_app",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(
                        notificationChannel);
            }

            if (Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.JELLY_BEAN) {
                builder = builder.setContent(
                        getCustomDesign(title, message));
            } // If Android Version is lower than Jelly Beans,
            // customized layout cannot be used and thus the
            // layout is set as follows
            else {
                builder = builder.setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.emergency_circle);
            }
            notificationManager.notify(0, builder.build());

        }

        // A customized design for the notification can be
        // set only for Android versions 4.1 and above. Thus
        // condition for the same is checked here.

        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.

    }
}