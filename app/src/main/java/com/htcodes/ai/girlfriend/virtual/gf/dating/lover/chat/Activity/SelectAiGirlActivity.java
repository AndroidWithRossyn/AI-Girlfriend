package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Activity;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.UserDataModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils.Constants;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.AdsManager;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.databinding.ActivitySelectAiGirlBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class SelectAiGirlActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int pic_id = 123;
    public static boolean resume = false;
    ActivitySelectAiGirlBinding binding;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialog1;
    Intent intent;
    int row_index = 0;
    Bitmap bitmap2 = null;
    int[] images = {R.drawable.girl_av2, R.drawable.girl_av3, R.drawable.girl_av4, R.drawable.girl_av5, R.drawable.girl_av6, R.drawable.girl_av7, R.drawable.girl_av8, R.drawable.girl_av9, R.drawable.girl_av10, R.drawable.girl_av11, R.drawable.boy_av1, R.drawable.boy_av2, R.drawable.boy_av3, R.drawable.boy_av4};
    UserDataModel userDataModel = new UserDataModel();
    int i = 0;

 

    public void BannerLoadads() {
        AdsManager.getInstance().loadBanner(SelectAiGirlActivity.this, getString(R.string.Admob_Banner));
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = (ActivitySelectAiGirlBinding) DataBindingUtil.setContentView(this, R.layout.activity_select_ai_girl);
        BannerLoadads();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        this.userDataModel = (UserDataModel) new Gson().fromJson(Constants.getDataFromSharedPref(this, Constants.USER_DATA),  UserDataModel.class);
        this.binding.rcv.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.binding.rcv.setAdapter(new ImageAdapter(this, this.images, this.userDataModel));
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAiGirlActivity.this.onBackPressed();
            }
        });

        this.binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (SelectAiGirlActivity.this.i == 0) {
                    SelectAiGirlActivity selectGirlImage = SelectAiGirlActivity.this;
                    selectGirlImage.intent = selectGirlImage.getIntent();
                    if (SelectAiGirlActivity.this.intent.getBooleanExtra("pronounsToSelectCharacter", false)) {
                        SelectAiGirlActivity.this.getGuest();
                        return;
                    }
                    SelectAiGirlActivity.this.intent = new Intent(SelectAiGirlActivity.this, TweakPersonalityActivity.class);
                    SelectAiGirlActivity selectGirlImage2 = SelectAiGirlActivity.this;
                    selectGirlImage2.startActivity(selectGirlImage2.intent);
                    SelectAiGirlActivity.this.overridePendingTransition(17432576, 17432577);
                    return;
                }
                SelectAiGirlActivity selectGirlImage3 = SelectAiGirlActivity.this;
                selectGirlImage3.intent = selectGirlImage3.getIntent();
                if (SelectAiGirlActivity.this.intent.getBooleanExtra("pronounsToSelectCharacter", false)) {
                    SelectAiGirlActivity.this.getGuest();
                    return;
                }
                SelectAiGirlActivity.this.intent = new Intent(SelectAiGirlActivity.this, TweakPersonalityActivity.class);
                SelectAiGirlActivity selectGirlImage4 = SelectAiGirlActivity.this;
                selectGirlImage4.startActivity(selectGirlImage4.intent);
                SelectAiGirlActivity.this.overridePendingTransition(17432576, 17432577);
            }
        });
        setView();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(17432576, 17432577);
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
                    ImageAdapter.this.m18xc5bc2298(i, imageHolder, view);
                }
            });
            if (SelectAiGirlActivity.this.row_index == i) {
                imageHolder.border.setBackgroundResource(R.drawable.rent_circle);
                SelectAiGirlActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                return;
            }
            imageHolder.border.setBackgroundResource(R.drawable.rent_circle_no_value);
        }

        

        public  void m18xc5bc2298(int i, ImageHolder imageHolder, View view) {
            SelectAiGirlActivity.this.i = 1;
            SelectAiGirlActivity.this.binding.btnNext.setEnabled(true);
            SelectAiGirlActivity selectGirlImage = SelectAiGirlActivity.this;
            selectGirlImage.intent = selectGirlImage.getIntent();
            boolean booleanExtra = SelectAiGirlActivity.this.intent.getBooleanExtra("pronounsToSelectCharacter", false);
            boolean booleanExtra2 = SelectAiGirlActivity.this.intent.getBooleanExtra("pronounsToSelectCharacter", false);
            if (booleanExtra) {
                SelectAiGirlActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                SelectAiGirlActivity.this.row_index = i;
                notifyDataSetChanged();
                SharedPreferences.Editor edit = SelectAiGirlActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit.putInt("imgName", this.imgList[i]);
                edit.commit();
            } else if (booleanExtra2) {
                SelectAiGirlActivity.this.binding.imgAvatar.setImageResource(this.imgList[i]);
                SelectAiGirlActivity.this.row_index = i;
                notifyDataSetChanged();
                SharedPreferences.Editor edit2 = SelectAiGirlActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).edit();
                edit2.putInt("imgName", this.imgList[i]);
                edit2.commit();
            } else {
                this.userDataModel.setImageId(String.valueOf(imageHolder.getAdapterPosition()));
                Constants.saveDataInSharedPref(this.context, Constants.USER_DATA, new Gson().toJson(this.userDataModel));
                SelectAiGirlActivity.this.row_index = i;
                notifyDataSetChanged();
                SelectAiGirlActivity.this.binding.imgAvatar.setImageResource(Constants.getSelectedImageAvatar(this.userDataModel.getImageId()));
            }
        }

        @Override 
        public int getItemCount() {
            Log.e("==>length", "getItemCount: " + this.imgList.length);
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

    private void setView() {
        this.binding.cameraIc.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                SelectAiGirlActivity.this.bottomSheetDialog = new BottomSheetDialog(SelectAiGirlActivity.this, R.style.AppBottomSheetDialogTheme);
                SelectAiGirlActivity.this.bottomSheetDialog.setContentView(R.layout.dialog_photo);
                SelectAiGirlActivity.this.bottomSheetDialog.setCancelable(false);
                ((TextView) SelectAiGirlActivity.this.bottomSheetDialog.findViewById(R.id.chooseFromGallery)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {
                        SelectAiGirlActivity.this.openGallery();
                        SelectAiGirlActivity.this.bottomSheetDialog.dismiss();
                    }
                });
                ((TextView) SelectAiGirlActivity.this.bottomSheetDialog.findViewById(R.id.TakePic)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {
                        SelectAiGirlActivity.this.startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 123);
                        SelectAiGirlActivity.this.bottomSheetDialog.dismiss();
                    }
                });
                ((TextView) SelectAiGirlActivity.this.bottomSheetDialog.findViewById(R.id.dismiss)).setOnClickListener(new View.OnClickListener() {
                    @Override 
                    public void onClick(View view2) {
                        SelectAiGirlActivity.this.bottomSheetDialog.dismiss();
                        SelectAiGirlActivity.this.onResume();
                    }
                });
                SelectAiGirlActivity.this.bottomSheetDialog.show();
            }
        });
    }

    
    public void openGallery() {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
    }

    
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);

        if (i == 123 && intent != null) {
            final Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
            this.binding.imgAvatar.setImageBitmap(bitmap);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
            this.bottomSheetDialog1 = bottomSheetDialog;
            bottomSheetDialog.setContentView(R.layout.dialog_premium);
            this.bottomSheetDialog1.setCancelable(true);

            ((TextView) this.bottomSheetDialog1.findViewById(R.id.UploadOther)).setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    SelectAiGirlActivity.this.bottomSheetDialog1.dismiss();
                    SelectAiGirlActivity.this.bottomSheetDialog.show();
                }
            });
            ((ImageView) this.bottomSheetDialog1.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    SelectAiGirlActivity.this.bottomSheetDialog1.dismiss();
                }
            });
            this.bottomSheetDialog1.show();
        }
        if (i == 1 && i2 == -1 && intent != null) {

            try {
                ParcelFileDescriptor openFileDescriptor = getContentResolver().openFileDescriptor(intent.getData(), "r");
                bitmap2 = BitmapFactory.decodeFileDescriptor(openFileDescriptor.getFileDescriptor());
                this.binding.imgAvatar.setImageBitmap(bitmap2);
                openFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
            this.bottomSheetDialog1 = bottomSheetDialog2;
            bottomSheetDialog2.setContentView(R.layout.dialog_premium);
            this.bottomSheetDialog1.setCancelable(false);
            ((TextView) this.bottomSheetDialog1.findViewById(R.id.UploadOther)).setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    SelectAiGirlActivity.this.bottomSheetDialog1.dismiss();
                    SelectAiGirlActivity.this.bottomSheetDialog.show();
                }
            });

            ((ImageView) this.bottomSheetDialog1.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    SelectAiGirlActivity.this.bottomSheetDialog1.dismiss();
                    SelectAiGirlActivity.this.binding.imgAvatar.setImageResource(SelectAiGirlActivity.this.getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
                }
            });
            this.bottomSheetDialog1.show();
        }
    }

    public void getGuest() {
        Intent intent = new Intent(this, TweakPersonalityActivity.class);
        this.intent = intent;
        intent.putExtra("selectCharToPersonality", true);
        overridePendingTransition(17432576, 17432577);
        startActivity(this.intent);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
    }

    
    @Override 
    public void onResume() {
        super.onResume();
        if (resume) {
            this.binding.imgAvatar.setImageResource(getSharedPreferences("YOUR_PREF_NAME", 0).getInt("imgName", R.drawable.girl_av2));
            resume = false;
        }
    }
}
