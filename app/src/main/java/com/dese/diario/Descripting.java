package com.dese.diario;

import android.animation.Animator;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
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
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dese.diario.Adapter.Adapter_Item;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.POJOS.Reflexion;
import com.dese.diario.Utils.CheckForSDCard;
import com.dese.diario.Utils.Constants;
import com.dese.diario.Utils.Upload;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

public class Descripting extends AppCompatActivity implements View.OnClickListener {

    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    //KEYS
    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";

    //FAB
    private FABToolbarLayout morph;
    FloatingActionButton fab;

    //REQUEST FILE
    private final int SELECT_PICTURE = 300;
    private final int PICK_DOC_REQUEST = 1;
    private final int PICK_AUD_REC_REQUEST = 2;
    private final int PICK_AUD_REQUEST = 3;
    private final int PICK_IMG_REQUEST = 4;
    //private final int PICK_VID_REQUEST = 6;

    //ReclyclerView and Upload
    ArrayList<String> paths = new ArrayList<>();
    private RecyclerView rcItems;
    Upload upload;
    Intent actReq;
    Adapter_Item ia;

    //Bind
    EditText etDescriptingM;

    private String mCurrentPhotoPath;

    private static  String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + Constants.mAudioDirectory+ "/Audio";
    private static String AUDIO_FILE_PATH_FULL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripting);

        initView();

        initListener();



    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Descripting.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        rcItems = (RecyclerView) findViewById(R.id.rvItems);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(3, 1);
        rcItems.setLayoutManager(staggeredGridLayout);

        etDescriptingM= (EditText)findViewById(R.id.etDescriptingM);

        validateWriteEditText();

    }//endView

    private void validateWriteEditText() {
        if(etDescriptingM.isActivated()){
            fab.setScaleX(0);
            fab.setScaleY(0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                        android.R.interpolator.fast_out_slow_in);

                fab.animate()
                        .scaleX(1)
                        .scaleY(1)
                        .setInterpolator(interpolador)
                        .setDuration(2600)
                        .setStartDelay(1000)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                fab.animate()
                                        .scaleY(0)
                                        .scaleX(0)
                                        .setInterpolator(interpolador)
                                        .setDuration(600)
                                        .start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
            }

        }
    }

    private void initListener() {

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


    }//endListener


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
                FishBun.with(Descripting.this)
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
                        .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(Descripting.this, R.drawable.ic_arrow_back_white_24dp))
                        .setOkButtonDrawable(ContextCompat.getDrawable(Descripting.this, R.drawable.ic_add_a_photo_white_24dp))
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
                                        Toast.makeText(Descripting.this, text.toString(), Toast.LENGTH_LONG).show();
                                        break;

                                    case 1:
                                        Intent intentA = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                        intentA.addCategory(Intent.CATEGORY_OPENABLE);
                                        intentA.setType("audio*//*");
                                        intentA.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        startActivityForResult(intentA, PICK_AUD_REQUEST);

                                        Toast.makeText(Descripting.this, text.toString(), Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        })
                        .show();


                break;
            case R.id.imDoc:


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                String [] mimeTypes = {"application/msword", "application/pdf", "application/vnd.ms-powerpoint", "application/vnd.ms-excel"};
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, PICK_DOC_REQUEST);

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
                Log.i("Descripting", "IOException");
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
        AndroidAudioRecorder.with(Descripting.this)
                // Required
                .setFilePath(AUDIO_FILE_PATH_FULL)
                .setColor(ContextCompat.getColor(Descripting.this, R.color.base10))
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
                   clipdataSelect(data);

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
                        ia = new Adapter_Item(paths, Descripting.this);
                        rcItems.setAdapter(ia);

                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "El audio no se grabo correctamente", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case com.veer.multiselect.Util.Constants.REQUEST_CODE_MULTISELECT:

                    paths = data.getStringArrayListExtra(com.veer.multiselect.Util.Constants.GET_PATHS);
                    ia = new Adapter_Item(paths, Descripting.this);
                    rcItems.setAdapter(ia);

                    break;
                case Define.ALBUM_REQUEST_CODE:


                    ArrayList<Uri> pathUri;
                    pathUri = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    for (int i = 0; i < pathUri.size(); i++) {
                        String realpath= " ";
                        try {

                            realpath = upload.getFilePath(Descripting.this, pathUri.get(i));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        paths.add(realpath);

                    }
                    ia = new Adapter_Item(paths, Descripting.this);
                    rcItems.setAdapter(ia);

                    //You can get image path(ArrayList<Uri>) Version 0.6.2 or later
                    break;
                case PICK_IMG_REQUEST:
                       paths.add(mCurrentPhotoPath);
                        ia = new Adapter_Item(paths, Descripting.this);
                        rcItems.setAdapter(ia);

                    break;

            }


        }//Fin resultCode

    }// Fin onActivityResult

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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String textMore;
        switch (id) {


            case R.id.action_post:
                Intent d= new Intent(Descripting.this, Register.class);
                if(!etDescriptingM.getText().equals(null)){

                    textMore=etDescriptingM.getText().toString();
                    d.putExtra("Descripcion", textMore);
                     d.putExtra("Paths", paths);
                    startActivity(d);
                    finish();


                   // Toast.makeText(this, "Texto"+textMore, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "No guardo", Toast.LENGTH_SHORT).show();
                }



                // failed_regpublication.setText("");
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void clipdataSelect(Intent data) {

        String imageEncoded;
        Uri mImageUri;
        if (data.getData() != null) {
            mImageUri = Uri.parse(data.getDataString());
            // Get the cursor
            try {
                imageEncoded= upload.getFilePath(Descripting.this, mImageUri);
                paths.add(imageEncoded);

                ia = new Adapter_Item(paths, Descripting.this);
                rcItems.setAdapter(ia);

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

                        String realpath = upload.getFilePath(Descripting.this, uri);

                        mArrayUri.add(uri);


                        paths.add(realpath);
                        ia = new Adapter_Item(paths, Descripting.this);
                        rcItems.setAdapter(ia);
                        Toast.makeText(Descripting.this, "Clipdata"+realpath, Toast.LENGTH_SHORT).show();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}
