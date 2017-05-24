package com.dese.diario.Objects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dese.diario.DataSchool;
import com.dese.diario.MainActivity;
import com.dese.diario.Profile;
import com.dese.diario.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class DetailPublication extends AppCompatActivity {
    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;
    Toolbar toolbar;
    Intent intent;
    Boolean homeButton = false, themeChanged;
    TextView tvTitlePubDetail, tvUserPubDetail, tvDatePubDetail, tvPubDetail;
    ImageView foto;
    String t,u,d,p,f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_publication);


        bindData();
        inicializarToolbar();
    }

    private void bindData() {

        tvTitlePubDetail = (TextView) findViewById(R.id.tvTitlePubDetail);
        tvUserPubDetail=(TextView) findViewById(R.id.tvUserPubDetail);
        tvDatePubDetail =(TextView) findViewById(R.id.tvDatePubDetail);
        tvPubDetail=(TextView) findViewById(R.id.tvPubDetail);
        foto=(ImageView) findViewById(R.id.imProfilPubDetail);

        Intent  i=this.getIntent();
        t=i.getExtras().getString("TITLE_KEY");
        u=i.getExtras().getString("USER_KEY");
        d=i.getExtras().getString("DATA_KEY");
         p=i.getExtras().getString("PUBLI_KEY");
        f=i.getExtras().getString("FOTO_KEY");


        tvTitlePubDetail.setText(t);
        tvUserPubDetail.setText(u);
        tvDatePubDetail.setText(d);
        tvPubDetail.setText(p);
        Picasso.with(DetailPublication.this)
                .load(Urls.fotouser+f)
                .resize(50, 50)
                .centerCrop()
                .into(foto);

    }


    private  void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDP);
        setSupportActionBar(toolbar);
       toolbar.setTitle(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        DetailPublication.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }



    public void theme() {
        sharedPreferences = getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt("THEME", 0);
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
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case android.R.id.home:
                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(DetailPublication.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("DOWNLOAD", false);
                        editor.apply();
                    }

                    //
                }
                if (id == android.R.id.home) {

                }

                break;


        }

        return super.onOptionsItemSelected(item);

    }

}
