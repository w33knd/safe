package com.example.safe.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.safe.EmergencyUtil.FallDetectService;
import com.example.safe.EmergencyUtil.userClass;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create shared preferences for emergency contact
        /**
         * TODO add this to correct preferences settings
         */
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        SharedPreferences.Editor  editUser=userPreferences.edit();
        userClass user=new userClass();
        user.addEmergencyContact("Trilok","9909402670","Son");
//        toast t=new toast();
//        t.handle_error(user.userName,getApplicationContext());
        Gson gson=new Gson();
        String userString=gson.toJson(user);
        editUser.putString("user",userString);
        editUser.commit();

        startFallDetectService();
        //show if login is already done and welcomed
        //make navigation bottom frame layout
        switchActivity ();
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


    public void showmap(View v){
//        Intent i = new Intent(MainActivity.this, MapsActivity.class);
//        startActivity(i);
    }
    public void sendLocation(View v){
        OkHttpClient client = new OkHttpClient();
        String url="https://reqres.in/api/users?page=2";
        Request request = new Request.Builder()
                .url(url)
                .build();

        new MyAsyncTask().execute(request);
    }


    class MyAsyncTask extends AsyncTask<Request, Void, Response> {

        @Override
        protected Response doInBackground(Request... requests) {
            Response response = null;
            OkHttpClient client = new OkHttpClient();
            try {
                response = client.newCall(requests[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            try {
                TextView txtRequest = (TextView) findViewById(R.id.textView);
                txtRequest.setText(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}