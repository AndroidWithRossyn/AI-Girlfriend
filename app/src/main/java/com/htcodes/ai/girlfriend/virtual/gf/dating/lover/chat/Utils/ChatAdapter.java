package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Model.NewChatModel;
import com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    int aiGirl;
    BottomSheetDialog bottomSheet;
    BottomSheetDialog bottomSheet1;
    Context context;
    int dp;
    DisplayMetrics metrics;
    ArrayList<NewChatModel> msg_list;
    int screenheight;
    int screenwidth;
    boolean giftLike = true;
    boolean giftDislike = true;
    boolean isOtherClicked = true;

    @Override 
    public long getItemId(int i) {
        return i;
    }

    @Override 
    public int getItemViewType(int i) {
        return i;
    }

    public ChatAdapter(Context context, ArrayList<NewChatModel> arrayList, int i) {
        this.context = context;
        this.msg_list = arrayList;
        this.dp = i;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.metrics = displayMetrics;
        this.screenheight = displayMetrics.heightPixels;
        this.screenwidth = this.metrics.widthPixels;
    }

    
    @Override 
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_message, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        this.aiGirl = this.context.getSharedPreferences("MyPrefs", 0).getInt("gfAvatar", R.drawable.char_1);
        Log.d("data==>", this.msg_list.get(i).getQuestionText());
        if (this.msg_list.get(i).getSenderType().equals("ai")) {
            if (i == this.msg_list.size() - 1) {
                myViewHolder.mLoading.setVisibility(View.VISIBLE);
                myViewHolder.mAnsView.setVisibility(View.GONE);
                if (this.msg_list.get(i).isFromHistory().equals("true")) {
                    myViewHolder.mLoading.setVisibility(View.GONE);
                    myViewHolder.mAnsView.setVisibility(View.VISIBLE);
                    myViewHolder.contentLikeReport.setVisibility(View.VISIBLE);
                    myViewHolder.tv_msg1.setCharacterDelay(25L);
                    myViewHolder.tv_msg1.animateText(this.msg_list.get(i).getAnsText());
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override 
                        public void run() {
                            myViewHolder.mLoading.setVisibility(View.GONE);
                            myViewHolder.mAnsView.setVisibility(View.VISIBLE);
                            myViewHolder.contentLikeReport.setVisibility(View.VISIBLE);
                            myViewHolder.tv_msg1.setCharacterDelay(25L);
                            myViewHolder.tv_msg1.animateText(ChatAdapter.this.msg_list.get(i).getAnsText());
                        }
                    }, 1500L);
                }
            } else {
                myViewHolder.contentLikeReport.setVisibility(View.GONE);
                myViewHolder.tv_msg1.setText(this.msg_list.get(i).getAnsText());
            }
            myViewHolder.layout_left.setVisibility(View.VISIBLE);
            myViewHolder.layout_right.setVisibility(View.GONE);
        } else {
            myViewHolder.layout_right.setVisibility(View.VISIBLE);
            myViewHolder.layout_left.setVisibility(View.GONE);
            if (this.msg_list.get(i).getQuestionType().equals("image")) {
                myViewHolder.tv_msg2.setVisibility(View.GONE);
                myViewHolder.giftSticker.setVisibility(View.VISIBLE);
                myViewHolder.giftSticker.setImageResource(this.msg_list.get(i).getQuestionImage());
            } else {
                myViewHolder.tv_msg2.setText(this.msg_list.get(i).getQuestionText());
            }
        }
        myViewHolder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (ChatAdapter.this.giftLike) {
                    myViewHolder.mDislike.setVisibility(View.GONE);
                    ChatAdapter.this.giftLike = false;
                    return;
                }
                myViewHolder.mDislike.setVisibility(View.VISIBLE);
                ChatAdapter.this.giftLike = true;
            }
        });
        myViewHolder.mDislike.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (ChatAdapter.this.giftDislike) {
                    myViewHolder.mLike.setVisibility(View.GONE);
                    ChatAdapter.this.giftDislike = false;
                    return;
                }
                myViewHolder.mLike.setVisibility(View.VISIBLE);
                ChatAdapter.this.giftDislike = true;
            }
        });
        myViewHolder.mReport.setOnClickListener(new AnonymousClass4());
    }

    

    
    public class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override 
        public void onClick(View view) {
            ChatAdapter.this.bottomSheet = new BottomSheetDialog(ChatAdapter.this.context, R.style.AppBottomSheetDialogTheme);
            View inflate = View.inflate(ChatAdapter.this.context, R.layout.dailog_report, null);
            ChatAdapter.this.bottomSheet.setContentView(inflate);
            BottomSheetBehavior from = BottomSheetBehavior.from((View) inflate.getParent());
            from.setMaxHeight(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
            from.setPeekHeight(1000);
            final TextView textView = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.offensive);
            final TextView textView2 = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.gender);
            final TextView textView3 = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.nameMistake);
            final TextView textView4 = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.memory);
            final TextView textView5 = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.repeatedQuestion);
            final TextView textView6 = (TextView) ChatAdapter.this.bottomSheet.findViewById(R.id.other);
            final AppCompatButton appCompatButton = (AppCompatButton) ChatAdapter.this.bottomSheet.findViewById(R.id.btnContinue);
            appCompatButton.setEnabled(false);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView2.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView3.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView4.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView5.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView6.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setText("Report");
                    ChatAdapter.this.isOtherClicked = true;
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView2.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView3.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView4.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView5.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView6.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setText("Report");
                    ChatAdapter.this.isOtherClicked = true;
                }
            });
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView3.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView2.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView4.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView5.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView6.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setText("Report");
                    ChatAdapter.this.isOtherClicked = true;
                }
            });
            textView4.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView4.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView2.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView3.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView5.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView6.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setText("Report");
                    ChatAdapter.this.isOtherClicked = true;
                }
            });
            textView5.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView5.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView2.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView3.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView4.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView6.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setText("Report");
                    ChatAdapter.this.isOtherClicked = true;
                }
            });
            textView6.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    textView6.setBackgroundResource(R.drawable.et_bg_filled_white_rounded);
                    textView6.setTextColor(ChatAdapter.this.context.getColor(R.color.black));
                    textView5.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView5.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView2.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView2.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView3.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView3.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    textView4.setBackgroundResource(R.drawable.rent_bg_border_rounded);
                    textView4.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    appCompatButton.setEnabled(true);
                    appCompatButton.setText("Continue");
                    appCompatButton.setTextColor(ChatAdapter.this.context.getColor(R.color.white));
                    ChatAdapter.this.isOtherClicked = false;
                }
            });
            ChatAdapter.this.bottomSheet1 = new BottomSheetDialog(ChatAdapter.this.context, R.style.DialogStyle);
            appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    if (ChatAdapter.this.isOtherClicked) {
                        if (ChatAdapter.this.bottomSheet.isShowing()) {
                            ChatAdapter.this.bottomSheet.dismiss();
                        }
                        Toasty.success(ChatAdapter.this.context, "Message has been reported").show();
                        return;
                    }
                    ChatAdapter.this.bottomSheet.dismiss();
                    ChatAdapter.this.bottomSheet1.setContentView(View.inflate(ChatAdapter.this.context, R.layout.report_other, null));
                    ChatAdapter.this.bottomSheet1.setCancelable(false);
                    final EditText editText = (EditText) ChatAdapter.this.bottomSheet1.findViewById(R.id.et_name);
                    ((AppCompatImageView) ChatAdapter.this.bottomSheet1.findViewById(R.id.mClose)).setOnClickListener(new View.OnClickListener() {
                        @Override 
                        public void onClick(View view3) {
                            if (ChatAdapter.this.bottomSheet1.isShowing()) {
                                ChatAdapter.this.bottomSheet1.dismiss();
                            }
                        }
                    });
                    ((AppCompatButton) ChatAdapter.this.bottomSheet1.findViewById(R.id.btnContinue)).setOnClickListener(new View.OnClickListener() {
                        @Override 
                        public void onClick(View view3) {
                            if (editText.getText().toString().trim().length() == 0) {
                                Toasty.error(ChatAdapter.this.context, "Please submit your issue").show();
                                return;
                            }
                            Toasty.success(ChatAdapter.this.context, "Message has been reported").show();
                            ChatAdapter.this.bottomSheet1.dismiss();
                        }
                    });
                    ChatAdapter.this.bottomSheet1.show();
                }
            });
            ((AppCompatImageView) ChatAdapter.this.bottomSheet.findViewById(R.id.mClose)).setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view2) {
                    if (ChatAdapter.this.bottomSheet.isShowing()) {
                        ChatAdapter.this.bottomSheet.dismiss();
                    }
                }
            });
            ChatAdapter.this.bottomSheet.show();
        }
    }

    @Override 
    public int getItemCount() {
        return this.msg_list.size();
    }

    
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat contentLikeReport;
        AppCompatImageView giftSticker;
        LinearLayoutCompat layout_left;
        LinearLayoutCompat layout_right;
        RelativeLayout mAnsView;
        AppCompatImageView mDislike;
        AppCompatImageView mLike;
        RelativeLayout mLoading;
        AppCompatImageView mReport;
        TextView name;
        RelativeLayout relative_main;
        TextView textUser;
        Typewriter tv_msg1;
        TextView tv_msg2;

        public MyViewHolder(View view) {
            super(view);
            this.giftSticker = (AppCompatImageView) view.findViewById(R.id.giftSticker);
            this.mAnsView = (RelativeLayout) view.findViewById(R.id.mAnsView);
            this.mLoading = (RelativeLayout) view.findViewById(R.id.mLoading);
            this.name = (TextView) view.findViewById(R.id.name);
            this.relative_main = (RelativeLayout) view.findViewById(R.id.relative_main);
            this.tv_msg1 = (Typewriter) view.findViewById(R.id.tv_msg1);
            this.tv_msg2 = (TextView) view.findViewById(R.id.tv_msg2);
            this.layout_left = (LinearLayoutCompat) view.findViewById(R.id.layout_left);
            this.layout_right = (LinearLayoutCompat) view.findViewById(R.id.layout_right);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.relative_main.getLayoutParams();
            layoutParams.width = (ChatAdapter.this.screenwidth * 1080) / 1080;
            layoutParams.height = -2;
            this.relative_main.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.layout_left.getLayoutParams();
            layoutParams2.width = (ChatAdapter.this.screenwidth * 1000) / 1080;
            layoutParams2.height = -2;
            this.layout_left.setLayoutParams(layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.layout_right.getLayoutParams();
            layoutParams3.width = (ChatAdapter.this.screenwidth * 1000) / 1080;
            layoutParams3.height = -2;
            this.layout_right.setLayoutParams(layoutParams3);
            this.contentLikeReport = (LinearLayoutCompat) view.findViewById(R.id.contentLikeReport);
            this.mLike = (AppCompatImageView) view.findViewById(R.id.mLike);
            this.mDislike = (AppCompatImageView) view.findViewById(R.id.mDislike);
            this.mReport = (AppCompatImageView) view.findViewById(R.id.mReport);
        }
    }
}
