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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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
import com.dese.diario.Utils.Upload;
import com.dese.diario.Utils.Urls;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class Feedback extends AppCompatActivity implements View.OnClickListener {
    //Toolbar
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    Intent intent;

    private FloatingActionButton fab;
    private FABToolbarLayout morph;
    EditText etFeedBaack;

    //REQUEST FILE
    private final int SELECT_PICTURE = 300;
    private final int PICK_DOC_REQUEST = 1;
    private final int PICK_AUD_REC_REQUEST = 2;
    private final int PICK_AUD_REQUEST = 3;
    private final int PICK_IMG_REQUEST = 4;
    private final int PICK_VID_REQUEST = 5;
    private String mCurrentPhotoPath;

    private static String AUDIO_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + Constants.mAudioDirectory + "/Audio";
    private static String AUDIO_FILE_PATH_FULL;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;


    //Mukltimedia
    Intent actReq;
    //Upload
    Upload upload;
    ArrayList<String> paths = new ArrayList<>();
    private RecyclerView rcItems;
    Adapter_Item ia;

    //repub
    final static String url = Urls.repuplication;

    final String KEY_IDUSUARIO = "idusuario";
    final String KEY_TITULO = "titulo";
    final String KEY_OBSERVACIONES = "observaciones";
    final String KEY_IDGROUP = "idgrupo";
    final String KEY_ROL = "idrol";
    final String KEY_IDPUBLICACION = "idpublicacion";
    final String CONTENT_TYPE = "Content-Type";
    final String APPLICATION = "application/x-www-form-urlencoded";

    String gpo, pa, cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Feedback.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
        bindActivity();


    }

    private void bindActivity() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        View uno, dos, tres, cuatro, cinco, seis, siete;

        uno = findViewById(R.id.imFont);
        dos = findViewById(R.id.imGIF);
        tres = findViewById(R.id.imCamera);
        cuatro = findViewById(R.id.imDoc);
        cinco = findViewById(R.id.imMic);
        seis = findViewById(R.id.imSeleced);
        siete = findViewById(R.id.imGallery);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        siete.setOnClickListener(this);

        etFeedBaack = (EditText) findViewById(R.id.etFeedBack);


        rcItems = (RecyclerView) findViewById(R.id.rvItemsFeedback);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(1, 1);
        rcItems.setLayoutManager(staggeredGridLayout);
        upload = new Upload();


        Intent i = this.getIntent();

        gpo = i.getExtras().getString("Gpo");
        pa = i.getExtras().getString("Papa");
        cont = i.getExtras().getString("Cont");

        etFeedBaack.setText(cont);
        etFeedBaack.requestFocus();

    }


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


                break;
            case R.id.imCamera:
                OpenCamera();

                break;
            case R.id.imGallery:
                selectedTypeGallery();
                break;

            case R.id.imMic:

                final CharSequence[] optione = {"Grabar", "Buscar"};
                new MaterialDialog.Builder(this)
                        .title("Seleccione")
                        .items(optione)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        recordingAudio();
                                        Toast.makeText(Feedback.this, text.toString(), Toast.LENGTH_LONG).show();
                                        break;

                                    case 1:
                                        Intent intentA = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                        intentA.addCategory(Intent.CATEGORY_OPENABLE);
                                        intentA.setType("audio/*");
                                        intentA.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        startActivityForResult(intentA, PICK_AUD_REQUEST);

                                        //Toast.makeText(Register.this, text.toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        })
                        .show();


                break;
            case R.id.imDoc:

                FilePickerBuilder fpb = new FilePickerBuilder();
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

    }//OnClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Adapter_Item ia;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PICK_DOC_REQUEST:
                    actReq = data;

                    //clipdataSelect(data);
                    String imageEncoded;
                    Uri mImageUri;
                    if (data.getData() != null) {
                        mImageUri = Uri.parse(data.getDataString());
                        // Get the cursor
                        try {
                            imageEncoded = upload.getFilePath(Feedback.this, mImageUri);
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
                    actReq = data;
                    clipdataSelect(data);
                    break;
                case PICK_VID_REQUEST:
                    actReq = data;
                    String imageEncoded2;
                    Uri mImageUri2;
                    if (data.getData() != null) {
                        mImageUri2 = Uri.parse(data.getDataString());
                        // Get the cursor
                        try {
                            imageEncoded2 = upload.getFilePath(Feedback.this, mImageUri2);
                            paths.add(imageEncoded2);

                           /* ia = new Adapter_Item(paths, Register.this);
                            rcItems.setAdapter(ia);*/
                            //Toast.makeText(this, "Saved to:" + imageEncoded, Toast.LENGTH_SHORT).show();

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case PICK_AUD_REC_REQUEST:
                    if (resultCode == RESULT_OK) {

                        Toast.makeText(this, "Audio Grabado Correctamente!", Toast.LENGTH_SHORT).show();
                        if (!AUDIO_FILE_PATH_FULL.isEmpty())
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
                        String realpath = " ";
                        try {
                            realpath = upload.getFilePath(Feedback.this, pathUri.get(i));
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
                    if (resultCode == Activity.RESULT_OK && data != null) {
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
            ia = new Adapter_Item(paths, Feedback.this);

            rcItems.setAdapter(ia);


        }//Fin resultCode

    }// Fin onActivityResult


    /*------------Theme choose by user--------------------*/
    private void theme() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            back_pressed = System.currentTimeMillis();
            intent = new Intent(Feedback.this, Register.class);
            startActivity(intent);
            finish();
        } else {
            final AlertDialog alert = new AlertDialog.Builder(this).create();

            alert.setMessage("Desea salir de la retroalimentación" + "\n" + "Se perderán todos los cambios no guardados.");
            alert.setButton(Dialog.BUTTON_POSITIVE, ("Cancelar"), new DialogInterface.OnClickListener() {

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
                String c = etFeedBaack.getText().toString();
                registerRePost(gpo, c, pa, gpo);
                //Toast.makeText(Feedback.this, "Daros:"+ gpo+ c+pa+gpo, Toast.LENGTH_SHORT).show();
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
                        intent = new Intent(Feedback.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        final AlertDialog alert = new AlertDialog.Builder(this).create();

                        alert.setMessage("Desea salir de la retroalimentación" + "\n" + "Se perderán todos los cambios no guardados.");
                        alert.setButton(Dialog.BUTTON_POSITIVE, ("Cancelar"), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alert.setButton(Dialog.BUTTON_NEGATIVE, ("Descartar"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                                NavUtils.navigateUpTo(Feedback.this, upIntent);

                            }
                        });

                        alert.show();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

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
                            Toast.makeText(Feedback.this, "Su comentario se registro.", Toast.LENGTH_SHORT).show();
                            upload.recorrerListPaths(paths, Feedback.this, actReq, grupe);
                            restartActivity();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;
                    String statusCode = String.valueOf(error.networkResponse.statusCode);

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
            RequestQueue requestQueue = Volley.newRequestQueue(Feedback.this);
            requestQueue.add(stringRequest);
        } else {
            String Mensaje = ("Debe rellenar todos los campos");
            Toast.makeText(Feedback.this, Mensaje, Toast.LENGTH_LONG).show();

        }//fin else cheked


    }

    private void restartActivity() {
        Intent reFe = new Intent(this, MainActivity.class);
        startActivity(reFe);
    }


    private void selectedTypeGallery() {
        final CharSequence[] optione = {"Imagenes", "Videos"};
        new MaterialDialog.Builder(this)
                .title("Seleccione")
                .items(optione)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                FishBun.with(Feedback.this)
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
                                        .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(Feedback.this, R.drawable.ic_arrow_back_white_24dp))
                                        .setOkButtonDrawable(ContextCompat.getDrawable(Feedback.this, R.drawable.ic_add_a_photo_white_24dp))
                                        .setAllViewTitle("Todos")
                                        .setActionBarTitle("Seleccione ")
                                        .textOnNothingSelected("Seleccione como maximo cuatro")
                                        .startAlbum();
                                break;

                            case 1:
                                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                galleryIntent.setType("video/*");
                                startActivityForResult(galleryIntent, PICK_VID_REQUEST);

                                break;
                        }
                    }
                })
                .show();
    }


    private void OpenCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Descripting", "IOException" + ex.getLocalizedMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", photoFile));
                startActivityForResult(cameraIntent, PICK_IMG_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + Constants.mImageDirectory);
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
        AUDIO_FILE_PATH_FULL = AUDIO_FILE_PATH + timestamp + ".mp3";
        AndroidAudioRecorder.with(Feedback.this)
                // Required
                .setFilePath(AUDIO_FILE_PATH_FULL)
                .setColor(ContextCompat.getColor(Feedback.this, R.color.base10))
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

    private void clipdataSelect(Intent data) {

        String imageEncoded;
        Uri mImageUri;
        if (data.getData() != null) {
            mImageUri = Uri.parse(data.getDataString());
            // Get the cursor
            try {
                imageEncoded = upload.getFilePath(Feedback.this, mImageUri);
                paths.add(imageEncoded);
                //   ia = new Adapter_Item(paths, Register.this);
                //   rcItems.setAdapter(ia);

//                Toast.makeText(Publication.this, "Data->"+ imageEncoded, Toast.LENGTH_SHORT).show();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        } else {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                for (int i = 0; i < mClipData.getItemCount(); i++) {
                    try {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();

                        String realpath = upload.getFilePath(Feedback.this, uri);

                        mArrayUri.add(uri);


                        paths.add(realpath);
                        // Toast.makeText(Register.this, "Clipdata"+realpath, Toast.LENGTH_SHORT).show();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        ia = new Adapter_Item(paths, Feedback.this);
        rcItems.setAdapter(ia);
    }
}
