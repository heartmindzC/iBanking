package com.example.ibanking2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    // Sau khi tao nguoi dung dang nhap android studio se tao 1 file ten user_prefs
    // KEY_TOKEN dung lam key de get value giong voi co che hashmap
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_TOKEN = "token";

    // SharedPreferences cung cap cho mot file de luu token
    private final SharedPreferences sharedPreferences;

    // dam bao chi co mot doi tuong instance duoc khoi tao
    private static TokenManager instance;

    // SharedPreferences.Editor cung cap nhung phuong thuc de thay doi du lieu trong file do
    private TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            return new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    // lay token
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    // luu lai token de goi nhung api sau khi dang nhap
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    // xoa token khi nguoi dung dang xuat
    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}
