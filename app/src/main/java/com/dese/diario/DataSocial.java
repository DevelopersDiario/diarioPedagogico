package com.dese.diario;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.ShowProgressDialog;
import com.dese.diario.Utils.Urls;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DataSocial extends AppCompatActivity implements View.OnClickListener{
    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;
    //Toolbar
    Toolbar toolbar;
    Boolean closeButton = false;
    Intent intent;
    Boolean homeButton = false, themeChanged;

    EditText etNameUserEdit, etEmailEdit,etPasswordEdit, etDateVigenciaEdit;
    Button etViewFriends;
    private final String url= Urls.updateinfsocial;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";
    final String KEY_USER="idusuario";
    final String KEY_ACCOUNT="cuenta";
    final String KEY_EMAIL="correo";
    final String KEY_VALIDATY="vigencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_social);
        etNameUserEdit= (EditText)findViewById(R.id.etNameUserEdit);
        etEmailEdit= (EditText)findViewById(R.id.etEmailEdit);
        etDateVigenciaEdit= (EditText)findViewById(R.id.etDateVigenciaEdit);
        etViewFriends= (Button) findViewById(R.id.etViewFriends);



        inicializarToolbar();

        bindActivity();

        setCurrentDateOnView();

        addListenerOnButton();

    } //fin Oncreate






    @Override
    protected void onStart() {
        super.onStart();
        setdata();
    }


    public void setdata(){
        DatosUsr DU = new DatosUsr();
        etNameUserEdit.setText(DU.getCuenta().toString());
        etEmailEdit.setText(DU.getCorreo().toString());
        etDateVigenciaEdit.setText(DU.getVigencia().toString());



    }//Fin setdata

    private void  updatedatos()  throws JSONException {

        final VariablesLogin varlogin =new VariablesLogin();

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DataSocial.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
                        openactivity();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                //get status code here
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");


                        Toast.makeText(DataSocial.this, body, Toast.LENGTH_LONG).show();


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
                params.put(KEY_ACCOUNT,etNameUserEdit.getText().toString() );
                params.put(KEY_EMAIL, etEmailEdit.getText().toString());
                params.put(KEY_VALIDATY, etDateVigenciaEdit.getText().toString());
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    } //fin updatedatos

    private void openactivity(){
        Intent intent =new Intent(DataSocial.this,Profile.class);
        startActivity(intent);
        finish();
    }//Fin

    // Display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

    }

    public void addListenerOnButton() {

        etDateVigenciaEdit =(EditText) findViewById(R.id.etDateVigenciaEdit);
        etDateVigenciaEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = 1+selectedMonth;
            day = selectedDay;

            etDateVigenciaEdit.setText(new StringBuilder().append(year)
                    .append("-").append(month).append("-").append(day)
                    .append(" "));


        }
    };

    private void bindActivity() {
        //Hide softKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etNameUserEdit= (EditText) findViewById(R.id.etNameUserEdit);
        etEmailEdit = (EditText)findViewById(R.id.etEmailEdit);
        etDateVigenciaEdit = (EditText) findViewById(R.id.etDateVigenciaEdit);
        etDateVigenciaEdit.setOnClickListener(this);
        etDateVigenciaEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etViewFriends.setOnClickListener(this);
    }


    private  void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSocial);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_information_social);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DataSocial.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }

     /*--------------Theme Selected----------------*/


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

        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(DataSocial.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("DOWNLOAD", false);
                        editor.apply();
                    }
                    intent = new Intent(DataSocial.this, Profile.class);
                    startActivity(intent);
                    finish();
                    //
                }
                if (id == android.R.id.home) {

                }

                break;
            case R.id.action_edit:
                if(etNameUserEdit.isEnabled()) {
                    enableDates();

                }else  if(!etNameUserEdit.isEnabled()){

                   disableDates();
                    Toast.makeText(this, R.string.message_disable, Toast.LENGTH_SHORT).show();

                }
                break;
            case  R.id.action_saved:

                try {
                   // Toast.makeText(this, R.string.action_saved, Toast.LENGTH_SHORT).show();
                    new ShowProgressDialog().MaterialDialogMsj(DataSocial.this, false, "Actualizando");
                    updatedatos();
                    enableDates();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void enableDates(){
        etNameUserEdit.setEnabled(false);
        etEmailEdit.setEnabled(false);
        etDateVigenciaEdit.setEnabled(false);

        etNameUserEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etEmailEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etDateVigenciaEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));

    }
    private  void disableDates(){
        etNameUserEdit.setEnabled(true);
        etEmailEdit.setEnabled(true);
        etDateVigenciaEdit.setEnabled(true);

        etNameUserEdit.setTextColor(getResources().getColor(R.color.md_black_1000));
        etEmailEdit.setTextColor(getResources().getColor(R.color.md_black_1000));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.etDateVigenciaEdit:
                setCurrentDateOnView();
                break;
            case R.id.etViewFriends:
                Intent t= new Intent(this, MyPublication.class);
                startActivity(t);
                break;


        }
    }


}
