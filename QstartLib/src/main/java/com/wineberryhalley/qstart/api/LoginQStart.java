package com.wineberryhalley.qstart.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BottomBaseShet;
import com.wineberryhalley.qstart.base.PicassoUtils;
import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.ui.adapter.CountryAdapter;
import com.wineberryhalley.qstart.ui.views.RoundButton;
import com.wineberryhalley.qstart.ui.views.WebFloating;
import com.wineberryhalley.qstart.utils.Country;
import com.wineberryhalley.qstart.utils.LoginInterface;
import com.wineberryhalley.qstart.utils.SelectedCountryListener;
import com.wineberryhalley.qstart.utils.Timer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.wineberryhalley.qstart.api.Qa.am_mwql;
import static com.wineberryhalley.qstart.api.Qa.ke_res;
import static com.wineberryhalley.qstart.api.Qa.saveUser;
import static com.wineberryhalley.qstart.utils.TextU.makeLinkSpan;
import static com.wineberryhalley.qstart.utils.TextU.makeLinksFocusable;

public class LoginQStart extends AppCompatActivity implements Qa.UserListener, SelectedCountryListener {


    private static boolean requireCountry = false;
    private static boolean requirePhoto = false;
    private static boolean requireGender = false;


    private static int icon_res = 0;

public static final int loging_permission = Qa.a_p;

public static void requestManageNow(Activity a){
    Qa.requestNowManage(a);
}

public static void logOut(){
    Qa.deleteData();
}

    public static void logOut(Activity activity){
        Qa.deleteData();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
    }

    public static void requireCountry(int appIconRes){
        requireCountry = true;
        icon_res = appIconRes;
    }

    public static void requirePhoto(int appIconRes){
        requirePhoto = true;
        icon_res = appIconRes;
    }

    public static void requireGender(int appIconRes){
        requireGender = true;
        icon_res = appIconRes;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideUp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logingListener = null;
        if(!writePermission) {
            requireCountry = false;
            requirePhoto = false;
            requireGender = false;
            icon_res = 0;
        }
        writePermission = false;
    }

    private static LoginInterface logingListener;
    private static boolean writePermission = false;
    private static String abb = "jeje";
    public static void init(Activity context, String key, String abu) {
        goodInit = true;
        SharedPreferences sharedPreferences = context.getSharedPreferences("qkt", MODE_PRIVATE);
        sharedPreferences.edit().putString(am_mwql, key).apply();
        abb = abu;
        if (context instanceof LoginInterface) {
            logingListener = (LoginInterface) context;
        }
        context.startActivity(new Intent(context, LoginQStart.class));
        Animatoo.animateSlideDown(context);
    }

    public static void initWritePermission(Activity context, String key) {
        goodInit = true;
        SharedPreferences sharedPreferences = context.getSharedPreferences("qkt", MODE_PRIVATE);
        sharedPreferences.edit().putString(am_mwql, key).apply();
        if (context instanceof LoginInterface) {
            logingListener = (LoginInterface) context;
        }
        writePermission = true;
        context.startActivity(new Intent(context, LoginQStart.class));
        Animatoo.animateSlideDown(context);
    }

    public static boolean isLogged(Activity activity){
        Qa.checkD(activity);
     return Qa.isLogged(activity);
    }

    public static User getUser(){
            return Qa.userByGSON();
    }

    private void assignViews() {
        roundButton = findViewById(R.id.login_bt);
        loading_rel = findViewById(R.id.loading_rel);

        text_loading = findViewById(R.id.text_loading);

        relativeLayouts[0] = findViewById(R.id.general_lay);
        relativeLayouts[1] = findViewById(R.id.permission_rel);
        relativeLayouts[2] = findViewById(R.id.explain_rel);

        loading = findViewById(R.id.loading_);

        change_country = findViewById(R.id.select_country);
        accept_permiss = findViewById(R.id.permiss_bt);
        appCompatSpinner = findViewById(R.id.gender_spinner);
        why_permiss = findViewById(R.id.why_permiss_bt);
        backto = findViewById(R.id.back_to_permiss);

    }
    private TextView text_loading;
    private View loading_rel;
    private AppCompatSpinner appCompatSpinner;
    private RoundButton roundButton, accept_permiss, why_permiss, backto, change_country;

    private RelativeLayout[] relativeLayouts = new RelativeLayout[3];
    private LottieAnimationView loading;

    private static boolean goodInit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_q_start);
        if(!goodInit){
            finish();
        }
        goodInit = false;

       assignViews();
       setConfig();

       configureOptionals();


    }

    private void setConfig() {
        Qa.checkD(this);
        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.e(TAG, "onClick: a "+Qa.canReadAndWrite(LoginQStart.this) );
                if(Qa.canReadAndWrite(LoginQStart.this)){
                    showLoading();
Qa.checkFile(LoginQStart.this, abb,LoginQStart.this);
      }else{
moveToNext();
      }
            }
        });

        why_permiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNext();
            }
        });

        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPrev();
            }
        });

        accept_permiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qa.requestPermiss(LoginQStart.this);
            }
        });

        change_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(getString(R.string.loading_countries));
                Ecapdamond ecapdamond = Ecapdamond.ecapdamond;
                ecapdamond.getCountries(new Ecapdamond.CountryListener() {
                    @Override
                    public void onLoad(ArrayList<Country> countries) {
                        hideLoading();
                        CountrySelection c = new CountrySelection(LoginQStart.this, countries, LoginQStart.this);
                        c.show(getSupportFragmentManager(), "ctoq");
                    }

                    @Override
                    public void onError(String erno) {
showError(getString(R.string.error_general), false);
                    }
                });
            }
        });
    findViewById(R.id.back_ic).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
            errorLogging("Canceled");
        }
    });

    findViewById(R.id.reco_bt).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            animRecovery();
        }
    });

    if(writePermission)
        moveToNext();
    }


    public void showLoading(String customText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.VISIBLE);
                loading.setAnimation(getString(R.string.loading_anim_q));
                loading.playAnimation();
                text_loading.setText(customText);
            }
        });

    }

    private void showSuccessTiming(String customText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.VISIBLE);
                loading.setAnimation(getString(R.string.success_anim));
                loading.setRepeatCount(1);
                loading.playAnimation();
                text_loading.setText(customText);
                Timer.init(new Timer.TimerListener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                        goRecoveryPut(false);
                            }
                        });
                    }
                }, 2300);
            }
        });
    }

    private void showSuccessTimingSignUp(String customText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.VISIBLE);
                loading.setAnimation(getString(R.string.success_anim));
                loading.setRepeatCount(1);
                loading.playAnimation();
                text_loading.setText(customText);
                Timer.init(new Timer.TimerListener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                                goRecoveryPut(true);
                            }
                        });
                    }
                }, 2300);
            }
        });
    }

    private void showSuccessTiming(String customText, boolean a, boolean signup){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.VISIBLE);
                loading.setAnimation(getString(R.string.success_anim));
                loading.setRepeatCount(1);
                loading.playAnimation();
                text_loading.setText(customText);
                Timer.init(new Timer.TimerListener() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                                if(a) {
                                    goRecoveryPut(false);
                                }else{
                                    successLogin(signup);
                                    onBackPressed();
                                }
                            }
                        });
                    }
                }, 2300);
            }
        });
    }

    private void goRecoveryPut(boolean signup) {
View recoverlay = findViewById(R.id.recover_pass_put);
        YoYo.with(Techniques.SlideInUp).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {

                YoYo.with(Techniques.FadeOut).duration(500).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        findViewById(R.id.signup_rel).setVisibility(View.GONE);
                    }
                }).playOn(findViewById(R.id.signup_rel));

                recoverlay.setVisibility(View.VISIBLE);
            }
        }).duration(800).playOn(recoverlay);

        AutoCompleteTextView tpassword = findViewById(R.id.passrecoverput);

        RoundButton r = findViewById(R.id.save_pass_btn);
        RoundButton sk = findViewById(R.id.skip_btn);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec = tpassword.getText().toString();

                if(rec.length() < 8){
                    showRed(getString(R.string.password_length), "OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else {

                    Ecapdamond p = Ecapdamond.ecapdamond;
showLoading(getString(R.string.secury));
                    p.putRecoverPass(rec, new Ecapdamond.StatusListener() {
                        @Override
                        public void onLoad(User user) {
                            showSuccessTiming(getString(R.string.succ), false,signup);
                        }

                        @Override
                        public void onError(String erno) {
showError(getString(R.string.error_general), false);
                        }
                    });
                }
            }
        });

        sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                successLogin(signup);
                onBackPressed();
            }
        });

    }

    private void animRecovery(){
        YoYo.with(Techniques.FadeOut).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                relativeLayouts[0].setVisibility(View.GONE);
                goRecovery();
            }
        }).duration(800).playOn(relativeLayouts[0]);
    }

    private void goRecovery(){

        View recoverlay = findViewById(R.id.recover_lay);
        YoYo.with(Techniques.SlideInUp).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                recoverlay.setVisibility(View.VISIBLE);
            }
        }).duration(800).playOn(recoverlay);

        AutoCompleteTextView usr = findViewById(R.id.username_rec);
        AutoCompleteTextView pass = findViewById(R.id.passrecover);

        Ecapdamond a = Ecapdamond.ecapdamond;

        findViewById(R.id.recover_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(pass.getText().toString().length() < 8){
                   showRed(getString(R.string.password_length), "OK", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                       }
                   });
               }else if(usr.getText().toString().contains(" ")){
                   showRed(getString(R.string.spc_isr), "Ok", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                       }
                   });
               }else if (usr.getText().toString().isEmpty()) {
                   showRed(getString(R.string.emptyuser), "Ok", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                       }
                   });
               }
               else{
                   showLoading(getString(R.string.recovering_));
                   String usa = usr.getText().toString();
a.getRecover(usa, pass.getText().toString(), new Ecapdamond.StatusListener() {
    @Override
    public void onLoad(User user) {
        user.saveUser();
        saveUser(user);
   showSuccessTiming(getString(R.string.welcomeagain)+" "+user.username, false, false);
    }

    @Override
    public void onError(String erno) {
       Log.e(TAG, "onError: "+erno );
        if(erno.equals("password-error")){
            showError(getString(R.string.password_or_user), false);
        }else {
            showError(getString(R.string.error_general), false);
        }
    }
});
               }
            }
        });

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToInitial(recoverlay);
            }
        });
    }

    private void successLogin(boolean signup){
        if(logingListener != null){
            if(!signup)
            logingListener.onLogged(Qa.userByGSON());
            else
                logingListener.OnSignUpUser(Qa.userByGSON());
        }
    }
    private void errorLogging(String erno){
        if(logingListener != null){
            logingListener.onError(erno);
        }
    }

    private void showLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.VISIBLE);
                loading.setAnimation(getString(R.string.loading_anim_q));
                loading.playAnimation();
                text_loading.setText(R.string.loading_t);
            }
        });

    }

    private void hideLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_rel.setVisibility(View.GONE);
            }
        });

    }

    private void showError(String erno, boolean needExit){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


        text_loading.setText(erno);
        loading.setAnimation(getString(R.string.error_loading));
        loading.playAnimation();
       // configAnim();
        Timer.init(new Timer.TimerListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                        if(needExit) {
                            errorLogging(erno);
                            onBackPressed();
                        }
                    }
                });
            }
        }, 3000);
            }
        });
    }

    private int ind = 0;
    private void moveToNext(){
        YoYo.with(Techniques.FadeOutLeft).duration(500).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                if(ind < 3){
                    now();

                }else{
                    ind = 2;
                }
               // relativeLayouts[ind-1].setVisibility(View.GONE);
            }
        }).playOn(relativeLayouts[ind]);

        ind++;

    }

    private void moveToInitial(View Actual){
        YoYo.with(Techniques.FadeOutLeft).duration(500).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                Actual.setVisibility(View.GONE);
                ind = 0;
                    now();
                // relativeLayouts[ind-1].setVisibility(View.GONE);
            }
        }).playOn(Actual);


    }

    private void moveToPrev(){
        YoYo.with(Techniques.FadeOutRight).duration(500).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                if(ind >= 0){
                    nowrev();

                }else{
                    ind = 0;
                }
                // relativeLayouts[ind-1].setVisibility(View.GONE);
            }
        }).playOn(relativeLayouts[ind]);

        ind--;

    }

    private void now() {

        YoYo.with(Techniques.SlideInRight).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                relativeLayouts[ind].setVisibility(View.VISIBLE);
            }
        }).duration(700).playOn(relativeLayouts[ind]);
    }

    private void nowrev() {

        YoYo.with(Techniques.FadeInLeft).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                relativeLayouts[ind].setVisibility(View.VISIBLE);
            }
        }).duration(700).playOn(relativeLayouts[ind]);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Qa.a_p) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
Qa.requestNowManage(this);
                    goManage = true;
                }
                else {
                    showGreen();
                    if(!writePermission)
                    moveToPrev();
else
    onBackPressed();
                }
            }else {
                Snackbar snackbar = Snackbar.make(root(), getString(R.string.permiss_nosucc), Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Qa.requestPermiss(LoginQStart.this);
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        }
    }

    private void showGreen() {
        Snackbar snackbar = Snackbar.make(root(), getString(R.string.permiss_succ), Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }

    private void showGreen(String mess) {
        Snackbar snackbar = Snackbar.make(root(), mess, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }

    private void showRed(String mess, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(root(), mess, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, listener);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public View root(){
        return this.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    public void onSignUp(User user) {
       // Log.e(TAG, "onSignUp: "+user.user_id );
        user.saveUser();
        showSuccessTimingSignUp(getString(R.string.succ));
        SharedPreferences sharedPreferences = getSharedPreferences("qkt", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(ke_res, true).apply();

    }

    private String TAG ="MAIN";
    @Override
    public void onError(String erno) {
     //   Log.e(TAG, "onError: "+erno );
            showError(erno, true);
    }

    private Runnable manageRunnable() {
   return new Runnable() {
       @Override
       public void run() {
           moveToNext();
           TextView t = findViewById(R.id.permiss_info);
           t.setText(R.string.android_q_s);
           accept_permiss.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Qa.requestNowManage(LoginQStart.this);
                   goManage = true;
               }
           });
       }
   };
    }

    private boolean goManage = false;
    @Override
    public void onLogin(User user) {
      //  Log.e(TAG, "onLogin: "+user.user_id );
user.saveUser();
showSuccessTiming(getString(R.string.logged), false, false);
        SharedPreferences sharedPreferences = getSharedPreferences("qkt", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(ke_res, true).apply();
    }

    @Override
    public void onChecked(String user_id) {
    //    Log.e(TAG, "onChecked: exist "+user_id );

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(goManage){
            goManage = false;
           // moveToPrev();
            showGreen();
            if(writePermission){
                onBackPressed();
            }
        }
    }

    /** =========================================== **/

    private boolean requireAny(){
        return requireCountry || requireGender || requirePhoto;
    }

    private ImageView profile_img;
    private String profileUri = null;
    private AutoCompleteTextView usern;



    private void configureOptionals(){

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usern.getText().toString().isEmpty()) {
                    showRed(getString(R.string.emptyuser), "Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else if(usern.getText().toString().contains(" ")){
                    showRed(getString(R.string.spc_isr), "Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                else {
                    if(requireAny()){
                        if(requirePhoto && profileUri == null){
                            showRed(getString(R.string.emptyph), "Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            return;
                        }

                        if(requireCountry && c == null){
                            showRed(getString(R.string.emptycountr), "Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            return;
                        }

                        if(requireGender && c == null){
                            showRed(getString(R.string.emptyge), "Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            return;
                        }

                        checkUsernm();

                    }else {
                        checkUsernm();
                    }

                }
            }
        });

        View countryRot = findViewById(R.id.rot_country);
        TextView tv = findViewById(R.id.policy);

        String pol = "\n "+getString(R.string.wn_pol);
        SpannableString link = makeLinkSpan(getResources().getColor(R.color.dark_qs), pol, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // respond to click
        //        Toast.makeText(LoginQStart.this, "Privacy", Toast.LENGTH_SHORT).show();
                WebFloating.showWeb(LoginQStart.this);
            }
        });
        // Set the TextView's text
        tv.setText(R.string.priv_st);

        // Append the link we created above using a function defined below.
        tv.append(link);

        // Append a period (this will not be a link).
        tv.append(".");

        // This line makes the link clickable!
        makeLinksFocusable(tv);

        View gendRot = findViewById(R.id.rot_gender);
        usern = findViewById(R.id.username);
        usern.setSingleLine(true);
        usern.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if  ((actionId == EditorInfo.IME_ACTION_DONE)) {
Qa.hideKeyboard(LoginQStart.this);
                }
                return false;

            }
        });
        View imgRot = findViewById(R.id.rot_img);

findViewById(R.id.rot_lin_country).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(countryIsExpand){
slideUp(findViewById(R.id.country_linea));
            countryRot.setRotation(0);
countryIsExpand = false;
        }else{
            slideDown(findViewById(R.id.country_linea));
            countryRot.setRotation(270);
            countryIsExpand = true;
        }
    }
});

        findViewById(R.id.rot_lin_gend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genderIsExpand){
                    slideUp(findViewById(R.id.gender_card));
                    gendRot.setRotation(0);
                    genderIsExpand = false;
                }else{
                    slideDown(findViewById(R.id.gender_card));
                    gendRot.setRotation(270);
                    genderIsExpand = true;
                }
            }
        });

        findViewById(R.id.rot_photo_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genderIsExpand){
                    slideUp(findViewById(R.id.img_lay));
                    imgRot.setRotation(0);
                    genderIsExpand = false;
                }else{
                    slideDown(findViewById(R.id.img_lay));
                    imgRot.setRotation(270);
                    genderIsExpand = true;
                }
            }
        });


        findViewById(R.id.upload_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(LoginQStart.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
profile_img = findViewById(R.id.img_preview);

        if(requirePhoto){
         TextView t = findViewById(R.id.photo_text);
        t.setText(R.string.img_prof2);
        findViewById(R.id.rot_photo_lin).performClick();
        }

        if(requireGender){
            TextView t = findViewById(R.id.gender_text);
            t.setText(R.string.gend_c2);
            findViewById(R.id.rot_lin_gend).performClick();
        }

        if(requireCountry){
            TextView t = findViewById(R.id.country_text);
            t.setText(R.string.country2);
            findViewById(R.id.rot_lin_country).performClick();
        }

    }

    private void checkUsernm(){
        Ecapdamond ecapdamond = Ecapdamond.ecapdamond;
        showLoading(getString(R.string.check_usern_loading));
        ecapdamond.checkUserName(usern.getText().toString(), new Ecapdamond.CheckListener() {
            @Override
            public void Exist() {
                showError(getString(R.string.usernam_exist), false);
            }

            @Override
            public void Available() {
                hideLoading();
signUp(ecapdamond);
            }

            @Override
            public void onError(String erno) {
showError(getString(R.string.error_general), true);
                Log.e(TAG, "onError: "+erno );
            }
        });
    }

    private void signUp(Ecapdamond ecapdamond) {


        if(profileUri != null && !profileUri.equals("")){
            showLoading(getString(R.string.up_ph));
ecapdamond.sendImage(profileUri, usern.getText().toString(), new Ecapdamond.UploadListener() {
    @Override
    public void onUploadSuccess(String url) {
  //      Log.e(TAG, "onUploadSuccess: "+url );
        showLoading(getString(R.string.signingup));
                       User user = new User();

                    user.username = usern.getText().toString();
                    user.gender = appCompatSpinner.getSelectedItem().toString();
                    String def = Locale.getDefault().getDisplayCountry();
                    user.country = c == null ? def : c.getName();
        //Log.e(TAG, "signUp: "+def );
                    user.imageUri = url;
                    showLoading(getString(R.string.signingup2));
        Qa.signUpNow(LoginQStart.this, abb,user, LoginQStart.this);
    }

    @Override
    public void onUploadError(String erno) {
        showError(getString(R.string.error_general), false);
        Log.e(TAG, "onUploadError: "+erno );
    }
});
        }else{
            User user = new User();

            user.username = usern.getText().toString();
            user.gender = appCompatSpinner.getSelectedItem().toString();
            String def = Locale.getDefault().getDisplayCountry();
    //        Log.e(TAG, "signUp: "+def );
            user.country = c == null ? def : c.getName();

showLoading(getString(R.string.signingup2));
Qa.signUpNow(this, abb, user, this);
        }


    }


    private boolean countryIsExpand = false;
    private boolean genderIsExpand = false;

    private void slideUp(View view){
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                if(view.getId() == R.id.gender_card){
                    findViewById(R.id.gender_spinner).setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(anim);
    }

    // slide the view from its current position to below itself
    private void slideDown(View view){
        if(view.getId() == R.id.gender_card){
            findViewById(R.id.gender_spinner).setVisibility(View.VISIBLE);
        }
        view.setVisibility(View.VISIBLE);
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                0, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(anim);

    }



    /** ============================================================== **/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            profile_img.setImageURI(fileUri);

            //You can get File object from intent
            //val file:File = ImagePicker.getFile(data)!!

                    //You can also get File Path from intent
            profileUri =ImagePicker.Companion.getFilePath(data);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
            } catch (IOException e) {
                Log.e(TAG, "onActivityResult: "+e.getMessage() );
                e.printStackTrace();
            }
            Bitmap lastBitmap = null;
            lastBitmap = bitmap;
            //encoding image to string
            if(lastBitmap != null)
            profileUri = getStringImage(lastBitmap);
        //    Log.e(TAG, "onActivityResult: "+fileUri.toString() );
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
showError(getString(R.string.error_general), false);
        } else {
            showError(getString(R.string.error_general), false);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    /** =============================================================== **/

    @Override
    public void needSignUp() {
runOnUiThread(new Runnable() {
    @Override
    public void run() {

        String[] items = new String[]{getString(R.string.non_c), getString(R.string.female), getString(R.string.male)};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginQStart.this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        appCompatSpinner.setAdapter(adapter);

        YoYo.with(Techniques.FadeOut).duration(400).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                if (requireAny()) {
                  //  Log.e(TAG, "call: a" );

                    RequireDialog requireDialog = new RequireDialog(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            YoYo.with(Techniques.BounceIn).duration(670).onStart(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    findViewById(R.id.signup_rel).setVisibility(View.VISIBLE);


                                }
                            }).playOn(findViewById(R.id.signup_rel));
                        }
                    });
                    requireDialog.show(getSupportFragmentManager(), "reqm");

                } else {

                    YoYo.with(Techniques.BounceIn).duration(670).onStart(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            findViewById(R.id.signup_rel).setVisibility(View.VISIBLE);


                        }
                    }).playOn(findViewById(R.id.signup_rel));
                }
            }
        }).playOn(relativeLayouts[0]);

        hideLoading();
    }
});
    }

    private LottieAnimationView lt_country;
    private ImageView country_flag;
    private TextView txt;
    @Override
    public void onSelected(Country selected) {
        lt_country = findViewById(R.id.loading_anim);
        country_flag = findViewById(R.id.img_country);
        txt = findViewById(R.id.text_country);
if(selected == null){
    selected = Country.getEmpty();
    txt.setText(R.string.non_c);
}else{
    txt.setText(selected.getNameTranslated());
    PicassoUtils.loadCountryFlag(selected.getAlpha2Code(), country_flag, lt_country);
}
this.c = selected;
    }
    private Country c;

    public static class CountrySelection extends BottomBaseShet{

        protected CountrySelection(LoginQStart c, ArrayList<Country> arrayList, SelectedCountryListener listener){
            countries.addAll(arrayList);
            lcontext = c;
            countryListener = listener;
        }

        private SelectedCountryListener countryListener;
        private LoginQStart lcontext;
        @Override
        public int layoutID() {
            return R.layout.q_sel_c;
        }

        private RecyclerView recyclerView;
        private RoundButton select_btn;
        private CountryAdapter countryAdapter;
        private ArrayList<Country> countries = new ArrayList<>();
        @Override
        public void OnStart() {
select_btn = find(R.id.select_btn);
recyclerView = find(R.id.rec_countris);
countryAdapter = new CountryAdapter(lcontext, countries);
recyclerView.setLayoutManager(new GridLayoutManager(lcontext, 3));
recyclerView.setAdapter(countryAdapter);
select_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Country c = null;
        for (int i = 0; i < countryAdapter.getArray().size(); i++) {
            if(countryAdapter.getArray().get(i).isSelected){
                c = countryAdapter.getArray().get(i);
                break;
            }
        }

            countryListener.onSelected(c);
            dismissAllowingStateLoss();
    }
});
//countryAdapter.setClickPos();

        }


    }


    public static class RequireDialog extends BottomBaseShet{

        private DialogInterface.OnDismissListener onDismissListener;
        protected RequireDialog(DialogInterface.OnDismissListener dismissListener){
this.onDismissListener = dismissListener;
        }

        @Override
        public int layoutID() {
            return R.layout.dialog_required_qs;
        }

        private ImageView imageView;
        private TextView ttl, required;
        private RoundButton ok;
        @Override
        public void OnStart() {
            ok = find(R.id.ok_btn);

            ttl = find(R.id.app_ttl);
            required = find(R.id.requires);
            imageView = find(R.id.app_aic);

            if(icon_res != 0){
                imageView.setImageResource(icon_res);
            }

            String tlu = getString(R.string.app_name)+" "+getString(R.string.permission_app);
            ttl.setText(tlu);
            String req = "";
            if(requireCountry){
                req = "◾"+getString(R.string.your_country)+"\n";
            }
            if(requireGender){
                req = req+"◾"+getString(R.string.your_gender)+"\n";
            }
            if(requirePhoto){
                req = req+ "◾"+getString(R.string.some_ph);
            }

            required.setText(req);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDismissListener != null){
                        onDismissListener.onDismiss(null);
                    }
                    dismissAllowingStateLoss();
                }
            });
        }
    }

}