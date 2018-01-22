package com.dese.diario;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Settings extends AppCompatActivity implements  View.OnClickListener{

    Toolbar toolbar;

    final Context context = this;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    Intent intent;
    RelativeLayout relativeLayoutChooseTheme, getRelativeLayoutChooseAccount;
    LinearLayout linearLayoutMain, linearLayoutSecond, linearLayoutSettings;
    Dialog dialog;
    Boolean homeButton = false, themeChanged;
//    TextView tvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Inicializar ToolBar
        //    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);


        inicializarToolbar();
        settingsButtons();

        themeChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void settingsButtons() {
        relativeLayoutChooseTheme = (RelativeLayout) findViewById(R.id.relativeLayoutChooseTheme);
        relativeLayoutChooseTheme.setOnClickListener(this);

        getRelativeLayoutChooseAccount = (RelativeLayout)findViewById(R.id.relativeLayoutChooseAccount);
        getRelativeLayoutChooseAccount.setOnClickListener(this);

        //tvVersionName= (TextView) findViewById(R.id.tvVersionName);


    }


    public void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Settings.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //n
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == android.R.id.home) {
            if (!homeButton) {
                NavUtils.navigateUpFromSameTask(Settings.this);
            }
            if (homeButton) {
                if (!themeChanged) {
                    editor = sharedPreferences.edit();
                    editor.putBoolean("DOWNLOAD", false);
                    editor.apply();
                }
                intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
                finish();
                //
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
        finish();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relativeLayoutChooseTheme:

                Intent ColorChoose= new Intent(Settings.this, ChooseColor.class);
                startActivity(ColorChoose);
                break;
            case R.id.relativeLayoutChooseAccount:

                //  finishSession();
                startActivity(new Intent(getBaseContext(), SelectAccount.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();

                break;
        }
    }
    private void finishSession() {
        SharedPreferences preferences =
                getSharedPreferences("session_end", MODE_PRIVATE);

        preferences.edit()
                .putBoolean("exit_all",true).apply();

        Intent SC = new Intent(this, SelectAccount.class);
        startActivity(SC);

        finish();
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();

    }

    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt("THEME", 0);
        settingTheme(theme);
    }
    private void themeChanged() {
        themeChanged = sharedPreferences.getBoolean("THEMECHANGED",false);
        homeButton = true;
    }
    public void setThemeFragment(int theme) {
        switch (theme) {
            case 1:

                editor = sharedPreferences.edit();
                editor.putInt("THEME", 1).apply();
                break;
            case 2:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 2).apply();
                break;
            case 3:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 3).apply();
                break;
            case 4:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 4).apply();
                break;
            case 5:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 5).apply();
                break;
            case 6:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 6).apply();
                break;
            case 7:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 7).apply();
                break;
            case 8:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 8).apply();
                break;
            case 9:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 9).apply();
                break;
            case 10:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 10).apply();
                break;
            case 11:
                editor = sharedPreferences.edit();
                editor.putInt("THEME", 11).apply();
                break;

        }
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
