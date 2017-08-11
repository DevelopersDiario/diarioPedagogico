package com.dese.diario;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.DownloadTask;
import com.dese.diario.Utils.Upload;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import com.dese.diario.POJOS.*;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.android.volley.Request.Method.GET;

public class Profile extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener,
    LoaderManager.LoaderCallbacks {


    //Animation Imagen
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 100;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer,rlProfileInfo,lyCard;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private CardView cvContainerProfile;

    //Theme
    SharedPreferences sharedPreferences, sharedPreferences2;
    SharedPreferences.Editor editor;
    int theme;
    int img;



    //Image View
    private static String APP_DIRECTORY = "DiarioApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "DiarioVirtual";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage, imagenAs;
    private  ImageView mPortada;
    private RelativeLayout mRlView;
    private String mPath;

    private CircleImageView cViewImagen;

    //Profile
    TextView tvState, tvInformationPrivate, tvInformationSchool, tvInformationSocial;
    EditText edState;
    TextView tvaccountProfile;
    TextView tvgeneroProfile;
    EditText etestadouserProfile;
    Intent intent;
    ArrayList listadatosuser;
    Adapter adapteruser;
    final  String KEY_IDUSUARIO="idusuario";
    final  String ESTADO="estado";
    final  String CONTENT_TYPE="Content-Type";
    final  String APPLICATION="application/x-www-form-urlencoded";
    final String nombre= "nombre";
    final String apellidos="apellidos";
    final String genero="genero";
    final String cuenta="cuenta";
    final String vigencia="vigencia";
    final String fnacimiento= "fnacimiento";
    final String correo= "correo";
    final String telefono= "telefono";
    final String institucion= "institucion";
    final String grupo= "grupo";
    final String estado= "estado";
    final String foto= "foto";
    final String fportada="fportada";
    final String URL= Urls.filtrousuarioXid;
    final String URLupdatestate= Urls.updateestado;
    VariablesLogin Vrlog=new VariablesLogin();

    private final static  String urlfoto= Urls.download;
    private final static  String URLSfoto= Urls.upload;
    private final static  String uploadHolder= Urls.uploadholder;

    CircleImageView circleImageView;
    Bitmap imgbitmap,bmHolder,bmProfile;
    String ide;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        theme();
        super.onCreate(savedInstanceState);
        getPersonalInformation();

        setContentView(R.layout.activity_profile);

        //Hide softKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        bindActivity();

        settingsButtons();



     //   downloadF();
        //performRequest();

        //dowlandHolder();


    }//End Create

    @Override
    protected void onStart() {
        super.onStart();
      // performRequest();
       // dowlandHolder();


    }
    private void openactivity(){
        Intent intent =new Intent(Profile.this, Profile.class);
        startActivity(intent);
    }





    private void  updateestado()  throws JSONException{

        final VariablesLogin varlogin =new VariablesLogin();

        final StringRequest stringRequest = new StringRequest(Request.Method.PUT, URLupdatestate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Profile.this, R.string.Tu_Estado_ha_sido_actualizado, Toast.LENGTH_LONG).show();
                        openactivity();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                //get status code here
//                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if(error.networkResponse.data!=null) {

                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                           /* JSONObject jsobjet = new JSONObject(body);
                         jsobjet.get("failed");
                            String rerror= jsobjet.toString();*/

                        Toast.makeText(Profile.this, body, Toast.LENGTH_LONG).show();



                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                //do stuff with the body...
            }//Fin onErrorResponse */
                /*public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateAccount.this, "!!!", Toast.LENGTH_LONG).show();
                }*/

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDUSUARIO,varlogin.getIdusuario() );
                params.put(ESTADO,etestadouserProfile.getText().toString() );
                params.put(CONTENT_TYPE, APPLICATION);
                return params;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    } //fin updateestado
    private void bindActivity() {
        mToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        mTitle = (TextView) findViewById(R.id.profile_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.profile_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.profile_appbar);
        rlProfileInfo = (LinearLayout) findViewById(R.id.rlProfileInfo);
        //  cvContainerProfile = (CardView)findViewById(R.id.cvContainerProfile);
        lyCard = (LinearLayout) findViewById(R.id.lyCard);

        //tview
        tvState = (TextView) findViewById(R.id.tvState);
        tvInformationPrivate = (TextView) findViewById(R.id.tvInformationPrivate);
        tvInformationSchool = (TextView) findViewById(R.id.tvInformationSchool);
        tvInformationSocial = (TextView) findViewById(R.id.tvInformationSocial);

        tvState.setOnClickListener(this);
        tvInformationPrivate.setOnClickListener(this);
        tvInformationSchool.setOnClickListener(this);
        tvInformationSocial.setOnClickListener(this);

        edState = (EditText) findViewById(R.id.etestadouserProfile);

        etestadouserProfile=(EditText) findViewById(R.id.etestadouserProfile);

        circleImageView = (CircleImageView) findViewById(R.id.imCircleView);
        mPortada = (ImageView) findViewById(R.id.profile_imageview_placeholder);
        DatosUsr du= new DatosUsr();

        Picasso.with(Profile.this)
                .load(Urls.download+du.getFoto())
                .error(R.drawable.image_cloud_sad)
                .resize(200, 200)
                .centerCrop()
                .into(  cViewImagen=(CircleImageView) findViewById(R.id.imCircleView));

        VariablesLogin vl =new VariablesLogin();
        Picasso.with(Profile.this)
                .load(Urls.download+du.getFportada())
                .error(R.drawable.image_cloud_sad)
                .resize(2000, 1200)
                .centerCrop()
                .into( mPortada);

    }


    public void getPersonalInformation(){
        final VariablesLogin varlogin=new VariablesLogin();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try{
                                listadatosuser = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    DatosUsr Du =new DatosUsr();
                                    Du.setIdusuario(jsonobject.getString(KEY_IDUSUARIO));
                                    Du.setNombre(jsonobject.getString(nombre));
                                    Du.setApellidos(jsonobject.getString(apellidos));
                                    Du.setGenero(jsonobject.getString(genero));
                                    Du.setCuenta(jsonobject.getString(cuenta));
                                    Du.setVigencia(jsonobject.getString(vigencia));
                                    Du.setFnacimiento(jsonobject.getString(fnacimiento));
                                    Du.setCorreo(jsonobject.getString(correo));
                                    Du.setTelefono(jsonobject.getString(telefono));
                                    Du.setInstitucion(jsonobject.getString(institucion));
                                    Du.setGrupo(jsonobject.getString(grupo));
                                    Du.setEstado(jsonobject.getString(estado));
                                    Du.setFoto(jsonobject.getString(foto));
                                    Du.setFportada(jsonobject.getString("fportada"));
                                   // tvInformationPrivate.setText(jsonobject.getString("cuenta"));
                                    etestadouserProfile.setText(jsonobject.getString(ESTADO));
                                  //  tvgeneroProfile.setText(jsonobject.getString("genero"));

                                      // System.out.println(listadatosuser);
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

        }

        ) {/**
         * Passing some request headers
         */
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put(CONTENT_TYPE, APPLICATION);
            headers.put(KEY_IDUSUARIO, varlogin.getIdusuario());
            return headers;
        }
        };


        queue.add(stringRequest);
// Signify that we are done refreshing

    }// Fin getPersonalInformation

    @Override
    public void onBackPressed() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void settingsButtons() {
        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.main);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        mSetImage = (ImageView) findViewById(R.id.profile_imageview_placeholder);
        mSetImage.setOnClickListener(this);

        cViewImagen = (CircleImageView) findViewById(R.id.imCircleView);
        cViewImagen.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        mayRequestStoragePermission();

        switch (v.getId()) {
            case  R.id.profile_imageview_placeholder:

                Snackbar.make(v, R.string.portada, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();
                ide="holder";
                showOptions();
                break;

            case R.id.imCircleView:
                Snackbar.make(v, R.string.profile, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();

                ide="profile";
                showOptions();
                break;

            case R.id.tvState:

                if (edState.isEnabled()){
                    try {
                        //Toast.makeText(this, R.string.action_saved, Toast.LENGTH_SHORT).show();
                          Snackbar.make(v,  R.string.action_saved, Snackbar.LENGTH_LONG)
                                 .setAction("Action", null).show();
                        updateestado();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (!edState.isEnabled()){

                    edState.setEnabled(true);
                    Snackbar.make(v, "Editar", Snackbar.LENGTH_LONG)
                            .setAction(R.string.action, null).show();
                }
               /* if(edState.isEnabled())

                    edState.setEnabled(false);
                else if(!edState.isEnabled())
                    edState.setEnabled(true);*/


                break;

            case  R.id.tvInformationPrivate:
                Intent iip= new Intent(this, DataPersonal.class);
                startActivity(iip);
                Snackbar.make(v, R.string.personal, Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorAccent))
                        .setActionTextColor(getResources().getColor(R.color.md_yellow_A700))
                        .setAction(R.string.action, null).show();

                break;
            case  R.id.tvInformationSchool:
                Intent iis= new Intent(this, DataSchool.class);
                startActivity(iis);
                Snackbar.make(v, R.string.academia, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();

                break;
            case R.id.tvInformationSocial:
                Intent is= new Intent(this, DataSocial.class);
                startActivity(is);
                Snackbar.make(v, R.string.social, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action, null).show();

                break;

        }
    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, R.string.Los_permisos_son_necesarios,
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);

                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                // rlProfileInfo.setPadding(5, 100,5,7);
                //cvContainerProfile.setPadding(5, 200,5,7);
                mIsTheTitleContainerVisible = false;
                lyCard.setPadding(10, 100, 10, 16);
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                lyCard.setPadding(10, 16, 10, 16);

                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    /*--------------Theme Selected----------------*/


    public void theme() {
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

    /*------------------Imagen Profile-------------------*/

    private void showOptions() {
        final CharSequence[] option = {getString(R.string.camara), getString(R.string.galeria)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle(R.string.elija_opcion);
        builder.setInverseBackgroundForced(true);
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == getString(R.string.camara)){

                    //agregarFotoCamara();
                    openCamera();

                }else if(option[which] == getString(R.string.galeria)){
                      //Gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType(getString(R.string.imageda));
                    startActivityForResult(galleryIntent, SELECT_PICTURE);

                  //  Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //intent.setType(getString(R.string.imageda));
                    //startActivityForResult(intent.createChooser(intent, getString(R.string.selecciona_app_imagen)), SELECT_PICTURE);

                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }


    private void openCamera() {
       /* File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + R.string.jpg;

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() +".provider", newFile));
            startActivityForResult(intent, PHOTO_CODE);

        }*/
       Intent cameraIntent = new Intent(
               android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
       //Creamos una carpeta en la memeria del terminal
       File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Mi diario/Perfil/");
       imagesFolder.mkdirs();
       File image = null;
       //añadimos el nombre de la imagen
       if (ide == "profile") 
            image= new File(imagesFolder, "Profile"+Vrlog.getIdusuario().toString()+".jpg");
       else if(ide=="holder")
           image= new File(imagesFolder, "Holder"+Vrlog.getIdusuario().toString()+".jpg");

       cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() +".provider", image));
       startActivityForResult(cameraIntent, PHOTO_CODE);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.file_path), mPath);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString(getString(R.string.file_path));
        //Toast.makeText(this, "ON RESTORE IMSTAM", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b=(getIntent().getExtras());

        Upload up = new Upload();

        if(resultCode == RESULT_OK && data != null){


            switch (requestCode){
                case PHOTO_CODE:


                    String path;
                    Bitmap bMap ;
                    if (ide == "profile") {
                        path=  Environment.getExternalStorageDirectory()+
                                "/Mi Diario/Perfil/"+"Profile"+Vrlog.getIdusuario().toString()+".jpg";


                        bMap = BitmapFactory.decodeFile(path);

                        cViewImagen.setImageBitmap(bMap);
                        Toast.makeText(this, "Data->"+path, Toast.LENGTH_LONG).show();
                        up.uploadPictureProfile(Profile.this, path);

                    } else if (ide=="holder") {
                        path= Environment.getExternalStorageDirectory()+
                                "/Mi Diario/Perfil/"+"Holder"+Vrlog.getIdusuario().toString()+".jpg";
                        bMap = BitmapFactory.decodeFile(path);

                        up.uploadPictureHolder(Profile.this, path);

                        mPortada.setImageBitmap(bMap);
                        Toast.makeText(this, "Data"+path, Toast.LENGTH_LONG).show();

                        // upload();
                    }





                    break;
                case SELECT_PICTURE:

                        Uri filePath = data.getData();
                    String pathito;
                    try {
                         pathito=up.getFilePath(Profile.this, filePath);
                        Toast.makeText(this, pathito, Toast.LENGTH_LONG).show();

                        if (ide == "holder") {
                            Toast.makeText(this, pathito, Toast.LENGTH_LONG).show();
                            mPortada.setImageURI(filePath);

                            up.uploadPictureHolder(this, pathito);
                        } else if (ide=="profile") {
                            Toast.makeText(this, pathito, Toast.LENGTH_LONG).show();
                            cViewImagen.setImageURI(filePath);
                            up.uploadPictureProfile(this, pathito);
                        }


                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                       /* try {
                            //getting image from gallery
                            imgbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                            // imagenAs.setImageResource(bitmap);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            imgbitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                          //  String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imgbitmap, "Title", null);
                          // uploadHolder();
                            //upload();

                            if (ide == "holder") {
                               bmHolder=(imgbitmap);
                                uploadHolder();
                            } else if (ide=="profile") {
                                bmProfile = imgbitmap;
                                upload();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/





                    break;


            }

        }else{
            Toast.makeText(this, "NUllos", Toast.LENGTH_LONG).show();
        }
    }
    private void downloadF(){
        final DatosUsr dusr=new DatosUsr();
        if(!dusr.equals(null)){
            if(!dusr.getFoto().isEmpty()){
                String download=Urls.download;
                String nombrearchivo= download+dusr.getFoto();
                new DownloadTask(Profile.this, null, nombrearchivo);
            }else{
                Toast.makeText(this, "Vacio", Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Profile.this, R.string.message_permisos_aceptados, Toast.LENGTH_SHORT).show();
                // fab.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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



    @Override
    public void onLoadFinished(Loader loader, Object data) {
    }

    @Override
    public void onLoaderReset(Loader loader) {


    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        onSaveInstanceState(args);
        args.putString(getString(R.string.file_path), mPath);
        Loader loader = new Loader(this);
        loader.equals(mPath);
        return  loader;
    }
}