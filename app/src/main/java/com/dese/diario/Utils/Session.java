package com.dese.diario.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eduardo on 05/02/2018.
 */


public class Session {

    private final String SHARED_PREFS_FILE = "HMPrefs";
    private final String KEY_EMAIL = "email";

    private final String KEY_PASS = "pass";

    private Context mContext;


    public Session (Context context){
        this.mContext=context;
    }
    private SharedPreferences getSettings(){
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
    }

    public String getUserEmail(){
        return getSettings().getString(KEY_EMAIL, null);
    }
    public String getUserPass(){
        return getSettings().getString(KEY_PASS, null);
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_EMAIL, email );
        editor.commit();
    }

    public void setUserPass(String pass){
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(KEY_PASS, pass );
        editor.commit();
    }

}
