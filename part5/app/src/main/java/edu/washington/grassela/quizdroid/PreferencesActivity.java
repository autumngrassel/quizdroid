package edu.washington.grassela.quizdroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;



public class PreferencesActivity extends PreferenceActivity {

    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add the xml resource
        addPreferencesFromResource(R.xml.user_settings);
    }
}
