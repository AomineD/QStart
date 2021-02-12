package com.wineberryhalley.qstartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.intro.IntroQStart;
import com.wineberryhalley.qstart.ui.activity.LoginQStart;
import com.wineberryhalley.qstart.ui.views.ImageLoadingView;
import com.wineberryhalley.qstart.ui.views.RoundButton;
import com.wineberryhalley.qstart.utils.LoginInterface;
import com.wineberryhalley.qstart.utils.LoginQStartBottom;
import com.wineberryhalley.qstart.utils.Qa;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginInterface {
String key = "Lpq7DROXRGQyQB4OZuQRRqCZ4hM4YvSvNnq4i+E=";
RoundButton b;
ImageLoadingView imageLoadingView;
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


     LoginQStart.requirePhoto();


      if(LoginQStart.isLogged(this)){
          Toast.makeText(this, "Logged", Toast.LENGTH_SHORT).show();
         User user = LoginQStart.getUser();
         imageLoadingView.loadImgCircle(user.imageUri);
         b.setText(user.username);
      }else {
          LoginQStartBottom.loginRequiredPopUp(this, key, true);
      }
        //LoginQStartBottom.loginRequiredPopUp(this, key, true);
    }

    private String TAG ="MAIN";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Qa.a_p && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                Qa.requestNowManage(this);
            }
            LoginQStartBottom.loginRequiredPermissionAccept(this, key);
        }else{
            LoginQStartBottom.loginRequiredPopUp(this, key, false);
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
}