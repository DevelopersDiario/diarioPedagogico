package com.dese.diario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Grupo;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Colaboration extends AppCompatActivity {
    final static String urlgpo= Urls.creategpo;
    final static String urlLisGpo= Urls.listgrupo;
    //Theme
    int theme;
    //Search
    Intent intent;
    Boolean homeButton = false, themeChanged;;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //Toolbar & Drwable
    Toolbar toolbar;
    ArrayList<String> al;

    //Search
    public ArrayList grupos = new ArrayList();
    FloatingActionButton fabAdd;
    //Gpo
    Intent grupo;
    ArrayList listGpo;
    public static final String KEY_NAMEG = "nombregrupo";
    public static final String KEY_IDU= "idusuario";
    public static final String KEY_NAMEU= "nombreusuario";
    public static final String KEY_IDG= "idgrupo";
    public static final String KEY_IDROL= "idrol"  ;

    final String KEY_VALUE = "VALUES";
    final String KEY_THEME="THEME";



    Adapter_Grupo adaptergpo;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    TextView tvNameGF, tvIdGF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        inicializarToolbar();
        bindActivity();

       listGpo();


    }
    private void bindActivity() {

        recyclerView = (RecyclerView)findViewById(R.id.recyclerGrupo);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                dialogGrup();

            }
        });




    }
    /*---------Navegatior Drawable--------------- */
    public void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFriends);
      setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
       Colaboration.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }
    /*------------Theme choose by user--------------------*/

    @Override
    public void onBackPressed() {
        intent = new Intent(Colaboration.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to th action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_view) {

            return true;
        }
        if (id == android.R.id.home) {
            if (!homeButton) {
                NavUtils.navigateUpFromSameTask(Colaboration.this);
            }
            if (homeButton) {

                if (!themeChanged) {
                    editor = sharedPreferences.edit();
                    editor.putBoolean("DOWNLOAD", false);
                    editor.apply();
                }
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



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



    private void dialogGrup() {
       final VariablesLogin varlogin =new VariablesLogin();
        LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = inflater.inflate(R.layout.dialog_grup, null);

        final EditText Gpoo = (EditText) dialoglayout.findViewById(R.id.etNameGroup);

        AlertDialog.Builder builder = new AlertDialog.Builder(Colaboration.this);
        builder
                .setCancelable(false)
                .setPositiveButton(R.string.btnAgree, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        String nombreg = Gpoo.getText().toString();
                        String usuario = varlogin.getIdusuario();
                        String nombreusuario= varlogin.getCuenta();


                        try {
                            registerGroup(nombreg, usuario, nombreusuario);

                            Intent r= new Intent(Colaboration.this, Colaboration.class);
                            startActivity(r);
                            finish();
                            listGpo();

                        } catch (JSONException e) {
                        }

                    }
                })

                .setNegativeButton(R.string.btnDisagree,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        builder.setView(dialoglayout);
        builder.show();
    }
    private void registerGroup(final String n, final String i,final String nu) throws JSONException{
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgpo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Colaboration.this, R.string.message_succes_group, Toast.LENGTH_LONG).show();
                        Intent i= new Intent(Colaboration.this, Colaboration.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");

                        Toast.makeText(Colaboration.this, body, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("Response:",e.toString());
                    }
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDU, i);
                params.put(KEY_NAMEG, n);
                params.put(KEY_NAMEU, nu);
                params.put(KEY_IDROL, "1");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }//Fin RegisterUser

    public void listGpo() {

        RequestQueue queue = Volley.newRequestQueue(this);
      //  grupo = new Intent(this, ListGroup.class);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLisGpo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                listGpo = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    //  System.out.println(jsonobject);

                                    listGpo.add(new com.dese.diario.Objects.Group(
                                            jsonobject.getString(KEY_NAMEG),
                                            jsonobject.getString(KEY_NAMEU),
                                            jsonobject.getString(KEY_IDG),
                                            jsonobject.getString(KEY_IDROL)));
                                    adaptergpo = new Adapter_Grupo(listGpo, Colaboration.this);

                                    recyclerView.setAdapter(adaptergpo);

                                }
                            } catch (JSONException e) {
                                Log.e("Oops!", "Fatal Errro!" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("°_°", "Ocurio un error !");
            }
        });


        queue.add(stringRequest);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView tv1=(TextView) findViewById(R.id.tvIdG);
        TextView tv2=(TextView) findViewById(R.id.NombreG);

        switch (item.getTitle().toString()){
            case "Agregar Miembro":

               /* String gpo= tv1.getText().toString();
                String namegpo= tv2.getText().toString();

                Intent sf= new Intent(Colaboration.this, Search_friends.class);
                sf.putExtra("namegpo", namegpo);
                sf.putExtra("gpo", gpo);
                startActivity(sf);*/
                break;
            default:
                item.getItemId();
                break;
        }

        return super.onContextItemSelected(item);
    }

}

