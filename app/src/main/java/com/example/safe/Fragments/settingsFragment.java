package com.example.safe.Fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.safe.R;

public class settingsFragment extends PreferenceFragment {
    private ListPreference mListPreference;

//    ...
    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        // Load the preferences from our XML file.
//        addPreferencesFromResource(R.xml.settings);
    }

//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//        // Indicate here the XML resource you created above that holds the preferences
//        setPreferencesFromResource(R.xml.settings, rootKey);
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
////        mListPreference = (ListPreference)  getPreferenceManager().findPreference("preference_key");
////        mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
////            @Override
////            public boolean onPreferenceChange(Preference preference, Object newValue) {
////                // your code here
////            }
////        });
//
////        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
}
