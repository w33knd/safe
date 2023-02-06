package com.example.safe.Account.SignupLogin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.safe.Account.userClass;
import com.example.safe.Account.userContact;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.Activities.FamilyActivity;
import com.example.safe.R;
import com.example.safe.SettingsActivity;
import com.example.safe.testing.toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.safe.testing.toast;
public class addContactSignupFragment extends Fragment {
    ArrayList<localContact> contacts=new ArrayList<>();
    Map<String, String> namePhoneMap = new HashMap<String, String>();
    public class localContact{
        public String contactName;
        public String mobileNo;
        localContact(String name,String mobile){
            this.contactName=name;
            this.mobileNo=mobile;
        }
    }
    private void getPhoneNumbers() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            toast t=new toast();
            t.handle_error("Permission is not granted",getContext());
            return;
        }
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Loop Through All The Numbers
        while (phones.moveToNext()) {
            int nameColumn=phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneNumberColumn=phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name="",phoneNumber="";
            if(nameColumn!=-1 && phoneNumberColumn!=-1){
                name = phones.getString(nameColumn);
                phoneNumber = phones.getString(phoneNumberColumn);
            }
            phoneNumber = phoneNumber.replaceAll("[()\\s-]+", "");
            localContact c= new localContact(name,phoneNumber);
            contacts.add(c);
        }
        phones.close();
    }

    /**
     * TODO: onresultpermission to refresh contact list
     */
    public void showDialog() {
        int PERMISSION_REQUEST_CONTACT=1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Contacts access needed");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setMessage("please confirm Contacts access");
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Only call the permission request api on Android M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getActivity().requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CONTACT);
                }else{
                    getPhoneNumbers();
                }
             }
        });

        builder.show();
    }
    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                //if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                    showDialog();
                //}
            }
        } else {
            showDialog();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_contact_signup, container, false);
        final ListView listview = (ListView) v.findViewById(R.id.make_contact_signup_list);
        askForContactPermission();
        getPhoneNumbers();
        final addContactSignupFragment.StableArrayAdapter adapter = new addContactSignupFragment.StableArrayAdapter(getActivity(),R.layout.new_contact_signup_row_layout, contacts);
        listview.setAdapter(adapter);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
//            }
//
//        });
        Button nextStepButton= v.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar progress=getActivity().findViewById(R.id.progressBar);
                Intent i=new Intent(view.getContext(), DashboardActivity.class);
                startActivity(i);
            }
        });
        return v;

    }
    private class StableArrayAdapter extends BaseAdapter {
        private final Context context;
        private ArrayList<localContact> values;

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public localContact getItem(int position) {
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  ArrayList<localContact> objects) {
//            refresh();
            this.context=context;
            this.values=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.new_contact_signup_row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.contactNameText);
            textView.setText(values.get(position).contactName);
            TextView contactNumber=(TextView) rowView.findViewById(R.id.contactNumberId);
            contactNumber.setText(values.get(position).mobileNo);
            TextView deleteContactButton= rowView.findViewById(R.id.addConnectionTextButton);
            deleteContactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                }
            });
            return rowView;
        }
        public void refresh(){
//            Gson gson=new Gson();
//            SharedPreferences userPreferences=getSharedPreferences("context",MODE_PRIVATE);
//            String userString=userPreferences.getString("user","");
//            userClass user = gson.fromJson(userString,userClass.class);
//            values=user.getContacts();
//            this.notifyDataSetChanged();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}