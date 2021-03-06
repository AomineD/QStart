package com.wineberryhalley.qstart.base;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.wineberryhalley.qstart.R;

public abstract class QDialog extends AlertDialog {
    protected QDialog(@NonNull Context context) {
        super(context);
    }

    protected QDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected QDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public abstract int reslayout();
    public abstract void onStart();
    private boolean isTouchOutside = true;
    protected void setTouchableBase(boolean value){
        isTouchOutside = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(reslayout() == 0){
            dismiss();
        }
        setContentView(reslayout());

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setBackgroundDrawable(ActivityCompat.getDrawable(getContext(), android.R.color.transparent));
        setCancelable(isTouchOutside);
        setCanceledOnTouchOutside(isTouchOutside);
        onStart();
    }
}
