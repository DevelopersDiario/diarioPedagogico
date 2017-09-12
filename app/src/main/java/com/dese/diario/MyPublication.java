package com.dese.diario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_File;
import com.dese.diario.Adapter.Adapter_MyPubication;
import com.dese.diario.Adapter.Adapter_Pubication;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.Utils.Constants;
import com.dese.diario.Utils.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.facebook.login.LoginManager;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPublication extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener {
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;


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
    Adapter_MyPubication adapter;

    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    LinearLayoutManager linearLayoutManager;

    CircleImageView imProfileMyPub;
    TextView tvUserMyPub;



    //Files
    private ArrayList<String> filename = new ArrayList<>();
    private RecyclerView rcItems;
    private Adapter_File ia;
    private LinearLayout lyContentImagenDetail;
    String idepublicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publication);

        iniToolbar();

        iniView();

        initRecyclerView();




    }//EndCreate


    private void iniToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TypedValue typedValueColorPrimaryDark = new TypedValue();
        MyPublication.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }


    private void iniView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Se cargo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        listMyPublications();
        //listarFile(idepublicacion);

        tvUserMyPub= (TextView) findViewById(R.id.tvUserMyPub);
       imProfileMyPub= (CircleImageView)findViewById(R.id.imProfileMyPub);

        //tvUserMyPub.setText(listpublicaciones.get(2).toString());


    }
    private void initRecyclerView() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swlMyPublication);
        recyclerView = (RecyclerView)findViewById(R.id.rvMyPublication);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listMyPublications();
            }
        }, 1000);
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

                                    listpublicaciones.add(new com.dese.diario.Objects.MyPublication(
                                            jsonobject.getString(idpublicacion),
                                            jsonobject.getString(idusuario),
                                            jsonobject.getString(nombre),
                                            jsonobject.getString(foto),
                                            jsonobject.getString(fecha),
                                            jsonobject.getString(titulo),
                                            jsonobject.getString("cuenta"),
                                            jsonobject.getString(observaciones),
                                            jsonobject.getString(setimientos),
                                            jsonobject.getString(evaluacion),
                                            jsonobject.getString(analisis),
                                            jsonobject.getString(conclusiones),
                                            jsonobject.getString(plan),
                                            jsonobject.getString(padre)));
                                    adapter=new Adapter_MyPubication(listpublicaciones, MyPublication.this);
                                    recyclerView.setAdapter(adapter);
                                    tvUserMyPub.setText(jsonobject.getString(nombre));
                                    idepublicacion= jsonobject.getString(idpublicacion);


                                    Picasso.with(MyPublication.this)
                                            .load(Urls.download+ jsonobject.getString(foto))
                                            .resize(200, 200)
                                            .centerCrop()
                                            .into( imProfileMyPub);

                                   // listarFile(idepublicacion);
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


    public  void listarFile( final String ide){

        RequestQueue queue = Volley.newRequestQueue(MyPublication.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.obtenerdetallepublicacion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                // paths = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String file=jsonobject.getString("descripcion");
                                    filename.add(file);
                                    if(file!=" "){

                                        ia = new Adapter_File(filename, MyPublication.this);
                                        rcItems.setAdapter(ia);

                                        //rcItems.setItemAnimator(new DefaultItemAnimator());
                                        //rcItems.setLayoutManager(new LinearLayoutManager(DetailPublication.this));

                                        lyContentImagenDetail.setVisibility(View.VISIBLE);
                                       // rcItems.setItemAnimator(new DefaultItemAnimator());
                                        //rcItems.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                        // System.out.println(paths);
                                    }

                                }
                            } catch (JSONException e) {
                                Log.e("Detail Publicacion", "Problema con" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Horror", "Response--->"+error);
            }

        }

        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idpublicacion", ide);
                //headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);
    }




    /*------------Theme choose by user--------------------*/
    private void theme() {
        sharedPreferences = getSharedPreferences(getString(R.string.values), Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt(getString(R.string.theme), 0);
        settingTheme(theme);
    }
    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme11);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                break;
            case 11:
                setTheme(R.style.AppTheme);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
}
