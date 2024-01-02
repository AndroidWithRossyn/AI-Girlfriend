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
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityGoalsNameBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GoalsNameActivity extends AppCompatActivity {
    ActivityGoalsNameBinding binding;
    int click = 1;
    UserDataModel getUserData = new UserDataModel();
    Intent intent;
    Dialog mWaitDialog;
 

    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(GoalsNameActivity.this, getString(R.string.Admob_Banner));
    }
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityGoalsNameBinding) DataBindingUtil.setContentView(this, R.layout.activity_goals_name);
        overridePendingTransition(17432576, 17432577);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),  UserDataModel.class);
        }
        BannerLoadads();

        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GoalsNameActivity goalsActivity = GoalsNameActivity.this;
                goalsActivity.intent = goalsActivity.getIntent();
                if (!GoalsNameActivity.this.intent.getBooleanExtra("InterestToGoal", false)) {
                    GoalsNameActivity.this.updateUser(view, "have fun");
                } else {
                    GoalsNameActivity.this.getGuest();
                }
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GoalsNameActivity.this.onBackPressed();
            }
        });
        Log.d("data==>66", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
        CheckState();
    }

    public void CheckState() {
        this.binding.ShareEmotions.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (GoalsNameActivity.this.click == 1) {
                    GoalsNameActivity.this.binding.ShareEmotions.setBackgroundResource(R.drawable.et_bg_filled_white);
                    GoalsNameActivity.this.binding.ShareEmotions.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.black));
                    GoalsNameActivity.this.click = 0;
                    return;
                }
                GoalsNameActivity.this.binding.ShareEmotions.setBackgroundResource(R.drawable.rent_bg_border);
                GoalsNameActivity.this.binding.ShareEmotions.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.white));
                GoalsNameActivity.this.click = 1;
            }
        });
        this.binding.ChatRandom.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (GoalsNameActivity.this.click == 1) {
                    GoalsNameActivity.this.binding.ChatRandom.setBackgroundResource(R.drawable.et_bg_filled_white);
                    GoalsNameActivity.this.binding.ChatRandom.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.black));
                    GoalsNameActivity.this.click = 0;
                    return;
                }
                GoalsNameActivity.this.binding.ChatRandom.setBackgroundResource(R.drawable.rent_bg_border);
                GoalsNameActivity.this.binding.ChatRandom.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.white));
                GoalsNameActivity.this.click = 1;
            }
        });
        this.binding.MakeFriend.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (GoalsNameActivity.this.click == 1) {
                    GoalsNameActivity.this.binding.MakeFriend.setBackgroundResource(R.drawable.et_bg_filled_white);
                    GoalsNameActivity.this.binding.MakeFriend.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.black));
                    GoalsNameActivity.this.click = 0;
                    return;
                }
                GoalsNameActivity.this.binding.MakeFriend.setBackgroundResource(R.drawable.rent_bg_border);
                GoalsNameActivity.this.binding.MakeFriend.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.white));
                GoalsNameActivity.this.click = 1;
            }
        });
        this.binding.HaveFun.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (GoalsNameActivity.this.click == 1) {
                    GoalsNameActivity.this.binding.HaveFun.setBackgroundResource(R.drawable.et_bg_filled_white);
                    GoalsNameActivity.this.binding.HaveFun.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.black));
                    GoalsNameActivity.this.click = 0;
                    return;
                }
                GoalsNameActivity.this.binding.HaveFun.setBackgroundResource(R.drawable.rent_bg_border);
                GoalsNameActivity.this.binding.HaveFun.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.white));
                GoalsNameActivity.this.click = 1;
            }
        });
        this.binding.TalkShameFree.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (GoalsNameActivity.this.click == 1) {
                    GoalsNameActivity.this.binding.TalkShameFree.setBackgroundResource(R.drawable.et_bg_filled_white);
                    GoalsNameActivity.this.binding.TalkShameFree.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.black));
                    GoalsNameActivity.this.click = 0;
                    return;
                }
                GoalsNameActivity.this.binding.TalkShameFree.setBackgroundResource(R.drawable.rent_bg_border);
                GoalsNameActivity.this.binding.TalkShameFree.setTextColor(GoalsNameActivity.this.getResources().getColor(R.color.white));
                GoalsNameActivity.this.click = 1;
            }
        });
    }

    
    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str2) {
                GoalsNameActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        GoalsNameActivity.this.mWaitDialog.dismiss();
                        GoalsNameActivity.this.getUserData.setGoals("have fun");
                        Constants.saveDataInSharedPref(GoalsNameActivity.this, Constants.USER_DATA, new Gson().toJson(GoalsNameActivity.this.getUserData));
                        GoalsNameActivity.this.intent = new Intent(GoalsNameActivity.this, PeaceFulActivity.class);
                        GoalsNameActivity.this.overridePendingTransition(17432576, 17432577);
                        GoalsNameActivity goalsActivity = GoalsNameActivity.this;
                        goalsActivity.startActivity(goalsActivity.intent);
                    } else {
                        Constants.showSnackBarMessage(GoalsNameActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(GoalsNameActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                GoalsNameActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(GoalsNameActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(GoalsNameActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("goals", str);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    public void getGuest() {
        Intent intent = new Intent(this, PeaceFulActivity.class);
        this.intent = intent;
        intent.putExtra("GoalsToRelax", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent);
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
}
