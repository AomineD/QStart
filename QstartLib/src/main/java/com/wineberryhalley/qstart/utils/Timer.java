package com.wineberryhalley.qstart.utils;

import java.util.TimerTask;

public class Timer {

    protected Timer(){

    }

    public interface TimerListener{
        void onSuccess();
    }
public static void init(TimerListener listener, long tim){
    new java.util.Timer().schedule(new TimerTask() {
        @Override
        public void run() {
listener.onSuccess();
        }
    }, tim);
}
}
