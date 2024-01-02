package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;


public class Typewriter extends androidx.appcompat.widget.AppCompatTextView {
    private Runnable characterAdder;
    private long mDelay;
    private Handler mHandler;
    private int mIndex;
    private CharSequence mText;
    static  int access$008(Typewriter typewriter) {
        int i = typewriter.mIndex;
        typewriter.mIndex = i + 1;
        return i;
    }

    public Typewriter(Context context) {
        super(context);
        this.mDelay = 500L;
        this.mHandler = new Handler();
        this.characterAdder = new Runnable() {
            @Override 
            public void run() {
                Typewriter typewriter = Typewriter.this;
                typewriter.setText(typewriter.mText.subSequence(0, Typewriter.access$008(Typewriter.this)));
                if (Typewriter.this.mIndex <= Typewriter.this.mText.length()) {
                    Typewriter.this.mHandler.postDelayed(Typewriter.this.characterAdder, Typewriter.this.mDelay);
                }
            }
        };
    }

    public Typewriter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDelay = 500L;
        this.mHandler = new Handler();
        this.characterAdder = new Runnable() {
            @Override 
            public void run() {
                Typewriter typewriter = Typewriter.this;
                typewriter.setText(typewriter.mText.subSequence(0, Typewriter.access$008(Typewriter.this)));
                if (Typewriter.this.mIndex <= Typewriter.this.mText.length()) {
                    Typewriter.this.mHandler.postDelayed(Typewriter.this.characterAdder, Typewriter.this.mDelay);
                }
            }
        };
    }

    public void animateText(CharSequence charSequence) {
        this.mText = charSequence;
        this.mIndex = 0;
        setText("");
        this.mHandler.removeCallbacks(this.characterAdder);
        this.mHandler.postDelayed(this.characterAdder, this.mDelay);
    }

    public void setCharacterDelay(long j) {
        this.mDelay = j;
    }
}
