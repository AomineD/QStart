package com.wineberryhalley.qstart.intro;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BaseFragmentPager;
import com.wineberryhalley.qstart.base.BasePagerAdapter;
import com.wineberryhalley.qstart.utils.Timer;

import java.util.ArrayList;
import java.util.TimerTask;

public class IntroPagerAdapter extends BasePagerAdapter<IntroObj, Object>{

    public IntroPagerAdapter(Activity activity, ArrayList<IntroObj> a) {
config(activity, a, null);
    }

    private int positionActual =0;

    public int getPositionActual(){
        return positionActual;
    }


    @Override
    public int resLayout() {
        return R.layout.fragment_intro;
    }

    @Override
    public void binded(View base, IntroObj model, Object mod, int position) {
     LottieAnimationView lt = base.findViewById(R.id.lottie_animation);
      TextView txt = base.findViewById(R.id.text_intro);
        Log.e("MAIN", "binded: "+base.getWidth() );
        if(!model.getAssetName().isEmpty() && lt != null){
            lt.setAnimation(model.getAssetName());
            lt.playAnimation();
        }
        if(!model.getTexto().isEmpty() && txt != null){
            txt.setText(model.getTexto());
        }

    }

}
