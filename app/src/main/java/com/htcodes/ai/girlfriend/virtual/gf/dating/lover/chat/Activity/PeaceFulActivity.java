package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.CheckUpdatePlay;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityPeaceFulBinding;
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


public class PeaceFulActivity extends AppCompatActivity {
    ActivityPeaceFulBinding binding;
    int click = 1;
    UserDataModel getUserData = new UserDataModel();
    Intent intent;
    Dialog mWaitDialog;


    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(PeaceFulActivity.this, getString(R.string.Admob_Banner));
    }

    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(PeaceFulActivity.this, getString(R.string.Admob_Interstitial));

    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadANNABHAGYAAds();

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityPeaceFulBinding) DataBindingUtil.setContentView(this, R.layout.activity_peace_ful);
        overridePendingTransition(17432576, 17432577);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
        }
        BannerLoadads();
        int i = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);


        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeaceFulActivity.this.onBackPressed();
            }
        });
        Log.d("data==>88", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
        Intent intent = getIntent();
        this.intent = intent;
        if (intent.getBooleanExtra("GoalsToRelax", false)) {
            this.binding.progressImg.setImageResource(i);
        } else {
            this.binding.progressImg.setImageResource(Constants.getSelectedImageAvatar(this.getUserData.getImageId()));
        }
        checkState();
    }

    public void checkState() {
        this.binding.readBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.readBook.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PeaceFulActivity.this.binding.readBook.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.readBook.setBackgroundResource(R.drawable.rent_bg_border);
                PeaceFulActivity.this.binding.readBook.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.watchingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.watchingTV.setBackgroundResource(R.drawable.rent_bg_border);
                    PeaceFulActivity.this.binding.watchingTV.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.watchingTV.setBackgroundResource(R.drawable.et_bg_filled_white);
                PeaceFulActivity.this.binding.watchingTV.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.goingRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PeaceFulActivity.this.m17x8c8a2938(view);
            }
        });
        this.binding.laughChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.laughChat.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PeaceFulActivity.this.binding.laughChat.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.laughChat.setBackgroundResource(R.drawable.rent_bg_border);
                PeaceFulActivity.this.binding.laughChat.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.goingSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.goingSwim.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PeaceFulActivity.this.binding.goingSwim.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.goingSwim.setBackgroundResource(R.drawable.rent_bg_border);
                PeaceFulActivity.this.binding.goingSwim.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.cleanHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.cleanHouse.setBackgroundResource(R.drawable.rent_bg_border);
                    PeaceFulActivity.this.binding.cleanHouse.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.cleanHouse.setBackgroundResource(R.drawable.et_bg_filled_white);
                PeaceFulActivity.this.binding.cleanHouse.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.playGame.setBackgroundResource(R.drawable.rent_bg_border);
                    PeaceFulActivity.this.binding.playGame.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.playGame.setBackgroundResource(R.drawable.et_bg_filled_white);
                PeaceFulActivity.this.binding.playGame.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.playGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.playGames.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PeaceFulActivity.this.binding.playGames.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.playGames.setBackgroundResource(R.drawable.rent_bg_border);
                PeaceFulActivity.this.binding.playGames.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.deep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PeaceFulActivity.this.click == 1) {
                    PeaceFulActivity.this.binding.deep.setBackgroundResource(R.drawable.et_bg_filled_white);
                    PeaceFulActivity.this.binding.deep.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.black));
                    PeaceFulActivity.this.click = 0;
                    return;
                }
                PeaceFulActivity.this.binding.deep.setBackgroundResource(R.drawable.rent_bg_border);
                PeaceFulActivity.this.binding.deep.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                PeaceFulActivity.this.click = 1;
            }
        });
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeaceFulActivity relaxActivity = PeaceFulActivity.this;
                relaxActivity.intent = relaxActivity.getIntent();
                if (!PeaceFulActivity.this.intent.getBooleanExtra("GoalsToRelax", false)) {
                    PeaceFulActivity.this.updateUser(view, "reading");
                    return;
                }
                CheckUpdatePlay.setPreference(PeaceFulActivity.this, "firstTime", true);
                PeaceFulActivity.this.binding.rlToolbar.setVisibility(View.GONE);
                PeaceFulActivity.this.binding.scroll.setVisibility(View.GONE);
                PeaceFulActivity.this.binding.content2.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PeaceFulActivity.this.binding.tvMsg2.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                        PeaceFulActivity.this.binding.img2.setImageResource(R.drawable.ic_checkbox_circle_fill);
                    }
                }, 1500L);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PeaceFulActivity.this.binding.tv3.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                        PeaceFulActivity.this.binding.img3.setImageResource(R.drawable.ic_checkbox_circle_fill);
                    }
                }, 2900L);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PeaceFulActivity.this.getGuest();
                    }
                }, 5000L);
            }
        });
    }


    public void m17x8c8a2938(View view) {
        if (this.click == 1) {
            this.binding.goingRun.setBackgroundResource(R.drawable.et_bg_filled_white);
            this.binding.goingRun.setTextColor(getResources().getColor(R.color.black));
            this.click = 0;
            return;
        }
        this.binding.goingRun.setBackgroundResource(R.drawable.rent_bg_border);
        this.binding.goingRun.setTextColor(getResources().getColor(R.color.white));
        this.click = 1;
    }


    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String str2) {
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        CheckUpdatePlay.setPreference(PeaceFulActivity.this, "firstTime", true);
                        PeaceFulActivity.this.getUserData.setRelax("reading");
                        Constants.saveDataInSharedPref(PeaceFulActivity.this, Constants.USER_DATA, new Gson().toJson(PeaceFulActivity.this.getUserData));
                        PeaceFulActivity.this.binding.rlToolbar.setVisibility(View.GONE);
                        PeaceFulActivity.this.binding.scroll.setVisibility(View.GONE);
                        PeaceFulActivity.this.binding.content2.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PeaceFulActivity.this.binding.tvMsg2.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                                PeaceFulActivity.this.binding.img2.setImageResource(R.drawable.ic_checkbox_circle_fill);
                            }
                        }, 1500L);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PeaceFulActivity.this.binding.tv3.setTextColor(PeaceFulActivity.this.getResources().getColor(R.color.white));
                                PeaceFulActivity.this.binding.img3.setImageResource(R.drawable.ic_checkbox_circle_fill);
                            }
                        }, 2900L);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CheckUpdatePlay.setPreference(PeaceFulActivity.this, "firstTime", true);
                                intent = new Intent(PeaceFulActivity.this, AIChatActivity.class);

                                AdsManager.getInstance().showInterstitialAd(PeaceFulActivity.this, new AdsManager.AdCloseListener() {
                                    @Override
                                    public void onAdClosed() {
                                        startActivity(intent);
                                        finish();
                                    }
                                });


                            }
                        }, 5000L);
                    } else {
                        Constants.showSnackBarMessage(PeaceFulActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(PeaceFulActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                PeaceFulActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(PeaceFulActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(PeaceFulActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("relax", str);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    public void getGuest() {
        this.intent = new Intent(this, AIChatActivity.class);
        CheckUpdatePlay.setPreference(getApplicationContext(), "firstTimeGuest", true);
        this.intent.putExtra("RelaxToChat", true);
        startActivity(this.intent);
        overridePendingTransition(17432576, 17432577);
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
