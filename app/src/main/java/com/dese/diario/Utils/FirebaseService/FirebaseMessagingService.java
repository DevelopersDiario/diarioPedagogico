package com.dese.diario.Utils.FirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.MainActivity;
import com.dese.diario.R;
import com.dese.diario.Register;
import com.dese.diario.Utils.Urls;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    private void showNotification(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pedingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pedingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    public void notificationPublication(Context c, final String token, final String titulo) throws JSONException {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if (error.networkResponse.data != null) {

                    try {
                        if(error!=null && error.getMessage() !=null){
                            Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                        }
                        body = new String(error.networkResponse.data, "UTF-8");

                        // failed_regpublication.setText(body);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("to",token);
                params.put("notification.title",titulo);
                params.put("notification.body","Nueva notificación");
                String creds = String.format("key", "AAAA_3Iy5Jc:APA91bHVmICBI1cYSipI6YaKwKZAqZKTQ5ajayT_79AmaWNQnl7DT893p0mgPXU67ymAMDFtmQo_ioCjkVKs3i-vJJPdx9YBF0XZrX2FPEGuMfhHJOgBAVNno0c7qODrmPNdaknT7eyw");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                //params.put("Authorization", "key=AAAA_3Iy5Jc:APA91bHVmICBI1cYSipI6YaKwKZAqZKTQ5ajayT_79AmaWNQnl7DT893p0mgPXU67ymAMDFtmQo_ioCjkVKs3i-vJJPdx9YBF0XZrX2FPEGuMfhHJOgBAVNno0c7qODrmPNdaknT7eyw");
                params.put("Content-Type", "application/json");
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        requestQueue.add(stringRequest);

    }

    public void setUserGrup(final Register register, final String idgrupo, final String titulo) {
        RequestQueue queue = Volley.newRequestQueue(register);

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

                                    Toast.makeText(register, _idUser, Toast.LENGTH_SHORT).show();
                                    getToken(register, _idUser, titulo);

                                }
                            } catch (JSONException e) {
                                Log.e("Notifications UserGpo", "Error +->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Search Friends", "Response--->" + error);
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

                                    notificationPublication(c, token, titulo);

                                    Toast.makeText(c, token, Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                Log.e("Notifications UserGpo", "Error +->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Search Friends", "Response--->"+error);
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
}

