package com.example.safe.Account.SignupLogin;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.safe.Account.userClass;
import com.example.safe.Activities.DashboardActivity;
import com.example.safe.HttpRequest.APIInterface;
import com.example.safe.HttpRequest.pojo.Success;
import com.example.safe.HttpRequest.pojo.userProfile;
import com.example.safe.HttpRequest.retrofitClass;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class completeProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_complete_profile, container, false);
        String[] countries= getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getContext(),R.layout.dropdown_countries,countries);
        AutoCompleteTextView autoCompleteTextView=v.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        Gson gson=new Gson();
        SharedPreferences userPreferences=getActivity().getSharedPreferences("context",MODE_PRIVATE);
        String userString=userPreferences.getString("user","");
        userClass user = gson.fromJson(userString,userClass.class);
        EditText birthDateEdit=v.findViewById(R.id.editBirthDate);
        Button pickDateButton=v.findViewById(R.id.pickDateButton);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                birthDateEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        Button nextStepButton=v.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText firstNameEditText=v.findViewById(R.id.firstNameEditText);
                String firstName=firstNameEditText.getText().toString();
                EditText lastNameEditText=v.findViewById(R.id.lastNameEditText);
                String lastName=lastNameEditText.getText().toString();
                EditText aadharNoEditText=v.findViewById(R.id.aadharNoEditText);
                String aadharNo=aadharNoEditText.getText().toString();
                EditText birthDateEdit=v.findViewById(R.id.editBirthDate);
                birthDateEdit.setFocusable(false);
                String birthDateString=birthDateEdit.getText().toString();
                TextInputLayout genderInput=v.findViewById(R.id.genderInputLayout);
                String gender=genderInput.getEditText().getText().toString();
                if(firstName!=null && !firstName.trim().isEmpty() && lastName!=null && !lastName.trim().isEmpty() && aadharNo!=null && !aadharNo.trim().isEmpty() && birthDateString!=null && !birthDateString.trim().isEmpty() && !gender.equals("Gender")){
//                    SimpleDateFormat df=new SimpleDateFormat("dd/mm/yyyy");
//                    try{
//                        String requestDate=df.parse(birthDateString).toString();
//                    }catch(ParseException E){
//                        new toast().handle_error("Birthdate is not in correct format",getContext());
//                    }
                    APIInterface apiInterface;
                    apiInterface = retrofitClass.getClient(getContext()).create(APIInterface.class);
                    userProfile profile=new userProfile(firstName,lastName,gender,birthDateString,aadharNo,user.getPostalCode());
                    Call<Success> call1 = apiInterface.updateUser("Bearer "+user.getSession(),user.getProfileId(),profile);
                    call1.enqueue(new Callback<Success>() {
                        @Override
                        public void onResponse(Call<Success> call, Response<Success> response) {
                            Success res=response.body();
                            if(res.success==false){
                                new toast().handle_error(res.msg,getContext());
                            }else{
                                user.setUserName(firstName+" "+lastName);
                                user.firstName=firstName;
                                user.updateSharedPreferences(getContext());
                                new toast().handle_error("Profile Updated Successfully",getContext());
//                                Intent i=new Intent(view.getContext(), DashboardActivity.class);
//                                startActivity(i);
                                ProgressBar progress=getActivity().findViewById(R.id.progressBar);
                                progress.setProgress(75);
                                getParentFragmentManager().beginTransaction().replace(R.id.signupStepLayout, addContactSignupFragment.class, null).commit();

                            }
                        }

                        @Override
                        public void onFailure(Call<Success> call, Throwable t) {
                            call.cancel();
                        }
                    });

                }else{
                    new toast().handle_error("Please enter all the fields", getContext());
                }


            }
        });
        return v;


    }
}