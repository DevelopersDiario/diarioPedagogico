package com.dese.diario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.Constants;
import com.dese.diario.Utils.Urls;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DetailMyPublication extends AppCompatActivity {
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    String t,u,d,p,f,sen, eva, ana, con, plan,  pa, idepublicacion;
    EditText etTitleMyPubE, etGrupoMyPubE, etDescriptingMyPubE,
            etFeelingsMyPubE, etTestingMyPubE, etAnalyzeMyPubE, etConclusionMyPubE, etPlanMyPubE;
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    updateSenMyPublication();
                    updateEvaMyPublication();
                    updateAnaMyPublication();
                    updateConMyPublication();
                    updatePlanMyPublication();

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

        etTitleMyPubE.setText(t);
        etDescriptingMyPubE.setText(p);
        etFeelingsMyPubE.setText(sen);
        etTestingMyPubE.setText(eva);
        etAnalyzeMyPubE.setText(ana);
        etConclusionMyPubE.setText(con);
        etPlanMyPubE.setText(plan);
    }



    private void  updateSenMyPublication()  throws JSONException {

            final VariablesLogin varlogin =new VariablesLogin();
            final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updateSentimientos,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(DetailMyPublication.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                        //    openactivity();

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
                            new MaterialDialog.Builder(DetailMyPublication.this)
                                    .content(body)
                                    .show();


                            // Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("idpublicacion",idepublicacion );
                    params.put("sentimiento",etFeelingsMyPubE.getText().toString() );
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);




    } //fin updatedatos

    private void  updateEvaMyPublication()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updateEvaluacion,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailMyPublication.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                      //  openactivity();

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
                        new MaterialDialog.Builder(DetailMyPublication.this)
                                .content(body)
                                .show();


                        // Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpublicacion",idepublicacion );
                params.put("sentimiento",etTestingMyPubE.getText().toString() );
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    } //fin updatedatos
    private void  updateAnaMyPublication()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updateAnalisis,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailMyPublication.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                      //  openactivity();

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
                        new MaterialDialog.Builder(DetailMyPublication.this)
                                .content(body)
                                .show();


                        // Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpublicacion",idepublicacion );
                params.put("evaluacion",etAnalyzeMyPubE.getText().toString() );
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    } //fin updatedatos
    private void  updateConMyPublication()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updateConclusion,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailMyPublication.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                      //  openactivity();

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
                        new MaterialDialog.Builder(DetailMyPublication.this)
                                .content(body)
                                .show();


                        // Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpublicacion",idepublicacion );
                params.put("sentimiento",etConclusionMyPubE.getText().toString() );
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    } //fin updatedatos

    private void  updatePlanMyPublication()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, Urls.updatePlan,
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
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idpublicacion",idepublicacion );
                params.put("sentimiento",etPlanMyPubE.getText().toString() );
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    } //fin updatedatos


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
}
