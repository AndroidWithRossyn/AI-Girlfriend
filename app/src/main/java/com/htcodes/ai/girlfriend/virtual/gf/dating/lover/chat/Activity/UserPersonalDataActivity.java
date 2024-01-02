package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivityUserPersonalDataBinding;

import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import es.dmoral.toasty.Toasty;


public class UserPersonalDataActivity extends AppCompatActivity {
    ActivityUserPersonalDataBinding binding;
    Intent intent;
    Dialog mWaitDialog;
    int i = 0;
    int row_index = -1;
    UserDataModel userDataModel = new UserDataModel();

    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(17432576, 17432577);
        this.binding = (ActivityUserPersonalDataBinding) DataBindingUtil.setContentView(this, R.layout.activity_user_personal_data);
        this.userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),UserDataModel.class);
        Dialog dialog = new Dialog(this);
        this.mWaitDialog = dialog;
        dialog.setContentView(R.layout.ic_loading);
        this.mWaitDialog.getWindow().setLayout(-1, -2);
        this.mWaitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mWaitDialog.setCancelable(false);


        setBlur();
        setClicks();
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        this.intent = intent;
        boolean booleanExtra = intent.getBooleanExtra("chatToProfile", false);
        boolean booleanExtra2 = this.intent.getBooleanExtra("chatToProfile", false);
        if (booleanExtra) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else if (booleanExtra2) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
        } else {
            this.userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA), UserDataModel.class);
            this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.userDataModel.getImageId()));
        }
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
        }
    }

    

    public  void m13xbc9e157b(View view) {
        onBackPressed();
    }

    public void setClicks() {
        this.binding.mBack.setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                UserPersonalDataActivity.this.m13xbc9e157b(view);
            }
        });
        this.binding.llPronounce.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                UserPersonalDataActivity.this.showPronounsDialog();
            }
        });
        this.binding.llName.setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                UserPersonalDataActivity.this.m14xe2321e7c(view);
            }
        });
        this.binding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                int[] iArr = {R.drawable.girl_av2, R.drawable.girl_av3, R.drawable.girl_av4, R.drawable.girl_av5, R.drawable.girl_av6, R.drawable.girl_av7, R.drawable.girl_av8, R.drawable.girl_av9, R.drawable.girl_av10, R.drawable.girl_av11, R.drawable.boy_av1, R.drawable.boy_av2, R.drawable.boy_av3, R.drawable.boy_av4};
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UserPersonalDataActivity.this, R.style.AppBottomSheetDialogTheme);
                bottomSheetDialog.setContentView(R.layout.dialog_box);
                bottomSheetDialog.setCancelable(true);
                RecyclerView recyclerView = (RecyclerView) bottomSheetDialog.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(UserPersonalDataActivity.this, 0, false));
                UserPersonalDataActivity profileActivity = UserPersonalDataActivity.this;
                recyclerView.setAdapter(new ImageAdapter(profileActivity, iArr, profileActivity.userDataModel));
                bottomSheetDialog.show();
            }
        });
        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                Toasty.success(UserPersonalDataActivity.this, "Tweak Personality Updated").show();
            }
        });
    }

    

    public  void m14xe2321e7c(View view) {
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
        this.binding.blurViewThird.setupWith(viewGroup, new RenderScriptBlur(this)).setFrameClearDrawable(background).setBlurRadius(20.0f);
        this.binding.blurViewThird.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        this.binding.blurViewThird.setClipToOutline(true);
    }

    public void showPronounsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_status);
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                textView.setBackgroundResource(R.drawable.blue_et_bg);
                textView2.setBackgroundResource(R.drawable.rent_full_et_bg);
                UserPersonalDataActivity.this.i = 0;
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                textView2.setBackgroundResource(R.drawable.blue_et_bg);
                textView.setBackgroundResource(R.drawable.rent_full_et_bg);
                UserPersonalDataActivity.this.i = 1;
            }
        });
        ((ImageView) dialog.findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                UserPersonalDataActivity.this.m15x643c097b(dialog, view);
            }
        });
        dialog.show();
    }

    

    public  void m15x643c097b(Dialog dialog, View view) {
        Toasty.success(this, "Status is updated").show();
        dialog.dismiss();
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
        final boolean booleanExtra = this.intent.getBooleanExtra("chatToProfile", false);
        final boolean booleanExtra2 = this.intent.getBooleanExtra("chatToProfile", false);
        if (booleanExtra) {
            editText.setText(Constants.getDataFromSharedPref(this, "gfname"));
        } else if (booleanExtra2) {
            editText.setText(Constants.getDataFromSharedPref(this, "gfname"));
        } else {
            editText.setText(this.userDataModel.getName());
        }
        ((ImageView) dialog.findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override 
            public final void onClick(View view) {
                UserPersonalDataActivity.this.m12x957952de(booleanExtra, editText, dialog, booleanExtra2, view);
            }
        });
        dialog.show();
    }

    

    public  void m12x957952de(boolean z, EditText editText, Dialog dialog, boolean z2, View view) {
        if (z) {
            Constants.saveDataInSharedPref(this, "gfname", editText.getText().toString());
            if (editText.length() == 0) {
                Toasty.error(this, "Please enter name").show();
                return;
            }
            Toasty.success(this, "Name is updated to " + editText.getText().toString()).show();
            dialog.dismiss();
        } else if (z2) {
            Constants.saveDataInSharedPref(this, "gfname", editText.getText().toString());
            if (editText.length() == 0) {
                Toasty.error(this, "Please enter name").show();
                return;
            }
            Toasty.success(this, "Name is updated to " + editText.getText().toString()).show();
            dialog.dismiss();
        } else {
            this.userDataModel.setName(editText.getText().toString().trim());
            Constants.saveDataInSharedPref(this, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
            if (editText.length() == 0) {
                Toasty.error(this, "Please enter name").show();
                return;
            }
            Toasty.success(this, "Name is updated to " + editText.getText().toString()).show();
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
                    ImageAdapter.this.m16x4d78e363(i, imageHolder, view);
                }
            });
            if (UserPersonalDataActivity.this.row_index == i) {
                imageHolder.border.setBackgroundResource(R.drawable.rent_circle);
            } else {
                imageHolder.border.setBackgroundResource(R.drawable.rent_circle_no_value);
            }
        }

        

        public  void m16x4d78e363(int i, ImageHolder imageHolder, View view) {
            UserPersonalDataActivity profileActivity = UserPersonalDataActivity.this;
            profileActivity.intent = profileActivity.getIntent();
            boolean booleanExtra = UserPersonalDataActivity.this.intent.getBooleanExtra("chatToProfile", false);
            boolean booleanExtra2 = UserPersonalDataActivity.this.intent.getBooleanExtra("chatToProfile", false);
            if (booleanExtra) {
                UserPersonalDataActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                UserPersonalDataActivity.this.row_index = i;
                notifyDataSetChanged();
                SharedPreferences.Editor edit = UserPersonalDataActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit.putInt("imgName", this.imgList[i]);
                edit.commit();
            } else if (booleanExtra2) {
                UserPersonalDataActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                UserPersonalDataActivity.this.row_index = i;
                notifyDataSetChanged();
                SharedPreferences.Editor edit2 = UserPersonalDataActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit2.putInt("imgName", this.imgList[i]);
                edit2.commit();
            } else {
                this.userDataModel.setImageId(String.valueOf(imageHolder.getAdapterPosition()));
                Constants.saveDataInSharedPref(this.context, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
                UserPersonalDataActivity.this.binding.imgAvatar.setImageResource(UserPersonalDataActivity.this.i);
                UserPersonalDataActivity.this.row_index = i;
                notifyDataSetChanged();
                SharedPreferences.Editor edit3 = UserPersonalDataActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit3.putInt("imgName", this.imgList[i]);
                edit3.commit();
                UserPersonalDataActivity.this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.userDataModel.getImageId()));
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

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }
}
