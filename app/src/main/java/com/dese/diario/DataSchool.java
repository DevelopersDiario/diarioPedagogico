package com.dese.diario;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSchool extends AppCompatActivity implements View.OnClickListener {
    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;
    String school;
    //Toolbar
    Toolbar toolbar;
    Intent intent;
    Boolean homeButton = false, themeChanged;

    //Buttons
    EditText etInstituEdit, etGrupoEdit;
   Spinner spRolEdit;
    private  final String url =Urls.updateinfacademica;
    private String roleCode;

    //Keys
    final String KEY_USER="idusuario";
    final String KEY_SCHOOL="institucion";
    final String KEY_GROUP="grupo";
    final String KEY_ROL="idrol";

    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_school);

        //Hide softKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        inicializarToolbar();

        bindActivity();

        addItemSpinner();


    }//Fin onCreate

    public void setdata(){
        DatosUsr DU = new DatosUsr();
        etInstituEdit.setText(DU.getInstitucion().toString());
        etGrupoEdit.setText(DU.getGrupo().toString());
      //  spRolEdit.setId(Integer.parseInt(roleCode));
       //spRolEdit.setText(DU.getRol().toString());


    }//Fin setdata

    private void  updatedatos()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DataSchool.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                        openactivity();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                //get status code here
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");

                        Toast.makeText(DataSchool.this, body, Toast.LENGTH_LONG).show();


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USER,varlogin.getIdusuario() );
                params.put(KEY_SCHOOL,etInstituEdit.getText().toString() );
                params.put(KEY_GROUP, etGrupoEdit.getText().toString());
                params.put(KEY_ROL, roleCode );
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    } //fin updatedatos

    private void openactivity(){

        Intent intent =new Intent(DataSchool.this,DataSchool.class);
        startActivity(intent);
        finish();

    }//Fin openactivity

    private void addItemSpinner() {
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.staff , android.R.layout.simple_dropdown_item_1line);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRolEdit.setAdapter(spinner_adapter);

        selectedItemSpinner();

    }



    private  void selectedItemSpinner(){
        spRolEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                String tmp= (String) adapterView.getItemAtPosition(pos);
                if(tmp.equals("Asesor")) roleCode="1";
                else if(tmp.equals("Alumno")) roleCode="2";
                else if (tmp.equals("Invitado"))roleCode="3";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
    }


    private void bindActivity() {
        etInstituEdit = (EditText) findViewById(R.id.etInstituEdit);
        etGrupoEdit = (EditText) findViewById(R.id.etGrupoEdit);
        spRolEdit = (Spinner) findViewById(R.id.etRolEdit);
        spRolEdit.setEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setdata();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imAddSchool:
                break;


            }
        }
    /*--------------Theme Selected----------------*/
    private  void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarAcademic);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_information_academic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DataSchool.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }



    public void theme() {
        sharedPreferences = getSharedPreferences(KEY_VALUE, Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt(KEY_THEME, 0);
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
    public void onBackPressed() {
        intent = new Intent(this, Profile.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case android.R.id.home:
                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(DataSchool.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("DOWNLOAD", false);
                        editor.apply();
                    }
                    intent = new Intent(DataSchool.this, Profile.class);
                    startActivity(intent);
                    finish();
                    //
                }
                if (id == android.R.id.home) {

                }

                break;
            case R.id.action_edit:

                if(etInstituEdit.isEnabled()){
                    enableDates();


                }else  if(!etInstituEdit.isEnabled()){

                        disableDates();
                    Toast.makeText(this, R.string.message_disable, Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.action_saved:
                enableDates();
                try {
                    Toast.makeText(this, R.string.action_saved, Toast.LENGTH_SHORT).show();
                    updatedatos();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

        }

         return super.onOptionsItemSelected(item);

    }

    private void enableDates(){
        etInstituEdit.setEnabled(false);
        etGrupoEdit.setEnabled(false);
        spRolEdit.setEnabled(false);

        etInstituEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etGrupoEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));

    }
    private void disableDates(){
        etInstituEdit.setEnabled(true);
        etGrupoEdit.setEnabled(true);
        spRolEdit.setEnabled(true);
        etInstituEdit.setTextColor(getResources().getColor(R.color.md_black_1000));
        etGrupoEdit.setTextColor(getResources().getColor(R.color.md_black_1000));

    }

}
