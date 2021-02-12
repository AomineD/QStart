package com.wineberryhalley.qstart.base;


public class User {
    public String user_id = "";
    public String username = "";
    public String gender = "";
    public String country = "";
    public String imageUri = "";
    public boolean status;
private static TinyDB tinyDB;
public static void setTinyDB(TinyDB db){
    tinyDB = db;
}

    public void saveUser(){
        if(tinyDB != null){
            tinyDB.saveUser(this);
        }
    }

    public User getUser(){
    return this;
    }
}
