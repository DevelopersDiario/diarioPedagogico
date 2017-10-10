package com.dese.diario.Utils.DataBase;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eduardo on 10/10/2017.
 */

public class Consults {
    String dataFoto, dataNombre, dataToken;


    public void consultarPublicacion(final Context c) {

        final VariablesLogin varlogin = new VariablesLogin();
        RequestQueue queue = Volley.newRequestQueue(c);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listxiduser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {

                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                                    jsonobject.getString("nombregrupo");


                                    getDataFriends(jsonobject.getString("parabuscaruser"), c);

                                }
                            } catch (JSONException e) {
                                Log.e("###", "--->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("###", "--->" + "Mensaje error");
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idusario", varlogin.getIdusuario());
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);
    }

    private void getDataFriends(final String parabuscaruser, final Context c) {

        final List finalUser = null;
        RequestQueue queue = Volley.newRequestQueue(c);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listxgrupo,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //JSONArray jsonArray = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                                try {

                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        //  System.out.println(jsonobject);
                                        VariablesLogin varllogin = new VariablesLogin();

                                     dataNombre= jsonobject.getString("nombre");
                                      jsonobject.getString("foto");
                                     jsonobject.getString("token");

                                        /// System.out.println(listpublicaciones);

                                    }
                                } catch (JSONException e) {
                                    Log.e("UUUps!!!!!", "Error!!" + e);
                                }
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("°_°", "ocurio un error !");
                }

            }

            ) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("idusuario", parabuscaruser);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };


            queue.add(stringRequest);

    }

    public String getDataNombre(){
        return  dataNombre;
    }public String getDataFoto(){
        return  dataFoto;
    }public String getDataToken(){
        return  dataToken;
    }
}
