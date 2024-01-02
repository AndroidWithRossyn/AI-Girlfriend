package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.GiftModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.NewChatModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.ChatAdapter;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.ChatDbHelper;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.CheckUpdatePlay;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.KeyboardHeightProvider;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.MsgModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.RetrofitAPI;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.URLs;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AppPreference;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AIChatActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ChatAdapter adapter;
    AppPreference appPreference;
    ImageView arrowUp;
    BottomSheetDialog bottomSheet;
    ImageView btnClose;
    ChatDbHelper chatDbHelper;
    LinearLayout contentSuggestion;
    int dp;
    String et_message;
    EditText et_msg;
    ImageView imageView;
    ImageView imgMic;
    CircleImageView img_dp;
    ImageView img_send;
    Intent intent;
    KeyListener listener;
    ImageView mGift;
    ImageView mSettings;
    DisplayMetrics metrics;
    CircleImageView pfImage;
    RecyclerView rcvSuggestion;
    RecyclerView rcv_chat;
    int screenheight;
    int screenwidth;
    TextView tv_username;
    String username;
    UserDataModel getUserData = new UserDataModel();
    ArrayList<NewChatModel> msgList = new ArrayList<>();
    ArrayList<String> suggestionList = new ArrayList<>();
    Dialog mWaitDialog;
    GoogleSignInClient mGoogleSignInClient;
    String firebaseToken = "";


    public void BannerLoadads() {
      //  AdsManager.getInstance().loadBanner(AIChatActivity.this, getString(R.string.Admob_Banner));
    }

    public void LoadANNABHAGYAAds() {
        AdsManager.getInstance().loadInterstitialAd(AIChatActivity.this, getString(R.string.Admob_Interstitial));

    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadANNABHAGYAAds();
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_ai_chat);

      //  BannerLoadads();

        this.chatDbHelper = new ChatDbHelper(this);
        this.appPreference = new AppPreference(this);
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);

        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build());

        new KeyboardHeightProvider(this, getWindowManager(), findViewById(R.id.contentBottom), new KeyboardHeightProvider.KeyboardHeightListener() {
            @Override
            public void onKeyboardHeightChanged(int i, boolean z, boolean z2) {
                Log.d("keyboard", "keyboardHeight: " + i + " keyboardOpen: " + z + " isLandscape: " + z2);
                if (z) {
                    AIChatActivity.this.findViewById(R.id.contentBottom).setPadding(0, 15, 0, i + 80);
                } else {
                    AIChatActivity.this.findViewById(R.id.contentBottom).setPadding(0, 15, 0, 15);
                }
            }
        });

        int i = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.metrics = displayMetrics;
        this.screenheight = displayMetrics.heightPixels;
        this.screenwidth = this.metrics.widthPixels;
        this.msgList.clear();
        this.msgList = this.chatDbHelper.getAllChatMessages();
        this.img_send = (ImageView) findViewById(R.id.img_send);
        this.et_msg = (EditText) findViewById(R.id.et_msg);
        this.rcv_chat = (RecyclerView) findViewById(R.id.rcv_chat);
        this.mSettings = (ImageView) findViewById(R.id.mSettings);
        this.pfImage = (CircleImageView) findViewById(R.id.pfImage);
        this.imageView = (ImageView) findViewById(R.id.src);
        this.mGift = (ImageView) findViewById(R.id.mGift);
        this.arrowUp = (ImageView) findViewById(R.id.arrowUp);
        this.btnClose = (ImageView) findViewById(R.id.btnClose);
        this.contentSuggestion = (LinearLayout) findViewById(R.id.contentSuggestion);
        this.rcvSuggestion = (RecyclerView) findViewById(R.id.rcvSuggestion);
        this.imgMic = (ImageView) findViewById(R.id.imgMic);

        this.et_msg.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            
            @Override 
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override 
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override 
            public void onDestroyActionMode(ActionMode actionMode) {
            }

            @Override 
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }
        });

        this.et_msg.setLongClickable(false);
        this.et_msg.setTextIsSelectable(false);
        this.getUserData = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
        final boolean booleanExtra = getIntent().getBooleanExtra("RelaxToChat", false);
        final boolean booleanExtra2 = getIntent().getBooleanExtra("guestChat", false);
        if (booleanExtra) {
            this.imageView.setImageResource(i);
            this.pfImage.setImageResource(i);

        } else if (booleanExtra2) {
            this.imageView.setImageResource(i);
            this.pfImage.setImageResource(i);
        } else {
            this.imageView.setImageResource(Constants.getSelectedImageAvatar(this.getUserData.getImageId()));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.username = extras.getString( "username");
            this.dp = extras.getInt("dp");
            String str = this.username;
            if (str != null) {
                this.tv_username.setText(str);
            }
            int i2 = this.dp;
            if (i2 != 0) {
                this.img_dp.setImageResource(i2);
            }
        }
        this.adapter = new ChatAdapter(this, this.msgList, this.dp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.rcv_chat.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        this.rcv_chat.setAdapter(this.adapter);
        this.pfImage.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (booleanExtra) {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, UserPersonalDataActivity.class);
                    AIChatActivity.this.intent.putExtra("chatToProfile", true);
                    AIChatActivity chatActivity = AIChatActivity.this;
                    chatActivity.startActivity(chatActivity.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else if (booleanExtra2) {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, UserPersonalDataActivity.class);
                    AIChatActivity.this.intent.putExtra("chatToProfile", true);
                    AIChatActivity chatActivity2 = AIChatActivity.this;
                    chatActivity2.startActivity(chatActivity2.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, UserPersonalDataActivity.class);
                    AIChatActivity chatActivity3 = AIChatActivity.this;
                    chatActivity3.startActivity(chatActivity3.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });
        this.mSettings.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (booleanExtra) {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, AISettingsActivity.class);
                    AIChatActivity.this.intent.putExtra("chatToSetting", true);
                    AIChatActivity chatActivity = AIChatActivity.this;
                    chatActivity.startActivity(chatActivity.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else if (booleanExtra2) {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, AISettingsActivity.class);
                    AIChatActivity.this.intent.putExtra("chatToSetting", true);
                    AIChatActivity chatActivity2 = AIChatActivity.this;
                    chatActivity2.startActivity(chatActivity2.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } else {
                    AIChatActivity.this.intent = new Intent(AIChatActivity.this, AISettingsActivity.class);
                    AIChatActivity chatActivity3 = AIChatActivity.this;
                    chatActivity3.startActivity(chatActivity3.intent);
                    AIChatActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });
        if (booleanExtra) {
            String dataFromSharedPref = Constants.getDataFromSharedPref(this, "username");
            Constants.getMessageList(dataFromSharedPref, Constants.getDataFromSharedPref(this, "gfname"));
            Constants.getGiftList(dataFromSharedPref);
        } else if (booleanExtra2) {
            String dataFromSharedPref2 = Constants.getDataFromSharedPref(this, "username");
            Constants.getMessageList(dataFromSharedPref2, Constants.getDataFromSharedPref(this, "gfname"));
            Constants.getGiftList(dataFromSharedPref2);
        } else {
            String userName = this.getUserData.getUserName();
            Constants.getMessageList(userName, this.getUserData.getName());
            Constants.getGiftList(userName);
        }
        if (this.msgList.isEmpty()) {
            this.msgList.add(new NewChatModel("", -1, "", "ai", Constants.msgList1.get(0)));
            this.adapter.notifyDataSetChanged();
            this.rcv_chat.scrollToPosition(this.msgList.size() - 1);
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    AIChatActivity.this.call();
                }
            }, 3200L);
        }
        this.img_send.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AIChatActivity chatActivity = AIChatActivity.this;
                chatActivity.listener = chatActivity.et_msg.getKeyListener();
                if (AIChatActivity.this.et_msg.getText().toString().equals("") || AIChatActivity.this.et_msg.getText().toString().equals(" ")) {
                    AIChatActivity.this.et_msg.setKeyListener(AIChatActivity.this.listener);
                    Constants.showSnackBarMessage(AIChatActivity.this, view, "Write your message");
                    return;
                }

                AIChatActivity chatActivity2 = AIChatActivity.this;
                chatActivity2.et_message = chatActivity2.et_msg.getText().toString();
                AIChatActivity.this.et_msg.setKeyListener(null);
                NewChatModel newChatModel = new NewChatModel(AIChatActivity.this.et_msg.getText().toString().trim(), -1, "text", "user", "");
                AIChatActivity.this.msgList.add(newChatModel);
                newChatModel.setFromHistory("true");
                AIChatActivity.this.chatDbHelper.insertChatMessage(newChatModel);
                AIChatActivity.this.adapter.notifyDataSetChanged();
                AIChatActivity.this.rcv_chat.scrollToPosition(AIChatActivity.this.msgList.size() - 1);
                AIChatActivity chatActivity3 = AIChatActivity.this;
                chatActivity3.callPrompt1(chatActivity3.et_msg.getText().toString().trim());
                AIChatActivity.this.et_msg.getText().clear();
            }
        });
        this.imgMic.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
                intent.putExtra("android.speech.extra.PROMPT", "Speech to text for AnaAi");
                try {
                    AIChatActivity.this.startActivityForResult(intent, 1);
                } catch (Exception e) {
                    AIChatActivity chatActivity = AIChatActivity.this;
                    Toast.makeText(chatActivity, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.mGift.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AIChatActivity.this.giftSheet();
            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AIChatActivity.this.btnClose.setVisibility(View.GONE);
                AIChatActivity.this.arrowUp.setVisibility(View.VISIBLE);
            }
        });
        this.arrowUp.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AIChatActivity.this.contentSuggestion.setVisibility(View.VISIBLE);
                AIChatActivity.this.arrowUp.setVisibility(View.GONE);
                AIChatActivity.this.btnClose.setVisibility(View.VISIBLE);
            }
        });
        addSuggestion();
        this.rcvSuggestion.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.rcvSuggestion.setAdapter(new SuggestionAdapter(this, this.suggestionList));
        this.btnClose.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AIChatActivity.this.contentSuggestion.setVisibility(View.GONE);
                AIChatActivity.this.btnClose.setVisibility(View.GONE);
                AIChatActivity.this.arrowUp.setVisibility(View.VISIBLE);
            }
        });
    }

    public  void m20x358b0067(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.firebaseToken = str;
        Log.e("==>", "setView: " + this.firebaseToken);
    }
    public void callPrompt1(String str) {
        ((RetrofitAPI) new Retrofit.Builder().baseUrl("http://api.brainshop.ai/").addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitAPI.class)).getMassage("http://api.brainshop.ai/get?bid=171959&key=ZGb8EBNUfxmXwJ8i&uid=[uid]&msg=" + str).enqueue(new Callback<MsgModel>() {
            @Override 
            
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    AIChatActivity.this.et_msg.setKeyListener(AIChatActivity.this.listener);
                    NewChatModel newChatModel = new NewChatModel("", -1, "", "ai", response.body().getCnt().replace("\n", ""));
                    AIChatActivity.this.msgList.add(newChatModel);
                    newChatModel.setFromHistory("true");
                    AIChatActivity.this.chatDbHelper.insertChatMessage(newChatModel);
                    AIChatActivity.this.adapter.notifyDataSetChanged();
                    AIChatActivity.this.rcv_chat.scrollToPosition(AIChatActivity.this.msgList.size() - 1);
                }
            }

            @Override 
            public void onFailure(Call<MsgModel> call, Throwable th) {
                Log.d("==>", "onFailure: " + th.getMessage());
            }
        });
    }

    
    public void call() {
        this.msgList.add(new NewChatModel("", -1, "", "ai", Constants.msgList1.get(1)));
        this.adapter.notifyDataSetChanged();
        this.rcv_chat.scrollToPosition(this.msgList.size() - 1);
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
                AIChatActivity.this.call1();
            }
        }, 3200L);
    }

    
    public void call1() {
        this.msgList.add(new NewChatModel("", -1, "", "ai", Constants.msgList1.get(2)));
        this.adapter.notifyDataSetChanged();
        this.rcv_chat.scrollToPosition(this.msgList.size() - 1);
        for (int i = 0; i < this.msgList.size(); i++) {
            NewChatModel newChatModel = this.msgList.get(i);
            newChatModel.setFromHistory("true");
            this.chatDbHelper.insertChatMessage(newChatModel);
        }
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        this.intent = intent;
        boolean booleanExtra = intent.getBooleanExtra("RelaxToChat", false);
        boolean booleanExtra2 = this.intent.getBooleanExtra("guestChat", false);
        if (booleanExtra) {
            int i = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.imageView.setImageResource(i);
            this.pfImage.setImageResource(i);
        } else if (booleanExtra2) {
            Log.e("-->", "onResume: Called");
            int i2 = getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2);
            this.imageView.setImageResource(i2);
            this.pfImage.setImageResource(i2);
        } else {
            UserDataModel userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
            this.getUserData = userDataModel;
            this.pfImage.setImageResource(Constants.getSelectedImageAvatar(userDataModel.getImageId()));
            this.imageView.setImageResource(Constants.getSelectedImageAvatar(this.getUserData.getImageId()));
        }
    }
    public void showLogoutDialog() {
        final boolean booleanExtra = getIntent().getBooleanExtra("RelaxToChat", false);
        final boolean booleanExtra2 = getIntent().getBooleanExtra("guestChat", false);
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

                    AdsManager.getInstance().showInterstitialAd(AIChatActivity.this, new AdsManager.AdCloseListener() {
                        @Override
                        public void onAdClosed() {
                            AIChatActivity.this.m23x8771c04d(booleanExtra, booleanExtra2, dialog, view);
                        }
                    });


            }
        });
        dialog.show();
    }
    @Override 
    public void onBackPressed() {
        showLogoutDialog();
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
        StringRequest stringRequest = new StringRequest(1, URLs.URL_LOG_OUT, new com.android.volley.Response.Listener<String>() {
            @Override 
            public void onResponse(String str) {
                AIChatActivity.this.mWaitDialog.dismiss();
                Log.e("==>ds", "onResponse: " + str);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean(FirebaseAnalytics.Param.SUCCESS)) {
                        SharedPreferences.Editor edit = AIChatActivity.this.getSharedPreferences(Constants.PREF_NAME, 0).edit();
                        edit.clear();
                        edit.apply();
                        edit.commit();
                        CheckUpdatePlay.setPreference(AIChatActivity.this.getApplicationContext(), "firstTime", true);
                        AIChatActivity.this.startActivity(new Intent(AIChatActivity.this, GuestLoginActivity.class));
                        AIChatActivity.this.finish();
                    } else {
                        AIChatActivity.this.mWaitDialog.dismiss();
                        Constants.showSnackBarMessage(AIChatActivity.this, rootView, jSONObject.getString("message"));
                    }
                } catch (JSONException e) {
                    AIChatActivity.this.mWaitDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() { 
            @Override 
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("==>", "====" + volleyError.getMessage());
                AIChatActivity.this.mWaitDialog.dismiss();
                Constants.showSnackBarMessage(AIChatActivity.this, rootView, volleyError.getMessage());
            }
        }) { 
            @Override 
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "Bearer " + Constants.getDataFromSharedPref(AIChatActivity.this, Constants.ACCESS_TOKEN));
                hashMap.put("Accept", "application/json");
                Log.e("==>ds", "getHeaders: " + hashMap.toString());
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(500000, 1, 1.0f));
        Volley.newRequestQueue(this).add(stringRequest);
        super.onStart();
    }




    
    
    public  void m1xc30ed553(Dialog dialog, View view) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        finishAffinity();
    }

    public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionHolder> {
        Context context;
        ArrayList<String> suggest;

        public SuggestionAdapter(Context context, ArrayList<String> arrayList) {
            new ArrayList();
            this.context = context;
            this.suggest = arrayList;
        }

        
        @Override 
        public SuggestionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new SuggestionHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ic_row, viewGroup, false));
        }

        @Override 
        public void onBindViewHolder(SuggestionHolder suggestionHolder, final int i) {
            suggestionHolder.tvSuggestion.setText(this.suggest.get(i));
            suggestionHolder.tvSuggestion.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    AIChatActivity.this.et_msg.setText(SuggestionAdapter.this.suggest.get(i));
                    AIChatActivity.this.img_send.performClick();
                }
            });
            AIChatActivity.this.et_msg.performClick();
        }

        @Override 
        public int getItemCount() {
            return AIChatActivity.this.suggestionList.size();
        }
    }

    public class SuggestionHolder extends RecyclerView.ViewHolder {
        TextView tvSuggestion;

        public SuggestionHolder(View view) {
            super(view);
            this.tvSuggestion = (TextView) view.findViewById(R.id.tvSuggestion);
        }
    }
    public void giftSheet() {
        this.bottomSheet = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        View inflate = View.inflate(this, R.layout.dialog_gift, null);
        this.bottomSheet.setContentView(inflate);
        BottomSheetBehavior from = BottomSheetBehavior.from((View) inflate.getParent());
        from.setMaxHeight(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
        from.setPeekHeight(1000);
        this.bottomSheet.show();
        GiftModel giftModel = new GiftModel();
        ArrayList arrayList = new ArrayList();
        giftModel.setGift(R.drawable.ice_cream);
        giftModel.setName("Ice Cream");
        giftModel.setPrice("Free");
        arrayList.add(giftModel);
        GiftModel giftModel2 = new GiftModel();
        giftModel2.setGift(R.drawable.lipstick);
        giftModel2.setName("LipStick");
        giftModel2.setPrice("Free");
        arrayList.add(giftModel2);
        GiftModel giftModel3 = new GiftModel();
        giftModel3.setGift(R.drawable.lily);
        giftModel3.setName("Lily");
        giftModel3.setPrice("Free");
        arrayList.add(giftModel3);
        GiftModel giftModel4 = new GiftModel();
        giftModel4.setGift(R.drawable.love);
        giftModel4.setName("Love Sticker");
        giftModel4.setPrice("Free");
        arrayList.add(giftModel4);
        RecyclerView recyclerView = (RecyclerView) this.bottomSheet.findViewById(R.id.rcvGift);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new GiftAdapter(this, arrayList, this.chatDbHelper));
        ((AppCompatImageView) this.bottomSheet.findViewById(R.id.mClose)).setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (AIChatActivity.this.bottomSheet.isShowing()) {
                    AIChatActivity.this.bottomSheet.dismiss();
                }
            }
        });
        this.bottomSheet.setCancelable(true);
        this.bottomSheet.show();
    }

    public class GiftAdapter extends RecyclerView.Adapter<GiftHolder> {
        ChatDbHelper chatDbHelper;
        Context context;
        ArrayList<GiftModel> giftModel;

        public GiftAdapter(Context context, ArrayList<GiftModel> arrayList, ChatDbHelper chatDbHelper) {
            this.context = context;
            this.giftModel = arrayList;
            this.chatDbHelper = chatDbHelper;
        }

        @Override 
        public GiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new GiftHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_gift_item, viewGroup, false));
        }

        @Override 
        public void onBindViewHolder(GiftHolder giftHolder, int i) {
            final GiftModel giftModel = this.giftModel.get(i);
            giftHolder.giftImage.setImageResource(giftModel.getGift());
            giftHolder.name.setText(giftModel.getName());
            giftHolder.price.setText(giftModel.getPrice());
            giftHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    NewChatModel newChatModel = new NewChatModel("", giftModel.getGift(), "image", "user", "");
                    AIChatActivity.this.msgList.add(newChatModel);
                    newChatModel.setFromHistory("true");
                    GiftAdapter.this.chatDbHelper.insertChatMessage(newChatModel);
                    AIChatActivity.this.adapter.notifyDataSetChanged();
                    AIChatActivity.this.rcv_chat.scrollToPosition(AIChatActivity.this.msgList.size() - 1);
                    if (AIChatActivity.this.bottomSheet.isShowing()) {
                        AIChatActivity.this.bottomSheet.dismiss();
                    }
                    NewChatModel newChatModel2 = new NewChatModel("", -1, "", "ai", Constants.msgGiftList.get(new Random().nextInt(3) + 0));
                    AIChatActivity.this.msgList.add(newChatModel2);
                    newChatModel2.setFromHistory("true");
                    GiftAdapter.this.chatDbHelper.insertChatMessage(newChatModel2);
                    AIChatActivity.this.adapter.notifyDataSetChanged();
                    AIChatActivity.this.rcv_chat.scrollToPosition(AIChatActivity.this.msgList.size() - 1);
                }
            });
        }

        @Override 
        public int getItemCount() {
            return this.giftModel.size();
        }
    }

    public class GiftHolder extends RecyclerView.ViewHolder {
        ImageView giftImage;
        TextView name;
        TextView price;
        public GiftHolder(View view) {
            super(view);
            this.giftImage = (ImageView) view.findViewById(R.id.giftImage);
            this.name = (TextView) view.findViewById(R.id.name);
            this.price = (TextView) view.findViewById(R.id.price);
        }
    }
    public void addSuggestion() {
        this.suggestionList.add("Hi");
        this.suggestionList.add("How are you?");
        this.suggestionList.add("What is your name?");
        this.suggestionList.add("Let's go for dinner tonight?");
        this.suggestionList.add("Where are you from?");
        this.suggestionList.add("How was your day today?");
        this.suggestionList.add("What is your age?");
        this.suggestionList.add("What are your interests and hobbies?");
        this.suggestionList.add("Can you tell me a joke?");
        this.suggestionList.add("What's your favorite movie or TV show?");
        this.suggestionList.add("Do you have any favorite music artists or bands?");
        this.suggestionList.add("What kind of food do you like?");
        this.suggestionList.add("Can you recommend a good book to read?");
        this.suggestionList.add("What are your thoughts on current events?");
        this.suggestionList.add("What's your favorite place to go on a date?");
        this.suggestionList.add("What's your idea of a perfect weekend?");
        this.suggestionList.add("Do you have any dreams or aspirations?");
        this.suggestionList.add("How do you handle stress or difficult situations?");
        this.suggestionList.add("What's the most memorable trip you've taken?");
        this.suggestionList.add("Do you have any pets or would you like to have one?");
        this.suggestionList.add("Can you help me with my daily tasks or reminders?");
        this.suggestionList.add("Can you suggest some fun activities or games to play together?");
        this.suggestionList.add("What are your favorite conversation topics?");
        this.suggestionList.add("Can you provide relationship advice or tips?");
        this.suggestionList.add("How do you define love?");
        this.suggestionList.add("What's your favorite way to show affection?");
    }


    
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1 && intent != null) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
            EditText editText = this.et_msg;
            Objects.requireNonNull(stringArrayListExtra);
            editText.setText(stringArrayListExtra.get(0));
        }
    }
}
