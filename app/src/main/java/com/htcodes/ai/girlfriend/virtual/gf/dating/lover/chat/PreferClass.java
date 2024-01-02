package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferClass {
    public static final String MY_PREFS_NAME = "fuelCity";
    private static final String PREFERENCE_NAME = "AdsID";
    public static String showAds = "showAds";
    public static String interval = "interval";

    public static int getInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(key)) {
            return preferences.getInt(key, 0);
        }
        return 0;
    }
    public static int getInterval(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (preferences.contains(key)) {
            return preferences.getInt(key, 3);
        }
        return 3;
    }

    public static void setInteger(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key,value);
        editor.apply();
    }

}