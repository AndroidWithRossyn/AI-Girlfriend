package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat;

import android.content.Context;
import android.content.SharedPreferences;


public class AppPreference {
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    String FirstTime = "FirstTime";

    public AppPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AdsKeyStore", 0);
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }


}
