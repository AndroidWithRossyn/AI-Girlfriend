package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityGenderBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GenderActivity extends AppCompatActivity {
    ActivityGenderBinding binding;
    Intent intent2;
    Dialog mWaitDialog;
    String pronouns;
    int i = 0;
    UserDataModel getUserData = new UserDataModel();

 

    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(GenderActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityGenderBinding) DataBindingUtil.setContentView(this, R.layout.activity_gender);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
        }
        NativeADmob();
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);

        Intent intent = getIntent();
        this.intent2 = intent;
        boolean booleanExtra = intent.getBooleanExtra("nameToPronouns", false);
        String stringExtra = this.intent2.getStringExtra("name");
        if (booleanExtra) {
            TextView textView = this.binding.headerText;
            textView.setText("Hi, " + stringExtra + "!");
        } else {
            TextView textView2 = this.binding.headerText;
            textView2.setText("Hi, " + this.getUserData.getUserName() + "!");
        }

        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GenderActivity.this.onBackPressed();
            }
        });
        checkState();
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GenderActivity pronounsActivity = GenderActivity.this;
                pronounsActivity.intent2 = pronounsActivity.getIntent();
                boolean booleanExtra2 = GenderActivity.this.intent2.getBooleanExtra("nameToPronouns", false);
                if (GenderActivity.this.i == 0) {
                    GenderActivity.this.pronouns = "He";
                } else if (GenderActivity.this.i == 1) {
                    GenderActivity.this.pronouns = "She";
                } else {
                    GenderActivity.this.pronouns = "They";
                }
                if (booleanExtra2) {
                    GenderActivity pronounsActivity2 = GenderActivity.this;
                    Constants.saveDataInSharedPref(pronounsActivity2, "pronouns", pronounsActivity2.pronouns);
                    GenderActivity.this.getGuest();
                    return;
                }
                GenderActivity pronounsActivity3 = GenderActivity.this;
                pronounsActivity3.updateUser(view, pronounsActivity3.pronouns);
            }
        });
        this.binding.btnNext.setEnabled(false);
        Log.d("data==>22", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
    }

    public void checkState() {
        this.binding.btnMale.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GenderActivity.this.binding.btnMale.setBackgroundResource(R.drawable.et_bg_filled_white);
                GenderActivity.this.binding.btnMale.setTextColor(GenderActivity.this.getResources().getColor(R.color.black));
                GenderActivity.this.binding.btnFemale.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnFemale.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnThey.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnThey.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnNext.setEnabled(true);
                GenderActivity.this.binding.btnNext.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.i = 0;
            }
        });
        this.binding.btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GenderActivity.this.binding.btnMale.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnFemale.setBackgroundResource(R.drawable.et_bg_filled_white);
                GenderActivity.this.binding.btnFemale.setTextColor(GenderActivity.this.getResources().getColor(R.color.black));
                GenderActivity.this.binding.btnMale.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnThey.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnThey.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnNext.setEnabled(true);
                GenderActivity.this.binding.btnNext.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.i = 1;
            }
        });
        this.binding.btnThey.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GenderActivity.this.binding.btnMale.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnMale.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnFemale.setBackgroundResource(R.drawable.rent_full_et_bg);
                GenderActivity.this.binding.btnFemale.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.binding.btnThey.setBackgroundResource(R.drawable.et_bg_filled_white);
                GenderActivity.this.binding.btnThey.setTextColor(GenderActivity.this.getResources().getColor(R.color.black));
                GenderActivity.this.binding.btnNext.setEnabled(true);
                GenderActivity.this.binding.btnNext.setTextColor(GenderActivity.this.getResources().getColor(R.color.white));
                GenderActivity.this.i = 2;
            }
        });
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }

    
    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str2) {
                GenderActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        GenderActivity.this.mWaitDialog.dismiss();
                        GenderActivity.this.getUserData.setPronouns(str);
                        Constants.saveDataInSharedPref(GenderActivity.this, Constants.USER_DATA, new Gson().toJson(GenderActivity.this.getUserData));
                        GenderActivity.this.intent2 = new Intent(GenderActivity.this, SelectAiGirlActivity.class);
                        GenderActivity.this.overridePendingTransition(17432576, 17432577);
                        GenderActivity pronounsActivity = GenderActivity.this;
                        pronounsActivity.startActivity(pronounsActivity.intent2);
                    } else {
                        Constants.showSnackBarMessage(GenderActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(GenderActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                GenderActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(GenderActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(GenderActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap);
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("pronouns", str);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap);
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    public void getGuest() {
        Intent intent = new Intent(this, SelectAiGirlActivity.class);
        this.intent2 = intent;
        intent.putExtra("pronounsToSelectCharacter", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent2);
    }
}
