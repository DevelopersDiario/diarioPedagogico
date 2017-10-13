package com.dese.diario;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.dese.diario.Adapter.Adapter_Item;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.CheckForSDCard;
import com.dese.diario.Utils.Constants;
import com.dese.diario.Utils.ShowProgressDialog;
import com.dese.diario.Utils.Upload;
import com.dese.diario.Utils.Urls;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class Register extends AppCompatActivity implements DatePickerListener,  View.OnClickListener{
    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    int Apptheme;

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

    //FAB
    private FABToolbarLayout morph;
    FloatingActionButton fab;
    //Reservar
    SharedPreferences.Editor edito;
    SharedPreferences prefs;

    //  //REQUEST FILE
    private final int SELECT_PICTURE = 300;
    private final int PICK_DOC_REQUEST = 1;
    private final int PICK_AUD_REC_REQUEST = 2;
    private final int PICK_AUD_REQUEST = 3;
    private final int PICK_IMG_REQUEST = 4;
    private String mCurrentPhotoPath;

    private static  String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + Constants.mAudioDirectory+ "/Audio";
    private static String AUDIO_FILE_PATH_FULL;
    //ReclyclerView and Upload

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        initLitener();

        // getShadesPreferences();

    }
    public void setShadesPreferences(){
        edito = getSharedPreferences("Register", MODE_PRIVATE).edit();
        if(!etTitle.getText().toString().isEmpty()){
            edito.putString("Titulo", etTitle.getText().toString());
            edito.putString("Descripcion", etDescripcion.getText().toString());
            edito.putString("Sentimientos", etSenimientos.getText().toString());
            edito.putString("Evaluacion", etEvaluacion.getText().toString());
            edito.putString("Analisis", etAnalisis.getText().toString());
            edito.putString("Conclusion", etConclusion.getText().toString());
            edito.putString("Plan", etPlan.getText().toString());
            edito.apply();
        }



    }

    public void getShadesPreferences(){
         prefs = getSharedPreferences("Register", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String T = prefs.getString("Titulo", "No name defined");
            String D = prefs.getString("Descripcion", "No name defined");
            String S = prefs.getString("Sentimientos", "No name defined");
            String E =  prefs.getString("Evaluacion", "No name defined");
            String A =  prefs.getString("Analisis", "No name defined");
            String C = prefs.getString("Conclusion", "No name defined");
            String P =  prefs.getString("Plan", "No name defined");
            etTitle.setText(T);
        }
    }
    private void initLitener() {
        HorizontalPicker picker= (HorizontalPicker) findViewById(R.id.datePicker);
        picker
                .setListener(Register.this)
                .init();

        picker.setBackgroundColor(Color.LTGRAY);

        picker.setDate(new DateTime());

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

                //RegisterPost(ed);

            }
        });



        View uno, dos, tres, cuatro, cinco, seis, siete;

        uno = findViewById(R.id.imFont);
        dos = findViewById(R.id.imGIF);
        tres = findViewById(R.id.imCamera);
        cuatro = findViewById(R.id.imDoc);
        cinco = findViewById(R.id.imMic);
        seis = findViewById(R.id.imSeleced);
        siete= findViewById(R.id.imGallery);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        siete.setOnClickListener(this);

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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        rcItems = (RecyclerView) findViewById(R.id.rvItems);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(1, 1);
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

//        setScroll();

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



    private void setScroll() {

        etDescripcion.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etDescripcion) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        etSenimientos.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etSenimientos) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        etEvaluacion.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etEvaluacion) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        etAnalisis.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etAnalisis) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        etConclusion.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etConclusion) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        etPlan.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.etPlan) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    /* @Override
     public void onClick(View v) {
         switch (v.getId()){


             case R.id.btnMoreDesc:

                     Intent desc = new Intent(Register.this, Descripting.class);
                   if(!etDescripcion.getText().toString().isEmpty()){
                       desc.putExtra("Descripcion", etDescripcion.getText().toString());
                   }
                       desc.putExtra("Titulo", etTitle.getText().toString());
                     startActivity(desc);

                 //
                 break;
            case R.id.btnMoreFeels:
                 Intent feelss= new Intent(Register.this, Feelings.class);
                if(etDescripcion.getText().toString().isEmpty()){
                    new MaterialDialog.Builder(this)
                            .content("Necesita agregar primero una descripción")
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
                     testing.putExtra("Paths", paths);
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
 */
   @Override
   public void onClick(View v) {

       switch (v.getId()) {

           case R.id.fab:

               morph.show();
               break;
           case R.id.imFont:

               //selectFont();

               break;
           case R.id.imGIF:

                /*if(!emojicons.isShown()){

                    emojicons.setVisibility(View.VISIBLE);
                    setEmojiconFragment(Boolean.FALSE);
                    //Hide softKeyboard
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }else{
                    emojicons.setVisibility(View.GONE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }*/

               break;
           case R.id.imCamera:
               OpenCamera();

               break;
           case R.id.imGallery:
               FishBun.with(Register.this)
                       .MultiPageMode()
                       .setMaxCount(4)
                       .setMinCount(1)
                       .setPickerSpanCount(5)
                       .setActionBarColor(Color.DKGRAY, Color.DKGRAY)
                       .setActionBarTitleColor(Color.parseColor("#ffffff"))
                       .setAlbumSpanCount(2, 3)
                       .setButtonInAlbumActivity(true)
                       .setCamera(true)
                       .exceptGif(true)
                       .setReachLimitAutomaticClose(true)
                       .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(Register.this, R.drawable.ic_arrow_back_white_24dp))
                       .setOkButtonDrawable(ContextCompat.getDrawable(Register.this, R.drawable.ic_add_a_photo_white_24dp))
                       .setAllViewTitle("Todos")
                       .setActionBarTitle("Seleccione ")
                       .textOnNothingSelected("Seleccione como maximo cuatro")
                       .startAlbum();
               break;

           case R.id.imMic:

               final CharSequence[] optione = { "Grabar", "Buscar"};
               new MaterialDialog.Builder(this)
                       .title("Seleccione")
                       .items(optione)
                       .itemsCallback(new MaterialDialog.ListCallback() {
                           @Override
                           public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                               switch (which){
                                   case 0:
                                       recordingAudio();
                                       Toast.makeText(Register.this, text.toString(), Toast.LENGTH_LONG).show();
                                       break;

                                   case 1:
                                       Intent intentA = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                       intentA.addCategory(Intent.CATEGORY_OPENABLE);
                                       intentA.setType("audio/*");
                                       intentA.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                       startActivityForResult(intentA, PICK_AUD_REQUEST);

                                       Toast.makeText(Register.this, text.toString(), Toast.LENGTH_LONG).show();
                                       break;
                               }
                           }
                       })
                       .show();


               break;
           case R.id.imDoc:

               FilePickerBuilder fpb= new FilePickerBuilder();
               //fpb.setActivityTheme(Apptheme);
               fpb.getInstance().setMaxCount(8)
                       .setSelectedFiles(paths)
                       .pickFile(this);
               break;


           default:
               morph.hide();
               break;

       }
       morph.hide();

   }//enOnClick



    private void OpenCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Descripting", "IOException"+ ex.getLocalizedMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() +".provider",photoFile));
                startActivityForResult(cameraIntent, PICK_IMG_REQUEST);
            }
        }
    }
    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File( Environment.getExternalStorageDirectory()+Constants.mImageDirectory);
        if (!storageDir.exists()) {
            storageDir.mkdir();

        }
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void recordingAudio() {
        File apkStorage = null;
        File outputFile = null;
        //Get File if SD card is present
        if (new CheckForSDCard().isSDCardPresent()) {

            apkStorage = new File(
                    Environment.getExternalStorageDirectory() + Constants.mAudioDirectory);
        } else
            Toast.makeText(this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

        //If File is not present create directory
        if (!apkStorage.exists()) {
            apkStorage.mkdir();

        }
        Long timestamp = System.currentTimeMillis() / 1000;
        AUDIO_FILE_PATH_FULL=AUDIO_FILE_PATH+timestamp+".mp3";
        AndroidAudioRecorder.with(Register.this)
                // Required
                .setFilePath(AUDIO_FILE_PATH_FULL)
                .setColor(ContextCompat.getColor(Register.this, R.color.base10))
                .setRequestCode(PICK_AUD_REC_REQUEST)
                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)
                // Start recording
                .record();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Adapter_Item ia;

        if (resultCode == RESULT_OK) {
            switch (requestCode){

                case PICK_DOC_REQUEST:
                    actReq=data;

                    //clipdataSelect(data);
                    String imageEncoded;
                    Uri mImageUri;
                    if (data.getData() != null) {
                        mImageUri = Uri.parse(data.getDataString());
                        // Get the cursor
                        try {
                            imageEncoded = upload.getFilePath(Register.this, mImageUri);
                            paths.add(imageEncoded);

                           /* ia = new Adapter_Item(paths, Register.this);
                            rcItems.setAdapter(ia);*/
                            //Toast.makeText(this, "Saved to:" + imageEncoded, Toast.LENGTH_SHORT).show();

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                   // Toast.makeText(this, "Saved to:" , Toast.LENGTH_LONG).show();
                    break;
                case PICK_AUD_REQUEST:
                    actReq=data;
                    clipdataSelect(data);
                    break;
                case PICK_AUD_REC_REQUEST:
                    if (resultCode == RESULT_OK) {

                        Toast.makeText(this, "Audio Grabado Correctamente!", Toast.LENGTH_SHORT).show();
                        if(!AUDIO_FILE_PATH_FULL.isEmpty())
                            paths.add(AUDIO_FILE_PATH_FULL);
                   /*  ia = new Adapter_Item(paths, Register.this);
                        rcItems.setAdapter(ia);
*/
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "El audio no se grabo correctamente", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case com.veer.multiselect.Util.Constants.REQUEST_CODE_MULTISELECT:

                    paths = data.getStringArrayListExtra(com.veer.multiselect.Util.Constants.GET_PATHS);
                   /* ia = new Adapter_Item(paths, Register.this);
                    rcItems.setAdapter(ia);
*/
                    break;
                case Define.ALBUM_REQUEST_CODE:

                    ArrayList<Uri> pathUri;
                    pathUri = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    for (int i = 0; i < pathUri.size(); i++) {
                        String realpath= " ";
                        try {
                            realpath = upload.getFilePath(Register.this, pathUri.get(i));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        paths.add(realpath);
                    }
                    /*ia = new Adapter_Item(paths, Register.this);
                    rcItems.setAdapter(ia);*/
                    //You can get image path(ArrayList<Uri>) Version 0.6.2 or later
                    break;
                case PICK_IMG_REQUEST:
                    paths.add(mCurrentPhotoPath);
                /*  ia = new Adapter_Item(paths, Register.this);
                    rcItems.setAdapter(ia);*/

                    break;
                case FilePickerConst.REQUEST_CODE_DOC:
                    if(resultCode== Activity.RESULT_OK && data!=null) {
                       paths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));

                      /* ia = new Adapter_Item(paths, Register.this);

                        rcItems.setAdapter(ia);*/
                    }
                    break;

            }
            HashSet hs = new HashSet();
            hs.addAll(paths);
            paths.clear();
             paths.addAll(hs);
            ia = new Adapter_Item(paths, Register.this);

            rcItems.setAdapter(ia);



        }//Fin resultCode

    }// Fin onActivityResult


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
            if(ide==null|| ide==""){
                new MaterialDialog.Builder(this)
                        .title("Necesita crear al menos una colaboración ")
                        .content("Desea crearla en este momento?")
                        .positiveText("Crear colaboración")
                        .negativeText("Cancelar")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent colaboracion =new Intent(Register.this, Colaboration.class);
                                startActivity(colaboracion);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        });

            }else{
                if (!etTitle.getText().toString().isEmpty() && !etDescripcion.getText().toString().isEmpty() ) {

                    new ShowProgressDialog().MaterialDialogMsj(Register.this, true, "Publicando");

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.insertpublicacion,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    upload.recorrerListPaths(paths, Register.this, actReq, ed);
                                    //  failed_regpublication.setText(R.string.message_succes_publication);
                                    com.dese.diario.Utils.FirebaseService.FirebaseMessagingService fms= new com.dese.diario.Utils.FirebaseService.FirebaseMessagingService();
                                    fms.setUserGrup(Register.this, ide, titulo);
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
                                        Toast.makeText(getApplicationContext(),"Intente maás tarde "+error.getMessage(),Toast.LENGTH_LONG).show();
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
                    new ShowProgressDialog().MaterialDialogMsj(Register.this, false, "Publicando");
                    String Mensaje=("Debe rellenar todos los campos");
                    //failed_regpublication.setText(Mensaje);
                    Toast.makeText(Register.this,Mensaje , Toast.LENGTH_LONG).show();

                }//fin else cheked

            }



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
                final Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {

                    TaskStackBuilder.create(this)

                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {


                    if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                        back_pressed = System.currentTimeMillis();
                        intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        final AlertDialog alert = new AlertDialog.Builder(this).create();

                        alert.setMessage("Desea salir de su descripción");
                        alert.setButton(Dialog.BUTTON_POSITIVE,("Continuar"),new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.setButton(Dialog.BUTTON_NEGATIVE, ("Salir"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                                NavUtils.navigateUpTo(Register.this, upIntent);

                            }
                        });

                        alert.show();
                    }
                }
                return true;
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
                Apptheme=R.style.AppTheme11;
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                Apptheme=R.style.AppTheme2;

                break;
            case 3:
                setTheme(R.style.AppTheme3);
                Apptheme=R.style.AppTheme3;

                break;
            case 4:
                setTheme(R.style.AppTheme4);
                Apptheme=R.style.AppTheme4;

                break;
            case 5:
                setTheme(R.style.AppTheme5);
                Apptheme=R.style.AppTheme5;

                break;
            case 6:
                setTheme(R.style.AppTheme6);
                Apptheme=R.style.AppTheme6;

                break;
            case 7:
                setTheme(R.style.AppTheme7);
                Apptheme=R.style.AppTheme7;

                break;
            case 8:
                setTheme(R.style.AppTheme8);
                Apptheme=R.style.AppTheme8;

                break;
            case 9:
                setTheme(R.style.AppTheme9);
                Apptheme=R.style.AppTheme9;

                break;
            case 10:
                setTheme(R.style.AppTheme10);
                Apptheme=R.style.AppTheme10;

                break;
            case 11:
                setTheme(R.style.AppTheme);
                Apptheme=R.style.AppTheme;

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

                        etTitle.setText(getD.getString("Titulo"));
                        etDescripcion.setText(getD.getString("Descripcion"));
                        paths= getIntent().getStringArrayListExtra("Paths");
                        etSenimientos.setText(getD.getString("Sentimientos"));
                        etEvaluacion.setText(getD.getString("Evaluacion"));
                        etAnalisis.setText(getD.getString("Analisis"));
                        etConclusion.setText(getD.getString("Conclusion"));
                        etPlan.setText(getD.getString("Plan"));

                        if(!paths.isEmpty()){
                            ia = new Adapter_Item(paths, Register.this);
                            rcItems.setAdapter(ia);
                        }


                    }

                }


    private void clipdataSelect(Intent data) {

        String imageEncoded;
        Uri mImageUri;
        if (data.getData() != null) {
            mImageUri = Uri.parse(data.getDataString());
            // Get the cursor
            try {
                imageEncoded= upload.getFilePath(Register.this, mImageUri);
                paths.add(imageEncoded);
             //   ia = new Adapter_Item(paths, Register.this);
             //   rcItems.setAdapter(ia);

//                Toast.makeText(Publication.this, "Data->"+ imageEncoded, Toast.LENGTH_SHORT).show();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        } else{
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    try {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();

                        String realpath = upload.getFilePath(Register.this, uri);

                        mArrayUri.add(uri);


                        paths.add(realpath);
                       // Toast.makeText(Register.this, "Clipdata"+realpath, Toast.LENGTH_SHORT).show();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        ia = new Adapter_Item(paths, Register.this);
        rcItems.setAdapter(ia);
    }


}
