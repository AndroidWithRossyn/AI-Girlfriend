package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivitySecondBinding;


public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;

    public void NativeADmob() {
        AdsManager.getInstance().showNativeAds(SecondActivity.this, (FrameLayout) findViewById(R.id.flNative), getString(R.string.Admob_Native));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdsManager.destroy();

    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivitySecondBinding) DataBindingUtil.setContentView(this, R.layout.activity_second);
        setContentView();
        NativeADmob();
    }

    private void setContentView() {


        this.binding.mNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                SecondActivity.this.m10xa657ef89(view);
            }
        });
    }

    

    public  void m10xa657ef89(View view) {
        startActivity(new Intent(this, PolicyActivity.class));
        finish();
    }

    
    @Override 
    public void onResume() {
        super.onResume();

    }
}
