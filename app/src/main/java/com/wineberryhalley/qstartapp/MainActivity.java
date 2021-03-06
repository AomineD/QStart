package com.wineberryhalley.qstartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.api.LoginQStart;
import com.wineberryhalley.qstart.ui.views.QImageLoadingView;
import com.wineberryhalley.qstart.ui.views.QStartLoginButton;
import com.wineberryhalley.qstart.ui.views.RoundButton;
import com.wineberryhalley.qstart.utils.LoginInterface;
import com.wineberryhalley.qstart.api.LoginQStartBottom;

public class MainActivity extends AppCompatActivity implements LoginInterface {
RoundButton b;
QImageLoadingView imageLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Log.e("MAIN", "onCreate: "+IntroQStart.wasViewed(this) );
    //    Log.e("MAIN", "onCreate: "+!IntroQStart.wasViewed(this) );
       // if(!IntroQStart.wasViewed(this))
      //  IntroQStart.startIntro(this);

        b = findViewById(R.id.btn_a);
        imageLoadingView = findViewById(R.id.img_pic);

     LoginQStart.requirePhoto(R.drawable.smartphone);

        QStartLoginButton q = findViewById(R.id.qstlg);
        q.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginQStartBottom.loginRequiredPopUp(MainActivity.this, true);
            }
        });

      if(LoginQStart.isLogged(this)){
          Toast.makeText(this, "Logged", Toast.LENGTH_SHORT).show();
         User user = LoginQStart.getUser();
         imageLoadingView.loadImgCircle(user.imageUri);
         b.setText(user.username);
      }else {
          LoginQStartBottom.loginRequiredPopUp(this, true);
      }
        //LoginQStartBottom.loginRequiredPopUp(this, key, true);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginQStart.logOut(MainActivity.this);
            }
        });
    }

    private String TAG ="MAIN";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == LoginQStart.loging_permission && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                LoginQStart.requestManageNow(this);
            }
            LoginQStartBottom.loginRequiredPermissionAccept(this);
        }else{
            LoginQStartBottom.loginRequiredPopUp(this, false);
        }
    }

    @Override
    public void onLogged(User user) {
        Log.e(TAG, "onLogged: "+user.username );
        imageLoadingView.loadImgCircle(user.imageUri);
        b.setText(user.username);
    }

    @Override
    public void onError(String erno) {
        Log.e(TAG, "onErrorMAIN: "+erno );
    }

    @Override
    public void OnSignUpUser(User user) {

    }
}