package com.dese.diario;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ani on 31/05/2017.
 */

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public  static  final String TAG = "DIARIO";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: " + token);

    enviarTokenAlServidor(token);
}

    private void enviarTokenAlServidor(String token) {
        // Enviar token al servidor
    }


}
