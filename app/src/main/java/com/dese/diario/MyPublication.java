package com.dese.diario;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Pubication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPublication extends AppCompatActivity {
    final String idpublicacion= "idpublicacion";
    final String nombre= "nombre";
    final String fecha="fecha";
    final String titulo= "titulo";
    final String idusuario= "idusuario";
    final String padre= "padre";
    final String foto= "foto";
    final String observaciones="observaciones";
    final String setimientos="sentimiento";
    final String analisis="analisis";
    final String evaluacion="evaluacion";
    final String conclusiones="conclusion";
    final String plan="planaccion";

    ArrayList listpublicaciones;
    Adapter_Pubication adapter;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publication);

        iniToolbar();

        iniView();

        initRecyclerView();




    }//EndCreate


    private void iniToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    private void iniView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listMyPublications();

    }
    private void initRecyclerView() {
        //swipeContainer = (SwipeRefreshLayout) findViewById(R.id.content_main);
        recyclerView = (RecyclerView)findViewById(R.id.rvMyPublication);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
       /* swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/
    }
    public void listMyPublications(){
    VariablesLogin vl= new VariablesLogin();
        final String id=vl.getIdusuario().toString();
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listxiduser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try{
                                listpublicaciones = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    //  System.out.println(jsonobject);
                                    VariablesLogin varllogin=new VariablesLogin();

                                    listpublicaciones.add(new com.dese.diario.Objects.Publication(
                                            jsonobject.getString(idpublicacion),
                                            jsonobject.getString(idusuario),
                                            jsonobject.getString(nombre),
                                            jsonobject.getString(foto),
                                            jsonobject.getString(fecha),
                                            jsonobject.getString(titulo),
                                            jsonobject.getString(observaciones),
                                            jsonobject.getString(setimientos),
                                            jsonobject.getString(evaluacion),
                                            jsonobject.getString(analisis),
                                            jsonobject.getString(conclusiones),
                                            jsonobject.getString(plan),
                                            jsonobject.getString(padre)));
                                    adapter=new Adapter_Pubication(listpublicaciones, MyPublication.this);
                                   // recyclerView.setAdapter(adapter);

                                  //  drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



                                }
                            }

                            catch (JSONException e){
                                Log.e(getString(R.string.UUUps), getString(R.string.Error)+e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getString(R.string.carita),getString(R.string.message_ocurrio_error));
            }
        }){
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(idusuario, id);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);

       // swipeContainer.setRefreshing(false);

    }// Fin conectionPublication

}
