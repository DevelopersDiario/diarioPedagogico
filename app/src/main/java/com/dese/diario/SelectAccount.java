package com.dese.diario;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Resource.OnBoarding.OnboardingActivity;
import com.dese.diario.Resource.ShowProgressDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SelectAccount extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    final static String url = Urls.createaccount;
    final static String urls = Urls.login;
    //Buttons
    Button btnLoginEmail, btnCreateAccount;

    //ResourceCA
    Button btnCreateAccountCA;
    EditText etEmailViewCA, etPassViewCA, etPassConfViewCA;
    private ImageButton imgProfileViewCA;
    //Image Loader
    private ImageLoader imageLoader;

    //Gmail Resources
    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    //facebook Resource
    private LoginButton loginButton;
    CallbackManager callbackManager;
    //Parametres
    final String KEY_NOMBRE = "nombre";
    final String KEY_APELLIDOS = "apellidos";
    final String KEY_PASSWORD = "password";
    final String KEY_EMAIL = "correo";
    final String KEY_CUENTA = "cuenta";
    final String KEY_VIGENCIA = "vigencia";
    final String KEY_9999 = "9999-12-31";
    final String KEY_IDUSUARIO = "idusuario";
    final String KEY_TELEFONO = "telefono";
    final String KEY_FOTO = "foto";
    VariablesLogin var_Login;
    String datos = "";

    TextView tv1;
    public static int MILISEGUNDOS_ESPERA = 5000;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;


    final String CONTENT_TYPE = "Content-Type";
    final String APPLICATION = "application/x-www-form-urlencoded";
    final String KEY_PREFERENCE = "my_preferences";
    final String KEY_ONBOARDING = "onboarding_complete";


    ShowProgressDialog spd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_select_account);

        spd= new ShowProgressDialog();

        eventsButtons();

        inicializarResourceGmail();

        inicializarResourceFacebook();

        inicializarResources();

        state1();


    }


    private void inicializarResourceFacebook() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        callbackManager = CallbackManager.Factory.create();   // para hacer llamado al manager de facebook y hacer el login
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_facebook); // Boton desde el cual se hace el llamado
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));  // nos da los permisos para obtener algunos datos del face para el caso del email es necesario especificarlo
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { // metodo para hacer el logeo se dividira en las tres posibles opciones
            // el onSucces que es un acceso correcto, onCancel que es cancelado y onError
            @Override
            public void onSuccess(LoginResult loginResult) {

                // login ok get access token
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                try {

                                    if (BuildConfig.DEBUG) {
                                        FacebookSdk.setIsDebugEnabled(true);
                                        FacebookSdk
                                                .addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

                                        System.out
                                                .println(R.string.AccessToken_getCurrentAccessToken
                                                        + AccessToken
                                                        .getCurrentAccessToken()
                                                        .toString());

                                        final String name = com.facebook.Profile.getCurrentProfile().getName();
                                        final String mail = object.getString("email");
                                        final String lastname = com.facebook.Profile.getCurrentProfile().getLastName();
                                        final String token = com.facebook.Profile.getCurrentProfile().getId();
                                        final String account = com.facebook.Profile.getCurrentProfile().getName();

                                        Toast.makeText(SelectAccount.this, name, Toast.LENGTH_LONG).show();

                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(SelectAccount.this, R.string.Su_registro_realizo_con_Exito, Toast.LENGTH_LONG).show();
                                                        finishLogin();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                String body;

                                                if (error.networkResponse.data != null) {

                                                    try {
                                                        body = new String(error.networkResponse.data, "UTF-8");
                                                        userLogin(mail, token);


                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }//Fin onErrorResponse


                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put(KEY_NOMBRE, name);
                                                params.put(KEY_APELLIDOS, lastname);
                                                params.put(KEY_EMAIL, mail);
                                                params.put(KEY_PASSWORD, token);
                                                params.put(KEY_CUENTA, account);
                                                params.put(KEY_VIGENCIA, KEY_9999);
                                                params.put(CONTENT_TYPE, APPLICATION);
                                                return params;
                                            }

                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(SelectAccount.this);
                                        requestQueue.add(stringRequest);

                                    }
                                } catch (JSONException e) {
                                    Log.e(getResources().getString(R.string.cero), e.toString());
                                }
                            }

                        });
                request.executeAsync();
                Bundle parameters = new Bundle();
                parameters.putString(getResources().getString(R.string.fields), getResources().getString(R.string.id_name_email)); // se controlan los datos q se obtienen
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                Toast.makeText(SelectAccount.this, "Cancelado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

                Log.e("(*<*)---->", error.toString());
            }
        });


    }



    private void inicializarResources() {
        getOnBoarding();
        tv1 = (TextView) findViewById(R.id.tvMessageWelcome);

    }

    private void getOnBoarding() {
        SharedPreferences preferences =
                getSharedPreferences(KEY_PREFERENCE, MODE_PRIVATE);

        if(!preferences.getBoolean(KEY_ONBOARDING,false)){

            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);

            finish();
            return;
        }
    }



    /*---onClicks------*/
    private void eventsButtons() {
        //Buttons
        //Button LoginActivity
        btnLoginEmail = (Button) findViewById(R.id.btn_login_email);
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(SelectAccount.this, LoginActivity.class);
                startActivity(iLogin);
            }
        });

        //Button CreateAccount
        btnCreateAccount = (Button) findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCreateAccount = new Intent(SelectAccount.this, CreateAccount.class);
                startActivity(iCreateAccount);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Intent iCA= new Intent (SelectAccount.this, CreateAccount.class);
            // startActivity(iCA);
            //Calling signin}
         signIn();

           spd.progressDilog(SelectAccount.this, true);

        }

    }

    /*-----------Gmail Sign In------------------*/
    private void inicializarResourceGmail() {

        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.login_google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin

            handleSignInResult(result);

        }//end if
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result)  {
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            final String name=  acct.getDisplayName();
            final String nameonly[]= name.split(" ", 2);
            final String na= nameonly[0];
            final String mail= acct.getEmail();
            final String lastname= acct.getFamilyName();
            final String token = acct.getId();
            final String account=acct.getDisplayName();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(SelectAccount.this,R.string.Su_registro_realizo_con_Exito, Toast.LENGTH_LONG).show();
                            finishLogin();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body;

                    if(error.networkResponse.data!=null) {

                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            userLogin( mail, token);



                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }//Fin onErrorResponse

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NOMBRE, na);
                    params.put(KEY_APELLIDOS, lastname);
                    params.put(KEY_EMAIL, mail);
                    params.put(KEY_PASSWORD, token);
                    params.put(KEY_CUENTA, account);
                    params.put(KEY_VIGENCIA, KEY_9999);
                    params.put(CONTENT_TYPE, APPLICATION);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }//End if
        else {
            //If login fails
            Toast.makeText(this, R.string.Login_Failed, Toast.LENGTH_LONG).show();
        }//end else

    }//end handleSignInResult
    /**---LOGIN---**/
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void userLogin(String mail, String token) {
        // System.out.println("prueba");
        try {
            if (isOnline()) {

                System.out.println(R.string.user_Login_entra);
                AsyncTask task = new SelectAccount.ObtenerDatos();
                String[][] parametros = {
                        {urls.toString()},
                        {KEY_EMAIL, KEY_PASSWORD},
                        {mail, token}};
                task.execute(parametros);
//                Toast.makeText(LoginActivity.this,mEmailView.getText().toString()+mPasswordView.getText().toString(),Toast.LENGTH_LONG).show();

            }
        }catch (Exception ex){
            System.out.println(ex);
        }
    }//Fin UserLogin
    private class ObtenerDatos extends AsyncTask<String[], Void, String> {
        @Override
        protected String doInBackground(String[]... parametros) {
            try {
                return ConexionServer(parametros[0][0], parametros[1], parametros[2]);
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String resultado){
            try {

                String datoslogin;
                JSONObject jsonObject = new JSONObject(resultado.toString());
                //   txtToken.setText(jsonObject.getString("success"));
                var_Login = new VariablesLogin();
                datoslogin = jsonObject.getString(getResources().getString(R.string.Success));

                if (datoslogin.length()>=10){
                    JSONArray jsonArray = new JSONArray(jsonObject.getString(getResources().getString(R.string.Success)));
                    for (int x=0; x<jsonArray.length(); x++) {
                        JSONObject json = new JSONObject(jsonArray.get(x).toString());
                        var_Login.setIdusuario(json.getString(KEY_IDUSUARIO));
                        var_Login.setCuenta(json.getString(KEY_CUENTA));
                        var_Login.setCorreo(json.getString(KEY_EMAIL));
                        var_Login.setTelefono(json.getString(KEY_TELEFONO));
                        var_Login.setTelefono(json.getString(KEY_FOTO));


                    }
                    //openProfile();
                  finishLogin();

                }
                else {

                    Toast.makeText(SelectAccount.this,"Failded!!...Verifique Email Y Password",Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {

            }

        }
    }

    private String ConexionServer(String dir_url,String[] variables, String[] valores) throws IOException {
        URL url = new URL(dir_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(60000);
        conn.setConnectTimeout(60000);
        conn.setRequestMethod("GET");
        int total = variables.length;
        for (int i = 0; i < total; i++) {
            conn.setRequestProperty(variables[i], valores[i]);
        }
        conn.setDoInput(true);
        conn.connect();
        datos = readStream(conn.getInputStream());
        conn.disconnect();
        System.out.println("datosserver: "+datos);
        return datos;

    }


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
            Toast.makeText(getBaseContext(), "Presione otra vez para salir!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }









    @Override
    protected void onStart() {
        super.onStart();
        state1();

    }

    public void state1(){
        //  Toast.makeText(Shared.this, "State1", Toast.LENGTH_SHORT).show();
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.state);
        tv1.setText(R.string.tag_message_selected);
        tv1.startAnimation(animation);

        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {


                movedSalida1();
            }
        });

    }


    private void  movedSalida1(){
        //  Toast.makeText(Shared.this, "Mover Salida1", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevoutput);
        tv1.setText(R.string.tag_message_selected);
        tv1.startAnimation(animation2);

        animation2.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {

                // Toast.makeText(Shared.this, "Hadler1 moved", Toast.LENGTH_SHORT).show();
                // state();
                moveEntrada2();
            }
        });

    }//End

    private void moveEntrada2() {
        //   Toast.makeText(Shared.this, "Mover Entrada2", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevinput);
        tv1.setText(R.string.tag_message_selected2);
        tv1.startAnimation(animation);

        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        //  state2();
                        movedSalida2();

                    }
                }, MILISEGUNDOS_ESPERA);


            }
        });
    }

    private void  movedSalida2(){
        //   Toast.makeText(Shared.this, "Mover Salida2", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevoutput);
        tv1.setText(R.string.tag_message_selected2);
        tv1.startAnimation(animation2);

        animation2.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {

                moveEntrada3();

            }
        });

    }//End

    private void moveEntrada3() {


        //  Toast.makeText(Shared.this, "Mover Entrada3", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevinput);
        tv1.setText(R.string.tag_message_selected3);
        tv1.startAnimation(animation3);

        animation3.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        moverSalida3();

                    }
                }, MILISEGUNDOS_ESPERA);
            }
        });


    }

    private void moverSalida3() {
        //  Toast.makeText(Shared.this, "Mover Salida3", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevoutput);
        tv1.setText(R.string.tag_message_selected3);
        tv1.startAnimation(animation3);

        animation3.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {

                moveEntarda1();

            }
        });

    }//End

    private void moveEntarda1() {

        //Toast.makeText(Shared.this, "Mover Entrada1", Toast.LENGTH_SHORT).show();
        //tv1.setVisibility(View.INVISIBLE);
        android.view.animation.Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movevinput);
        tv1.setText(R.string.tag_message_selected);
        tv1.startAnimation(animation3);

        animation3.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {


            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        state1();

                    }
                }, MILISEGUNDOS_ESPERA);
            }
        });


    }

    private void finishLogin() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
