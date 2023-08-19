package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private UserSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = (UserSettings) getApplication();

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch descToggle = (Switch) findViewById(R.id.desctoggle);
        loadSharedPrefs();
        String desc = UserSettings.getDescToggle();

        descToggle.setChecked(desc.equals("on"));

        descToggle.setOnClickListener(view -> {
            if (desc.equals("on")) {
                descToggle.setChecked(false);
                settings.setDescToggle(UserSettings.OFF);
                Toast.makeText(getApplicationContext(), "Descriptions OFF", Toast.LENGTH_SHORT).show();
            } else {
                descToggle.setChecked(true);
                settings.setDescToggle(UserSettings.ON);
                Toast.makeText(getApplicationContext(), "Descriptions ON", Toast.LENGTH_SHORT).show();
            }

            SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES,MODE_PRIVATE).edit();
            editor.putString(UserSettings.DESCRIPTION, UserSettings.getDescToggle());
            editor.apply();
        });
    }

    private void loadSharedPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        String description = sharedPreferences.getString(UserSettings.DESCRIPTION, UserSettings.ON);
        settings.setDescToggle(description);
    }
}