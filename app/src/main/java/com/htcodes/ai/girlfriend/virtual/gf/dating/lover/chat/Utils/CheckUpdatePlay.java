package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AppOpenAds;
import com.facebook.ads.AudienceNetworkAds;
import com.firebase.client.Firebase;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;

public class CheckUpdatePlay extends Application {
    private AppOpenAds appOpenManager;
    private static final String TAG = CheckUpdatePlay.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        AudienceNetworkAds.isInitialized(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        Firebase.setAndroidContext(getApplicationContext());


        appOpenManager = new AppOpenAds(this);

    }

    public static boolean getPreference(Context context, String str, boolean z) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, z);
    }

    public static void setPreference(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static void clearPreference(Context context) {
        SharedPreferences.Editor clear = PreferenceManager.getDefaultSharedPreferences(context).edit().clear();
        clear.commit();
        clear.apply();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
