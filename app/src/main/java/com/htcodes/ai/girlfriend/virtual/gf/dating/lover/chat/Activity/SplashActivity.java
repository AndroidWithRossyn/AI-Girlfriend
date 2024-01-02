package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;

import static com.facebook.ads.AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AppPreference;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.PreferClass;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.CheckUpdatePlay;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivitySplashBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    AppPreference appPreference;

    ActivitySplashBinding binding;
    Intent intent;
    Boolean isLogin = false;

    SplashActivity splash_Activity;


    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(SplashActivity.this, getString(R.string.Admob_Interstitial));
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   LoadANNABHAGYAAds();
        fetchAd();
    }

    SplashActivity splash_Activity2;

    RelativeLayout mainadsgone;
    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private Activity currentActivity = SplashActivity.this;
    private static boolean isShowingAd = false;
    private boolean isLoadingAd = false;
    String Openads;

    public void fetchAd() {

        if (isAdAvailable()) {
            return;
        }
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        appOpenAd = ad;
                        isLoadingAd = true;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        isLoadingAd = false;
                    }

                };
        final AdRequest request = getAdRequest();
        AppOpenAd.load(getApplicationContext(), currentActivity.getString(R.string.Admob_AppOpen), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null;
                    isShowingAd = false;
                    mainadsgone.setVisibility(View.GONE);
                    splash_Activity2.startActivity(splash_Activity2.intent);
                    SplashActivity.this.finish();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    splash_Activity2.startActivity(splash_Activity2.intent);
                    SplashActivity.this.finish();

                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                    mainadsgone.setVisibility(View.VISIBLE);
                }
            };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }
    }


    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        this.binding = (ActivitySplashBinding) DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setView();

        AudienceNetworkAds.initialize(this);

        AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        AdSettings.addTestDevice("cbe0f48c-47d1-4ac2-adb6-3b01befe2aba");
        AdsManager.getInstance().init(SplashActivity.this);

        mainadsgone = (RelativeLayout) findViewById(R.id.mainadsgone);
        PreferClass.setInteger(getApplicationContext(), PreferClass.showAds, 0);

    }

    private void setView() {
        this.appPreference = new AppPreference(this);

        Constants.saveSubscription(SplashActivity.this, "subscribe", true);
        Constants.saveSubscription(SplashActivity.this, "is_subscribe", true);
    }

    public void GetData() {
        SplashActivity.this.Next();
    }


    public void Next() {
        Application application = getApplication();

        startActivity();
    }

    private void startActivity() {
        this.isLogin = Constants.getBooleanDataFromSharedPref(this, Constants.IS_LOGIN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CheckUpdatePlay.getPreference(SplashActivity.this.getApplicationContext(), "firstTime", false)) {
                    if (SplashActivity.this.isLogin.booleanValue()) {
                        SplashActivity.this.getUserDetails();
                        return;
                    }
                    if (CheckUpdatePlay.getPreference(SplashActivity.this.getApplicationContext(), "firstTimeGuest", false)) {
                        SplashActivity.this.intent = new Intent(SplashActivity.this, AIChatActivity.class);
                        SplashActivity.this.intent.putExtra("guestChat", true);
                    } else {
                        SplashActivity.this.intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                    }

                    splash_Activity = SplashActivity.this;
                    splash_Activity.startActivity(splash_Activity.intent);
                    SplashActivity.this.finish();

                    /*AdsManager.getInstance().showInterstitialAd(SplashActivity.this, new AdsManager.AdCloseListener() {
                        @Override
                        public void onAdClosed() {
                            splash_Activity = SplashActivity.this;
                            splash_Activity.startActivity(splash_Activity.intent);
                            SplashActivity.this.finish();
                        }
                    });

                     */


                    return;
                }
                SplashActivity.this.intent = new Intent(SplashActivity.this, StartActivity.class);
                splash_Activity2 = SplashActivity.this;

                if (!isShowingAd && isAdAvailable()) {
                    showAdIfAvailable();
                } else {
                    splash_Activity2.startActivity(splash_Activity2.intent);
                    SplashActivity.this.finish();
                }


            }
        }, 6000);
    }

    private void showInternetPermission() {
        new NoInternetDialogSignal.Builder(this, getLifecycle()).build();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!Constants.isConnected(getApplicationContext())) {
            showInternetPermission();
        } else {
            GetData();
        }
    }


    public void getUserDetails() {
        StringRequest stringRequest = new StringRequest(0, URLs.GET_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String str) {
                try {
                    Log.e("==>", "onResponse: " + str);
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        if (jSONObject.getString("data").equals("null")) {
                            SplashActivity.this.intent = new Intent(SplashActivity.this, MainActivity.class);
                        } else {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                            Log.d("userdata==>", jSONObject2.toString());
                            UserDataModel userDataModel = new UserDataModel();
                            userDataModel.setId(jSONObject2.getString("id"));
                            userDataModel.setUserId(jSONObject2.getString("user_id"));
                            userDataModel.setUserName(jSONObject2.getString("user_name"));
                            userDataModel.setPronouns(jSONObject2.getString("pronouns"));
                            userDataModel.setName(jSONObject2.getString("name"));
                            userDataModel.setFlirty(jSONObject2.getString("flirty"));
                            userDataModel.setOptimistic(jSONObject2.getString("optimistic"));
                            userDataModel.setMysterious(jSONObject2.getString("mysterious"));
                            userDataModel.setInterests(jSONObject2.getString("interests"));
                            userDataModel.setGoals(jSONObject2.getString("goals"));
                            userDataModel.setRelax(jSONObject2.getString("relax"));
                            userDataModel.setImageId(jSONObject2.getString("image_id"));
                            Constants.saveDataInSharedPref(SplashActivity.this, Constants.USER_DATA, new Gson().toJson(userDataModel));
                            if (!userDataModel.getUserName().equals("null") && !userDataModel.getUserName().isEmpty()) {
                                if (!userDataModel.getPronouns().equals("null") && !userDataModel.getPronouns().isEmpty()) {
                                    if (!userDataModel.getName().equals("null") && !userDataModel.getName().isEmpty()) {
                                        if (!userDataModel.getImageId().equals("null") && !userDataModel.getImageId().isEmpty()) {
                                            if (!userDataModel.getFlirty().equals("null") && !userDataModel.getFlirty().isEmpty()) {
                                                if (!userDataModel.getOptimistic().equals("null") && !userDataModel.getOptimistic().isEmpty()) {
                                                    if (!userDataModel.getMysterious().equals("null") && !userDataModel.getMysterious().isEmpty()) {
                                                        if (!userDataModel.getInterests().equals("null") && !userDataModel.getInterests().isEmpty()) {
                                                            if (!userDataModel.getGoals().equals("null") && !userDataModel.getGoals().isEmpty()) {
                                                                if (!userDataModel.getRelax().equals("null") && !userDataModel.getRelax().isEmpty()) {
                                                                    SplashActivity.this.intent = new Intent(SplashActivity.this, AIChatActivity.class);
                                                                }
                                                                SplashActivity.this.intent = new Intent(SplashActivity.this, AIChatActivity.class);
                                                            }
                                                            SplashActivity.this.intent = new Intent(SplashActivity.this, AIChatActivity.class);
                                                        }
                                                        SplashActivity.this.intent = new Intent(SplashActivity.this, AIChatActivity.class);
                                                    }
                                                    SplashActivity.this.intent = new Intent(SplashActivity.this, TweakPersonalityActivity.class);
                                                }
                                                SplashActivity.this.intent = new Intent(SplashActivity.this, TweakPersonalityActivity.class);
                                            }
                                            SplashActivity.this.intent = new Intent(SplashActivity.this, TweakPersonalityActivity.class);
                                        }
                                        SplashActivity.this.intent = new Intent(SplashActivity.this, SelectAIActivity.class);
                                    }
                                    SplashActivity.this.intent = new Intent(SplashActivity.this, GirlNameActivity.class);
                                }
                                SplashActivity.this.intent = new Intent(SplashActivity.this, GenderActivity.class);
                            }
                            SplashActivity.this.intent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        SplashActivity splash_Activity = SplashActivity.this;
                        splash_Activity.startActivity(splash_Activity.intent);
                        SplashActivity.this.finish();
                        return;
                    }
                    SplashActivity.this.intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                    SplashActivity splash_Activity2 = SplashActivity.this;
                    splash_Activity2.startActivity(splash_Activity2.intent);
                    SplashActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    SplashActivity.this.intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                    SplashActivity splash_Activity3 = SplashActivity.this;
                    splash_Activity3.startActivity(splash_Activity3.intent);
                    SplashActivity.this.finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                SplashActivity.this.intent = new Intent(SplashActivity.this, GuestLoginActivity.class);
                SplashActivity splash_Activity = SplashActivity.this;
                splash_Activity.startActivity(splash_Activity.intent);
                SplashActivity.this.finish();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(SplashActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap);
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }


}
