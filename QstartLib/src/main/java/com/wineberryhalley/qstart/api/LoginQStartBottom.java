package com.wineberryhalley.qstart.api;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.base.BottomBaseShet;
import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.ui.views.AboutQStartText;
import com.wineberryhalley.qstart.ui.views.ImageLoadingView;
import com.wineberryhalley.qstart.ui.views.RoundButton;
import com.wineberryhalley.qstart.utils.LoginInterface;
import com.wineberryhalley.qstart.utils.Timer;

import static android.content.Context.MODE_PRIVATE;
import static com.wineberryhalley.qstart.api.Qa.am_mwql;

public class LoginQStartBottom extends BottomBaseShet implements Qa.UserListener {
    protected LoginQStartBottom(){

    }

    String k = "";
    public static void showLoginPopUp(AppCompatActivity activity, String key, boolean ask){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.get(key, ask);
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    public static void showLoginPopUpAcccepted(AppCompatActivity activity, String key, boolean ask){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.get(key, ask);
        loginQStartBottom.permissionResult = true;
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    public static void showLoginPopUpDenied(AppCompatActivity activity, String key, boolean ask){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.get(key, ask);
        loginQStartBottom.permissionResult = false;
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    protected LoginQStartBottom get(String key, boolean ask){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
    loginQStartBottom.k = key;
    loginQStartBottom.askPermiss = ask;
    return loginQStartBottom;
    }

    protected LoginQStartBottom getRequired(String key){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom.k = key;
        loginQStartBottom.isRequired = true;
        return loginQStartBottom;
    }


    public static void loginRequiredPopUp(AppCompatActivity activity, String key, boolean ask){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.get(key, ask);
loginQStartBottom.isRequired = true;
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    public static void loginRequiredPermissionAccept(AppCompatActivity activity, String key){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.getRequired(key);
        loginQStartBottom.permissionResult = true;
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    public static void loginRequiredPermissionNo(AppCompatActivity activity, String key){
        LoginQStartBottom loginQStartBottom = new LoginQStartBottom();
        loginQStartBottom = loginQStartBottom.getRequired(key);
        loginQStartBottom.permissionResult = false;
        loginQStartBottom.show(activity.getSupportFragmentManager(), "qkfrmk");
    }

    private boolean permissionResult;
    private boolean askPermiss = false;
    private boolean isRequired = false;

    @Override
    public int layoutID() {
        return R.layout.l_q_frw;
    }

    private ImageLoadingView img;
    private RoundButton roundButton, closeButton;
    private LottieAnimationView loading;
    private TextView textView;
    private AboutQStartText aboutQStartText;
    private LoginInterface loginInterface;
    @Override
    public void OnStart() {
        if(getActivity() instanceof LoginInterface){
            loginInterface = (LoginInterface) getActivity();
        }
        loading = find(R.id.loading_);
textView = find(R.id.login_ttl);
aboutQStartText = find(R.id.about_qstart);
if(getActivity() != null){
    aboutQStartText.click(getActivity());
    Qa.checkD(getActivity());
}
getTextTitle();

roundButton = find(R.id.boton_log);
closeButton = find(R.id.close_bt);

roundButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(askPermiss && !Qa.canReadAndWrite(getActivity())) {
           // Qa.requestPermiss(getActivity());
            LoginQStart.initWritePermission(getActivity(), k);
            writePerms = true;
         //   dismissAllowingStateLoss();
        }else if(permissionResult || Qa.canReadAndWrite(getActivity())){
            if (Qa.canReadAndWrite(getActivity()) && Qa.existFile()) {
                showLoadingLogging();
            } else if (getContext() != null) {
                passToBig();
            }
        }else{
passToBig();
        }
    }
});
img = find(R.id.ic_lg);

img.loadImgCircle(R.drawable.placeholder_img);


    }
    private void passToBig(){
        LoginQStart.init(getActivity(), k, getActivity().getPackageName());
        dismissAllowingStateLoss();
    }

    private void showLoadingLogging(){
        aboutQStartText.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("qkt", MODE_PRIVATE);
        sharedPreferences.edit().putString(am_mwql, k).apply();
        loading.setVisibility(View.VISIBLE);
        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img.showLoading();
        roundButton.setText("");
        textView.setText(R.string.logging);
        Timer.init(new Timer.TimerListener() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Qa.loginUser(getActivity(), LoginQStartBottom.this);
                    }
                });
            }
        }, 600);

    }

    private void hideLoadingLogging(User user){
        if(getActivity() != null ){
     getActivity().runOnUiThread(new Runnable() {
         @Override
         public void run() {
             loading.setVisibility(View.GONE);
             img.loadImgCircle(user.imageUri);
             String cont = getString(R.string.continue_like)+" "+user.username;
             roundButton.setText(cont);
             roundButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                  user.saveUser();
                     if (isAdded()) {
                         if(loginInterface != null){
                             loginInterface.onLogged(user);
                         }
                         dismissAllowingStateLoss();
                     }
                 }
             });
             textView.setText(R.string.succ_logging);
         }
     });
        }
    }


    private void showError(String erno){
        if(getActivity() != null ){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.setVisibility(View.GONE);
                    img.hideLoading();
                    roundButton.setText(R.string.error_internal);
                    textView.setText(erno);
                    Timer.init(new Timer.TimerListener() {
                        @Override
                        public void onSuccess() {
                            if (isAdded()) {
                                if(loginInterface != null){
                                    loginInterface.onError(erno);
                                }
                                dismissAllowingStateLoss();
                            }
                        }
                    }, 3500);
                }
            });
        }
    }


    private void getTextTitle(){
        if(isRequired && !permissionResult){
            textView.setText(R.string.required_lg);
            textView.setTextSize(16);
        }else if(isRequired && permissionResult){
            textView.setText(R.string.permission_accept_lg);
            textView.setTextSize(16);
        }else if(permissionResult){
            textView.setText(R.string.permission_accept_lg);
            textView.setTextSize(16);
        }
    }

    @Override
    public void onSignUp(User user) {

    }

    @Override
    public void needSignUp() {
        passToBig();
    }

    @Override
    public void onError(String erno) {
showError(erno);
        Log.e("MAIN", "onError: "+erno );
    }

    @Override
    public void onLogin(User user) {
hideLoadingLogging(user);
    }

    @Override
    public void onChecked(String user_id) {

    }

    private boolean writePerms = false;
    @Override
    public void onResume() {
        super.onResume();
        Log.e("MAIN", "onResume: resumido" );
        if(writePerms && Qa.canReadAndWrite(getActivity())){
            textView.setText(R.string.permission_accept_lg);
            textView.setTextSize(16);
            writePerms = false;
        }else if(writePerms){
            roundButton.setText(R.string.try_again);
            closeButton.setVisibility(View.VISIBLE);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                }
            });
            textView.setText(R.string.permiss_nosucc);
            textView.setTextSize(16);
            writePerms = false;
        }
    }
}
