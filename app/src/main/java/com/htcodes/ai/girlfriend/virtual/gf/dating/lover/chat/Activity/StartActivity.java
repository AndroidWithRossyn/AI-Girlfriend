package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityStartBinding;


public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;
 

    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(StartActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivityStartBinding) DataBindingUtil.setContentView(this, R.layout.activity_start);
        setContentView();

        NativeADmob();
    }

    private void setContentView() {


        this.binding.mNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                StartActivity.this.m9x91c0c5a8(view);
            }
        });
    }

    public  void m9x91c0c5a8(View view) {
        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }

    @Override 
    public void onResume() {
        super.onResume();
    }
}
