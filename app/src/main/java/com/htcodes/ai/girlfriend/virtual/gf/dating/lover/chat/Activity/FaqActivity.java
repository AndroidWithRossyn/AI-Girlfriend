package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityFaqactivityBinding;

import eightbitlab.com.blurview.RenderScriptBlur;


public class FaqActivity extends AppCompatActivity {
    ActivityFaqactivityBinding binding;
    boolean guest;
    Intent intent;

 

    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(FaqActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(17432576, 17432577);
        this.binding = (ActivityFaqactivityBinding) DataBindingUtil.setContentView(this, R.layout.activity_faqactivity);
        setBlur();
        Intent intent = getIntent();
        this.intent = intent;
        this.guest = intent.getBooleanExtra("SettingToFaq", false);
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                FaqActivity.this.onBackPressed();
            }
        });


        NativeADmob();
    }

    public void setBlur() {
        View decorView = getWindow().getDecorView();
        ViewGroup viewGroup = (ViewGroup) decorView.findViewById(16908290);
        Drawable background = decorView.getBackground();
        this.binding.blurView.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurView.setClipToOutline(true);
        this.binding.blurViewSecond.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewSecond.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewSecond.setClipToOutline(true);
        this.binding.blurViewThird.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewThird.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewThird.setClipToOutline(true);
        this.binding.blurViewFour.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewFour.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewFour.setClipToOutline(true);
        this.binding.blurViewFive.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewFive.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewFive.setClipToOutline(true);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        if (this.guest) {
            AISettingsActivity.settingToFaq = true;
        }
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }
}
