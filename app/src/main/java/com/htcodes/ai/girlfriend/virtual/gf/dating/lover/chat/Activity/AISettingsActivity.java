package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.ChatDbHelper;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.CheckUpdatePlay;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityAiSettingsBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import es.dmoral.toasty.Toasty;


public class AISettingsActivity extends AppCompatActivity {
    private static int selected_pos = -1;
    public static boolean settingToFaq = false;
    public static boolean settingToPolicy = false;
    public static boolean settingToTerm = false;
    ActivityAiSettingsBinding binding;
    BottomSheetDialog bottomSheet;
    ChatDbHelper chatDbHelper;
    Intent intent;
    GoogleSignInClient mGoogleSignInClient;
    Dialog mWaitDialog;
    String pronouns;
    int i = 0;
    UserDataModel userDataModel = new UserDataModel();
    String firebaseToken = "";

    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(AISettingsActivity.this, getString(R.string.Admob_Interstitial));

    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadANNABHAGYAAds();
    }

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        this.binding = (ActivityAiSettingsBinding) DataBindingUtil.setContentView(this, R.layout.activity_ai_settings);
        this.chatDbHelper = new ChatDbHelper(this);
        this.userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),  UserDataModel.class);
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);

        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build());


        setBlur();
        setClicks();
        Intent intent = getIntent();
        this.intent = intent;
        boolean booleanExtra = intent.getBooleanExtra("chatToSetting", false);
        boolean booleanExtra2 = this.intent.getBooleanExtra("chatToSetting", false);
        if (booleanExtra) {
            int i = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.binding.guestLL.setVisibility(View.VISIBLE);
            this.binding.name.setText(Constants.getDataFromSharedPref(this, "username"));
            this.binding.Pronounce.setText(Constants.getDataFromSharedPref(this, "pronouns"));
            this.binding.imgAvatar.setImageResource(i);
        } else if (booleanExtra2) {
            int i2 = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.binding.guestLL.setVisibility(View.VISIBLE);
            this.binding.name.setText(Constants.getDataFromSharedPref(this, "username"));
            this.binding.Pronounce.setText(Constants.getDataFromSharedPref(this, "pronouns"));
            this.binding.imgAvatar.setImageResource(i2);
        } else if (settingToTerm) {
            int i3 = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.binding.guestLL.setVisibility(View.VISIBLE);
            this.binding.name.setText(Constants.getDataFromSharedPref(this, "username"));
            this.binding.Pronounce.setText(Constants.getDataFromSharedPref(this, "pronouns"));
            this.binding.imgAvatar.setImageResource(i3);
        } else if (settingToPolicy) {
            int i4 = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.binding.guestLL.setVisibility(View.VISIBLE);
            this.binding.name.setText(Constants.getDataFromSharedPref(this, "username"));
            this.binding.Pronounce.setText(Constants.getDataFromSharedPref(this, "pronouns"));
            this.binding.imgAvatar.setImageResource(i4);
        } else if (settingToFaq) {
            int i5 = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.binding.guestLL.setVisibility(View.VISIBLE);
            this.binding.name.setText(Constants.getDataFromSharedPref(this, "username"));
            this.binding.Pronounce.setText(Constants.getDataFromSharedPref(this, "pronouns"));
            this.binding.imgAvatar.setImageResource(i5);
        } else {
            this.binding.guestLL.setVisibility(View.GONE);
            this.binding.name.setText(this.userDataModel.getUserName());
            this.binding.Pronounce.setText(this.userDataModel.getPronouns());
        }
    }

    
    public  void m20x358b0067(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.firebaseToken = str;
        Log.e("==>", "setView: " + this.firebaseToken);
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        boolean booleanExtra = this.intent.getBooleanExtra("chatToSetting", false);
        boolean booleanExtra2 = this.intent.getBooleanExtra("chatToSetting", false);
        if (booleanExtra) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else if (booleanExtra2) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else if (settingToTerm) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else if (settingToPolicy) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else if (settingToFaq) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else {
            this.userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
            this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.userDataModel.getImageId()));
        }
    }

    
    /* renamed from: lambda$setClicks$1$ai-girlfriend-virtual-chatgirl-Activity-SettingsActivity  reason: not valid java name */
    public  void m21xb7be1e2e(View view) {
        onBackPressed();
    }

    public void setClicks() {
        this.binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                AISettingsActivity.this.m21xb7be1e2e(view);
            }
        });
        this.binding.llPronounce.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                AISettingsActivity.this.showPronounsDialog();
            }
        });
        this.binding.llName.setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                AISettingsActivity.this.m22x44ab354d(view);
            }
        });


        this.binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                int[] iArr = {R.drawable.girl_av2, R.drawable.girl_av3, R.drawable.girl_av4, R.drawable.girl_av5, R.drawable.girl_av6, R.drawable.girl_av7, R.drawable.girl_av8, R.drawable.girl_av9, R.drawable.girl_av10, R.drawable.girl_av11, R.drawable.boy_av1, R.drawable.boy_av2, R.drawable.boy_av3, R.drawable.boy_av4};
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AISettingsActivity.this, R.style.AppBottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.dialog_box);
                bottomSheetDialog.setCancelable(true);
                RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(AISettingsActivity.this, 0, false));
                AISettingsActivity settingsActivity = AISettingsActivity.this;
                recyclerView.setAdapter(new ImageAdapter(settingsActivity, iArr, settingsActivity.userDataModel));
                bottomSheetDialog.show();
            }
        });
        this.binding.mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                AISettingsActivity.this.showLogoutDialog();
            }
        });

        this.binding.headerText.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                AISettingsActivity.this.bottomSheet = new BottomSheetDialog(AISettingsActivity.this, R.style.AppBottomSheetDialogTheme);
                View inflate = View.inflate(AISettingsActivity.this, R.layout.dialog_login, null);
                AISettingsActivity.this.bottomSheet.setContentView(inflate);
                BottomSheetBehavior from = BottomSheetBehavior.from((View) inflate.getParent());
                from.setMaxHeight(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
                from.setPeekHeight(1000);
                AISettingsActivity.this.bottomSheet.setCancelable(false);
                AISettingsActivity.this.bottomSheet.show();
                ((AppCompatImageView) AISettingsActivity.this.bottomSheet.findViewById(R.id.mClose)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {
                        AISettingsActivity.this.bottomSheet.dismiss();
                    }
                });
                ((RelativeLayout) AISettingsActivity.this.bottomSheet.findViewById(R.id.rl_mail)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {

                    }
                });
                ((RelativeLayout) AISettingsActivity.this.bottomSheet.findViewById(R.id.rl_google)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {
                        AISettingsActivity.this.signIn();
                    }
                });
            }
        });
    }

    
    public  void m22x44ab354d(View view) {
        nameDialog();
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

        this.binding.blurViewFive.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewFive.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewFive.setClipToOutline(true);
    }

    public void showPronounsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_gender);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        View decorView = getWindow().getDecorView();
        Drawable background = decorView.getBackground();
        BlurView blurView = (BlurView) dialog.findViewById(R.id.blurView);
        blurView.setupWith((ViewGroup) decorView.findViewById(16908290), new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);
        final TextView textView = (TextView) dialog.findViewById(R.id.btnMale);
        final TextView textView2 = (TextView) dialog.findViewById(R.id.btnFemale);
        final TextView textView3 = (TextView) dialog.findViewById(R.id.btnThey);
        final boolean booleanExtra = this.intent.getBooleanExtra("chatToSetting", false);
        final boolean booleanExtra2 = this.intent.getBooleanExtra("chatToSetting", false);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                textView.setBackgroundResource(R.drawable.blue_et_bg);
                textView2.setBackgroundResource(R.drawable.rent_full_et_bg);
                textView3.setBackgroundResource(R.drawable.rent_full_et_bg);
                AISettingsActivity.this.i = 0;
                if (booleanExtra) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "He");
                } else if (booleanExtra2) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "He");
                } else if (AISettingsActivity.settingToTerm) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "He");
                } else if (AISettingsActivity.settingToPolicy) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "He");
                } else if (AISettingsActivity.settingToFaq) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "He");
                } else {
                    AISettingsActivity.this.userDataModel.setPronouns("He");
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                textView2.setBackgroundResource(R.drawable.blue_et_bg);
                textView.setBackgroundResource(R.drawable.rent_full_et_bg);
                textView3.setBackgroundResource(R.drawable.rent_full_et_bg);
                AISettingsActivity.this.i = 1;
                if (booleanExtra) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "She");
                } else if (booleanExtra2) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "She");
                } else if (AISettingsActivity.settingToTerm) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "She");
                } else if (AISettingsActivity.settingToPolicy) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "She");
                } else if (AISettingsActivity.settingToFaq) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "She");
                } else {
                    AISettingsActivity.this.userDataModel.setPronouns("She");
                }
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                textView3.setBackgroundResource(R.drawable.blue_et_bg);
                textView2.setBackgroundResource(R.drawable.rent_full_et_bg);
                textView.setBackgroundResource(R.drawable.rent_full_et_bg);
                AISettingsActivity.this.i = 2;
                if (booleanExtra) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "They");
                } else if (booleanExtra2) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "They");
                } else if (AISettingsActivity.settingToTerm) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "They");
                } else if (AISettingsActivity.settingToPolicy) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "They");
                } else if (AISettingsActivity.settingToFaq) {
                    Constants.saveDataInSharedPref(AISettingsActivity.this, "pronouns", "They");
                } else {
                    AISettingsActivity.this.userDataModel.setPronouns("They");
                }
            }
        });
        ((ImageView) dialog.findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                AISettingsActivity.this.m24x3deaa2e(booleanExtra, dialog, booleanExtra2, view);
            }
        });
        dialog.show();
    }

    

    public  void m24x3deaa2e(boolean z, Dialog dialog, boolean z2, View view) {
        int i = this.i;
        if (i == 0) {
            this.binding.Pronounce.setText("He");
            this.pronouns = "He";
        } else if (i == 1) {
            this.binding.Pronounce.setText("She");
            this.pronouns = "She";
        } else {
            this.binding.Pronounce.setText("They");
            this.pronouns = "They";
        }
        if (z) {
            Constants.saveDataInSharedPref(this, "pronouns", this.pronouns);
            dialog.dismiss();
        } else if (z2) {
            Constants.saveDataInSharedPref(this, "pronouns", this.pronouns);
            dialog.dismiss();
        } else if (settingToTerm) {
            Constants.saveDataInSharedPref(this, "pronouns", this.pronouns);
            dialog.dismiss();
        } else if (settingToPolicy) {
            Constants.saveDataInSharedPref(this, "pronouns", this.pronouns);
            dialog.dismiss();
        } else if (settingToFaq) {
            Constants.saveDataInSharedPref(this, "pronouns", this.pronouns);
            dialog.dismiss();
        } else {
            this.userDataModel.setPronouns(this.pronouns);
            Constants.saveDataInSharedPref(this, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
            dialog.dismiss();
        }
    }

    public void nameDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_name);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        View decorView = getWindow().getDecorView();
        Drawable background = decorView.getBackground();
        BlurView blurView = (BlurView) dialog.findViewById(R.id.blurView);
        blurView.setupWith((ViewGroup) decorView.findViewById(16908290), new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);
        final EditText editText = (EditText) dialog.findViewById(R.id.et_name);
        editText.setBackground(null);
        final boolean booleanExtra = this.intent.getBooleanExtra("chatToSetting", false);
        final boolean booleanExtra2 = this.intent.getBooleanExtra("chatToSetting", false);
        if (booleanExtra) {
            editText.setText(Constants.getDataFromSharedPref(this, "username"));
        } else if (booleanExtra2) {
            editText.setText(Constants.getDataFromSharedPref(this, "username"));
        } else if (settingToTerm) {
            editText.setText(Constants.getDataFromSharedPref(this, "username"));
        } else if (settingToPolicy) {
            editText.setText(Constants.getDataFromSharedPref(this, "username"));
        } else if (settingToFaq) {
            editText.setText(Constants.getDataFromSharedPref(this, "username"));
        } else {
            editText.setText(this.userDataModel.getUserName());
        }
        ((ImageView) dialog.findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                AISettingsActivity.this.m19xfa4a8d2b(booleanExtra, editText, dialog, booleanExtra2, view);
            }
        });
        dialog.show();
    }

    

    public  void m19xfa4a8d2b(boolean z, EditText editText, Dialog dialog, boolean z2, View view) {
        if (z) {
            Constants.saveDataInSharedPref(this, "username", editText.getText().toString());
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        } else if (z2) {
            Constants.saveDataInSharedPref(this, "username", editText.getText().toString());
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        } else if (settingToTerm) {
            Constants.saveDataInSharedPref(this, "username", editText.getText().toString());
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        } else if (settingToPolicy) {
            Constants.saveDataInSharedPref(this, "username", editText.getText().toString());
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        } else if (settingToFaq) {
            Constants.saveDataInSharedPref(this, "username", editText.getText().toString());
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        } else {
            this.userDataModel.setUserName(editText.getText().toString());
            Constants.saveDataInSharedPref(this, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
            this.binding.name.setText(editText.getText().toString());
            dialog.dismiss();
        }
    }

    
    public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {
        Context context;
        int[] imgList;
        UserDataModel userDataModel;

        public ImageAdapter(Context context, int[] iArr, UserDataModel userDataModel) {
            this.context = context;
            this.imgList = iArr;
            this.userDataModel = userDataModel;
        }

        
        @Override 
        public ImageHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ImageHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imagelist, viewGroup, false));
        }

        @Override 
        public void onBindViewHolder(final ImageHolder imageHolder, final int i) {
            Glide.with(imageHolder.itemView.getContext()).load(Integer.valueOf(this.imgList[i])).into(imageHolder.imageView);
            imageHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override 
                public final void onClick(View view) {
                    ImageAdapter.this.m32x5f9a2083(i, imageHolder, view);
                }
            });
            if (AISettingsActivity.selected_pos == i) {
                imageHolder.border.setBackgroundResource(R.drawable.rent_circle);
            } else {
                imageHolder.border.setBackgroundResource(R.drawable.rent_circle_no_value);
            }
        }

        

        public  void m32x5f9a2083(int i, ImageHolder imageHolder, View view) {
            AISettingsActivity settingsActivity = AISettingsActivity.this;
            settingsActivity.intent = settingsActivity.getIntent();
            boolean booleanExtra = AISettingsActivity.this.intent.getBooleanExtra("chatToSetting", false);
            boolean booleanExtra2 = AISettingsActivity.this.intent.getBooleanExtra("chatToSetting", false);
            if (booleanExtra) {
                int unused = AISettingsActivity.selected_pos = i;
                AISettingsActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                notifyDataSetChanged();
                SharedPreferences.Editor edit = AISettingsActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit.putInt("imgName", this.imgList[i]);
                edit.commit();
            } else if (booleanExtra2) {
                int unused2 = AISettingsActivity.selected_pos = i;
                AISettingsActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                notifyDataSetChanged();
                SharedPreferences.Editor edit2 = AISettingsActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit2.putInt("imgName", this.imgList[i]);
                edit2.commit();
            } else {
                this.userDataModel.setImageId(String.valueOf(imageHolder.getAdapterPosition()));
                Constants.saveDataInSharedPref(this.context, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
                int unused3 = AISettingsActivity.selected_pos = i;
                AISettingsActivity.this.binding.imgAvatar.setImageResource(AISettingsActivity.this.i);
                notifyDataSetChanged();
                SharedPreferences.Editor edit3 = AISettingsActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit3.putInt("imgName", this.imgList[i]);
                edit3.commit();
                AISettingsActivity.this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.userDataModel.getImageId()));
            }
        }

        @Override 
        public int getItemCount() {
            return this.imgList.length;
        }
    }

    
    public class ImageHolder extends RecyclerView.ViewHolder {
        RelativeLayout border;
        CircleImageView imageView;

        public ImageHolder(View view) {
            super(view);
            this.imageView = (CircleImageView) view.findViewById(R.id.imgView);
            this.border = (RelativeLayout) view.findViewById(R.id.imgBorder);
        }
    }

    
    public void showLogoutDialog() {
        final boolean booleanExtra = this.intent.getBooleanExtra("chatToSetting", false);
        final boolean booleanExtra2 = this.intent.getBooleanExtra("chatToSetting", false);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {

                    AdsManager.getInstance().showInterstitialAd(AISettingsActivity.this, new AdsManager.AdCloseListener() {
                        @Override
                        public void onAdClosed() {
                            AISettingsActivity.this.m23x8771c04d(booleanExtra, booleanExtra2, dialog, view);
                        }
                    });


            }
        });
        dialog.show();
    }

    

    public  void m23x8771c04d(boolean z, boolean z2, Dialog dialog, View view) {
        if (z) {
            CheckUpdatePlay.clearPreference(this);
            getSharedPreferences("YOUR_PREF_NAME", 0).edit().clear().commit();
            SharedPreferences.Editor edit = getSharedPreferences(Constants.PREF_NAME, 0).edit();
            edit.clear();
            edit.apply();
            edit.commit();
            CheckUpdatePlay.setPreference(getApplicationContext(), "firstTime", true);
            Intent intent = new Intent(this, GuestLoginActivity.class);
            this.intent = intent;
            startActivity(intent);
        } else if (z2) {
            CheckUpdatePlay.clearPreference(this);
            getSharedPreferences("YOUR_PREF_NAME", 0).edit().clear().commit();
            SharedPreferences.Editor edit2 = getSharedPreferences(Constants.PREF_NAME, 0).edit();
            edit2.clear();
            edit2.apply();
            edit2.commit();
            CheckUpdatePlay.setPreference(getApplicationContext(), "firstTime", true);
            Intent intent2 = new Intent(this, GuestLoginActivity.class);
            this.intent = intent2;
            startActivity(intent2);
        } else {
            dialog.dismiss();
            this.mWaitDialog.show();
            logOutUser();
            this.mGoogleSignInClient.signOut();
        }
    }

    private void logOutUser() {
        final View rootView = getWindow().getDecorView().getRootView();
        StringRequest stringRequest = new StringRequest(1, URLs.URL_LOG_OUT, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str) {
                AISettingsActivity.this.mWaitDialog.dismiss();
                Log.e("==>ds", "onResponse: " + str);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        SharedPreferences.Editor edit = AISettingsActivity.this.getSharedPreferences(Constants.PREF_NAME, 0).edit();
                        edit.clear();
                        edit.apply();
                        edit.commit();
                        CheckUpdatePlay.setPreference(AISettingsActivity.this.getApplicationContext(), "firstTime", true);
                        AISettingsActivity.this.startActivity(new Intent(AISettingsActivity.this, GuestLoginActivity.class));
                        AISettingsActivity.this.finish();
                    } else {
                        AISettingsActivity.this.mWaitDialog.dismiss();
                        Constants.showSnackBarMessage(AISettingsActivity.this, rootView, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    AISettingsActivity.this.mWaitDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("==>", "====" + volleyError.getMessage());
                AISettingsActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(AISettingsActivity.this, rootView, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(AISettingsActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }

    
    public void deleteAccount() {
        final View rootView = getWindow().getDecorView().getRootView();
        StringRequest stringRequest = new StringRequest(1, URLs.URL_DELETE_ACC, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str) {
                AISettingsActivity.this.mWaitDialog.dismiss();
                Log.e("==>ds", "onResponse: " + str);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        SharedPreferences.Editor edit = AISettingsActivity.this.getSharedPreferences(Constants.PREF_NAME, 0).edit();
                        edit.clear();
                        edit.apply();
                        edit.commit();
                        AISettingsActivity.this.startActivity(new Intent(AISettingsActivity.this, GuestLoginActivity.class));
                        AISettingsActivity.this.finish();
                    } else {
                        AISettingsActivity.this.mWaitDialog.dismiss();
                        Constants.showSnackBarMessage(AISettingsActivity.this, rootView, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    AISettingsActivity.this.mWaitDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("==>", "====" + volleyError.getMessage());
                AISettingsActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(AISettingsActivity.this, rootView, volleyError.getMessage());
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(AISettingsActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
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
        StringRequest stringRequest = new StringRequest(1, URLs.URL_LOGIN_WITH_GOOGLE, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str4) {
                try {
                    JSONObject jSONObject = new JSONObject(str4);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        Constants.saveDataInSharedPref(AISettingsActivity.this, Constants.ACCESS_TOKEN, jSONObject.getJSONObject("data").getString("token"));
                        Constants.saveBooleanDataInSharedPref(AISettingsActivity.this, Constants.IS_LOGIN, true);
                        AISettingsActivity.this.getUserDetails();
                    } else {
                        AISettingsActivity.this.mWaitDialog.dismiss();
                        Constants.showSnackBarMessage(AISettingsActivity.this, rootView, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    AISettingsActivity.this.mWaitDialog.dismiss();
                    e.printStackTrace();
                    Constants.showSnackBarMessage(AISettingsActivity.this, rootView, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("==>LogIn", "onErrorResponse: " + volleyError.getMessage());
                AISettingsActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(AISettingsActivity.this, rootView, volleyError.getMessage());
            }
        }) {
            @Override 
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("name", str2);
                hashMap.put("id", str);
                hashMap.put("firebase_token", AISettingsActivity.this.firebaseToken);
                hashMap.put("email", str3);
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        super.onStart();
    }

    
    public void getUserDetails() {
        StringRequest stringRequest = new StringRequest(0, URLs.GET_USER_INFO, new Response.Listener<String>() {
            @Override 
            public void onResponse(String str) {
                AISettingsActivity.this.mWaitDialog.dismiss();
                try {
                    Log.e("==>", "onResponse: " + str);
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        if (jSONObject.getString("data").equals("null")) {
                            AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, MainActivity.class);
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
                            Constants.saveDataInSharedPref(AISettingsActivity.this, Constants.USER_DATA, new Gson().toJson(userDataModel));
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
                                                                    AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, AIChatActivity.class);
                                                                }
                                                                AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, PeaceFulActivity.class);
                                                            }
                                                            AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, GoalsNameActivity.class);
                                                        }
                                                        AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, PersonalInterestActivity.class);
                                                    }
                                                    AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, TweakPersonalityActivity.class);
                                                }
                                                AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, TweakPersonalityActivity.class);
                                            }
                                            AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, TweakPersonalityActivity.class);
                                        }
                                        AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, SelectAIActivity.class);
                                    }
                                    AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, GirlNameActivity.class);
                                }
                                AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, GenderActivity.class);
                            }
                            AISettingsActivity.this.intent = new Intent(AISettingsActivity.this, MainActivity.class);
                        }
                        AISettingsActivity settingsActivity = AISettingsActivity.this;
                        settingsActivity.startActivity(settingsActivity.intent);
                        return;
                    }
                    Toasty.error(AISettingsActivity.this, "Something went wrong").show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                AISettingsActivity.this.mWaitDialog.dismiss();
            }
        }) {
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(AISettingsActivity.this, Constants.ACCESS_TOKEN));
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
        super.onBackPressed();
    }
}
