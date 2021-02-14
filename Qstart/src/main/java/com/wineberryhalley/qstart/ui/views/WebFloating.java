package com.wineberryhalley.qstart.ui.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BaseDialog;
import com.wineberryhalley.qstart.utils.Timer;

public class WebFloating extends BaseDialog {

    private Activity activity;
    public static void showWeb(Context a){

     WebFloating w = new WebFloating(a);
     w.activity = (Activity) a;
     w.show();
    }
    protected WebFloating(@NonNull Context context) {
        super(context);
    }

    protected WebFloating(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WebFloating(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public int reslayout() {
        return R.layout.we_b_d;
    }

    @Override
    public void onStart() {

        setTouchableBase(false);
        WebView w = findViewById(R.id.webinside);
        RoundButton r = findViewById(R.id.close_btn);

        LottieAnimationView lt = findViewById(R.id.lt);

        w.getSettings().setJavaScriptEnabled(true);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        w.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Timer.init(new Timer.TimerListener() {
                    @Override
                    public void onSuccess() {
   hide(lt);
                    }
                }, 1400);

            }
        });
        w.loadUrl("file:///android_asset/export.html");

    }

    private void hide(LottieAnimationView lt){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lt.setVisibility(View.GONE);
                }
            });


    }
}
