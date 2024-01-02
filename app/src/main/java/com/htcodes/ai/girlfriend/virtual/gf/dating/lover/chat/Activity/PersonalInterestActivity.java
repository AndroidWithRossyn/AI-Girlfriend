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
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityPersonalInterestBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PersonalInterestActivity extends AppCompatActivity {
    ActivityPersonalInterestBinding binding;
    int click = 1;
    UserDataModel getUserData = new UserDataModel();
    Intent intent;
    Dialog mWaitDialog;

 
    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(PersonalInterestActivity.this, getString(R.string.Admob_Banner));
    }
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityPersonalInterestBinding) DataBindingUtil.setContentView(this, R.layout.activity_personal_interest);
        overridePendingTransition(17432576, 17432577);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
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
                PersonalInterestActivity interestActivity = PersonalInterestActivity.this;
                interestActivity.intent = interestActivity.getIntent();
                if (!PersonalInterestActivity.this.intent.getBooleanExtra("AiGirlToInterest", false)) {
                    PersonalInterestActivity.this.updateUser(view, "gardening");
                } else {
                    PersonalInterestActivity.this.getGuest();
                }
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                PersonalInterestActivity.this.onBackPressed();
            }
        });
        CheckState();
        Log.d("data==>77", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
    }

    public void CheckState() {
        this.binding.btnGardening.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnGardening.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvGarden.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnGardening.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvGarden.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnHike.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnHike.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvhike.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnHike.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvhike.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnCat.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnCat.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvcat.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnCat.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvcat.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnPolitics.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnPolitics.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvpolitics.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnPolitics.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvpolitics.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnVideoGames.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnVideoGames.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvGames.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnVideoGames.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvGames.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnWine.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnWine.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvwine.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnWine.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvwine.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnMusic.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvmusic.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnMusic.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvmusic.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnInstagram.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvinsta.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnInstagram.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvinsta.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnSwim.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnSwim.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvswim.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnSwim.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvswim.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnOutdoors.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnOutdoors.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvoutdoors.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnOutdoors.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvoutdoors.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnTea.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnTea.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvtea.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnTea.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvtea.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnBeer.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnBeer.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvbeer.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnBeer.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvbeer.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnWalk.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvwalk.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnWalk.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvwalk.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnRunning.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnRunning.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvrun.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnRunning.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvrun.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
        this.binding.btnAstrology.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (PersonalInterestActivity.this.click == 1) {
                    PersonalInterestActivity.this.binding.btnAstrology.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PersonalInterestActivity.this.binding.tvastro.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.black));
                    PersonalInterestActivity.this.click = 0;
                    return;
                }
                PersonalInterestActivity.this.binding.btnAstrology.setBackgroundResource(R.drawable.rent_full_et_bg);
                PersonalInterestActivity.this.binding.tvastro.setTextColor(PersonalInterestActivity.this.getResources().getColor(R.color.white));
                PersonalInterestActivity.this.click = 1;
            }
        });
    }

    
    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str2) {
                PersonalInterestActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        PersonalInterestActivity.this.mWaitDialog.dismiss();
                        PersonalInterestActivity.this.getUserData.setInterests("gardening");
                        Constants.saveDataInSharedPref(PersonalInterestActivity.this, Constants.USER_DATA, new Gson().toJson(PersonalInterestActivity.this.getUserData));
                        PersonalInterestActivity.this.intent = new Intent(PersonalInterestActivity.this, GoalsNameActivity.class);
                        PersonalInterestActivity.this.overridePendingTransition(17432576, 17432577);
                        PersonalInterestActivity interestActivity = PersonalInterestActivity.this;
                        interestActivity.startActivity(interestActivity.intent);
                    } else {
                        Constants.showSnackBarMessage(PersonalInterestActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(PersonalInterestActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                PersonalInterestActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(PersonalInterestActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(PersonalInterestActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("interests", str);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }

    public void getGuest() {
        Intent intent = new Intent(this, GoalsNameActivity.class);
        this.intent = intent;
        intent.putExtra("InterestToGoal", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent);
    }
}
