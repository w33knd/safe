package com.example.safe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safe.EmergencyUtil.userClass;
import com.example.safe.EmergencyUtil.userContact;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Gson gson=new Gson();
    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
//        SharedPreferences.Editor userEditor= getPreferences(MODE_PRIVATE).edit();
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
//        for(userContact contact: user.getContacts()){
//
//        }
//        toast t=new toast();
//        t.handle_error(user.userName,getApplicationContext());
//        TextView element=findViewById(R.id.privacySettingText);
////
//        element.setText(user.getContacts().get(0).contactName);
        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.contact_row_layout, values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
//                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
        private final Context context;
        private final String[] values;
        public StableArrayAdapter(Context context, int textViewResourceId,
                                  String[] objects) {
            super(context, textViewResourceId, objects);
//            for (int i = 0; i < objects.size(); ++i) {
//                mIdMap.put(objects.get(i), i);
//
//            }
            this.context=context;
            this.values=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.contact_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.contactNameText);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.contactIcon);
            textView.setText(values[position]);
            // Change the icon for Windows and iPhone
            String s = values[position];
            if (s.startsWith("Windows7") || s.startsWith("iPhone")
                    || s.startsWith("Solaris")) {
                imageView.setImageResource(R.drawable.emergency_circle);
            } else {
                imageView.setImageResource(R.drawable.emergency_circle_black);
            }

            return rowView;
        }

//        @Override
//        public long getItemId(int position) {
////            String item = getItem(position);
////            return mIdMap.get(item);
//        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}