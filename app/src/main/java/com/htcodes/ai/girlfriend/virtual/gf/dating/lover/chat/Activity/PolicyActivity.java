package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AppPreference;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.CheckUpdatePlay;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityPolicyBinding;

public class PolicyActivity extends AppCompatActivity {
    AppPreference appPreference;
    ActivityPolicyBinding binding;
    boolean subscribe = false;
    Boolean isLogin = false;


    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(PolicyActivity.this, getString(R.string.Admob_Interstitial));

    }

    @Override
    protected void onStart() {
        super.onStart();

      //  LoadANNABHAGYAAds();

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityPolicyBinding) DataBindingUtil.setContentView(this, R.layout.activity_policy);
        setView();
    }

    private void setView() {
        this.appPreference = new AppPreference(this);
        this.subscribe = Constants.getSubscription(this, "subscribe").booleanValue();
        Log.e("-->", "setView: subscribe" + this.subscribe);
        if (!this.subscribe && getIntent() != null && getIntent().getBooleanExtra("is_boarding", false)) {
            this.binding.ivBack.setVisibility(View.INVISIBLE);
        }
        this.isLogin = Constants.getBooleanDataFromSharedPref(this, Constants.IS_LOGIN);
        this.binding.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                CheckUpdatePlay.setPreference(PolicyActivity.this, "firstTime", true);
                if (PolicyActivity.this.isLogin.booleanValue()) {

                    intent = new Intent(PolicyActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(PolicyActivity.this, GuestLoginActivity.class);
                }
                startActivity(intent);


           /*     AdsManager.getInstance().showInterstitialAd(PolicyActivity.this, new AdsManager.AdCloseListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(intent);

                            finish();
                        }
                    });

            */




            }
        });
        this.binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        WebSettings settings = this.binding.wvPrivacyAndPolicy.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        this.binding.wvPrivacyAndPolicy.setWebChromeClient(new WebChromeClient());
        this.binding.wvPrivacyAndPolicy.loadUrl(getString(R.string.privacy));
        this.binding.wvPrivacyAndPolicy.setWebViewClient(new Webclient());
    }
    public class Webclient extends WebViewClient {
        public Webclient() {
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            binding.wvPrivacyAndPolicy.loadUrl("javascript:this.insuranceCalculatorOpen.bind(this)");

        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
