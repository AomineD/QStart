package com.wineberryhalley.qstart.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.wineberryhalley.qstart.R;
import com.wineberryhalley.qstart.api.Api;
import com.wineberryhalley.qstart.base.TinyDB;
import com.wineberryhalley.qstart.base.User;
import com.wineberryhalley.qstart.ui.activity.LoginQStart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Qa {
    public static final int a_p = 465;
    public static final String ke_res = "MLQMDASDASD";
    public static final String am_mwql = "mawlfrkkmdsmkdsd";

    public static void checkD(Activity activity) {
        if (tinyDB == null) {
            tinyDB = new TinyDB(activity);
            User.setTinyDB(tinyDB);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private static TinyDB tinyDB;
    public static boolean isLogged(Activity activity) {
        return canReadAndWrite(activity) && tinyDB.getUserLogged() != null && !tinyDB.getUserLogged().user_id.equals("") && tinyDB.getUserLogged().status;
    }

    public static void checkUserAvailibity(Activity activity, CheckListener listener){
        checkD(activity);
        Api api = Api.get(activity);
        String usid = "";
        if(isLogged(activity)){
        //    Log.e("MAIN", "checkUserAvailibity: akja" );
            usid = tinyDB.getUserLogged().user_id;
        }else if(canReadAndWrite(activity) && existFile()){
            usid = user_id_by_file();
        }

        //    Log.e("MAIN", "checkUserAvailibity: "+usid );
        api.login(usid, new Api.StatusListener() {
            @Override
            public void onLoad(User user) {
                listener.onSuccess(user);
            }

            @Override
            public void onError(String erno) {
                Log.e("MAIN", "onError: "+erno );
                if(erno.contains("User id not valid")){
                    deleteData();
                    checkFile(activity, new UserListener() {
                        @Override
                        public void onSignUp(User user) {
                            listener.onSuccess(user);
                        }

                        @Override
                        public void onError(String erno) {
                            listener.onError(erno);
                        }

                        @Override
                        public void onLogin(User user) {

                        }

                        @Override
                        public void onChecked(String user_id) {

                        }

                        @Override
                        public void needSignUp() {
                            listener.onError(activity.getString(R.string.signupaga));
                        }
                    });
                }else{
                    listener.onError(erno);
                }
            }
        });
    }

    public static void loginUser(Activity activity, UserListener listener){
        checkD(activity);
        Api api = Api.get(activity);
        String usid = "";
        if(isLogged(activity)){
            //    Log.e("MAIN", "checkUserAvailibity: akja" );
            usid = tinyDB.getUserLogged().user_id;
        }else if(canReadAndWrite(activity) && existFile()){
            usid = user_id_by_file();
        }

        //    Log.e("MAIN", "checkUserAvailibity: "+usid );
        api.login(usid, new Api.StatusListener() {
            @Override
            public void onLoad(User user) {
                listener.onLogin(user);
            }

            @Override
            public void onError(String erno) {
                Log.e("MAIN", "onError: "+erno );
                if(erno.contains("User id not valid")){
               listener.needSignUp();
                }else{
                    listener.onError(erno);
                }
            }
        });
    }

    public interface CheckListener{
        void onSuccess(User user);
        void onError(String erno);
    }

    public interface UserListener{
        void onSignUp(User user);
        void needSignUp();
        void onError(String erno);
        void onLogin(User user);
        void onChecked(String user_id);
    }


    public static void checkFile(Activity activity, UserListener userListener) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                File fail = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                //      String path = fail.getAbsolutePath().replace(activity.getPackageName()+"/files", "");

                File folder = new File(fail.getAbsolutePath()+File.separator+".quickstrt");
                //   Log.e("MAIN", "run: "+folder.getAbsolutePath());
                if(!folder.exists()){
                    folder.mkdirs();
                }

                File lg = new File(folder+File.separator+"qslgn");

                if(!lg.exists()){

                  //  Api api = Api.get(activity);
                    if(activity instanceof LoginQStart){
                        LoginQStart l = (LoginQStart) activity;
                        l.showLoading(activity.getString(R.string.signingup));
                    }
userListener.needSignUp();
                 /*   api.signup(new Api.StatusListener() {
                        @Override
                        public void onLoad(User user) {

                            File gpxfile = new File(folder, "lgn");

                            //       Log.e("MAIN", "onLoad: "+user.user_id );
                            FileWriter writer = null;
                            try {
                                if(!gpxfile.exists()){
                                    gpxfile.createNewFile();
                                }
                                writer = new FileWriter(gpxfile);

                                writer.append(user.user_id);
                                writer.flush();
                                writer.close();
                                user.saveUser();

                                //    Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show();
                                //      Log.e("MAIN", "onLoad: saved in "+gpxfile.getAbsolutePath() );
                                //    activity.hideLoading();
                                userListener.onSignUp(user);
                            } catch (IOException e) {
                                Log.e("MAIN", "IO Exc: "+e.getMessage() );
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String erno) {
                            //     activity.hideLoading();
                            userListener.onError(erno);
                            Log.e("MAIN", "onError SIG: "+erno );
                        }
                    });
*/

                }else{
                    if(tinyDB.getUserLogged() != null && !tinyDB.getUserLogged().user_id.isEmpty()) {
                        userListener.onChecked(tinyDB.getUserLogged().user_id);
                    }else{
                        try {

                            String contents = read_(lg);
                            User user = new User();
                            user.user_id = contents;
                            Log.e("MAIN", "run: "+contents );
                            checkUserAvailibity(activity, new CheckListener() {
                                @Override
                                public void onSuccess(User user) {
                                    user.saveUser();
                                    userListener.onLogin(user);
                                }

                                @Override
                                public void onError(String erno) {
                                    userListener.onError(erno);
                                }
                            });
                            //    Log.e("MAIN", "run: "+contents+" "+(ActivityCompat.checkSelfPermission(activity, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) );
                        } catch (IOException e) {
                            userListener.onError(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
                //   Log.e("MAIN", "run: "+lg.getAbsolutePath()+" "+lg.exists() );
            }
        });
        t.start();

    }

    public static void signUpNow(Activity activity, User user, UserListener userListener){
        Api api = Api.get(activity);
        File fail = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        //      String path = fail.getAbsolutePath().replace(activity.getPackageName()+"/files", "");

        File folder = new File(fail.getAbsolutePath()+File.separator+".quickstrt");
        //   Log.e("MAIN", "run: "+folder.getAbsolutePath());
        if(!folder.exists()){
            folder.mkdirs();
        }

     //   File lg = new File(folder+File.separator+"lgn");
        api.signup(user,new Api.StatusListener() {
            @Override
            public void onLoad(User user) {

                File gpxfile = new File(folder, "qslgn");

                //       Log.e("MAIN", "onLoad: "+user.user_id );
                FileWriter writer = null;
                try {
                    if(!gpxfile.exists()){
                        gpxfile.createNewFile();
                    }
                    writer = new FileWriter(gpxfile);

                    writer.append(user.user_id);
                    writer.flush();
                    writer.close();
                    user.saveUser();

                    //    Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show();
                    //      Log.e("MAIN", "onLoad: saved in "+gpxfile.getAbsolutePath() );
                    //    activity.hideLoading();
                    userListener.onSignUp(user);
                } catch (IOException e) {
                    Log.e("MAIN", "IO Exc: "+e.getMessage() );
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String erno) {
                //     activity.hideLoading();
                userListener.onError(erno);
                Log.e("MAIN", "onError SIG: "+erno );
            }
        });
    }

    public static String read_(File file) throws IOException {
        String highScore = "";
        BufferedReader br = new BufferedReader(new FileReader(file));

        highScore = br.readLine();
        br.close();


        return highScore;
    }

    //LEER ARCHIVO

    public static String user_id_by_file(){
        File fail = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        //      String path = fail.getAbsolutePath().replace(activity.getPackageName()+"/files", "");

        File folder = new File(fail.getAbsolutePath()+File.separator+".quickstrt");
        //   Log.e("MAIN", "run: "+folder.getAbsolutePath());
        if(!folder.exists()){
            folder.mkdirs();
        }

        File lg = new File(folder+File.separator+"qslgn");
        if(lg.exists()){
            try {

                String contents = read_(lg);
                User user = new User();
                user.user_id = contents;
                return contents;
                //    Log.e("MAIN", "run: "+contents+" "+(ActivityCompat.checkSelfPermission(activity, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) );
            } catch (IOException e) {
                Log.e("MAIN", "user_id_by_file: "+e.getMessage() );
                return "0";
            }
        }else
            return "0";
    }

    //EL ARCHIVO EXISTE?
    public static boolean existFile(){

        return fileQuik().exists();
    }


    public static User userByGSON(){
        if(tinyDB != null)
            return tinyDB.getUserLogged();
        else
            return null;
    }

    public static File fileQuik(){
        File fail = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        //      String path = fail.getAbsolutePath().replace(activity.getPackageName()+"/files", "");

        File folder = new File(fail.getAbsolutePath()+File.separator+".quickstrt");
        //   Log.e("MAIN", "run: "+folder.getAbsolutePath());
        if(!folder.exists()){
            folder.mkdirs();
        }

        File lg = new File(folder+File.separator+"qslgn");
        return lg;
    }

    public static void deleteData(){
        if(fileQuik().exists()){
            fileQuik().delete();
        }
    }

    /** PERMISSION MANAGER **/




    public interface PermissionListener{
        void onGranted();
        void onDenied();
    }

    public static String[] permissionBelowR = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static String[] permissionTop = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static boolean checkPermiss(FragmentActivity activity) {
        if (canReadAndWrite(activity)) {
            // You can use the API that requires the permission.
            Log.e("MAIN", "checkPermiss: si granted" );
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            return false;
        } else {
            // Log.e("MAIN", "checkPermiss: get" );
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermiss(activity);
            return false;
        }
    }

    public static void requestPermiss(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(activity, permissionTop, 465);
        }else{
            ActivityCompat.requestPermissions(activity, permissionBelowR, 465);
        }
    }

    public static boolean canReadAndWrite(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ActivityCompat.checkSelfPermission(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED && Environment.isExternalStorageManager();
        }else{
            return  ActivityCompat.checkSelfPermission(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED;
        }
    }

    public static final String keyword_file_access = "open failed: EACCES (Permission denied)";

    public static boolean permissionDeniedManage(String erno){
        return erno.contains(keyword_file_access) && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R;
    }


    public static void requestNowManage(Activity activity){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            String data = "package:"+activity.getPackageName();
            intent.setData(Uri.parse(data));
            activity.startActivity(intent);
        }
    }

}
