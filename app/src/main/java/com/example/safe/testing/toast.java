package com.example.safe.testing;

import android.content.Context;
import android.widget.Toast;

public class toast{
    public static void handle_error(String data,Context c){
//        Context context=getApplicationContext();
        int duration= Toast.LENGTH_LONG;
        Toast t=Toast.makeText(c,data,duration);
        t.show();
    }
}
