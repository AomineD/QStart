package com.wineberryhalley.qstart.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.intro.IntroQStart;

public class AboutQStartText extends AppCompatTextView {

    public AboutQStartText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AboutQStartText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText(R.string.about_qs);
        setTypeface(getTypeface(), Typeface.BOLD_ITALIC);
        setTextColor(getContext().getResources().getColor(R.color.dark));
        setPadding(10, 10, 10, 10);
    }

    public void click(Activity activity){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IntroQStart.startIntro(activity);
            }
        });
    }

}
