package com.wineberryhalley.qstart.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.wineberryhalley.qstart.R;

import java.util.Timer;
import java.util.TimerTask;

public class RoundButton extends RelativeLayout {
    private int colorBackground, textColor;
    private String text;
    private float buttonRadius, textPadding, buttonElevation;
    private boolean isBold = false;
    private int textSize;

    private TextView texto;
    private CardView cardView;

    public void setText(String text){
        if(texto != null){
            texto.setText(text);
    //        Log.e(TAG, "setText: "+text+" "+texto.getText().toString() );
        }
    }

    public void setText(int resId)
    {
        if(texto != null){
            texto.setText(resId);
        }
    }

    public RoundButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.RoundButton, 0, 0);

        try {
            //get the text and colors specified using the names in attrs.xml
            colorBackground = a.getColor(R.styleable.RoundButton_backgroundColor, getContext().getResources().getColor(R.color.dark));
            textColor = a.getColor(R.styleable.RoundButton_textColor, getContext().getResources().getColor(R.color.white));//0 is default
            isBold = a.getBoolean(R.styleable.RoundButton_textBold, false);
            textSize = a.getInteger(R.styleable.RoundButton_textSize, 0);
            buttonRadius = a.getDimension(R.styleable.RoundButton_buttonRadius, 0);
            textPadding = a.getDimension(R.styleable.RoundButton_textPadding, 0);
            buttonElevation = a.getDimension(R.styleable.RoundButton_buttonElevation,0);
            text = a.getString(R.styleable.RoundButton_text);
            if(text == null || text.isEmpty()){
                text = "Button";
            }
        } finally {
          //  Log.e("MAIN", "init: no" );
            a.recycle();
        }
        configView();
    }

    private void configView() {
        View main = LayoutInflater.from(getContext()).inflate(R.layout.r_o_bt_, (ViewGroup) getRootView(), false);

        this.addView(main);



/*
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {

     RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) main.getLayoutParams();
        //  Log.e("MAIN", "configView: "+(main.getLayoutParams() != null) );
        params.width = ((View) main.getParent()).getWidth();
        params.height = ((View) main.getParent()).getHeight();
     //   Log.e("MAIN", "run: "+params.width +" "+params.height);
                        if(params.width == 0 || params.height == 0){
                            runSized(main, 1000);
                        }else {
                            main.setLayoutParams(params);
                        }


                    } catch (Exception e) {
                        Log.e("MAIN", "ROUNDED BUTTON: no relative ");
runSized(main, 1000);
                    }
                }
            }, 800);*/






        /** === CONFIG DATA **/

       // Log.e(TAG, "padding: " + textPadding);
       // Log.e(TAG, "elevation: " + buttonElevation);
        texto = main.findViewById(R.id.text_bt);
        cardView = main.findViewById(R.id.btn_id);

        cardView.setCardBackgroundColor(colorBackground);
        cardView.setCardElevation(buttonElevation);
        cardView.setRadius(buttonRadius);

        texto.setText(text);
      //  Log.e(TAG, "configView: "+textSize );
             texto.setTextSize(textSize);
   // setTextSize(textSize);
   //     int paddingFinal = (int) textPadding;
    //   texto.setPadding(paddingFinal, paddingFinal, paddingFinal, paddingFinal);
        texto.setTextColor(textColor);

        if(isBold){
            texto.setTypeface(texto.getTypeface(), Typeface.BOLD);
        }else{
            texto.setTypeface(texto.getTypeface(), Typeface.NORMAL);
        }

    }

    private String TAG ="MAIN";

private void runSized(View main, long timing){
    if(timing == 0){
        timing = 250;
    }
    new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
            try {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) main.getLayoutParams();
                        //  Log.e("MAIN", "configView: "+(main.getLayoutParams() != null) );
                        params.width = ((View) main.getParent()).getWidth();
                        params.height = ((View) main.getParent()).getHeight();
                        // Log.e("MAIN", "run: "+params.width );
                        main.setLayoutParams(params);
            } catch (Exception e) {
                Log.e("MAIN", "ROUNDED BUTTON: no relative 2 "+e.getMessage());

            }
        }
    }, timing);
}

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }

}
