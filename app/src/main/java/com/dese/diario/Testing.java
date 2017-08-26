package com.dese.diario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

public class Testing extends AppCompatActivity {
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    //KEYS
    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";

    //Bind
    EditText etTestingM;

    //Intents State
    String D, S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        initView();

        initListener();



    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Testing.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }

        etTestingM= (EditText)findViewById(R.id.etTestingM);

        Bundle getD = getIntent().getExtras();

        if (getD != null)
            D=(getD.getString("Descripcion"));
            S=(getD.getString("Sentimientos"));


    }

    private void initListener() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textMore;
                Intent t = new Intent(Testing.this, Register.class);


                textMore = etTestingM.getText().toString();
                t.putExtra("Evaluacion", textMore);
                t.putExtra("Descripcion", D);
                t.putExtra("Sentimientos", S);
                startActivity(t);
                finish();
            }
        });

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



}