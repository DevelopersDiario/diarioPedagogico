package com.dese.diario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_File;
import com.dese.diario.Adapter.Adapter_RePubication;
import com.dese.diario.Adapter.ListRepublication;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.ExportPDF;
import com.dese.diario.Utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailPublication extends AppCompatActivity implements  View.OnClickListener{
    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;
    Toolbar toolbar;
    Intent intent;
    Boolean homeButton = false, themeChanged;
    TextView tvTitlePubDetail, tvUserPubDetail, tvDatePubDetail, tvPubDetail;
    ImageView foto;
    String t,u,d,p,f,sen, eva, ana, con, plan,  pa, idepublicacion;
    ImageView imvDowloandPDF;

    public ImageView cvFeels, cvTest, cvAnalyze, cvConclusion, cvPlan;

    FloatingActionButton btnPost;


    ArrayList listRepublicaciones;
    Adapter_RePubication adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout lyContentImagenDetail;
    String ed;

    TableLayout tlMainSelectDetail;

    //repub
    final static String url= Urls.repuplication;

    final String KEY_IDUSUARIO = "idusuario";
    final String KEY_TITULO = "titulo";
    final String KEY_OBSERVACIONES = "observaciones";
    final String KEY_IDGROUP = "idgrupo";
    final String KEY_ROL = "idrol";
    final String KEY_IDPUBLICACION = "idpublicacion";
    final String CONTENT_TYPE = "Content-Type";
    final String APPLICATION = "application/x-www-form-urlencoded";


    final static String urlListRepublication = Urls.listarrepublication;

    //
    final static String getFilesList= Urls.obtenerdetallepublicacion;
    ArrayList<String> filename = new ArrayList<>();
    ArrayList<String> paths = new ArrayList<>();
    private RecyclerView rcItems;
    Adapter_File ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_publication);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerRepost);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        bindData();
        inicializarToolbar();
    }

    private void bindData() {
        final ListRepublication lp =new ListRepublication();
        btnPost= (FloatingActionButton) findViewById(R.id.btnRep);


        rcItems = (RecyclerView) findViewById(R.id.rvItemDetailPublicacion);
       StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(4,1);
      // rcItems.setLayoutManager(staggeredGridLayout);


        lyContentImagenDetail= (LinearLayout) findViewById(R.id.lyContentImagenDetail);
        tvTitlePubDetail = (TextView) findViewById(R.id.tvTitlePubDetail);
        tvUserPubDetail=(TextView) findViewById(R.id.tvUserPubDetail);
        tvDatePubDetail =(TextView) findViewById(R.id.tvDatePubDetail);
        tvPubDetail=(TextView) findViewById(R.id.tvPubDetail);
        foto=(ImageView) findViewById(R.id.imProfilPubDetail);

        cvFeels =(ImageView) findViewById(R.id.imvFeelsD);
        cvTest  = (ImageView) findViewById(R.id.imvTestD);
        cvAnalyze= (ImageView) findViewById(R.id.imvAnalyzeD);
        cvConclusion= (ImageView) findViewById(R.id.imvConclusionD);
        cvPlan= (ImageView) findViewById(R.id.imvPlanD);

        cvFeels.setOnClickListener(this);
        cvTest.setOnClickListener(this);
        cvAnalyze.setOnClickListener(this);
        cvConclusion.setOnClickListener(this);
        cvPlan.setOnClickListener(this);

        imvDowloandPDF= (ImageView)findViewById(R.id.imvDowloandPDF);
        imvDowloandPDF.setOnClickListener(this);
        tlMainSelectDetail= (TableLayout)findViewById(R.id.tlMainSelectDetail   );


        Intent  i=this.getIntent();
        idepublicacion=i.getExtras().getString("_IDE_KEY");
        t=i.getExtras().getString("TITLE_KEY");
        u=i.getExtras().getString("USER_KEY");
        d=i.getExtras().getString("DATA_KEY");
         p=i.getExtras().getString("PUBLI_KEY");
        f=i.getExtras().getString("FOTO_KEY");
        pa=i.getExtras().getString("PAPA");

        sen=i.getExtras().getString("SEN_KEY");
        eva=i.getExtras().getString("TES_KEY");
        ana=i.getExtras().getString("ANA_KEY");
        con=i.getExtras().getString("CON_KEY");
        plan=i.getExtras().getString("PLAN_KEY");
        if(!sen.isEmpty()){

            tlMainSelectDetail.setVisibility(View.VISIBLE);
            cvFeels.setVisibility(View.VISIBLE);
        }else {
            cvFeels.setVisibility(View.GONE);
        }
        if(!eva.isEmpty()){
            cvTest.setVisibility(View.VISIBLE);
        }else {
            cvTest.setVisibility(View.GONE);
        }
        if(!ana.isEmpty()){
            cvAnalyze.setVisibility(View.VISIBLE);
        }else {
            cvAnalyze.setVisibility(View.GONE);
        }
        if(!con.isEmpty()){
            cvConclusion.setVisibility(View.VISIBLE);
        }else {
            cvConclusion.setVisibility(View.GONE);
        }
        if(!plan.isEmpty()){
            cvPlan.setVisibility(View.VISIBLE);
        }else {
            cvPlan.setVisibility(View.GONE);
        }


        listarRe( idepublicacion);
        listarFile(idepublicacion);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertRepublication(pa);

            }
        });
        tvTitlePubDetail.setText(t);
        tvUserPubDetail.setText(u);
        tvDatePubDetail.setText(d);
        tvPubDetail.setText(p);

        Picasso.with(DetailPublication.this)
                .load(Urls.download+f)
                .resize(1200, 1200)
                .noPlaceholder()
                .centerCrop()
                .into(foto);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imvFeelsD:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_sent)
                        .content(sen)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.imvTestD:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_eval)
                        .content(eva)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.imvAnalyzeD:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_analis)
                        .content(ana)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.imvConclusionD:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_concl)
                        .content(con)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.imvPlanD:
                new MaterialDialog.Builder(this)
                        .title(R.string.input_plan)
                        .content(plan)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.imvDowloandPDF:
                exportPDF(t,u,d,p,f,sen, eva, ana, con, plan,  pa );
                break;

        }

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
    public void AlertRepublication(final String pa) {
        final VariablesLogin varlogin = new VariablesLogin();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialoglayout = inflater.inflate(R.layout.dialog_republication, null);

        final EditText TITLE = (EditText) dialoglayout.findViewById(R.id.ettitlepost);
        final EditText PUBLIC = (EditText) dialoglayout.findViewById(R.id.etPublication);
        final EditText GPO = (EditText) dialoglayout.findViewById(R.id.edGrupoRep);
        listGpo(GPO, pa);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setCancelable(false)
                .setPositiveButton("Republicar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        String titulo = TITLE.getText().toString();
                        String publica = PUBLIC.getText().toString();
                        String idgrupe = ed;
                        String papa = pa;

                       // Toast.makeText(DetailPublication.this, titulo + " -"+ publica+" "+idgrupe+" "+papa,Toast.LENGTH_SHORT).show();

                        registerRePost(titulo, publica, papa, idgrupe);

                    }
                })

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        builder.setView(dialoglayout);
        builder.show();
    }

    private void registerRePost(String t, String o, String papa, String g) {

        if (!t.isEmpty() && !o.isEmpty()) {
            final VariablesLogin varlogin = new VariablesLogin();
            //  Toast.makeText(DetailPublication.this, "   Register Post",Toast.LENGTH_SHORT).show();
            final String idusuario = varlogin.getIdusuario();
            final String titulo = t;
            final String observaciones = o;
            final String padre = papa;
            final String grupe = g;


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(DetailPublication.this, "CORRRECTO", Toast.LENGTH_SHORT).show();
                            openActivity();
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

                            //   failed_regpublication.setText(body);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_IDUSUARIO, idusuario);
                    params.put(KEY_TITULO, titulo);
                    params.put(KEY_IDGROUP, grupe);
                    params.put(KEY_OBSERVACIONES, observaciones);
                    params.put(KEY_ROL, "1");
                    params.put(KEY_IDPUBLICACION, padre);
                    params.put(CONTENT_TYPE, APPLICATION);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(DetailPublication.this);
            requestQueue.add(stringRequest);
        }
        else{
                String Mensaje=("Debe rellenar todos los campos");
              //  failed_regpublication.setText(Mensaje);
                Toast.makeText(DetailPublication.this, Mensaje , Toast.LENGTH_LONG).show();

            }//fin else cheked


        }

    private void openActivity(){
        Intent is=new Intent(DetailPublication.this, MainActivity.class);
        startActivity(is);

    }

    public  void listarRe( final String pa){

        RequestQueue queue = Volley.newRequestQueue(DetailPublication.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlListRepublication,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                listRepublicaciones = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                                    listRepublicaciones.add(new com.dese.diario.Objects.RePublication(
                                            jsonobject.getString("padre"),
                                            jsonobject.getString("idpublicacion"),
                                            jsonobject.getString("foto"),
                                            jsonobject.getString("observaciones"),
                                            jsonobject.getString("titulo"),
                                            jsonobject.getString("nombre"),
                                            jsonobject.getString("idusuario")));
                                    adapter = new Adapter_RePubication(listRepublicaciones,DetailPublication.this);
                                    recyclerView.setAdapter(adapter);

                                    // Toast.makeText(DetailPublication.this, "Lista"+ listRepublicaciones, Toast.LENGTH_LONG).show();


                                  //&  System.out.println(listRepublicaciones);
                                }
                            } catch (JSONException e) {
                                Log.e("DetailPublication", "Error +->" + e);
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
                headers.put("idpublicacion", pa);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);
    }




    private void listGpo(final EditText gpo, final String pa) {
        VariablesLogin variablesLogin = new VariablesLogin();
        final String id= variablesLogin.getIdusuario().toString();

        RequestQueue queue = Volley.newRequestQueue(DetailPublication.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listargpoxpublicacion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            try {
                                //leadsNames  = new ArrayList<String>();
                                ///leadsIdes= new ArrayList<String>();
                                JSONObject jsonObject = new JSONObject(response);
                                for (int i = 0; i < jsonObject.length(); i++) {
                                    String getGruop=jsonObject.getString("nombregrupo").toString();
                                    gpo.setText(getGruop);
                                    gpo.setEnabled(false);
                                    ed=jsonObject.getString("idgrupo").toString();

                                   // Toast.makeText(DetailPublication.this, "Lista"+ getGruop, Toast.LENGTH_LONG).show();
                                    //recyclerView.setAdapter(adaptergpo);

                                }
                            } catch (JSONException e) {
                                Log.e("Detalle", "Error +->" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("DetallePub", "Response--->"+error);
            }
        }
        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idpublicacion", pa);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);

    }

    public  void listarFile( final String ide){

        RequestQueue queue = Volley.newRequestQueue(DetailPublication.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,getFilesList ,
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

                                        ia = new Adapter_File(filename, DetailPublication.this);
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
      // getMenuInflater().inflate(R.menu.menu_detail, menu);
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

          /*  case R.id.action_download:
                exportPDF(t,u,d,p,f,sen, eva, ana, con, plan,  pa );

                break;*/
            case R.id.action_help:
                break;


        }

        return super.onOptionsItemSelected(item);

    }

    private void exportPDF(String t, String u, String d, String p,
                           String f, String sen, String eva, String ana,
                           String con, String plan, String pa) {


            // TODO Auto-generated method stub
            String filename =t; //
            String filecontent = "Autor: "+u + "                 Fecha: "+d+ "\n"+
                                 "Titulo: "+ t+ "\n"+
                                    "Descripción: "+p + "\n"+
                                    "Sentimientos: "+sen+ "\n"+
                                    "Evaluación: "+eva+ "\n"+
                                    "Analisis: "+ ana+ "\n"+
                                    "Conclusión: "+con+ "\n"+
                                    "Plan de Acción: "+plan;
            ExportPDF e = new ExportPDF();
            if (e.write(filename, filecontent)) {
                Toast.makeText(getApplicationContext(),
                        filename + ".pdf created", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(), "I/O error",
                        Toast.LENGTH_SHORT).show();
            }
        }



}
