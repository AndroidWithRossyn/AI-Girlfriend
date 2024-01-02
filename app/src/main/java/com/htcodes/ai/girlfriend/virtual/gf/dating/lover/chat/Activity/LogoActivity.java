package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;

public class LogoActivity extends AppCompatActivity {
    Intent intent;
    ImageView logo1;
    ImageView logo2;
    TextView textView;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_logo);

        this.logo1 = (ImageView) findViewById(R.id.logo1);
        this.logo2 = (ImageView) findViewById(R.id.logo2);
        this.textView = (TextView) findViewById(R.id.btnNext);
        this.logo1.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                LogoActivity.this.changeIcon();
            }
        });
        this.logo2.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                LogoActivity.this.newIcon();
            }
        });
        this.textView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                LogoActivity.this.intent = new Intent(LogoActivity.this, PolicyActivity.class);
                LogoActivity chooseLogoActivity = LogoActivity.this;
                chooseLogoActivity.startActivity(chooseLogoActivity.intent);
            }
        });
    }

    
    public void changeIcon() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.gsbusiness.aigirlfriend.Activity.ChooseLogoActivity"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.gsbusiness.aigirlfriend.ChooseLogoAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Enable Old Icon", Toast.LENGTH_LONG).show();
    }

    
    public void newIcon() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.gsbusiness.aigirlfriend.Activity.ChooseLogoActivity"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, "com.gsbusiness.aigirlfriend.ChooseLogoAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Enable New Icon", Toast.LENGTH_LONG).show();
    }
}
