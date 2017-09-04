package com.dese.diario;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Item;
import com.dese.diario.Utils.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.Upload;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity implements DatePickerListener,  View.OnClickListener{
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    //KEYS
    final String KEY_VALUE="VALUES";
    final String KEY_THEME="THEME";
    final String KEY_NAMEG = "nombregrupo";
    final String KEY_IDUSUARIO="idusuario";
    final String KEY_TITULO="titulo";
    final String KEY_OBSERVACIONES="observaciones";
    final String KEY_IDGROUP="idgrupo";
    final String KEY_FEELS="sentimiento";
    final String KEY_TEST="evaluacion";
    final String KEY_ANLYSE="analisis";
    final String KEY_CONCLUSION="conclusion";
    final String KEY_PLAN="planaccion";

    final String CONTENT_TYPE="Content-Type";
    final String APPLICATION="application/x-www-form-urlencoded";

    //Toolbar
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    Intent intent;
    Boolean homeButton = false, themeChanged;

    //Bind
    EditText  etTitle, etGrupo, etDescripcion, etSenimientos, etEvaluacion, etAnalisis, etConclusion, etPlan;
    Button btnMoreFeels, btnMoreDesc, btnMoreTest, btnMoreAnalisis, btnMoreConclusion, btnMorePlan;
    Spinner spGpoP;

    //Spinner
    List leadsNames, leadsIdes;
    ArrayAdapter mLeadsAdapter;
    String ed;


    //Upload
    Upload upload;
    String idusuario;
    String titulo;
    String observaciones, sentimientos, evaluacion, analisis, conclusion, plan ;
    String padre;
    Intent actReq;
    //RV
    ArrayList<String> paths = new ArrayList<>();
    private RecyclerView rcItems;
    Adapter_Item ia;


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
                titulo = etTitle.getText().toString();
                observaciones = etDescripcion.getText().toString();
                sentimientos=etSenimientos.getText().toString();
                evaluacion =etEvaluacion.getText().toString();
                analisis=etAnalisis.getText().toString();
                conclusion=etConclusion.getText().toString();
                plan=etPlan.getText().toString();

                try {
                    RegisterPost(ed);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*Snackbar snackbar = Snackbar
                        .make(view, "Datos obtenidos:"+ titulo
                                    +observaciones
                                    +sentimientos
                                    +evaluacion
                                    + analisis
                                    +conclusion+ "-----"
                                + ed
                                +plan, Snackbar.LENGTH_LONG);
*/
             //   snackbar.show();
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Register.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
        rcItems = (RecyclerView) findViewById(R.id.rvItems);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(3, 1);
        rcItems.setLayoutManager(staggeredGridLayout);
        upload = new Upload();
        listGpos();

        etTitle=(EditText) this.findViewById(R.id.etTitle);
        etDescripcion = (EditText) this.findViewById(R.id.etDescripcion);
        etSenimientos= (EditText) findViewById(R.id.etSenimientos);
        etEvaluacion= (EditText) this.findViewById(R.id.etEvaluacion);
        etAnalisis= (EditText)this.findViewById(R.id.etAnalisis);
        etConclusion= (EditText) this.findViewById(R.id.etConclusion);
        etPlan=(EditText) this.findViewById(R.id.etPlan);


       // etTitle.setOnClickListener(this);
      //  etDescripcion.setOnClickListener(this);
      //  etSenimientos.setOnClickListener(this);
       // etEvaluacion.setOnClickListener(this);
        //etAnalisis.setOnClickListener(this);
       // etConclusion.setOnClickListener(this);
       // etPlan.setOnClickListener(this);

        btnMoreFeels= (Button) findViewById(R.id.btnMoreFeels);
        btnMoreDesc= (Button) findViewById(R.id.btnMoreDesc);
        btnMoreTest= (Button) findViewById(R.id.btnMoreTest);
        btnMoreAnalisis= (Button) findViewById(R.id.btnMoreAnalisi);
        btnMoreConclusion= (Button) findViewById(R.id.btnMoreConclusion);
        btnMorePlan=(Button) findViewById(R.id.btnMorePlan);

        btnMoreFeels.setOnClickListener(this);
        btnMoreTest.setOnClickListener(this);
        btnMoreDesc.setOnClickListener(this);

        btnMoreAnalisis.setOnClickListener(this);
        btnMoreConclusion.setOnClickListener(this);
        btnMorePlan.setOnClickListener(this);


        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.btnMoreDesc:
                Intent desc= new Intent(Register.this, Descripting.class);
                startActivity(desc);
                break;
           case R.id.btnMoreFeels:
                Intent feelss= new Intent(Register.this, Feelings.class);
               if(etDescripcion.getText().toString().isEmpty()){
                   new MaterialDialog.Builder(this)
                           .title("Alerta!")
                           .content("Necesita agregar primero una descrición")
                           .show();

               }else {
                   feelss.putExtra("Descripcion", etDescripcion.getText().toString());
                   startActivity(feelss);
               }

                break;

            case R.id.btnMoreTest:
                Intent testing= new Intent(Register.this, Testing.class);
                if(etSenimientos.getText().toString().isEmpty()){


                }else {
                    testing.putExtra("Descripcion", etDescripcion.getText().toString());
                    testing.putExtra("Sentimientos", etSenimientos.getText().toString());
                    startActivity(testing);
                }

                break;
            case R.id.btnMoreAnalisi:
                Intent Analisis= new Intent(Register.this, Analyze.class);
                if(etEvaluacion.getText().toString().isEmpty()){

                }else {
                    Analisis.putExtra("Descripcion", etDescripcion.getText().toString());
                    Analisis.putExtra("Sentimientos", etSenimientos.getText().toString());
                    Analisis.putExtra("Evaluacion", etEvaluacion.getText().toString());
                    startActivity(Analisis);
                }
                break;
            case R.id.btnMoreConclusion:
                Intent Conc= new Intent(Register.this, Conclusion.class);
                if(etAnalisis.getText().toString().isEmpty()){

                }else {
                    Conc.putExtra("Descripcion", etDescripcion.getText().toString());
                    Conc.putExtra("Sentimientos", etSenimientos.getText().toString());
                    Conc.putExtra("Evaluacion", etEvaluacion.getText().toString());
                    Conc.putExtra("Analisis", etAnalisis.getText().toString());
                    startActivity(Conc);
                }
                break;

            case R.id.btnMorePlan:
                Intent plan= new Intent(Register.this, Plan.class);
                if(etAnalisis.getText().toString().isEmpty()){

                }else {
                    plan.putExtra("Descripcion", etDescripcion.getText().toString());
                    plan.putExtra("Sentimientos", etSenimientos.getText().toString());
                    plan.putExtra("Evaluacion", etEvaluacion.getText().toString());
                    plan.putExtra("Conclusion", etConclusion.getText().toString());
                    plan.putExtra("Analisis", etAnalisis.getText().toString());
                    startActivity(plan);
                }
                break;
        }
    }


    private void RegisterPost(final String  ide) throws JSONException {
        final VariablesLogin  varlogin =new VariablesLogin();
        idusuario= varlogin.getIdusuario();
        titulo = etTitle.getText().toString().trim();
        observaciones = etDescripcion.getText().toString().trim();
        sentimientos=etSenimientos.getText().toString().trim();
        evaluacion =etEvaluacion.getText().toString().trim();
        analisis=etAnalisis.getText().toString().trim();
        conclusion=etConclusion.getText().toString().trim();
        plan=etPlan.getText().toString().trim();
        padre="0";

        if (!etTitle.getText().toString().isEmpty() && !etDescripcion.getText().toString().isEmpty()) {



            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.insertpublicacion,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            upload.recorrerListPaths(paths, Register.this, actReq, ed);
                          //  failed_regpublication.setText(R.string.message_succes_publication);

                            openMainactivity();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if (error.networkResponse.data != null) {

                        try {
                            if(error!=null && error.getMessage() !=null){
                                Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                            }
                            body = new String(error.networkResponse.data, "UTF-8");

                           // failed_regpublication.setText(body);


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("",error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_IDUSUARIO,idusuario );
                    params.put(KEY_TITULO, titulo);
                    params.put(KEY_IDGROUP, ide);
                    params.put(KEY_OBSERVACIONES, observaciones);
                    params.put("sentimiento", sentimientos);
                    params.put("evaluacion", evaluacion);
                    params.put("analisis", analisis);
                    params.put("conclusion", conclusion);
                    params.put("planaccion", plan);
                    params.put(CONTENT_TYPE, APPLICATION);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }//Fin isChecked
        else{
            String Mensaje=("Debe rellenar todos los campos");
            //failed_regpublication.setText(Mensaje);
            Toast.makeText(Register.this,Mensaje , Toast.LENGTH_LONG).show();

        }//fin else cheked



    }//Fin RegisterPost

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

            alert.setMessage("Desea salir del registro de experiencia");
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
                try {
                    RegisterPost(ed);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case android.R.id.home:
                if (homeButton) {

                    if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                        back_pressed = System.currentTimeMillis();
                        intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return  true;
                    } else {
                        final AlertDialog alert = new AlertDialog.Builder(this).create();

                        alert.setMessage("Desea salir del registro de experiencia");
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

              /*  if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(Register.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.download), false);
                        editor.apply();


                    }*/
                  //  intent = new Intent(Register.this, MainActivity.class);
                    //startActivity(intent);
                    //finish();

                     /* if(etDescripcion.getText().toString()!=" "&&etDescripcion.getText().toString().isEmpty()){
                      if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                            back_pressed = System.currentTimeMillis();
                            intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return  true;
                        } else {
                            final AlertDialog alert = new AlertDialog.Builder(this).create();

                            alert.setMessage("Desea salir del registro de experiencia");
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
*/

                     //   }

                   // }
            break;
           //  return false;



               // }
       //return true;
        }

        return false;
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

    private void listGpos() {
        VariablesLogin variablesLogin = new VariablesLogin();
        final String id= variablesLogin.getIdusuario().toString();

        RequestQueue queue = Volley.newRequestQueue(Register.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listgrupo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            try {
                                leadsNames  = new ArrayList<String>();
                                leadsIdes= new ArrayList<String>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (final int[] i = {0}; i[0] < jsonarray.length(); i[0]++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i[0]);

                                    leadsNames.add(0, jsonobject.getString(KEY_NAMEG));
                                    leadsIdes.add(0, jsonobject.getString(KEY_IDGROUP));


                                    spGpoP= (Spinner) findViewById(R.id.spGpoP);
                                    mLeadsAdapter = new ArrayAdapter<String>(Register.this,
                                            android.R.layout.simple_spinner_item, leadsNames);
                                    mLeadsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    spGpoP.setAdapter(mLeadsAdapter);
                                    spGpoP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
                                        {


                                                ed= (String) leadsIdes.get(pos);

                                                // spGpoP.setEnabled(true);
                                                //Toast.makeText(Register.this, ed.toString(),Toast.LENGTH_SHORT).show();



                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent)
                                        {    }
                                    });
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
                headers.put("idusuario", id);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);

    }
            private void openMainactivity() {
                // showProgress(true);
                Intent intent= new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            } //Fin open login


                public void getData(){
                    Bundle getD = getIntent().getExtras();
                    if (getD != null) {


                        etDescripcion.setText(getD.getString("Descripcion"));
                        paths= getIntent().getStringArrayListExtra("Paths");
                     //   Toast.makeText(this, paths.size(), Toast.LENGTH_LONG).show();
                        etSenimientos.setText(getD.getString("Sentimientos"));
                        etEvaluacion.setText(getD.getString("Evaluacion"));
                        etAnalisis.setText(getD.getString("Analisis"));
                        etConclusion.setText(getD.getString("Conclusion"));
                        etPlan.setText(getD.getString("Plan"));
                          ia = new Adapter_Item(paths, Register.this);
                         rcItems.setAdapter(ia);

                    }

                }


}
