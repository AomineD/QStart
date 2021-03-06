package com.wineberryhalley.qstart.intro;

import androidx.fragment.app.Fragment;

import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.QFragment;

public class IntroFragment extends QFragment {

    public int pos;
    public int posNext;
    public static IntroFragment newInstance(int pos, String assetAnim, String text){
        IntroFragment fragment = new IntroFragment();
      //  fragment.setPosRevealInDevice(posv,2);
        fragment.pos = pos;
        fragment.assetAnimName = assetAnim;
        fragment.textOf = text;
        return fragment;
    }

    public String assetAnimName = "";
    private String textOf = "";

    @Override
    public int layoutId() {
        return R.layout.fragment_intro_qs;
    }

    @Override
    public Fragment lastFragment() {
        return null;
    }

    private LottieAnimationView lt;
    private TextView txt;



    @Override
    public void Awake() {
        lt = find(R.id.lottie_animation);
        txt = find(R.id.text_intro);

if(!assetAnimName.isEmpty() && lt != null){
    lt.setAnimation(assetAnimName);
    lt.playAnimation();
}
if(!textOf.isEmpty() && txt != null){
    txt.setText(textOf);
}

    }
}