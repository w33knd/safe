package com.example.safe.HttpRequest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.safe.R;
import com.example.safe.testing.toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class postRequest {
    public void get(String url,String requestData, Context c, String token  ){
//        url="https://reqres.in/api/users?page=2";
//
//        url="http://"+c.getResources().getString(R.string.hostname)+":"+c.getResources().getString(R.string.port)+url;
//        Log.d("tag",url);
//        final MediaType JSON
//                = MediaType.get("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(requestData, JSON);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .addHeader("Authorization", "Bearer " + token)
//                .build();
//        httpAsyncTask task=new httpAsyncTask(c);
//
//        try{
//            Response r = task.execute(request).get();
//            if(r!=null){
//                String json_response=r.body().string();            Log.d("tag",json_response);
//
//                JSONObject response=new JSONObject(json_response);
//                Log.d("tag",response.getString("success"));
//                new toast().handle_error(response.getString("success"),c);
//            }
//        }  catch(Exception E){
//            new toast().handle_error(E.toString(),c);
//        }
//    }
//    class httpAsyncTask extends AsyncTask<Request, Void, Response> {
//        Context curr;
//        Response response = null;
//        httpAsyncTask(Context c){
//            curr=c;
//        }
//        @Override
//        protected Response doInBackground(Request... requests) {
//            OkHttpClient client = new OkHttpClient();
//            try {
//                response = client.newCall(requests[0]).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(Response response) {
////            try {
//////                TextView txtRequest = (TextView) findViewById(R.id.textView);
//////                txtRequest.setText(response.body().string());
////                if(response!=null){
////                    new toast().handle_error(response.body().string(), curr);
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            super.onPostExecute(response);
//
//        }
    }
}
