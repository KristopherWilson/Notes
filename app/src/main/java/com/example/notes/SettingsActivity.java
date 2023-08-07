package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    static boolean descriptionBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch descToggle = (Switch) findViewById(R.id.desctoggle);

        if (descriptionBool)
        {
            descToggle.setChecked(true);
        }
        else {
            descToggle.setChecked(false);
        }

        descToggle.setOnClickListener(view -> {
            if (!descriptionBool) {
                descriptionBool = true;
                Toast.makeText(getApplicationContext(), "Descriptions ON", Toast.LENGTH_SHORT).show();
            } else {
                descriptionBool = false;
                Toast.makeText(getApplicationContext(), "Descriptions OFF", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isDescriptionBool() {
        return descriptionBool;
    }
}