package com.dese.diario;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_File;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.Urls;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailMyPublication extends AppCompatActivity {
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    //Toolbar
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    Intent intent;
    Boolean homeButton = false, themeChanged;


    String t,u,d,p,f,sen, eva, ana, con, plan,  pa, idepublicacion;
    EditText etTitleMyPubE, etGrupoMyPubE, etDescriptingMyPubE,
            etFeelingsMyPubE, etTestingMyPubE, etAnalyzeMyPubE, etConclusionMyPubE, etPlanMyPubE;



    //Files
    private ArrayList<String> filename = new ArrayList<>();
    private RecyclerView rcItems;
    private Adapter_File ia;
    private LinearLayout lyContentImagenDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_my_publication);



        initView();

        getParams();


    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DetailMyPublication.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateMyPublication();
                   // updateSenMyPublication();
                    //updateEvaMyPublication();
                    //updateAnaMyPublication();
                    //updateConMyPublication();
                    //updatePlanMyPublication();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        etTitleMyPubE =(EditText)findViewById(R.id.etTitleMyPubE);
        etGrupoMyPubE =(EditText)findViewById(R.id.etGrupoMyPubE);
        etDescriptingMyPubE= (EditText) findViewById(R.id.etDescriptingMyPubE);
        etFeelingsMyPubE=(EditText)findViewById(R.id.etFeelingsMyPubE);
        etTestingMyPubE= (EditText)findViewById(R.id.etTestingMyPubE);
        etAnalyzeMyPubE =(EditText)findViewById(R.id.etAnalyzeMyPubE);
        etConclusionMyPubE= (EditText)findViewById(R.id.etConclusionMyPubE);
        etPlanMyPubE= (EditText)findViewById(R.id.etPlanMyPubE);

        rcItems = (RecyclerView) findViewById(R.id.rvItemMyDetailPublicacion);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(4,1);
        lyContentImagenDetail= (LinearLayout) findViewById(R.id.lyContentImagenDetail);
    }

    public void getParams() {
        Intent i = this.getIntent();
        idepublicacion = i.getExtras().getString("_IDE_KEY");
        t = i.getExtras().getString("TITLE_KEY");
        u = i.getExtras().getString("USER_KEY");
        d = i.getExtras().getString("DATA_KEY");
        p = i.getExtras().getString("PUBLI_KEY");
        f = i.getExtras().getString("FOTO_KEY");
        pa = i.getExtras().getString("PAPA");

        sen = i.getExtras().getString("SEN_KEY");
        eva = i.getExtras().getString("TES_KEY");
        ana = i.getExtras().getString("ANA_KEY");
        con = i.getExtras().getString("CON_KEY");
        plan = i.getExtras().getString("PLAN_KEY");

        //listarFile(idepublicacion);

        etTitleMyPubE.setText(t);
        etDescriptingMyPubE.setText(p);
        etFeelingsMyPubE.setText(sen);
        etTestingMyPubE.setText(eva);
        etAnalyzeMyPubE.setText(ana);
        etConclusionMyPubE.setText(con);
        etPlanMyPubE.setText(plan);
    }
    private void  updateMyPublication()  throws JSONException {
        if(!etTitleMyPubE.getText().toString().isEmpty()){
            final VariablesLogin varlogin =new VariablesLogin();
            final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updateMyPublication,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(DetailMyPublication.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                            openactivity();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                        // HTTP Status Code: 401 Unauthorized
                        if (error.networkResponse.data != null) {

                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                                new MaterialDialog.Builder(DetailMyPublication.this)
                                        .content(body)
                                        .show();

                                // Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            switch(response.statusCode){
                                case 403:
                                    new MaterialDialog.Builder(DetailMyPublication.this)
                                            .content(response.statusCode)
                                            .show();
                                    break;
                            }
                        }
                    }
                    /**/

                }


            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("idpublicacion",idepublicacion );
                    params.put("titulo", etTitleMyPubE.getText().toString());
                    params.put("observaciones", etDescriptingMyPubE.getText().toString());
                    params.put("sentimiento",etFeelingsMyPubE.getText().toString() );
                    params.put("evaluacion",etTestingMyPubE.getText().toString() );
                    params.put("analisis",etAnalyzeMyPubE.getText().toString() );
                    params.put("conclusion",etConclusionMyPubE.getText().toString() );
                    params.put("planaccion",etPlanMyPubE.getText().toString() );
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }else{
            Toast.makeText(DetailMyPublication.this, "Necesita agregar un título", Toast.LENGTH_LONG).show();
        }





    } //fin updatedatos


    public  void listarFile( final String ide){

        RequestQueue queue = Volley.newRequestQueue(DetailMyPublication.this);

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

                                        ia = new Adapter_File(filename, DetailMyPublication.this);
                                        rcItems.setAdapter(ia);

                                        //rcItems.setItemAnimator(new DefaultItemAnimator());
                                        //rcItems.setLayoutManager(new LinearLayoutManager(DetailPublication.this));

                                        lyContentImagenDetail.setVisibility(View.VISIBLE);
                                        rcItems.setItemAnimator(new DefaultItemAnimator());
                                        rcItems.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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



    private void openactivity() {
        Intent idp= new Intent(DetailMyPublication.this, MyPublication.class);
        startActivity(idp);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            back_pressed = System.currentTimeMillis();
            intent = new Intent(DetailMyPublication.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            final AlertDialog alert = new AlertDialog.Builder(this).create();

            alert.setMessage("Desea salir sin guardar cambios");
            alert.setButton(Dialog.BUTTON_POSITIVE,("Guardar"),new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        updateMyPublication();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            alert.setButton(Dialog.BUTTON_NEGATIVE, ("Salir"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alert.show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {



            case android.R.id.home:
                final Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

                    TaskStackBuilder.create(this)

                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {


                    if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                        back_pressed = System.currentTimeMillis();
                        intent = new Intent(DetailMyPublication.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        final AlertDialog alert = new AlertDialog.Builder(this).create();

                        alert.setMessage("Desea salir sin guardar cambios");
                        alert.setButton(Dialog.BUTTON_POSITIVE,("Cancelar"),new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.setButton(Dialog.BUTTON_NEGATIVE, ("Salir"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                                NavUtils.navigateUpTo(DetailMyPublication.this, upIntent);

                            }
                        });

                        alert.show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
