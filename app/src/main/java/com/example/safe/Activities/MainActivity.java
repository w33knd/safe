package com.example.safe.Activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.safe.EmergencyUtil.FallDetectService;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;

import java.io.IOException;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //show if login is already done and welcomed
        //make navigation bottom frame layout
        switchActivity ();
    }
    //start user fall detect service
    public void startFallDetectService(){
        Intent msgIntent = new Intent(this, FallDetectService.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("currentActivity",this.getClass());
        msgIntent.putExtras(bundle);
//        msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
        startService(msgIntent);
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