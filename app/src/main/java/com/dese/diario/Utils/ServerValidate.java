package com.dese.diario.Utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eduardo on 08/10/2017.
 */

public class ServerValidate {
    //final static String validate="http://201.144.197.51:8080/diariopws/api/1.0/main/status";
    final static String validate ="http://192.168.0.105:8080/diariopws/api/1.0/main/status";


    public boolean ServerActive(final Context c) throws JSONException {
        String datoslogin;

        JSONObject jsonObject = new JSONObject(validate);

        datoslogin = jsonObject.getString("Success");

        if (datoslogin.equals("[]")|| datoslogin.isEmpty()) {
            Toast.makeText(c, "Hubo un problema. Intente mas tarde", Toast.LENGTH_LONG).show();
            return false;

        } else {
            Toast.makeText(c, datoslogin.toString(), Toast.LENGTH_LONG).show();
            return true;

        }
    }
}
