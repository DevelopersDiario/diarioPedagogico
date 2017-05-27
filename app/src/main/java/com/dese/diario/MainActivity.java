package com.dese.diario;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Pubication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener{
    final static String url= Urls.listpublicacion;
    final String idpublicacion= "idpublicacion";
    final String nombre= "nombre";
    final String fecha="fecha";
    final String titulo= "titulo";
    final String idusuario= "idusuario";
    final String padre= "padre";
    final String foto= "foto";
    final String observaciones="observaciones";
    final String URL= Urls.filtrousuarioXid;
    // final static String url= Urls.listxiduser;



    //Bars
    Toolbar toolbar;
    ActionBar actionBar;

    //Resource
    TextView textView;
    DrawerLayout drawerLayout;
    RelativeLayout conten;


    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    // Adapter
    private SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerView;

    ArrayList listpublicaciones;
    Adapter_Pubication adapter;

    LinearLayoutManager linearLayoutManager;
    VideoView vidView;

    //Resouce nav_header_main
    VariablesLogin varlogin=new VariablesLogin();
    DatosUsr du=new DatosUsr();
    String navHloginaccount =varlogin.getCuenta();
    String navHemaillogin=varlogin.getCorreo();
    CircleImageView fotouser;
    ImageView imType;
    TextView nvloginaccount;
    TextView nvemaillogin;
    VariablesLogin varLogin;

    //BackPress
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VariablesLogin.getInstance();
        // Select theme saved by user
        theme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarToolBar();

        actionfabButton();

        bindActivity();



        reciclerViewinit();

       // getSelect();
    }//Fin onCreate
    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("starttime", (Serializable) VariablesLogin.getInstance());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String varlogin = savedInstanceState.getString("starttime");
        //Toast.makeText(this, "ON RESTORE IMSTAM", Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void bindActivity() {
           // getSelect();


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        nvemaillogin=(TextView)hView.findViewById(R.id.nvemaillogin);
        nvloginaccount=(TextView)hView.findViewById(R.id.nvloginaccount);
        nvemaillogin.setText(varlogin.getCorreo().toString());
        nvloginaccount.setText(varlogin.getCuenta().toString());

       /* String  type= getIntent().getExtras().getString("Type");
        Toast.makeText(this, type, Toast.LENGTH_LONG).show();
        Uri uri = null;
        switch (type){
            case "Gmail":
              //uri=  Uri.parse("android.resource://"+this.getPackageName()+"/drawable/ic_google");
              uri=  Uri.parse("anhttps://sites.google.com/a/wadsworthschools.org/beta-bear/_/rsrc/1470874216923/search/Search%20icon.png");
                Picasso.with(this)
                        .load(uri)
                        .placeholder(R.drawable.image_cloud_sad)
                        .error(R.drawable.image_sun_smile)
                        .into( imType=(ImageView) hView.findViewById(R.id.imType));
                break;
            case "Facebook":
             // uri=  Uri.parse("android.resource://"+this.getPackageName()+"/drawable/ic_facebook_box");
              uri=  Uri.parse("https://www.seeklogo.net/wp-content/uploads/2016/09/facebook-icon-preview-1.png");
                Picasso.with(this)
                        .load(uri)
                        .placeholder(R.drawable.image_cloud_sad)
                        .error(R.drawable.image_sun_smile)
                        .into( imType=(ImageView) hView.findViewById(R.id.imType));
                break;
            case "Manual":
                //uri=  Uri.parse("android.resource://"+this.getPackageName()+"/drawable/ic_contact_mail_black_24dp");
                uri=  Uri.parse("http://www.iconarchive.com/download/i87103/graphicloads/colorful-long-shadow/Mail-at.ico");
                Picasso.with(this)
                        .load(uri)
                        .placeholder(R.drawable.image_cloud_sad)
                        .error(R.drawable.image_sun_smile)
                        .into( imType=(ImageView) hView.findViewById(R.id.imType));

                break;
        }*/

        Picasso.with(MainActivity.this)
                .load(Urls.fotouser+du.getFoto())
                .resize(200, 200)
                .centerCrop()
                .into(  fotouser=(CircleImageView)hView.findViewById(R.id.cvProfile)); Picasso.with(MainActivity.this)
                .load(Urls.fotouser+du.getFoto())
                .resize(200, 200)
                .centerCrop()
                .into(  fotouser=(CircleImageView)hView.findViewById(R.id.cvProfile));



    }



    private void reciclerViewinit() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.content_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                conectionPublication();
            }
        }, 3000);

    }



    private void inicializarToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);

        }

        setupNavigationDrawerContent(navigationView);
    }

    /*------------Theme choose by user--------------------*/
    private void theme() {
        sharedPreferences = getSharedPreferences(getString(R.string.values), Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt(getString(R.string.theme), 0);
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


    private void actionfabButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intentPubli = new Intent(MainActivity.this, Publication.class);
                startActivity(intentPubli);


            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /*---------Navegatior Drawable--------------- */
    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }*/
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), R.string.message_presione_otra_vez,
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
        LoginManager.getInstance().logOut();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //   textView = (TextView) findViewById(R.id.textView);


                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_home:
                                Intent intentHome = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intentHome);
//
                                finish();

                                break;
                            case R.id.item_navigation_drawer_publication:

                                Intent intentPubli = new Intent(MainActivity.this, Publication.class);
                                startActivity(intentPubli);
                                break;
                            case R.id.item_navigation_drawer_profile:
                                menuItem.setChecked(true);

                                Intent intentProfile= new Intent(MainActivity.this, Profile.class);
                                startActivity(intentProfile);

                                return true;
                            case R.id.item_navigation_drawer_friends:
                                menuItem.setChecked(true);
                                //  drawerLayout.openDrawer(GravityCompat.START);
                                Intent intentFriends = new Intent(MainActivity.this, Colaboration.class);
                                startActivity(intentFriends);

                                return true;

                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);

                                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                                startActivity(intentSettings);
                                return true;
                            case R.id.item_navigation_drawer_share:
                                shareCode();
                                return true;

                            case R.id.item_navigation_drawer_about:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                //drawerLayout.closeDrawer(GravityCompat.START);
                               /* Dialog dialog = new Dialog(MainActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.activity_about);
                                dialog.show();
                                */
                                Intent ia= new Intent(MainActivity.this, About.class);
                                startActivity(ia);

                                return true;
                            case R.id.item_navigation_drawer_exit:
                                menuItem.setChecked(true);
                                finish();
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                LoginManager.getInstance().logOut();
                                break;
                        }
                        return true;
                    }
                });
    }

    private void shareCode() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(getString(R.string.text_plain));
        intent.putExtra(Intent.EXTRA_TEXT, R.string.message_utiliza_la_app);
        startActivity(Intent.createChooser(intent, getString(R.string.Share_with)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }




    /**Conection**/
    @Override
    protected void onStart() {
        super.onStart();

        conectionPublication();

    } //Fin onStart

    public void conectionPublication(){

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try{
                                listpublicaciones = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    //  System.out.println(jsonobject);
                                    VariablesLogin varllogin=new VariablesLogin();

                                    listpublicaciones.add(new com.dese.diario.Objects.Publication(
                                            jsonobject.getString(idpublicacion),
                                            jsonobject.getString(idusuario),
                                            jsonobject.getString(nombre),
                                            jsonobject.getString(foto),
                                            jsonobject.getString(fecha),
                                            jsonobject.getString(titulo),
                                            jsonobject.getString(observaciones),
                                            jsonobject.getString(padre)));
                                    adapter=new Adapter_Pubication(listpublicaciones, MainActivity.this);
                                    recyclerView.setAdapter(adapter);
                                   /// System.out.println(listpublicaciones);
                                    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



                                }
                            }

                            catch (JSONException e){
                                Log.e(getString(R.string.UUUps), getString(R.string.Error)+e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getString(R.string.carita),getString(R.string.message_ocurrio_error));
            }
        });


        queue.add(stringRequest);
// Signify that we are done refreshing
        swipeContainer.setRefreshing(false);
    }// Fin conectionPublication

  /*  public void getPersonalInformation(){
        final VariablesLogin varlogin=new VariablesLogin();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try{
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    DatosUsr Du =new DatosUsr();
                                    Du.setIdusuario(jsonobject.getString("idusuario"));
                                    Du.setNombre(jsonobject.getString("nombre"));
                                    Du.setApellidos(jsonobject.getString("apellidos"));
                                    Du.setGenero(jsonobject.getString("genero"));
                                    Du.setCuenta(jsonobject.getString("cuenta"));
                                    Du.setVigencia(jsonobject.getString("vigencia"));
                                    Du.setFnacimiento(jsonobject.getString("fnacimiento"));
                                    Du.setCorreo(jsonobject.getString("correo"));
                                    Du.setTelefono(jsonobject.getString("telefono"));
                                    Du.setInstitucion(jsonobject.getString("institucion"));
                                    Du.setGrupo(jsonobject.getString("grupo"));
                                    Du.setEstado(jsonobject.getString("estado"));
                                    Du.setFoto(jsonobject.getString("foto"));

                                 //    nvemaillogin.setText(jsonobject.getString("correo"));
                                   // nvloginaccount.setText(jsonobject.getString("cuenta"));

                                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                    if (navigationView != null) {
                                        // Añadir carácteristicas
                                    }
                                    nvloginaccount.setText(jsonobject.getString("cuenta"));
                                }
                            }

                            catch (JSONException e){
                                Log.e("UUUps!!!!!", "Error!!"+e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("°_°","ocurio un error !");
            }

        }

        ) {*//**
         * Passing some request headers
         *//*
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("idusuario", varlogin.getIdusuario());
            return headers;
        }
        };

        queue.add(stringRequest);
// Signify that we are done refreshing

    }// Fin response*/
    @Override
    public void onClick(View v) {

    }
}
