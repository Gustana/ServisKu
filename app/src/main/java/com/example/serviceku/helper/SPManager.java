package com.example.serviceku.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {
    public static final String ID_KEY = "idUser";
    public static final String USERNAME_KEY = "username";
    public static final String PASS_KEY = "pass";
    public static final String LOGGED_IN_KEY = "loggedIn";
    public static final String LEVEL_KEY = "level";
    public static final String LEVEL_ADMIN = "admin";
    public static final String LEVEL_USER = "user";

    private static final String SHARED_PREF = "sharedPref";


    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SPManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    //for admin
    public void setLogin(String username, String password, String level) {
        editor = sharedPreferences.edit();

        editor.putString(USERNAME_KEY, username);
        editor.putString(PASS_KEY, password);
        editor.putString(LEVEL_KEY, level);
        editor.putBoolean(LOGGED_IN_KEY, true);

        editor.commit();
    }

    //for user
    public void setLogin(int id, String username, String password, String level) {
        editor = sharedPreferences.edit();

        editor.putInt(ID_KEY, id);
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASS_KEY, password);
        editor.putString(LEVEL_KEY, level);
        editor.putBoolean(LOGGED_IN_KEY, true);

        editor.commit();
    }

    public int getIdUser(){
        return sharedPreferences.getInt(ID_KEY, 0);
    }

    public boolean isLevelAdmin(){
        String level = sharedPreferences.getString(LEVEL_KEY, null);

        return level.equals(LEVEL_ADMIN);
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false);
    }

    public void clearSP(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
