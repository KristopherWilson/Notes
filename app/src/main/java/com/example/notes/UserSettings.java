package com.example.notes;

import android.app.Application;

public class UserSettings extends Application {


    public static final String PREFERENCES = "preferences";

    public static final String DESCRIPTION = "description";
    public static final String ON = "on";
    public static final String OFF = "off";

    private static String descToggle;

    public static String getDescToggle() {
        return descToggle;
    }

    public void setDescToggle(String descToggle) {
        this.descToggle = descToggle;
    }
}
