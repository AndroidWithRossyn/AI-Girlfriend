package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityGirlNameBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GirlNameActivity extends AppCompatActivity {
    ActivityGirlNameBinding binding;
    UserDataModel getUserData = new UserDataModel();
    Intent intent;
    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(GirlNameActivity.this, getString(R.string.Admob_Banner));
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityGirlNameBinding) DataBindingUtil.setContentView(this, R.layout.activity_girl_name);
        overridePendingTransition(17432576, 17432577);
        int i = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", 0);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),  UserDataModel.class);
        }
        BannerLoadads();
        Intent intent = getIntent();
        this.intent = intent;
        if (intent.getBooleanExtra("personalityToAiGirlName", false)) {
            this.binding.imgAvatar.setImageResource(i);
        } else {
            this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.getUserData.getImageId()));
        }


        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GirlNameActivity aiGirlName = GirlNameActivity.this;
                aiGirlName.intent = aiGirlName.getIntent();
                if (GirlNameActivity.this.intent.getBooleanExtra("personalityToAiGirlName", false)) {
                    if (GirlNameActivity.this.binding.etName.length() == 0) {
                        GirlNameActivity.this.binding.etName.setError("Please enter name");
                    } else if (GirlNameActivity.this.binding.etName.length() == 1) {
                        GirlNameActivity.this.binding.etName.setError("Name is too short");
                    } else {
                        GirlNameActivity.this.getGuest();
                        GirlNameActivity aiGirlName2 = GirlNameActivity.this;
                        Constants.saveDataInSharedPref(aiGirlName2, "gfname", aiGirlName2.binding.etName.getText().toString().trim());
                    }
                } else if (GirlNameActivity.this.binding.etName.length() == 0) {
                    GirlNameActivity.this.binding.etName.setError("Please enter name");
                } else if (GirlNameActivity.this.binding.etName.length() == 1) {
                    GirlNameActivity.this.binding.etName.setError("Name is too short");
                } else {
                    GirlNameActivity aiGirlName3 = GirlNameActivity.this;
                    aiGirlName3.updateUser(view, aiGirlName3.binding.etName.getText().toString());
                }
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GirlNameActivity.this.onBackPressed();
            }
        });
        this.binding.etName.addTextChangedListener(new TextWatcher() {
            @Override 
            public void afterTextChanged(Editable editable) {
            }

            @Override 
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override 
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (charSequence.length() > 0) {
                    GirlNameActivity.this.binding.btnNext.setEnabled(true);
                    GirlNameActivity.this.binding.btnNext.setTextColor(GirlNameActivity.this.getResources().getColor(R.color.white));
                    return;
                }
                GirlNameActivity.this.binding.btnNext.setEnabled(false);
                GirlNameActivity.this.binding.btnNext.setTextColor(GirlNameActivity.this.getResources().getColor(R.color.light_white));
            }
        });
        Log.d("data==>55", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
        this.binding.scrollView.setEnabled(false);
        showSoftKeyboard(this.binding.etName);
    }

    
    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() { // from class: com.gsbusiness.aigirlfriend.Activity.AiGirlName.5
            @Override 
            public void onResponse(String str2) {
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        GirlNameActivity.this.getUserData.setName(GirlNameActivity.this.binding.etName.getText().toString());
                        Constants.saveDataInSharedPref(GirlNameActivity.this, Constants.USER_DATA, new Gson().toJson(GirlNameActivity.this.getUserData));
                        GirlNameActivity.this.intent = new Intent(GirlNameActivity.this, PersonalInterestActivity.class);
                        GirlNameActivity.this.overridePendingTransition(17432576, 17432577);
                        GirlNameActivity aiGirlName = GirlNameActivity.this;
                        aiGirlName.startActivity(aiGirlName.intent);
                    } else {
                        Constants.showSnackBarMessage(GirlNameActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(GirlNameActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                Constants.showSnackBarMessage(GirlNameActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(GirlNameActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("name", str);
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
        Intent intent = new Intent(this, PersonalInterestActivity.class);
        this.intent = intent;
        intent.putExtra("AiGirlToInterest", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 1);
        }
    }
}
