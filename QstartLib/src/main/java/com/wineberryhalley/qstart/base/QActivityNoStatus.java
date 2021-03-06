package com.wineberryhalley.qstart.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class QActivityNoStatus extends AppCompatActivity {
    public abstract void Main();
    public abstract void statusChanged(int pixelesSizeBar);
    public abstract int resLayout();
    public abstract ArrayList<String> keysNotification();
    public abstract void onReceiveValues(ArrayList<String> values);

    public void onNotificationReceived(RemoteMessage.Notification n, Bundle datos){

    }

    public void onReceiveJsonFromNotif(JSONObject object){

    }

    public static QActivityNoStatus main;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        setContentView(resLayout());
        handleNotifIfExist();
        configStatus();
        Main();




    }

    private Runnable runnable(int pxls) {
        return new Runnable() {
            @Override
            public void run() {
              statusChanged(pxls);

            }
        };
    }
    private void configStatus() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int dpactual =    transparentStatusAndNavigation(QActivityNoStatus.this);

                runOnUiThread(runnable(dpactual));

            }
        }, 200);
    }

    protected boolean receiveNotification = false;

    private void handleNotifIfExist() {
        if(getIntent() != null && getIntent().getExtras() != null && keysNotification() != null){
            Bundle b = getIntent().getExtras();
          //   Log.e("MAIN", "handleNotifIfExist: aja" );
          /*  for (String val:
                 b.keySet()) {
                Log.e("MAIN", "handleNotifIfExist: "+val );
            }*/
            ArrayList<String> vars = new ArrayList<>();
            receiveNotification = true;
            for(int i=0; i < keysNotification().size(); i++) {
                if (b.containsKey(keysNotification().get(i))) {
                    String var = b.getString(keysNotification().get(i));
                    vars.add(var);
                   // String id = b.getString("id");
                    //¿Log.e("MAIN", "handleNotifIfExist: " + type + " id= " + id);
                }
            }
         //   Log.e("MAIN", "handleNotifIfExist: "+vars.size() );
            if(vars.size() > 0)
            onReceiveValues(vars);



        }
    }

    private int transparentStatusAndNavigation(Activity context) {
     /*   Rect rectangle = new Rect();
        Window window = context.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;

        Log.e("MAIN", "StatusBar Height= " + statusBarHeight + " , TitleBar Height = " + titleBarHeight);

        return statusBarHeight;*/

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        //convertPixelsToDp(result, context));
        int finalResult = result;
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 21) {
                    setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false, context);
                    context.getWindow().setStatusBarColor(Color.TRANSPARENT);
                    context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

                    //  context.getWindow().setNavigationBarColor(Color.TRANSPARENT);
             //       Log.e("MAIN", "StatusBar Height= " + finalResult);
                }
            }
        });

        return result;



    }

    private void setWindowFlag(final int bits, boolean on, Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static boolean canReceiveNotifications(){
        return main != null && main.keysNotification() != null && main.keysNotification().size() > 0;
    }

    public View root(){
        return this.getWindow().getDecorView().findViewById(android.R.id.content);
    }

}
