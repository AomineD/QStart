package com.wineberryhalley.qstart.utils;

import com.wineberryhalley.qstart.base.User;

public interface LoginInterface {
    void onLogged(User user);
    void onError(String erno);
    void OnSignUpUser(User user);
}
