package com.dese.diario.Utils.FirebaseService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.dese.diario.POJOS.DatosUsr;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Eduardo on 26/09/2017.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    public static final String TAG= "AMDI";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public String token;
    String tokenHandle;
    DatosUsr du= new DatosUsr();



    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

       token = FirebaseInstanceId.getInstance().getToken();

        savedNewToken(token);
        du.setToken(token);
        Log.d(TAG, "Token: "+ token);


    }

    /*public String getToken(){
        if(token!=null)
            return  token;
        else
        return "tokenprovicional";
    }
*/
    public void  savedNewToken(String tokens ){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("tokenNew", tokens);
        editor.apply();
        Log.i(TAG, "New Token: " + tokens);
    }



    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        tokenHandle  = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "FCM Registration Token: " + token);
    }
}
