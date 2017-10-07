package com.dese.diario.Utils.FirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.MainActivity;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.dese.diario.Register;
import com.dese.diario.Utils.Urls;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Eduardo on 26/09/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public static final String TAG = "PUBLICACIONES";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensjae de recibido de: " + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notificacion: " + remoteMessage.getNotification().getBody());

            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }


    }


    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void showNotification( String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pedingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(new long[100])
                .setContentIntent(pedingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }


    public void setUserGrup(final Register register, final String idgrupo, final String titulo) {
        RequestQueue queue = Volley.newRequestQueue(register);
        final VariablesLogin variablesLogin= new VariablesLogin();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listuserxgpo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {

                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                                    final String _idUser = jsonobject.getString("idusuario");
                                    if(variablesLogin.getIdusuario()!=_idUser){
                                        //Toast.makeText(register, _idUser, Toast.LENGTH_SHORT).show();
                                        getToken(register, _idUser, titulo);
                                    }



                                }
                            } catch (JSONException e) {
                                Log.e("Notifications UserGpo", "Error +->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FOREBASE", "Response--->" + error);
            }

        }
        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idgrupo", idgrupo);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);

    }


    public void getToken(final Context c, final String iduser, final String titulo ) {
        RequestQueue queue = Volley.newRequestQueue(c);
        final VariablesLogin variablesLogin= new VariablesLogin();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.filtrousuarioXid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {

                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {

                                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                                    final String token= jsonobject.getString("token");
                                    final String username=  jsonobject.getString("nombre");

                                    if(token!=variablesLogin.getToken().toString())
                                    notificationPublication(token, titulo, username);

                                    //Toast.makeText(c, token , Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                Log.e("Notifications UserGpo", "Error +->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Firebase Mesaje", "Response--->"+error);
            }
        }
        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idusuario", iduser);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        queue.add(stringRequest);

    }

    public void notificationPublication(final String token, final String titulo, String username){

        new PostJSON(username, titulo, token );

    }

}

