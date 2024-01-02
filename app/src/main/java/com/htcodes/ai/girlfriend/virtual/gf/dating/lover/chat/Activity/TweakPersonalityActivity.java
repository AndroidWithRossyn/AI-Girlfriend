package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityTweakPersonalityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TweakPersonalityActivity extends AppCompatActivity {
    ActivityTweakPersonalityBinding binding;
    Intent intent;
    Dialog mWaitDialog;
    Random random = new Random();
    UserDataModel getUserData = new UserDataModel();
    int flirtyNumber = this.random.nextInt(10) + 1;
    int optimisticNumber = this.random.nextInt(10) + 1;
    int mysteriousNumber = this.random.nextInt(10) + 1;
    String flirty = String.valueOf(this.flirtyNumber);
    String optimistic = String.valueOf(this.optimisticNumber);
    String mysterious = String.valueOf(this.mysteriousNumber);

 

    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(TweakPersonalityActivity.this, getString(R.string.Admob_Banner));
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityTweakPersonalityBinding) DataBindingUtil.setContentView(this, R.layout.activity_tweak_personality);
        overridePendingTransition(17432576, 17432577);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
        }
        BannerLoadads();
        Intent intent = getIntent();
        this.intent = intent;
        final boolean booleanExtra = intent.getBooleanExtra("selectCharToPersonality", false);
   
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);
        if (booleanExtra) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else {
            this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.getUserData.getImageId()));
        }
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (booleanExtra) {
                    TweakPersonalityActivity.this.getGuest();
                    return;
                }
                TweakPersonalityActivity personalityActivity = TweakPersonalityActivity.this;
                personalityActivity.updateUser(view, personalityActivity.flirty, TweakPersonalityActivity.this.optimistic, TweakPersonalityActivity.this.mysterious);
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                TweakPersonalityActivity.this.onBackPressed();
            }
        });
        Log.d("data==>44", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void updateUser(final View view, final String str, final String str2, final String str3) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str4) {
                TweakPersonalityActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str4);
                    JSONObject jSONObject = new JSONObject(str4);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        TweakPersonalityActivity.this.mWaitDialog.dismiss();
                        TweakPersonalityActivity.this.getUserData.setFlirty(str);
                        TweakPersonalityActivity.this.getUserData.setOptimistic(str2);
                        TweakPersonalityActivity.this.getUserData.setMysterious(str3);
                        Constants.saveDataInSharedPref(TweakPersonalityActivity.this, Constants.USER_DATA, new Gson().toJson(TweakPersonalityActivity.this.getUserData));
                        TweakPersonalityActivity.this.intent = new Intent(TweakPersonalityActivity.this, GirlNameActivity.class);
                        TweakPersonalityActivity.this.overridePendingTransition(17432576, 17432577);
                        TweakPersonalityActivity personalityActivity = TweakPersonalityActivity.this;
                        personalityActivity.startActivity(personalityActivity.intent);
                    } else {
                        Constants.showSnackBarMessage(TweakPersonalityActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                TweakPersonalityActivity.this.mWaitDialog.dismiss();
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(TweakPersonalityActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("flirty", str);
                hashMap.put("optimistic", str2);
                hashMap.put("mysterious", str3);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }

    public void getGuest() {
        Intent intent = new Intent(this, GirlNameActivity.class);
        this.intent = intent;
        intent.putExtra("personalityToAiGirlName", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent);
    }
}
