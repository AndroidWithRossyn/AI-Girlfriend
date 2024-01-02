package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityGuestLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class GuestLoginActivity extends AppCompatActivity {
    ActivityGuestLoginBinding binding;
    String firebaseToken = "";
    Intent intent;
    GoogleSignInClient mGoogleSignInClient;
    Dialog mWaitDialog;


    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(GuestLoginActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }


    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(GuestLoginActivity.this, getString(R.string.Admob_Interstitial));
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadANNABHAGYAAds();

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityGuestLoginBinding) DataBindingUtil.setContentView(this, R.layout.activity_guest_login);
        setView();
        NativeADmob();
    }

    private void setView() {

        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);
        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build());


        this.binding.guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(GuestLoginActivity.this, MainActivity.class);
                intent.putExtra("guest", true);
                startActivity(intent);

                AdsManager.getInstance().showInterstitialAd(GuestLoginActivity.this, new AdsManager.AdCloseListener() {
                    @Override
                    public void onAdClosed() {
                       // startActivity(intent);
                    }
                });




            }
        });
    }


    public void m4xb8c0b296(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.firebaseToken = str;
        Log.e("==>", "setView: " + this.firebaseToken);
    }


    public void signIn() {
        startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), 2);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            try {
                GoogleSignInAccount result = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
                if (!this.mWaitDialog.isShowing()) {
                    this.mWaitDialog.show();
                }
                loginWithGoogle(result.getId(), result.getDisplayName(), result.getEmail());
                this.mGoogleSignInClient.signOut();
            } catch (ApiException unused) {
                this.mWaitDialog.dismiss();
            }
        }
    }

    private void loginWithGoogle(final String str, final String str2, final String str3) {
        final View rootView = getWindow().getDecorView().getRootView();
        StringRequest stringRequest = new StringRequest(1, URLs.URL_LOGIN_WITH_GOOGLE, new Response.Listener<String>() { // from class: com.gsbusiness.aigirlfriend.Activity.GoogleLogIn_Activity.4
            @Override
            public void onResponse(String str4) {
                try {
                    JSONObject jSONObject = new JSONObject(str4);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        Constants.saveDataInSharedPref(GuestLoginActivity.this, Constants.ACCESS_TOKEN, jSONObject.getJSONObject("data").getString("token"));
                        Constants.saveBooleanDataInSharedPref(GuestLoginActivity.this, Constants.IS_LOGIN, true);
                        getUserDetails();
                    } else {
                        mWaitDialog.dismiss();
                        Constants.showSnackBarMessage(GuestLoginActivity.this, rootView, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    mWaitDialog.dismiss();
                    e.printStackTrace();
                    Constants.showSnackBarMessage(GuestLoginActivity.this, rootView, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("==>LogIn", "onErrorResponse: " + volleyError.getMessage());
                mWaitDialog.dismiss();
                Constants.showSnackBarMessage(GuestLoginActivity.this, rootView, volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("name", str2);
                hashMap.put("id", str);
                hashMap.put("firebase_token", firebaseToken);
                hashMap.put("email", str3);
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        super.onStart();
    }


    public void getUserDetails() {
        StringRequest stringRequest = new StringRequest(0, URLs.GET_USER_INFO, new Response.Listener<String>() { // from class: com.gsbusiness.aigirlfriend.Activity.GoogleLogIn_Activity.7
            @Override
            public void onResponse(String str) {
                mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str);
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        if (jSONObject.getString("data").equals("null")) {
                            intent = new Intent(GuestLoginActivity.this, MainActivity.class);
                        } else {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
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
                            Constants.saveDataInSharedPref(GuestLoginActivity.this, Constants.USER_DATA, new Gson().toJson(userDataModel));
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
                                                                    intent = new Intent(GuestLoginActivity.this, AIChatActivity.class);
                                                                }
                                                                intent = new Intent(GuestLoginActivity.this, PeaceFulActivity.class);
                                                            }
                                                            intent = new Intent(GuestLoginActivity.this, GoalsNameActivity.class);
                                                        }
                                                        intent = new Intent(GuestLoginActivity.this, PersonalInterestActivity.class);
                                                    }
                                                    intent = new Intent(GuestLoginActivity.this, TweakPersonalityActivity.class);
                                                }
                                                intent = new Intent(GuestLoginActivity.this, TweakPersonalityActivity.class);
                                            }
                                            intent = new Intent(GuestLoginActivity.this, TweakPersonalityActivity.class);
                                        }
                                        intent = new Intent(GuestLoginActivity.this, SelectAIActivity.class);
                                    }
                                    intent = new Intent(GuestLoginActivity.this, GirlNameActivity.class);
                                }
                                intent = new Intent(GuestLoginActivity.this, GenderActivity.class);
                            }
                            intent = new Intent(GuestLoginActivity.this, MainActivity.class);
                        }
                        GuestLoginActivity googleLogIn_Activity = GuestLoginActivity.this;
                        googleLogIn_Activity.startActivity(googleLogIn_Activity.intent);
                        return;
                    }
                    Toasty.error(GuestLoginActivity.this, "Something went wrong").show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mWaitDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(GuestLoginActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
