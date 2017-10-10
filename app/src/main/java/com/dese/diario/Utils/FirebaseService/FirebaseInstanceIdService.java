package com.dese.diario.Utils.FirebaseService;

import android.util.Log;

import com.dese.diario.POJOS.DatosUsr;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Eduardo on 26/09/2017.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    public static final String TAG= "AMDI";
    public String token;
    DatosUsr du= new DatosUsr();

    public FirebaseInstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
       token = FirebaseInstanceId.getInstance().getToken();
        if(token!=null)
        du.setToken(token);
        Log.d(TAG, "Token: "+ token);

    }

    public String getToken(){
        if(token!=null)
            return  token;
        else
        return du.getToken();
    }


}
