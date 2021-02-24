package com.wineberryhalley.qstart.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.rd.PageIndicatorView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BaseActivityNoStatus;
import com.wineberryhalley.qstart.ui.views.RoundButton;
import com.wineberryhalley.qstart.utils.LoginQStartBottom;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class IntroQStart extends BaseActivityNoStatus {


    public static void startIntro(Context c){
        c.startActivity(new Intent(c, IntroQStart.class));
        Animatoo.animateShrink(c);
    }


    private static boolean about = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        about = false;
    }

    /**
     * Determine if the intro was already viewed,
     * if the user already watched, return true
     *
     */
    public static boolean wasViewed(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("qkt",MODE_PRIVATE);
      //  Log.e("MAIN", "wasViewed: "+!preferences.getBoolean(key_s, false) );
       return preferences.getBoolean(key_s, false);
    }

    private SharedPreferences preferences;
private static String key_s = "MALWa";
    private IntroPagerAdapter introPagerAdapter;
    private RoundButton nextText;
    private ArrayList<IntroObj> introFragments = new ArrayList<>();
    PageIndicatorView pageIndicatorView;
    private ViewPager viewPager;
    @Override
    public void Main() {
        preferences = getSharedPreferences("qkt",MODE_PRIVATE);
introFragments = configIntros();
nextText = findViewById(R.id.next_btn);
pageIndicatorView = findViewById(R.id.indicator);
        pageIndicatorView.setCount(introFragments.size()); // specify total count of indicators
        pageIndicatorView.setSelection(0);

viewPager = findViewById(R.id.viewpag_intro);
pageIndicatorView.setViewPager(viewPager);
introPagerAdapter = new IntroPagerAdapter(this, introFragments);
viewPager.setAdapter(introPagerAdapter);
viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      //  Log.e("MAIN", "onPageScrolled: ");
        if((position+1) == introFragments.size()){
            nextText.setText(getString(R.string.go_next));
            canContinue = true;
        }else
        if(position < introFragments.size()){
            canContinue = false;
            nextText.setText(R.string.next);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});

findViewById(R.id.next_btn).setOnClickListener(clicking(TypeClickBtn.NEXT));

findViewById(R.id.previous_btn).setOnClickListener(clicking(TypeClickBtn.PREVIOUS));

    }

    private View.OnClickListener clicking(TypeClickBtn typeClickBtn){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          if(typeClickBtn == TypeClickBtn.NEXT){
              int vpost = viewPager.getCurrentItem();
              vpost++;
              if(vpost < introFragments.size()){
          viewPager.setCurrentItem(vpost, true);
              }
              if(canContinue){
                  saveProgress();
                  finish();
              }
              if((vpost+1) == introFragments.size()){
                  nextText.setText(getString(R.string.go_next));
                  canContinue = true;
              }



          }else{
              int vpost = viewPager.getCurrentItem();
              vpost--;
              canContinue = false;
              if(vpost >= 0){
                  viewPager.setCurrentItem(vpost, true);
              }
              if(vpost < introFragments.size()){
                  nextText.setText(R.string.next);
              }
          }
       //   pageIndicatorView.setSelection(introPagerAdapter.getPositionActual());
            }
        };
    }

    private void saveProgress() {
        preferences.edit().putBoolean(key_s, true).apply();
        //LoginQStartBottom.showLoginPopUp(this);
    }

    private boolean canContinue = false;
    private ArrayList<IntroObj> configIntros() {
        ArrayList<IntroObj> arr = new ArrayList<>();
        IntroObj i1 = new IntroObj("social_login.json",getString(R.string.intro_1));//IntroFragment.newInstance(0,);
        arr.add(i1);
        IntroObj i2 = new IntroObj( "security_anim.json",getString(R.string.intro_2));
        arr.add(i2);
        IntroObj i3 = new IntroObj("time_spent.json",getString(R.string.intro_3));
        arr.add(i3);
        IntroObj i4 = new IntroObj("rocket_ai.json",getString(R.string.intro_4));
        arr.add(i4);
        return arr;
    }

    @Override
    public void statusChanged(int pixelesSizeBar) {

    }

    @Override
    public int resLayout() {
        return R.layout.activity_intro_q_start;
    }

    @Override
    public ArrayList<String> keysNotification() {
        return null;
    }

    @Override
    public void onReceiveValues(ArrayList<String> values) {

    }

}