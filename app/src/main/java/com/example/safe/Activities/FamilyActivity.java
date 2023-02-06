package com.example.safe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.safe.Account.userClass;
import com.example.safe.Account.userContact;
import com.example.safe.Fragments.navigationBarFragment;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.navigationFrameLayout, navigationBarFragment.class, null).commit();
        Gson gson=new Gson();
        SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        final ListView listview = (ListView) findViewById(R.id.make_contact_signup_list);
        ArrayList<userContact> contacts=user.getContacts();
        final StableArrayAdapter adapter = new StableArrayAdapter(this,R.layout.contact_row_layout, contacts);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final userContact item = (userContact) parent.getItemAtPosition(position);
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

        //implement add button in contacts
        FloatingActionButton addContact=findViewById(R.id.addContactButton);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.contact_add_edit_layout, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height,focusable);
//                popupWindow.showAsDropDown(view, Gravity.CENTER, Gravity.CENTER);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.findViewById(R.id.saveNewContactButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText contactView=popupView.findViewById(R.id.newContactNameText);
                        String contactName=contactView.getText().toString();
                        EditText mobileView=popupView.findViewById(R.id.newContactMobileText);
                        String mobile=mobileView.getText().toString();
                        EditText relationView=popupView.findViewById(R.id.newContactRelationText);
                        String relation=relationView.getText().toString();
                        if(contactName.length()==0 || mobile.length()==0|| relation.length()==0){
                            toast t=new toast();
                            t.handle_error("Please fill appropriate values",getApplicationContext());
                            return;
                        }
                        user.addEmergencyContact(contactName,mobile,relation,getApplicationContext());
                        adapter.refresh();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    private class StableArrayAdapter extends BaseAdapter {
        private final Context context;
        private ArrayList<userContact> values;

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public userContact getItem(int position) {
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  ArrayList<userContact> objects) {
//            refresh();
            this.context=context;
            this.values=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.contact_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.contactNameText);
            textView.setText(values.get(position).contactName);
            TextView relation=(TextView) rowView.findViewById(R.id.contactNameRelation);
            relation.setText(values.get(position).relation);
            TextView contactNumber=(TextView) rowView.findViewById(R.id.contactNumberId);
            contactNumber.setText(values.get(position).mobileNo);
            ImageButton deleteContactButton=(ImageButton) rowView.findViewById(R.id.deleteContactButton);
            deleteContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson=new Gson();
                    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
                    String userString=userPreferences.getString("user","");
                    userClass user = gson.fromJson(userString,userClass.class);
                    if(!user.removeEmergencyContact(getItem(position), getApplicationContext())){
                        toast t=new toast();
                        t.handle_error("data remove error",getApplicationContext());
                    }

                    refresh();
                }
            });
            return rowView;
        }
        public void refresh(){
            Gson gson=new Gson();
            SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
            String userString=userPreferences.getString("user","");
            userClass user = gson.fromJson(userString,userClass.class);
            values=user.getContacts();
            this.notifyDataSetChanged();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}