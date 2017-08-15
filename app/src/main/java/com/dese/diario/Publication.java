package com.dese.diario;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Item;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Utils.Font;
import com.dese.diario.Utils.Upload;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Publication extends AppCompatActivity implements  View.OnClickListener,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{
    final static String urlLisGpo= Urls.listgrupo;

    Intent intent;

    //Permissions
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 110;
    private final int MY_PERMISSIONS = 100;
    private RelativeLayout mRlView;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 39;
    //resorce post publication
    final static String url= Urls.insertpublicacion;
    EmojiconEditText edPublication;
    Button btnPostearPub;
    EditText ettitlepost;

    Typeface TF1, TF2, TF3, TF4, TF5, TF6, TF7, TF8, TF9, TF10, TF11, TF12, TF13, TF14;
    public static final String KEY_NAMEG = "nombregrupo";

    final  String KEY_IDUSUARIO="idusuario";
    final  String KEY_TITULO="titulo";
    final String KEY_OBSERVACIONES="observaciones";
    final  String KEY_IDGROUP="idgrupo";
    TextView failed_regpublication;

    String idusuario;
    String titulo;
    String observaciones;
    String padre;
    //Action
    FloatingActionButton fab;



    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    String colorfonde;

    //Toolbar & Drwable
    Toolbar toolbar;
    final Context context = this;
    Boolean homeButton = false, themeChanged;

    //date
    Date d;
    WebView myWebView;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    TextView tvDate;

    //KEYS
    final  String KEY_VALUE="VALUES";
    final  String KEY_THEME="THEME";

    //REQUEST FILE
    private final int SELECT_PICTURE = 300;
    private final int PICK_DOC_REQUEST = 1;
    private final int PICK_IMG_REQUEST = 2;
    private final int PICK_AUD_REQUEST = 3;
    private final int PICK_VID_REQUEST = 4;

    //Dates Upload
    ArrayList<String> paths = new ArrayList<>();
    private RecyclerView rcItems;


    private FABToolbarLayout morph;
    ImageView imPictures1, imPictures2, imPictures3, imPictures4;
  //  ImageView imgSelect1, imgSelect2, imgSelect3;
    List leadsNames, leadsIdes;
    ArrayAdapter mLeadsAdapter;
    Spinner spGpoP;
    String ed;
    String emoji;

    final public String formatofecha="d, MMMM 'del' yyyy";
    final public String formatosimple="h:mm a";
    final  String CONTENT_TYPE="Content-Type";
    final  String APPLICATION="application/x-www-form-urlencoded";
    Font font ;




    //
    FrameLayout emojicons;


    Upload upload;
    //
    Intent actReq;
    String type;
    Adapter_Item ia;
    int contador = 0;
    ImageView imFile1, imFile2, imFile3;
    int color1, color2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        d = new Date();
        font = new Font();
        checkPermissions();
        inicializarToolbar();
        bindActivity();
        startedResource();
        startedDate();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.
                    READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;

            }
        }


    }


    private void bindActivity() {
        myWebView = (WebView) this.findViewById(R.id.wvDate);
        tvDate = (TextView) findViewById(R.id.tvDate);
        upload = new Upload();
        listGpo();
        rcItems = (RecyclerView) findViewById(R.id.rvItems);
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(3, 1);
        rcItems.setLayoutManager(staggeredGridLayout);

        font.getContext(Publication.this);
        emojicons= (FrameLayout) findViewById(R.id.emojicons);

        edPublication = (EmojiconEditText)findViewById(R.id.etPublication);
        edPublication.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (emojicons.isShown())
                    emojicons.setVisibility(View.GONE);
                return false;
            }
        });


    }

    //Resource
    private void startedResource() {
        //Cast resouce post Diario

        ettitlepost = (EditText)findViewById(R.id.ettitlepost);
        failed_regpublication =(TextView)findViewById(R.id.failed_registerpost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);



        View uno, dos, tres, cuatro, cinco, seis;

        uno = findViewById(R.id.imFont);
        dos = findViewById(R.id.imGIF);
        tres = findViewById(R.id.imCamera);
        cuatro = findViewById(R.id.imDoc);
        cinco = findViewById(R.id.imMic);
        seis = findViewById(R.id.imSeleced);

        fab.setOnClickListener(this);
        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);

        String font_path_Abtrakt, font_path_AbteciaBasic, font_path_ActionJackson, font_path_AcuteAbove;
        String font_path_Adore64, AdventureSubtitlesNormal, AtoZ, Congratulations_DEMO, DKPimpernel;
        String KGPPrimaryItalics, Maria_lucia, PENMP, Precursive_1_FREE, PWSchoolScript, PWScolarpaper;

        font_path_Abtrakt = "font/font1.ttf";
        font_path_AbteciaBasic = "font/font2.ttf";
        font_path_ActionJackson = "font/font3.ttf";
        font_path_AcuteAbove = "font/font4.ttf";
        font_path_Adore64 = "font/font5.ttf";
        AdventureSubtitlesNormal = "font/font6.ttf";
        AtoZ = "font/font7.ttf";
        Congratulations_DEMO = "font/font8.ttf";
        DKPimpernel = "font/font9.otf";
        KGPPrimaryItalics= "font/font10.ttf";
        Maria_lucia= "font/font11.ttf";
        PENMP= "font/font12.TTF";
        Precursive_1_FREE= "font/font13.otf";
        PWScolarpaper= "font/font15.ttf";


        TF1 = Typeface.createFromAsset(getAssets(), font_path_Abtrakt);
        TF2 = Typeface.createFromAsset(getAssets(), font_path_AbteciaBasic);
        TF3 = Typeface.createFromAsset(getAssets(), font_path_ActionJackson);
        TF4 = Typeface.createFromAsset(getAssets(), font_path_AcuteAbove);
        TF5 = Typeface.createFromAsset(getAssets(), font_path_Adore64);
        TF6 = Typeface.createFromAsset(getAssets(), AdventureSubtitlesNormal);
        TF7 = Typeface.createFromAsset(getAssets(), AtoZ);
        TF8 = Typeface.createFromAsset(getAssets(), Congratulations_DEMO);
        TF9 = Typeface.createFromAsset(getAssets(), DKPimpernel);
        TF10 = Typeface.createFromAsset(getAssets(), KGPPrimaryItalics);
        TF11 = Typeface.createFromAsset(getAssets(), Maria_lucia);
        TF12 = Typeface.createFromAsset(getAssets(), PENMP);
        TF13 = Typeface.createFromAsset(getAssets(), Precursive_1_FREE);
        TF14 = Typeface.createFromAsset(getAssets(), PWScolarpaper);


        imPictures1 =(ImageView)findViewById(R.id.imViewPublication1);
        imPictures2 =(ImageView) findViewById(R.id.imViewPublication2);
        imPictures3 =(ImageView)findViewById(R.id.imViewPublication3);
        imPictures4 =(ImageView) findViewById(R.id.imViewPublication4);

        imFile1= (ImageView) findViewById(R.id.imFile1);
       imFile2= (ImageView) findViewById(R.id.imFile2);
       imFile3= (ImageView) findViewById(R.id.imFile3);

    }

    public void listGpo() {
        final VariablesLogin variablesLogin= new VariablesLogin();
        final String id=variablesLogin.getIdusuario().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLisGpo,
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
                                    mLeadsAdapter = new ArrayAdapter<String>(Publication.this,
                                            android.R.layout.simple_spinner_item, leadsNames);
                                    mLeadsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    spGpoP.setAdapter(mLeadsAdapter);
                                    spGpoP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
                                        {

                                            if(view.callOnClick()){
                                                ed= (String) leadsIdes.get(pos);
                                                spGpoP.isEnabled();
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent)
                                        {    }
                                    });
                                }



                            } catch (JSONException e) {
                                Log.e(getString(R.string.message_Oops), getString(R.string.message_Publication_Error) + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getString(R.string.carita), getString(R.string.message_ocurrio_error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idusuario", id);
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };

        queue.add(stringRequest);

    }

    public void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Publication.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }

        myWebView = (WebView) this.findViewById(R.id.wvDate);
        tvDate = (TextView) findViewById(R.id.tvDate);

    }
    protected Boolean estaConectado(){
        if(conectadoWifi()){
            showAlertDialog(Publication.this, getString(R.string.message_Conexion_a_Internet),
                    getString(R.string.message_Conexion_a_Wifi), true);
            return true;
        }else{
            if(conectadoRedMovil()){
                showAlertDialog(Publication.this, getString(R.string.message_Conexion_a_Internet),
                        getString(R.string.message_Conexion_movil), true);
                return true;
            }else{
                showAlertDialog(Publication.this, getString(R.string.message_Conexion_a_Internet),
                        getString(R.string.message_no_conexion_internet), false);
                return false;
            }
        }
    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//Context parameter
        builder.setTitle(title);
        builder.setIcon((status) ? R.drawable.ic_cancel_black_24dp: R.drawable.saveblack);
        builder.setPositiveButton(R.string.message_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do stuff
            }
        });
        builder.setMessage(message);
        builder.create();
    }//End showAlertDialog


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startedDate() {

        if(estaConectado()){
            myWebView.setVisibility(View.VISIBLE);
            tvDate.setVisibility(View.INVISIBLE);
            myWebView.loadUrl(getString(R.string.pagina_del_dia)+colorfonde+".png&texto=FFFFFF");
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.isEnabled();

        }else {
            if(!estaConectado())
            {
                myWebView.setVisibility(View.INVISIBLE);
                tvDate.setVisibility(View.VISIBLE);

                SimpleDateFormat fecc=new SimpleDateFormat(formatofecha);
                String fechacComplString = fecc.format(d);

                SimpleDateFormat ho=new SimpleDateFormat(formatosimple);
                String horaString = ho.format(d);

                tvDate.setText(fechacComplString + " "+ horaString);
            }

        }//end Else

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onRestart() {
        super.onRestart();
        startedDate();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            back_pressed = System.currentTimeMillis();
            intent = new Intent(Publication.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            final AlertDialog alert = new AlertDialog.Builder(this).create();

            alert.setMessage(getResources().getString(R.string.message_whis_saved));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
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
            case R.id.action_saved:
                try {
                    RegisterPost(ed);
                    //upload.uploadMultipartFile(actReq, Publication.this, type, ed);
                    //final File file = new File(actReq.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                  //  Toast.makeText(this, "Intent="+file.getName(),  Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                failed_regpublication.setText("");
                break;

            case android.R.id.home:

                if (!homeButton) {
                    NavUtils.navigateUpFromSameTask(Publication.this);
                }
                if (homeButton) {
                    if (!themeChanged) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.download), false);
                        editor.apply();
                    }
                    intent = new Intent(Publication.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    return true;




                }

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
                colorfonde= getString(R.string.colorRed);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                colorfonde=getString(R.string.colorCian);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                colorfonde=getString(R.string.colorOliva);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                colorfonde= getString(R.string.colorOrange);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                colorfonde=getString(R.string.colorVioleta);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                colorfonde=getString(R.string.colorVioleta);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                colorfonde=getString(R.string.colorVioleta);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                colorfonde=getString(R.string.colorBlue);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                colorfonde=getString(R.string.colorBlue);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                colorfonde=getString(R.string.colorCian);
                break;
            case 11:
                setTheme(R.style.AppTheme);
                colorfonde=getString(R.string.colorBlue);
                break;
            default:
                setTheme(R.style.AppTheme);
                colorfonde=getString(R.string.colorBlue);
                break;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.fab:
                morph.show();
                break;
            case R.id.imFont:

               selectFont();

                break;
            case R.id.imGIF:
                if(!emojicons.isShown()){

                    emojicons.setVisibility(View.VISIBLE);
                    setEmojiconFragment(Boolean.FALSE);
                    //Hide softKeyboard
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }else{
                    emojicons.setVisibility(View.GONE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }


                break;
            case R.id.imCamera:

                FishBun.with(Publication.this)
                        .MultiPageMode()
                        .setMaxCount(4)
                        .setMinCount(1)
                        .setPickerSpanCount(5)
                        .setActionBarColor(R.style.AppTheme, R.style.AppTheme2)
                        .setActionBarTitleColor(Color.parseColor("#ffffff"))
                        .setAlbumSpanCount(2, 3)
                        .setButtonInAlbumActivity(true)
                        .setCamera(true)
                        .exceptGif(true)
                        .setReachLimitAutomaticClose(true)
                        .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(Publication.this, R.drawable.ic_arrow_back_white_24dp))
                        .setOkButtonDrawable(ContextCompat.getDrawable(Publication.this, R.drawable.ic_add_a_photo_white_24dp))
                        .setAllViewTitle("Todos")
                        .setActionBarTitle("Seleccione ")
                        .textOnNothingSelected("Seleccione como maximo cuatro")
                        .startAlbum();

                break;

            case R.id.imMic:
                //String msg="Puede Iniciar la Grabacion";
                //Recording r= new Recording();
                //r.dialogGrabar(this,msg);
               /* new MaterialFilePicker()
                        .withActivity(Publication.this)
                        .withRequestCode(PICK_AUD_REQUEST)
                        .withFilter(Pattern.compile(".*\\.(?:wav|mp3|ogg|3gp)$"))// Filtering files and directories by file name using regexp
                        .withTitle("Seleccione  un archivo")
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();*/
                Intent intentA = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intentA.addCategory(Intent.CATEGORY_OPENABLE);
                intentA.setType("audio/*");
                intentA.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intentA, PICK_AUD_REQUEST);

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

    }

    private void setEmojiconFragment(boolean useSystemDefault)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
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

                case com.veer.multiselect.Util.Constants.REQUEST_CODE_MULTISELECT:

                    paths = data.getStringArrayListExtra(com.veer.multiselect.Util.Constants.GET_PATHS);

                    ia = new Adapter_Item(paths, Publication.this);
                    rcItems.setAdapter(ia);

                    break;
                case Define.ALBUM_REQUEST_CODE:


                    ArrayList<Uri> pathUri;
                    pathUri = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                    for (int i = 0; i < pathUri.size(); i++) {
                        String realpath= " ";
                        try {

                            realpath = upload.getFilePath(Publication.this, pathUri.get(i));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        paths.add(realpath);

                    }
                    ia = new Adapter_Item(paths, Publication.this);
                    rcItems.setAdapter(ia);

                    //You can get image path(ArrayList<Uri>) Version 0.6.2 or later
                    break;

            }


        }//Fin resultCode

    }// Fin onActivityResult

    private void clipdataSelect(Intent data) {

        String imageEncoded;
        Uri mImageUri;
        if (data.getData() != null) {
            mImageUri = Uri.parse(data.getDataString());
            // Get the cursor
            try {
                imageEncoded= upload.getFilePath(Publication.this, mImageUri);
                paths.add(imageEncoded);

                ia = new Adapter_Item(paths, Publication.this);
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

                        String realpath = upload.getFilePath(Publication.this, uri);

                        mArrayUri.add(uri);


                        paths.add(realpath);
                        ia = new Adapter_Item(paths, Publication.this);
                        rcItems.setAdapter(ia);
                        Toast.makeText(Publication.this, "Clipdata"+realpath, Toast.LENGTH_SHORT).show();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void selectFont(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.formato);
        final String [] t= getResources().getStringArray(R.array.font_name);


        b.setItems(t, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                switch(which){
                    case 0:
                        edPublication.setTypeface(Typeface.DEFAULT);

                        break;
                    case 1:
                        edPublication.setTypeface(TF1);

                        break;
                    case 2:
                        edPublication.setTypeface(TF2);

                        break;
                    case 3:
                        edPublication.setTypeface(TF3);
                        break;
                    case 4:
                        edPublication.setTypeface(TF4);
                        break;
                    case 5:
                        edPublication.setTypeface(TF5);
                        break;
                    case 6:
                        edPublication.setTypeface(TF6);
                        break;
                    case 7:
                        edPublication.setTypeface(TF7);
                        break;
                    case 8:
                        edPublication.setTypeface(TF8);
                        break;
                    case 9:
                        edPublication.setTypeface(TF9);
                        break;
                    case 10:
                        edPublication.setTypeface(TF10);
                        break;
                    case 11:
                        edPublication.setTypeface(TF11);
                        break;
                    case 12:
                        edPublication.setTypeface(TF12);
                        break;
                    case 13:
                        edPublication.setTypeface(TF13);
                        break;
                    case 14:
                        edPublication.setTypeface(TF14);

                }
            }

        });

        b.show();
    }//End Alert Font



    ///Post publication

       private void RegisterPost(final String  ide) throws JSONException {
        final VariablesLogin  varlogin =new VariablesLogin();

        if (!ettitlepost.getText().toString().isEmpty() && !edPublication.getText().toString().isEmpty()) {
            idusuario= varlogin.getIdusuario();
            titulo = ettitlepost.getText().toString().trim();
            observaciones = edPublication.getText().toString().trim();
            padre="0";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            upload.recorrerListPaths(paths, Publication.this, actReq, ed);
                           // upload.uploadMultipart(Publication.this, actReq, ed);
                            failed_regpublication.setText(R.string.message_succes_publication);

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
                            body = new String(error.networkResponse.data, "UTF-8");

                            failed_regpublication.setText(body);


                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_IDUSUARIO,idusuario );
                    params.put(KEY_TITULO, titulo);
                    params.put(KEY_IDGROUP, ide);
                    params.put(KEY_OBSERVACIONES, observaciones);
                    params.put(CONTENT_TYPE, APPLICATION);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }//Fin isChecked
        else{
            String Mensaje=("Debe rellenar todos los campos");
            failed_regpublication.setText(Mensaje);
            Toast.makeText(Publication.this,Mensaje , Toast.LENGTH_LONG).show();

        }//fin else cheked



    }//Fin RegisterPost

    private void openMainactivity() {
        // showProgress(true);
        Intent intent= new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();
    } //Fin open login








    /**
    **mayRequestPermission*
                            * */

    private  void checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                mayRequestStoragePermission();
            } else {
                Log.d("Home", "Already granted access");
                //initializeView(v);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Home", "Permission Granted");
                    // initializeView(v);
                } else {
                    Log.d("Home", "Permission Failed");
                    Toast.makeText(this.getBaseContext(), "You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
            }
            // Add additional cases for other permissions you may have asked for
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


    // ***** Métodos de la interfaz EmojiconGridFragment.OnEmojiconClickedListener *****

    @Override
    /**
     * Este método se ejecuta cuando se hace click en el fragment de los emojicons
     * @param emojicon Emojicon en el que se hace click
     */
    public void onEmojiconClicked(Emojicon emojicon)
    {
        // Ponemos el emojicon en el que hemos hecho click en el EmojiconEditText mEditEmojicon
        EmojiconsFragment.input(edPublication, emojicon);
    }

    // *********************************************************************************

    // ***** Métodos de la interfaz EmojiconsFragment.OnEmojiconBackspaceClickedListener *****

    @Override
    /**
     * Este método se ejecuta cuando se pulsa el backspace del fragment de los emojicons
     */
    public void onEmojiconBackspaceClicked(View v)
    {
        EmojiconsFragment.backspace(edPublication);
    }

    // ***************************************************************************************

}
