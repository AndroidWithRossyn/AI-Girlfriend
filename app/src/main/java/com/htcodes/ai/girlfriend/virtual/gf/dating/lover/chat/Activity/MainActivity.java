package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityMainBinding;
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


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Intent intent;
    Boolean isLogin = false;
    UserDataModel getUserData = new UserDataModel();

 

    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(MainActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityMainBinding) DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.isLogin = Constants.getBooleanDataFromSharedPref(this, Constants.IS_LOGIN);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
        }
        NativeADmob();

        this.binding.scrollView.setNestedScrollingEnabled(false);
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.intent = mainActivity.getIntent();
                if (MainActivity.this.intent.getBooleanExtra("guest", false)) {
                    if (MainActivity.this.binding.etName.length() == 0) {
                        MainActivity.this.binding.etName.setError("Please enter name");
                        return;
                    } else {
                        MainActivity.this.getGuest();
                        return;
                    }
                }
                String trim = MainActivity.this.binding.etName.getText().toString().trim();
                if (!trim.isEmpty()) {
                    MainActivity.this.updateUser(view, trim);
                } else {
                    MainActivity.this.binding.etName.setError("Please enter name");
                }
            }
        });
        this.binding.scrollView.setEnabled(false);
        this.binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    MainActivity.this.binding.btnNext.setEnabled(true);
                    MainActivity.this.binding.btnNext.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
                    return;
                }
                MainActivity.this.binding.btnNext.setEnabled(false);
                MainActivity.this.binding.btnNext.setTextColor(MainActivity.this.getResources().getColor(R.color.light_white));
            }
        });
        showSoftKeyboard(this.binding.etName);
    }

    public void getGuest() {
        Intent intent = new Intent(this, GenderActivity.class);
        this.intent = intent;
        intent.putExtra("name", this.binding.etName.getText().toString());
        this.intent.putExtra("nameToPronouns", true);
        Constants.saveDataInSharedPref(this, "username", this.binding.etName.getText().toString().trim());
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(this.intent);
    }


    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String str2) {
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        if (MainActivity.this.getUserData == null) {
                            UserDataModel userDataModel = new UserDataModel();
                            userDataModel.setUserName(MainActivity.this.binding.etName.getText().toString().trim());
                            Constants.saveDataInSharedPref(MainActivity.this, Constants.USER_DATA, new Gson().toJson(userDataModel));
                        } else {
                            MainActivity.this.getUserData.setUserName(MainActivity.this.binding.etName.getText().toString().trim());
                            Constants.saveDataInSharedPref(MainActivity.this, Constants.USER_DATA, new Gson().toJson(MainActivity.this.getUserData));
                        }
                        MainActivity.this.intent = new Intent(MainActivity.this, GenderActivity.class);
                        MainActivity.this.intent.putExtra("name", MainActivity.this.binding.etName.getText().toString());
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.startActivity(mainActivity.intent);
                        return;
                    }
                    Constants.showSnackBarMessage(MainActivity.this, view, jSONObject.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(MainActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.showSnackBarMessage(MainActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(MainActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_name", str);
                Log.e("==>", "getParams:SaveUserInfo " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 1);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
