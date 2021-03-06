package com.app.remote_controller_app.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.app.remote_controller_app.MainActivity;
import com.app.remote_controller_app.R;
import java.util.Locale;


public class Settings extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.action_settings));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    /* Metoda reagująca na wybranie nowej wartości w preferencjach */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("language_key")) {
            if(sharedPreferences.getString("language_key", "device").equals("device")) {
                if (!(Locale.getDefault().getLanguage()).equals(getResources().getConfiguration().locale.toString()))
                    getActivity().recreate();
            } else {
                if(!sharedPreferences.getString("language_key", "device").equals(getResources().getConfiguration().locale.toString()))
                    getActivity().recreate();
            }
        }
    }

}