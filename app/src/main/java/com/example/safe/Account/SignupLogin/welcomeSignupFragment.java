package com.example.safe.Account.SignupLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.safe.R;

public class welcomeSignupFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Button nextStepButton= getView().findViewById(R.id.nextStepButton);
//        nextStepButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ProgressBar progress=getActivity().findViewById(R.id.progressBar);
//                progress.setProgress(20);
//                getParentFragmentManager().beginTransaction().replace(R.id.signupStepLayout, locationSignupFragment.class, null).commit();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_welcome_signup, container, false);
        Button nextStepButton= v.findViewById(R.id.nextStepButton);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar progress=getActivity().findViewById(R.id.progressBar);
                progress.setProgress(25);
                getParentFragmentManager().beginTransaction().replace(R.id.signupStepLayout, locationSignupFragment.class, null).commit();
            }
        });
        return v;
    }
}