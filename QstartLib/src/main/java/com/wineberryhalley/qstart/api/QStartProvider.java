package com.wineberryhalley.qstart.api;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.wineberryhalley.qstart.base.User;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public class QStartProvider extends ContentProvider {
    static Context context;
    @Override
    public boolean onCreate() {
        context = getContext();
        if(Qa.existFile()) {

            Ecapdamond.ecapdamond.active_in(new Ecapdamond.StatusListener() {
                        @Override
                        public void onLoad(User user) {
                            Log.e("M", "stt: in");
                        }

                        @Override
                        public void onError(String erno) {
                            //   Log.e("MAIN", "onError: "+erno );
                        }
                    });

            goActive();
        }
        return true;
    }

    private void goActive(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Ecapdamond.ecapdamond.active(new Ecapdamond.StatusListener() {
                    @Override
                    public void onLoad(User user) {
                        Log.e("M", "stt: naw");
                    }

                    @Override
                    public void onError(String erno) {
                        //   Log.e("MAIN", "onError: "+erno );
                    }
                });
            }
        }, 100, 300 * 1000);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
