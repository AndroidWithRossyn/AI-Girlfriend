package com.htcodes.ai.girlfriend.virtual.gf.dating.lover.chat.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;



public class KeyboardHeightProvider extends PopupWindow {

    
    public interface KeyboardHeightListener {
        void onKeyboardHeightChanged(int i, boolean z, boolean z2);
    }

    public KeyboardHeightProvider(final Context context, final WindowManager windowManager, final View view, final KeyboardHeightListener keyboardHeightListener) {
        super(context);
        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public final void onGlobalLayout() {
                KeyboardHeightProvider.lambda$new$0(windowManager, linearLayout, context, keyboardHeightListener);
            }
        });
        setContentView(linearLayout);
        setSoftInputMode(21);
        setInputMethodMode(1);
        setWidth(0);
        setHeight(-1);
        setBackgroundDrawable(new ColorDrawable(0));
        view.post(new Runnable() {
            @Override 
            public final void run() {
                KeyboardHeightProvider.this.m33x892c6f0(view);
            }
        });
    }

    
    public static  void lambda$new$0(WindowManager windowManager, LinearLayout linearLayout, Context context, KeyboardHeightListener keyboardHeightListener) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        Rect rect = new Rect();
        linearLayout.getWindowVisibleDisplayFrame(rect);
        int i = displayMetrics.heightPixels - (rect.bottom - rect.top);
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            i -= context.getResources().getDimensionPixelSize(identifier);
        }
        if (i < 100) {
            i = 0;
        }
        boolean z = displayMetrics.widthPixels > displayMetrics.heightPixels;
        boolean z2 = i > 0;
        if (keyboardHeightListener != null) {
            keyboardHeightListener.onKeyboardHeightChanged(i, z2, z);
        }
    }

    

    public  void m33x892c6f0(View view) {
        showAtLocation(view, 0, 0, 0);
    }
}
