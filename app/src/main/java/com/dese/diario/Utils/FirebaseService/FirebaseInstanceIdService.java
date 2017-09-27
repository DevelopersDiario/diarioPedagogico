package com.dese.diario.Utils.FirebaseService;

import android.util.Log;

import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Eduardo on 26/09/2017.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {

    public static final String TAG= "AMDI";
    VariablesLogin varlogin= new VariablesLogin();
    DatosUsr du= new DatosUsr();

    public FirebaseInstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: "+ token);
        FirebaseConection FC= new FirebaseConection();
        FC.setDatabaseUser(du.getCuenta(), token.toString() );
    }


}
