package com.wineberryhalley.qstart.ui.views;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wineberryhalley.qstart.R;

public class QStartLoginButton extends RelativeLayout {
    public QStartLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public QStartLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private OnClickListener onClickListener;

    public void setClickListener(OnClickListener a){
        this.onClickListener = a;
    }

    private int typeText;
    private TextView t;
    private ImageView im;
    private void init(AttributeSet attrs) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.QStartLoginButton, 0, 0);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.bt_lg_sqe,  (ViewGroup) getRootView(), false);
this.addView(v);
        try {
            //get the text and colors specified using the names in attrs.xml
            typeText = a.getInt(R.styleable.QStartLoginButton_typetext, 0);
        } finally {
            //  Log.e("MAIN", "init: no" );
            a.recycle();
        }
        t = v.findViewById(R.id.text_qstart);
        im = v.findViewById(R.id.icqstart);
        switch (typeText){
            case 0:
                t.setText(R.string.login_b_1);
                break;
            case 1:
                t.setText(R.string.login_b_2);
                break;

            case 2:
                t.setText(R.string.login_b_3);
                break;
        }


        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setRepeatCount(4);
                    t.setVisibility(GONE);
                    rotate.setInterpolator(new LinearInterpolator());
rotate.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
runLogin(v);
t.setVisibility(VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
                    im.startAnimation(rotate);
                }
            }
        });

    }

    private void runLogin(View v) {
    if(onClickListener != null){
        onClickListener.onClick(v);
    }
    }
}
