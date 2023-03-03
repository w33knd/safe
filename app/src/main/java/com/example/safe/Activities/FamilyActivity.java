package com.example.safe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.connectionPojo;
import com.example.safe.HttpRequest.pojo.contactPojo;
import com.example.safe.HttpRequest.pojo.getConnectionsResponse;
import com.example.safe.HttpRequest.pojo.getContactsResponse;
import com.example.safe.HttpRequest.pojo.getPendingRequestResponse;
import com.example.safe.HttpRequest.pojo.pendingRequestPojo;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyActivity extends AppCompatActivity {
    PendingArrayAdapter adapterPending;
    ContactArrayAdapter adapterContact;
    ConnectionArrayAdapter adapterConnection;
    public ArrayList<userContact> getPendingRequests(userClass user){
        ArrayList<userContact> pendingRequests=new ArrayList<>();
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(getApplicationContext()).create(APIInterface.class);
        Call<getPendingRequestResponse> call1 = apiInterface.getPendingRequests("Bearer "+user.getSession(),user.getProfileId());
        call1.enqueue(new Callback<getPendingRequestResponse>() {
            @Override
            public void onResponse(Call<getPendingRequestResponse> call, Response<getPendingRequestResponse> response) {
                getPendingRequestResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,getApplicationContext());
                }else{
                    for(pendingRequestPojo i: res.data){
                        userContact newContact=new userContact(i.name,i.contact,i.RequestID);
                        pendingRequests.add(newContact);
                    }
                    if(adapterPending!=null){
                        adapterPending.values=pendingRequests;
                        adapterPending.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<getPendingRequestResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", getApplicationContext());
                call.cancel();
            }
        });
        return pendingRequests;
    }

    public void updateContacts(userClass user,Context context){
        user.updateSharedPreferences(context);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        Call<getContactsResponse> call1 = apiInterface.getContacts("Bearer "+user.getSession(),user.getProfileId());
        call1.enqueue(new Callback<getContactsResponse>() {
            @Override
            public void onResponse(Call<getContactsResponse> call, Response<getContactsResponse> response) {
                getContactsResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,context);
                }else{
//                    new toast().handle_error("success" + res.data.size() + "contact",context);
                    ArrayList<userContact> contacts=user.getContacts();
                    contacts.clear();
                    for(contactPojo i: res.data){
                        userContact newContact=new userContact(i.name,i.email,i.contact,i.relation,i.age,i.ContactID);
//                        Log.d("debug",newContact.);
                        contacts.add(newContact);
                    }

                    user.updateSharedPreferences(context);
                    if(adapterContact!=null){
                        adapterContact.values=contacts;
                        adapterContact.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<getContactsResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
    }

    public void updateConnections(userClass user,Context context){
        user.updateSharedPreferences(context);
        APIInterface apiInterface;
        apiInterface = retrofitClass.getClient(context).create(APIInterface.class);
        Call<getConnectionsResponse> call1 = apiInterface.getConnections("Bearer "+user.getSession(),user.getProfileId());
        call1.enqueue(new Callback<getConnectionsResponse>() {
            @Override
            public void onResponse(Call<getConnectionsResponse> call, Response<getConnectionsResponse> response) {
                getConnectionsResponse res=response.body();
                if(res.success==false){
                    new toast().handle_error(res.msg,context);
                }else{
//                    new toast().handle_error("success"+ res.data.size()+"connections",context);
                    ArrayList<userContact> connections=user.getConnections();
                    connections.clear();
                    for(connectionPojo i: res.data){
                        userContact newContact=new userContact(i.name,i.contact,i.uid);
//                        Log.d("debug",newContact.contactName);
                        connections.add(newContact);
                    }
                    user.updateSharedPreferences(context);
                    adapterConnection.values=connections;
                    adapterConnection.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<getConnectionsResponse> call, Throwable t) {
                new toast().handle_error("Connection Error", context);
                call.cancel();
            }
        });
    }

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

        final ListView listviewContact = (ListView) findViewById(R.id.contactSignupRow);
        ArrayList<userContact> contacts=user.getContacts();
        adapterContact = new ContactArrayAdapter(this,R.layout.contact_row_layout, contacts);
        updateContacts(user,getApplicationContext());
        listviewContact.setAdapter(adapterContact);

        updateConnections(user,getApplicationContext());
        final ListView listviewConnection = (ListView) findViewById(R.id.connectionSettingRow);
        ArrayList<userContact> connections=user.getConnections();
        adapterConnection = new ConnectionArrayAdapter(this,R.layout.contact_row_layout, connections);
        listviewConnection.setAdapter(adapterConnection);

        ArrayList<userContact> pendingRequests=getPendingRequests(user);
        final ListView listviewPendingRequest = (ListView) findViewById(R.id.pendingRequestAccept);
        adapterPending = new PendingArrayAdapter(this,R.layout.contact_row_layout, pendingRequests);
        listviewPendingRequest.setAdapter(adapterPending);


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
                        user.addEmergencyContact(contactName,mobile,relation,18,getApplicationContext());
                        adapterContact.values=user.getContacts();
                        adapterContact.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    private class ConnectionArrayAdapter extends BaseAdapter {
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

        public ConnectionArrayAdapter(Context context, int textViewResourceId,
                                  ArrayList<userContact> objects) {
//            refresh();
            this.context=context;
            this.values=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.connection_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.contactNameText);
            textView.setText(values.get(position).contactName);
            TextView contactNumber=(TextView) rowView.findViewById(R.id.contactNumberId);
            contactNumber.setText(values.get(position).mobileNo);
            TextView uidConnection=(TextView) rowView.findViewById(R.id.uidConnectionText);
            uidConnection.setText(values.get(position).UID);
            ImageButton deleteContactButton=(ImageButton) rowView.findViewById(R.id.deleteConnectionButton);
//            deleteContactButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Gson gson=new Gson();
//                    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
//                    String userString=userPreferences.getString("user","");
//                    userClass user = gson.fromJson(userString,userClass.class);
//                    if(!user.removeEmergencyContact(getItem(position), getApplicationContext())){
//                        toast t=new toast();
//                        t.handle_error("data remove error",getApplicationContext());
//                    }
//
//                    refresh();
//                }
//            });
            return rowView;
        }
        public void refresh(){
            Gson gson=new Gson();
            SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
            String userString=userPreferences.getString("user","");
            userClass user = gson.fromJson(userString,userClass.class);
            user.updateConnections(context);
            values=user.getContacts();
            this.notifyDataSetChanged();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    private class ContactArrayAdapter extends BaseAdapter {
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

        public ContactArrayAdapter(Context context, int textViewResourceId,
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
            TextView contactIdText=(TextView) rowView.findViewById(R.id.contactIdText);
            contactIdText.setText(values.get(position).ContactID);
            ImageButton deleteContactButton=(ImageButton) rowView.findViewById(R.id.deleteContactButton);
            deleteContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson=new Gson();
                    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
                    String userString=userPreferences.getString("user","");
                    userClass user = gson.fromJson(userString,userClass.class);
//                    if(!user.removeEmergencyContact(getItem(position), getApplicationContext())){
//                        toast t=new toast();
//                        t.handle_error("data remove error",getApplicationContext());
//                    }
                    user.removeEmergencyContact(values.get(position).ContactID,context);

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
    private class PendingArrayAdapter extends BaseAdapter {
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

        public PendingArrayAdapter(Context context, int textViewResourceId,
                                   ArrayList<userContact> objects) {
            this.context=context;
            this.values=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.pending_connection_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.contactNameText);
            textView.setText(values.get(position).contactName);
            TextView contactNumber=(TextView) rowView.findViewById(R.id.contactNumberId);
            contactNumber.setText(values.get(position).mobileNo);
            ImageButton acceptRequestButton=(ImageButton) rowView.findViewById(R.id.acceptRequestButton);
            ImageButton deleteRequestButton=(ImageButton) rowView.findViewById(R.id.deleteRequestButton);
            acceptRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson=new Gson();
                    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
                    String userString=userPreferences.getString("user","");
                    userClass user = gson.fromJson(userString,userClass.class);
//                    if(!user.removeEmergencyContact(getItem(position), getApplicationContext())){
//                        toast t=new toast();
//                        t.handle_error("data remove error",getApplicationContext());
//                    }
                    APIInterface apiInterface;
                    apiInterface = retrofitClass.getClient(getApplicationContext()).create(APIInterface.class);
                    Call<Success> call1 = apiInterface.acceptRequest("Bearer "+user.getSession(),user.getProfileId(),values.get(position).UID);
                    call1.enqueue(new Callback<Success>() {
                        @Override
                        public void onResponse(Call<Success> call, Response<Success> response) {
                            Success res=response.body();
                            if(res.success==false){
                                new toast().handle_error(res.msg,getApplicationContext());
                            }else{
                                new toast().handle_error("Success",getApplicationContext());
                                getPendingRequests(user);
                                updateConnections(user,context);
                            }
                        }

                        @Override
                        public void onFailure(Call<Success> call, Throwable t) {
                            new toast().handle_error("Connection Error", getApplicationContext());
                            call.cancel();
                        }
                    });

                    refresh();
                }
            });
            deleteRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson=new Gson();
                    SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
                    String userString=userPreferences.getString("user","");
                    userClass user = gson.fromJson(userString,userClass.class);
                    APIInterface apiInterface;
                    apiInterface = retrofitClass.getClient(getApplicationContext()).create(APIInterface.class);
                    Call<Success> call1 = apiInterface.deleteRequest("Bearer "+user.getSession(),user.getProfileId(),values.get(position).UID);
                    call1.enqueue(new Callback<Success>() {
                        @Override
                        public void onResponse(Call<Success> call, Response<Success> response) {
                            Success res=response.body();
                            if(res.success==false){
                                new toast().handle_error(res.msg,getApplicationContext());
                            }else{
                                new toast().handle_error("Success",getApplicationContext());
                                 getPendingRequests(user);
                            }
                        }

                        @Override
                        public void onFailure(Call<Success> call, Throwable t) {
                            new toast().handle_error("Connection Error", getApplicationContext());
                            call.cancel();
                        }
                    });

                    refresh();
                }
            });
            return rowView;
        }
        public void refresh(){
            this.notifyDataSetChanged();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}