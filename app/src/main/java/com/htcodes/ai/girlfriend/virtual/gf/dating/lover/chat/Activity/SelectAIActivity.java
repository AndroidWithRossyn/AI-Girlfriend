package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivitySelectAiBinding;
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

import eightbitlab.com.blurview.RenderScriptBlur;


public class SelectAIActivity extends AppCompatActivity {
    ActivitySelectAiBinding binding;
    Intent intent;
    Dialog mWaitDialog;
    int[] imageArray = {R.drawable.girl_av2, R.drawable.girl_av3, R.drawable.girl_av4, R.drawable.girl_av5, R.drawable.girl_av6, R.drawable.girl_av7, R.drawable.girl_av8, R.drawable.girl_av9, R.drawable.girl_av10, R.drawable.girl_av11, R.drawable.boy_av1, R.drawable.boy_av2, R.drawable.boy_av3, R.drawable.boy_av4};
    int counter = 0;
    UserDataModel getUserData = new UserDataModel();

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivitySelectAiBinding) DataBindingUtil.setContentView(this, R.layout.activity_select_ai);
        if (!Constants.getDataFromSharedPref(this, Constants.USER_DATA).equals("") || Constants.getDataFromSharedPref(this, Constants.USER_DATA) != null) {
            this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),  UserDataModel.class);
        }


        showImage(this.counter);
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);
        View decorView = getWindow().getDecorView();
        this.binding.blurView.setupWith((ViewGroup) decorView.findViewById(16908290), new RenderScriptBlur(this)).setFrameClearDrawable(decorView.getBackground()).setBlurRadius(20.0f);
        this.binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurView.setClipToOutline(true);
        this.binding.imgAvatar.setImageResource(this.imageArray[this.counter]);
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAIActivity.this.onBackPressed();
            }
        });
        this.binding.arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAIActivity.this.showNextImage();
            }
        });
        this.binding.arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAIActivity.this.showPreviousImage();
            }
        });
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAIActivity selectCharacter = SelectAIActivity.this;
                selectCharacter.intent = selectCharacter.getIntent();
                if (SelectAIActivity.this.intent.getBooleanExtra("pronounsToSelectCharacter", false)) {
                    SelectAIActivity.this.getGuest();
                    return;
                }
                SelectAIActivity selectCharacter2 = SelectAIActivity.this;
                selectCharacter2.updateUser(view, String.valueOf(selectCharacter2.counter));
            }
        });
        this.binding.content.setBackgroundResource(this.imageArray[this.counter]);
        Log.d("data==>33", Constants.getDataFromSharedPref(this, Constants.USER_DATA));
    }

    private void showImage(int i) {
        if (i < 0 || i >= this.imageArray.length) {
            return;
        }
        this.binding.imgAvatar.setImageResource(this.imageArray[i]);
        this.binding.content.setBackgroundResource(this.imageArray[i]);
        SharedPreferences.Editor edit = getSharedPreferences("YOUR_PREF_NAME", 0).edit();
        edit.putInt("imgName", this.imageArray[i]);
        edit.commit();
    }

    
    public void showNextImage() {
        int i = this.counter + 1;
        this.counter = i;
        if (i >= this.imageArray.length) {
            this.counter = 0;
        }
        showImage(this.counter);
    }

    
    public void showPreviousImage() {
        int i = this.counter - 1;
        this.counter = i;
        if (i < 0) {
            this.counter = this.imageArray.length - 1;
        }
        showImage(this.counter);
    }

    
    public void updateUser(final View view, final String str) {
        StringRequest stringRequest = new StringRequest(1, URLs.SAVE_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str2) {
                SelectAIActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str2);
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        SelectAIActivity.this.mWaitDialog.dismiss();
                        SelectAIActivity.this.getUserData.setImageId(String.valueOf(SelectAIActivity.this.counter));
                        Constants.saveDataInSharedPref(SelectAIActivity.this, Constants.USER_DATA, new Gson().toJson(SelectAIActivity.this.getUserData));
                        SelectAIActivity.this.intent = new Intent(SelectAIActivity.this, TweakPersonalityActivity.class);
                        SelectAIActivity selectCharacter = SelectAIActivity.this;
                        selectCharacter.startActivity(selectCharacter.intent);
                    } else {
                        Constants.showSnackBarMessage(SelectAIActivity.this, view, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Constants.showSnackBarMessage(SelectAIActivity.this, view, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                SelectAIActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(SelectAIActivity.this, view, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(SelectAIActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }

            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("image_id", str);
                Log.e("==>counter", "getParams:SaveUserInfo " + hashMap.toString());
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

    public void getGuest() {
        Intent intent = new Intent(this, TweakPersonalityActivity.class);
        this.intent = intent;
        intent.putExtra("selectCharToPersonality", true);
        startActivity(this.intent);
    }
}
