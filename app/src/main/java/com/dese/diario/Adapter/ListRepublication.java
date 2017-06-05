package com.dese.diario.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.MainActivity;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Publication;
import com.dese.diario.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eduardo on 03/06/2017.
 */

public class ListRepublication extends AppCompatActivity {
    final static String urllistar = "http://187.188.168.51:8080/diariopws/api/1.0/publicacion/listrepublication";
    final static String url= Urls.listpublicacion;
    final static String urlLisGpo= Urls.listgrupo;

    final String idpublicacion= "idpublicacion";
    final String nombre= "nombre";
    final String fecha="fecha";
    final String titulo= "titulo";
    final String idusuario= "idusuario";
    final String padre= "padre";
    final String foto= "foto";
    final String observaciones="observaciones";

    final String KEY_IDUSUARIO = "idusuario";
    final String KEY_TITULO = "titulo";
    final String KEY_OBSERVACIONES = "observaciones";
    final String KEY_IDGROUP = "idgrupo";
    final String KEY_ROL = "idrol";
    final String KEY_IDPUBLICACION = "idpublicvv v acion";

    final String KEY_NAMEU = "nombre";
    final String KEY_PAPA = "padre";
    final String KEY_FOTO = "foto";

    ArrayList listRepublicaciones;
    Adapter_RePubication adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    final String CONTENT_TYPE = "Content-Type";
    final String APPLICATION = "application/x-www-form-urlencoded";
    List leadsNames, leadsIdes;
    ArrayAdapter mLeadsAdapter;
    Spinner spGpoP;
    String ed;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        DetailPublication dp = new DetailPublication();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerRepost);
        linearLayoutManager= new LinearLayoutManager(dp.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void AlertRepublication(final String pa, final Context context) {
        final VariablesLogin varlogin = new VariablesLogin();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialoglayout = inflater.inflate(R.layout.dialog_republication, null);

        final EditText TITLE = (EditText) dialoglayout.findViewById(R.id.ettitlepost);
        final EditText PUBLIC = (EditText) dialoglayout.findViewById(R.id.etPublication);
        final Spinner GPO = (Spinner) dialoglayout.findViewById(R.id.spGpoP);
        listGpo(context, GPO);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setCancelable(false)
                .setPositiveButton("Republicar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        String titulo = TITLE.getText().toString();
                        String publica = PUBLIC.getText().toString();
                        String idgrupe = ed;
                        String papa = pa;

                        try {
                            registerRePost(context, titulo, publica, papa, idgrupe);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        builder.setView(dialoglayout);
        builder.show();
    }


    private void registerRePost(final Context context, final String t, final String o, final String p, final String g) throws JSONException {
        final VariablesLogin varlogin = new VariablesLogin();
        Toast.makeText(context, "   Register Post",Toast.LENGTH_SHORT).show();
        final String idusuario = varlogin.getIdusuario();
        final String titulo = t;
        final String observaciones = o;
        final String padre = p;
        final String grupe = g;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                        body = new String(error.networkResponse.data, "UTF-8");

                        //   failed_regpublication.setText(body);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDUSUARIO, idusuario);
                params.put(KEY_TITULO, titulo);
                params.put(KEY_IDGROUP, grupe);
                params.put(KEY_OBSERVACIONES, observaciones);
                params.put(KEY_ROL, "1");
                params.put(KEY_IDPUBLICACION, padre);
                params.put(CONTENT_TYPE, APPLICATION);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }//Fin RegisterPost


  /*  public void listarRepublicaciones(final Context context,final String pa) {


        // final RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        Toast.makeText(context,"Entra al metodo listar con padre"+pa,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urllistar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context,"response"+response.length(),Toast.LENGTH_SHORT).show();
                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                listpublicaciones = new ArrayList<>();

                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    Toast.makeText(context, " Post"+jsonobject.getString("titulo"),Toast.LENGTH_SHORT).show();
                                    //VariablesLogin varllogin = new VariablesLogin();

                                    listpublicaciones.add(new com.dese.diario.Objects.RePublication(
                                            jsonobject.getString(KEY_IDPUBLICACION),
                                            jsonobject.getString(KEY_IDUSUARIO),
                                            jsonobject.getString(KEY_NAMEU),
                                            jsonobject.getString(KEY_FOTO),
                                            jsonobject.getString("ruta"),
                                            jsonobject.getString(KEY_TITULO),
                                            jsonobject.getString(KEY_PAPA)));
                                    adapter = new Adapter_RePubication(listpublicaciones,context);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(adapter);

                                    Toast.makeText(context, "listapublicacoones=="+listpublicaciones,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(context, "listapublicacoones=="+jsonarray.toString(),Toast.LENGTH_SHORT).show();
                                    /// System.out.println(listpublicaciones);



                                }
                            } catch (JSONException e) {
                                Log.e("Oops!", "Sucedio un error" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context,
                            ("Tiempo error"),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                }
            }


        }

        ) {

            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("idpublicacion", pa);
               // params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };


        queue.add(stringRequest);
    }*/

    public  void listarRe(final Context context, final String pa){

        final DetailPublication dp= new DetailPublication();

    Toast.makeText(context, "Entro a listar con el padre"+ pa, Toast.LENGTH_LONG).show();


        RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, urllistar,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //JSONArray jsonArray = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                                try {
                                    listRepublicaciones = new ArrayList<>();
                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);


                                        listRepublicaciones.add(new com.dese.diario.Objects.RePublication(
                                                jsonobject.getString("padre"),
                                                jsonobject.getString("idpublicacion"),
                                                jsonobject.getString("foto"),
                                                jsonobject.getString("ruta"),
                                                jsonobject.getString("titulo"),
                                                jsonobject.getString("nombre"),
                                                jsonobject.getString("idusuario")));
                                        adapter = new Adapter_RePubication(listRepublicaciones, context);
                                        Toast.makeText(context, "Lista"+ listRepublicaciones, Toast.LENGTH_LONG).show();
                                      // recyclerView.setAdapter(adapter);
                                         System.out.println(listRepublicaciones);

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
                    headers.put("idpublicacion", pa);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };


            queue.add(stringRequest);
        }





    public void listGpo(final Context context, final Spinner gpo) {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLisGpo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                leadsNames  = new ArrayList<String>();
                                leadsIdes= new ArrayList<String>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (final int[] i = {0}; i[0] < jsonarray.length(); i[0]++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i[0]);

                                    leadsNames.add(0, jsonobject.getString("nombregrupo"));
                                    leadsIdes.add(0, jsonobject.getString(KEY_IDGROUP));


                                   // GPO= (Spinner) findViewById(R.id.spGpoP);
                                    mLeadsAdapter = new ArrayAdapter<String>(context,
                                            android.R.layout.simple_spinner_item, leadsNames);
                                    mLeadsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    gpo.setAdapter(mLeadsAdapter);
                                    gpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
                                        {
                                            ed= (String) leadsIdes.get(pos);

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent)
                                        {    }
                                    });
                                }



                            } catch (JSONException e) {
                                Log.e(getString(R.string.message_Oops), getString(R.string.message_Publication_Error) + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getString(R.string.carita), getString(R.string.message_ocurrio_error));
            }
        });


        queue.add(stringRequest);

    }
}
