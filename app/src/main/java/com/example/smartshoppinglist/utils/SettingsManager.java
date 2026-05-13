package com.example.smartshoppinglist.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {

    private final SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "app_settings";
    private static final String CONFIRM_DELETE_KEY = "confirm_delete";

    public SettingsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isConfirmDeleteEnabled() {
        return sharedPreferences.getBoolean(CONFIRM_DELETE_KEY, true);
    }

    public void saveConfirmDeleteEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(CONFIRM_DELETE_KEY, enabled).apply();
    }
}