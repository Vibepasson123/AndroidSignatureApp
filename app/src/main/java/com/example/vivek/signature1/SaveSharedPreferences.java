package com.example.vivek.signature1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SaveSharedPreferences {
    public static final String USERNAME_PREF = "USERNAME_PREF";
    public static final String DOMAIN_PREF = "DOMAIN_PREF";
    public static final String API_KEY_PREF = "API_KEY_PREF";
    public static final String USER_ID_PREF = "USER_ID_PREF";
    public static final String WAREHOUSE_ID_PREF = "WAREHOUSE_ID_PREF";


    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, Map<String, String> login, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();

        Log.d("SPREF", "setLoggedIn: "+login);

        editor.putString(USERNAME_PREF,         login.get(USERNAME_PREF));
        editor.putString(DOMAIN_PREF,           login.get(DOMAIN_PREF));
        editor.putString(API_KEY_PREF,          login.get(API_KEY_PREF));

        editor.putString(USER_ID_PREF,          login.get(USER_ID_PREF));
        editor.putString(WAREHOUSE_ID_PREF,     login.get(WAREHOUSE_ID_PREF));

        editor.apply();
    }

    public static Map<String, String> getLogin(Context context) {
        Map<String, String> login = new HashMap<>();

        login.put(USERNAME_PREF,        getPreferences(context).getString(USERNAME_PREF, ""));
        login.put(DOMAIN_PREF,      getPreferences(context).getString(DOMAIN_PREF, ""));
        login.put(API_KEY_PREF,     getPreferences(context).getString(API_KEY_PREF, ""));
        //login.put(USER_ID_PREF,          getPreferences(context).getString(USER_ID_PREF), "");
        //login.put(WAREHOUSE_ID_PREF,    getPreferences(context).getString(WAREHOUSE_ID_PREF), "");

        return login;
    }
}
