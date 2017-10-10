package com.dese.diario;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.dese.diario.Utils.Constants;
import com.dese.diario.Utils.FirebaseService.FirebaseConection;
import com.dese.diario.Utils.Urls;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener{

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

    //Bars
    Toolbar toolbar;
    ActionBar actionBar;

    //Resource
    TextView textView;
    DrawerLayout drawerLayout;
    RelativeLayout content;

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
    private final int MY_PERMISSIONS = 100;


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

        mayRequestStoragePermission();

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

        fotouser=(CircleImageView)hView.findViewById(R.id.cvProfile);
        imType=(ImageView) hView.findViewById(R.id.imType);
        varlogin= new VariablesLogin();

        Picasso.with(MainActivity.this)
                .load(Urls.download+du.getFoto())
                .resize(200, 200)
                .centerCrop()
                .into( fotouser);

            Picasso.with(MainActivity.this)
                .load(Urls.download+du.getFportada())
                .resize(2000, 1200)
                .centerCrop()
                .into(  imType);
        FirebaseConection fc= new FirebaseConection();
        fc.setDatabaseDatosUser(du);
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

                //Intent intentPubli = new Intent(MainActivity.this, Publication.class);
                //startActivity(intentPubli);

                Intent ir= new Intent(MainActivity.this, Register.class);
                startActivity(ir);


            }
        });

        File f = new File(Environment.getExternalStorageDirectory() + Constants.mMainDirectory);
// Comprobamos si la carpeta está ya creada

// Si la carpeta no está creada, la creamos.

        if(!f.isDirectory()) {
            String newFolder =Constants.mMainDirectory; //cualquierCarpeta es el nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File myNewFolder = new File(extStorageDirectory + newFolder);
            myNewFolder.mkdir(); //creamos la carpeta
        }else{
            Log.d("MAIN","La carpeta ya estaba creada");
        }


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


    private void setupNavigationDrawerContent(final NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {


                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //   textView = (TextView) findViewById(R.id.textView);
// NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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
                              //  Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();

                                Intent ia= new Intent(MainActivity.this, About.class);
                                startActivity(ia);

                                return true;
                            case R.id.item_navigation_drawer_exit:
                                menuItem.setChecked(true);

                                LoginManager.getInstance().logOut();

                                startActivity(new Intent(getBaseContext(), SelectAccount.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();
                                break;
                            case R.id.item_navigation_drawer_mypublication:
                                menuItem.setChecked(true);
                                //  Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();

                                Intent ip= new Intent(MainActivity.this, MyPublication.class);
                                startActivity(ip);
                                break;

                        }
                        return true;
                    }
                });
    }

    private void shareCode() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"¡Hey utiliza Mi Diario, esta genial, ahi me puedes encontar! https://play.google.com/store/apps/details?id=com.dese.diario&hl=es");
        startActivity(Intent.createChooser(intent, "Shared with"));
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


        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,// Urls.listxiduser,
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
                                            jsonobject.getString("sentimiento"),
                                            jsonobject.getString("evaluacion"),
                                            jsonobject.getString("analisis"),
                                            jsonobject.getString("conclusion"),
                                            jsonobject.getString("planaccion"),
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

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(swipeContainer, R.string.Los_permisos_son_necesarios,
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, MY_PERMISSIONS);
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, R.string.message_permisos_aceptados, Toast.LENGTH_SHORT).show();
                // fab.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }
    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.message_permisos_denegados);
        builder.setMessage(R.string.message_funciones);
        builder.setPositiveButton(R.string.btnAgree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();

                Uri uri = Uri.fromParts(getString(R.string.packge), getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.btnDisagree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }
}
