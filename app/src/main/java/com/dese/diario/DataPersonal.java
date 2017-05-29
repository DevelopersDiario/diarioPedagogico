package com.dese.diario;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
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
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import info.hoang8f.android.segmented.SegmentedGroup;


public class DataPersonal extends AppCompatActivity implements View.OnClickListener {

    private final String url= Urls.updateinfPersonal;

    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;

    //Toolbar
    Toolbar toolbar;
    Intent intent;
    Boolean homeButton = false, themeChanged;

    //Buttons
    ImageView imEditPersonalInf;
    private   TextView imPersonal;
    private EditText etName, etLastName, etPhone, etDateBithdayEdit;
    private RadioButton rdMatch, rdFemale, rdMale;
    private SegmentedGroup sgGroupMatch;
    String rdResult = " ";
    FloatingActionButton fabPersonal;

    //Date
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;
    Date d;
    static final int DATE_DIALOG_ID = 999;

    //KEY
    final  String KEY_IDUSUARIO="idusuario";
    final  String KEY_NAME="nombre";
    final String  KEY_LASTNAME="apellidos";
    final String  KEY_PHONE="telefono";
    final String  KEY_MATCH="genero";
    final String  KEY_DATE="fnacimiento";

    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_personal);

        d = new Date();

        inicializarToolbar();
        bindActivity();

        setCurrentDateOnView();
        addListenerOnButton();


    }  //Fin onCreate

    @Override
    protected void onStart() {
        super.onStart();
        setdata();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setdata();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setdata(){

        DatosUsr DU = new DatosUsr();
        try{
            etName.setText(DU.getNombre().toString());
            etLastName.setText(DU.getApellidos().toString());
            etPhone.setText(DU.getTelefono().toString());
            etDateBithdayEdit.setText(DU.getFnacimiento().toString());
            String genero =DU.getGenero().toString();
            getMatch(genero);
        }catch(NullPointerException e){

        }

    }//Fin setdata


    private void getMatch(String genero) {
         if(genero.equals("Masculino")) {
             sgGroupMatch.check(R.id.rdMale);
         }
        else if(genero.equals("Femenino")) {

             sgGroupMatch.check(R.id.rdFemale);
         }
    }

    private void  updatedatos()  throws JSONException{

        final VariablesLogin varlogin =new VariablesLogin();
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DataPersonal.this, R.string.message_succes_information, Toast.LENGTH_LONG).show();
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

                        Toast.makeText(DataPersonal.this, body, Toast.LENGTH_LONG).show();


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDUSUARIO,varlogin.getIdusuario() );
                params.put(KEY_NAME,etName.getText().toString() );
                params.put(KEY_LASTNAME, etLastName.getText().toString());
                params.put(KEY_PHONE, etPhone.getText().toString());
                params.put(KEY_MATCH, rdResult.toString());
                params.put(KEY_DATE, etDateBithdayEdit.getText().toString());
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    } //fin updatedatos

    private void openactivity(){
        Intent intent =new Intent(DataPersonal.this,Profile.class);
        startActivity(intent);
        finish();
    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public void addListenerOnButton() {

        etDateBithdayEdit =(EditText) findViewById(R.id.etDateBithdayEdit);
        etDateBithdayEdit.setOnClickListener(new View.OnClickListener() {

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
            month = selectedMonth;
            day = selectedDay;
            etDateBithdayEdit.setText(new StringBuilder().append(year)
                    .append("-").append(month).append("-").append(day)
                    .append(" "));

        }
    };
    private void bindActivity() {

        etName = (EditText) findViewById(R.id.etNameEdit);
        etLastName = (EditText) findViewById(R.id.etLastNameEdit);
        etPhone = (EditText) findViewById(R.id.etLPhoneEdit);
        rdMale = (RadioButton) findViewById(R.id.rdMale);
        rdFemale = (RadioButton) findViewById(R.id.rdFemale);
        etDateBithdayEdit = (EditText) findViewById(R.id.etDateBithdayEdit);
        sgGroupMatch =(SegmentedGroup) findViewById(R.id.sgGroupMatch);

        etDateBithdayEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etDateBithdayEdit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.etDateBithdayEdit:
                setCurrentDateOnView();
                break;

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarData);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_information_private);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DataPersonal.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_edit:
                if(etName.isEnabled()&&etLastName.isEnabled()&&  etPhone.isEnabled()&&etDateBithdayEdit.isEnabled()) {
                    enableDates();

                }
                else if(!etName.isEnabled()&& !etLastName.isEnabled()&& !etPhone.isEnabled() && !etDateBithdayEdit.isEnabled()){
                    disableDates();
                    Toast.makeText(this, R.string.message_disable, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.action_saved:

                enableDates();
                try {
                    getRadioButtonesDate();
                    Toast.makeText(this, R.string.action_saved, Toast.LENGTH_SHORT).show();


                    updatedatos();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case  android.R.id.home:
                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(DataPersonal.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("DOWNLOAD", false);
                        editor.apply();
                    }
                    intent = new Intent(DataPersonal.this, Profile.class);
                    startActivity(intent);
                    finish();
                    //
                }
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private  void enableDates(){
        etName.setEnabled(false);

        etLastName.setEnabled(false);
        etPhone.setEnabled(false);
        etDateBithdayEdit.setEnabled(false);
        rdMale.setEnabled(false);
        rdFemale.setEnabled(false);

        etName.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etPhone.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etLastName.setTextColor(getResources().getColor(R.color.md_grey_500_50));
        etDateBithdayEdit.setTextColor(getResources().getColor(R.color.md_grey_500_50));

    }
    private  void disableDates(){
        etName.setEnabled(true);
        etName.requestFocus();
        etName.setCursorVisible(true);
        etLastName.setEnabled(true);
        etPhone.setEnabled(true);
        etDateBithdayEdit.setEnabled(true);
        rdMale.setEnabled(true);
        rdFemale.setEnabled(true);

        etName.setTextColor(getResources().getColor(R.color.md_black_1000));
        etPhone.setTextColor(getResources().getColor(R.color.md_black_1000));
        etLastName.setTextColor(getResources().getColor(R.color.md_black_1000));
        etDateBithdayEdit.setTextColor(getResources().getColor(R.color.md_black_1000));

    }

    private void getRadioButtonesDate(){

        if(rdMale.isChecked())
            rdResult= rdMale.getText().toString();
        else  if (rdFemale.isChecked())
            rdResult= rdFemale.getText().toString();

    }



}
