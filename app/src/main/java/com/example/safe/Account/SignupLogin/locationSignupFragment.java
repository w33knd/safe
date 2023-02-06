package com.example.safe.Account.SignupLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.safe.Account.userClass;
import com.example.safe.R;
import com.example.safe.testing.toast;
import com.google.android.material.textfield.TextInputLayout;


public class locationSignupFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_location_signup, container, false);
        String[] countries= getResources().getStringArray(R.array.countries);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getContext(),R.layout.dropdown_countries,countries);
        AutoCompleteTextView autoCompleteTextView=v.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
        Button nextStepButton= v.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView postalCodeEditText=v.findViewById(R.id.postalCodeEditText);
                String postalCode=postalCodeEditText.getText().toString();
                TextInputLayout countryInput=v.findViewById(R.id.countryInputLayout);
                String country=countryInput.getEditText().getText().toString();
                if(postalCode!=null && !postalCode.trim().isEmpty()){
                    if(!country.equals("Country")){
                        userClass user=new userClass();
                        user.setPostalCode(postalCode);
                        if(country.equals("India")){
                            user.phoneExtension="+91";
                        }
                        user.updateSharedPreferences(getContext());
                    }else{
                        new toast().handle_error("Enter your country",view.getContext());
                        return;
                    }
                }else{
                    new toast().handle_error("Empty postal Code",view.getContext());
                    return;
                }
                ProgressBar progress=getActivity().findViewById(R.id.progressBar);
                progress.setProgress(50);
                getParentFragmentManager().beginTransaction().replace(R.id.signupStepLayout, emailSignupFragment.class, null).commit();
            }
        });
        return v;
    }
}