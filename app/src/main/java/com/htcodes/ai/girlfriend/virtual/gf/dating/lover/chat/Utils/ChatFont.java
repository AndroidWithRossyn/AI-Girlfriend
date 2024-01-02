package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class ChatFont extends androidx.appcompat.widget.AppCompatTextView {
    public ChatFont(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public ChatFont(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ChatFont(Context context) {
        super(context);
        init();
    }

    public void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Montserrat.otf"), Typeface.BOLD);
    }
}
