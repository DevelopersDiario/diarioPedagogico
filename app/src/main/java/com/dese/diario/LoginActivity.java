package com.dese.diario;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Resource.ShowProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    final String correo= "correo";
    final String password1= "password";
    final String idusuario= "idusuario";
    final String cuenta= "cuenta";
    final String telefono= "telefono";
    final String foto= "foto";
    final String Success="Success";
    final String GET="GET";
    final static String url= Urls.login;
    //Resource
    String datos = "";
    // private EditText mEmailView;
    public EditText mPasswordView;

   public  AutoCompleteTextView mEmailView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView failedLoginMessage, mForgetPassword;
    View focusView = null;
    private String email;
    private String password;
    VariablesLogin var_Login;
    ShowProgressDialog spd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         spd= new ShowProgressDialog();
        inicializarButton();

        //Hide softKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }//Fin oNCreate

    private void inicializarButton() {
        mEmailView= (AutoCompleteTextView) findViewById(R.id.tvEmail_Login);
        //       populateAutoComplete();
        failedLoginMessage = (TextView)findViewById(R.id.failed_login);
        mPasswordView=(EditText)findViewById(R.id.tvPassword_Login);
        //    buttonLogin = (Button) findViewById(R.id.email_sign_in_button);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                   // attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                failedLoginMessage.setText("");
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mForgetPassword = (TextView) findViewById(R.id.tvForgetPassword);
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fp = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(fp);
            }
        });
    }



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

    private void attemptLogin(){
        boolean mCancel = this.loginValidation();
        if (mCancel) {
            focusView.requestFocus();
        } else {
            if(isNetworkConnected()){
            spd.progressDilog(LoginActivity.this, true);
            userLogin();
            }else{
                showAlertDialog(getResources().getString(R.string.title_conection_fail),
                        getResources().getString(R.string.message_need_conection));
            }


        }
    }
    // Check if the device is connected to the Internet
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {

            return false;
        } else
            return true;
    }

    private void showAlertDialog(String title, String message) {
        final AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setButton(Dialog.BUTTON_POSITIVE,("Ajustes"),new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });
        alert.setButton(Dialog.BUTTON_NEGATIVE,"Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.show();
    }

    private boolean loginValidation() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        return cancel;
    }

  /*  private void populateAutoComplete(){
        String[] countries = getResources().getStringArray(R.array.autocomplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,countries);
        mEmailView.setAdapter(adapter);
    }*/

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private void userLogin() {
        // System.out.println("prueba");
        try {
            if (isOnline()) {
                System.out.println(R.string.user_Login_entra);
                //URL url = new URL("http://192.168.1.87:8084/diariopws/api/1.0/user/autenticar");
                AsyncTask task = new ObtenerDatos();
                String[][] parametros = {
                        {url.toString()},
                        {correo, password1},
                        {mEmailView.getText().toString(), mPasswordView.getText().toString()}};
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
                datoslogin = jsonObject.getString(Success);

                if (datoslogin.length()>=10){
                    JSONArray jsonArray = new JSONArray(jsonObject.getString(Success));
                    for (int x=0; x<jsonArray.length(); x++) {
                        JSONObject json = new JSONObject(jsonArray.get(x).toString());
                        var_Login.setIdusuario(json.getString(idusuario));
                        var_Login.setCuenta(json.getString(cuenta));
                        var_Login.setCorreo(json.getString(correo));
                        var_Login.setTelefono(json.getString(telefono));
                        var_Login.setTelefono(json.getString(foto));


                    }
                    //openProfile();
                    finishLogin();

                }
                else {
                    //spd.progressDilog(LoginActivity.this, false);
                    Toast.makeText(LoginActivity.this,R.string.Failded,Toast.LENGTH_LONG).show();
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
        conn.setRequestMethod(GET);
        int total = variables.length;
        for (int i = 0; i < total; i++) {
            conn.setRequestProperty(variables[i], valores[i]);
        }
        conn.setDoInput(true);
        conn.connect();
        datos = readStream(conn.getInputStream());
        conn.disconnect();
       // System.out.println("datosserver: "+datos);
        return datos;

    }

    /*private void openProfile(){
        showProgress(true);
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        // Intent intent = new Intent(this, MainActivity.class);
        // intent.putExtra(KEY_EMAIL, email);
        startActivity(intent);


    }*/

    private void finishLogin() {

        SharedPreferences preferences =
                getSharedPreferences(getString(R.string.my_preferences2), MODE_PRIVATE);

        preferences.edit()
                .putBoolean(getString(R.string.login_complete),true).apply();

        Intent main = new Intent(this, MainActivity.class);

        startActivity(main);


        finish();
    }




}
