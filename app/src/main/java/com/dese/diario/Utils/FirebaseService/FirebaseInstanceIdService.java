package com.dese.diario.Utils.FirebaseService;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Eduardo on 26/09/2017.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    public static final String TAG= "AMDI";
    public String token;

    public FirebaseInstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
       token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: "+ token);

    }


}
