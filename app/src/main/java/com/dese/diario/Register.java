package com.dese.diario;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
import org.json.JSONException;

public class Register extends AppCompatActivity implements DatePickerListener,  View.OnClickListener{
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    //KEYS
    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";

    //Toolbar
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    Intent intent;
    Boolean homeButton = false, themeChanged;

    //EditText
    EditText  etTitle, etGrupo, etDescripcion, etSenimientos, etEvaluacion, etAnalisis, etConclusion, etPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        initLitener();


    }

    private void initLitener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker
                .setListener(Register.this)
                .init();
        picker.setDate(new DateTime().plusDays(4));


    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Register.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }

        etTitle=(EditText) this.findViewById(R.id.etTitle);
        etGrupo= (EditText) this.findViewById(R.id.etGrupo);
        etDescripcion = (EditText) this.findViewById(R.id.etDescripcion);
        etEvaluacion= (EditText) this.findViewById(R.id.etEvaluacion);
        etConclusion= (EditText) this.findViewById(R.id.etConclusión);
        etPlan=(EditText) this.findViewById(R.id.etPlan);

       // etTitle.setOnClickListener(this);
       // etGrupo.setOnClickListener(this);
        etDescripcion.setOnClickListener(this);
        etSenimientos.setOnClickListener(this);
        etEvaluacion.setOnClickListener(this);
        etAnalisis.setOnClickListener(this);
        etConclusion.setOnClickListener(this);
        etPlan.setOnClickListener(this);

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
        Log.i("HorizontalPicker","Fecha seleccionada="+dateSelected.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            back_pressed = System.currentTimeMillis();
            intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            final AlertDialog alert = new AlertDialog.Builder(this).create();

            alert.setMessage(getResources().getString(R.string.message_whis_saved));
            alert.setButton(Dialog.BUTTON_POSITIVE,("Cancelar"),new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //   Toast.makeText(Publication.this, R.string.salvar, Toast.LENGTH_SHORT).show();
                }
            });
            alert.setButton(Dialog.BUTTON_NEGATIVE, ("Descartar"), new DialogInterface.OnClickListener() {
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

            case R.id.action_post:

                break;
            case R.id.action_saved:


               // failed_regpublication.setText("");
                break;

            case android.R.id.home:

                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(Register.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.download), false);
                        editor.apply();
                    }
                    intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    return true;




                }

        }

        return super.onOptionsItemSelected(item);
    }
    /*------------Theme choose by user--------------------*/
    private void theme() {
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
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.etDescripcion:

                //Hide softKeyboard
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Toast.makeText(this, "Descripcion", Toast.LENGTH_LONG).show();
                Intent desc= new Intent(Register.this, Publication.class);
                startActivity(desc);
                break;

            case R.id.etSentimientos:
                Toast.makeText(this, "Setnimientos", Toast.LENGTH_LONG).show();

                break;
            case R.id.etEvaluacion:
                Toast.makeText(this, "Evaluación", Toast.LENGTH_LONG).show();

                break;
            case R.id.etAnalisis:
                Toast.makeText(this, "Analisis", Toast.LENGTH_LONG).show();


                break;

            case R.id.etConclusión:
                Toast.makeText(this, "Conclusión", Toast.LENGTH_LONG).show();

                break;

            case R.id.etPlan:
                Toast.makeText(this, "Plan de acción", Toast.LENGTH_LONG).show();

                break;
        }
    }
}
