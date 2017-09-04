package com.dese.diario;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Utils.Urls;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    final static String url= Urls.createaccount;



    final  String KEY_NOMBRE="nombre";
    final String KEY_APELLIDOS="apellidos";
    final  String KEY_PASSWORD="password";
    final  String KEY_EMAIL="correo";
    final  String KEY_CUENTA="cuenta";

    String name;
    String lastname;
    String account;
    String password;
    String email;
    private View createAccount_progress;
    private View CreateAccount_form;
    View focusView = null;
    //Resource
    EditText etName_CreateAcc,etLastName_CreateAcc,  etEmail_CreateAcc,  etPassword_CreateAcc,  etPasswordConfirme_CreateAcc;
    TextView failed_createAccount;
    CheckBox chkbTerms_CreateAcc;

    Button btnCreateAccount_CreateAcc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        inicializarResources();

        //Hide softKeyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buttons();
    }

    private void buttons() {
        TextView txtTerms= (TextView) findViewById(R.id.txtTerms_CreateAcc);
        txtTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent terms = new Intent(CreateAccount.this, Terms.class);
                startActivity(terms);

            }
        });
    }

    private void inicializarResources() {
        etName_CreateAcc = (EditText) findViewById(R.id.etName_CreateAcc);
        etLastName_CreateAcc = (EditText) findViewById(R.id.etLastName_CreateAcc);
        etEmail_CreateAcc = (EditText) findViewById(R.id.etEmail_CreateAcc);
        etPassword_CreateAcc = (EditText)findViewById(R.id.etPassword_CreateAcc);
        etPasswordConfirme_CreateAcc = (EditText)findViewById(R.id.etPasswordConfirme_CreateAcc);
        chkbTerms_CreateAcc = (CheckBox)findViewById(R.id.chkbTerms_CreateAcc);
        failed_createAccount =(TextView)findViewById(R.id.failed_createAccount);
        btnCreateAccount_CreateAcc = (Button) findViewById(R.id.btnCreateAccount_CreateAcc);
        btnCreateAccount_CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == btnCreateAccount_CreateAcc){
                    try {
                        if(isNetworkConnected()) {
                            RegisterUser();
                        }else{
                            showAlertDialog(getResources().getString(R.string.title_conection_fail),
                                    getResources().getString(R.string.message_need_conection));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                failed_createAccount.setText("");

            }});
        createAccount_progress = findViewById(R.id.createAccount_progress);
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

    private void RegisterUser() throws JSONException{
        if(etPassword_CreateAcc.getText().toString().trim().matches(etPasswordConfirme_CreateAcc.getText().toString().trim())) {
        } else{
            Toast.makeText(CreateAccount.this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
            etPassword_CreateAcc.setText("");
            etPassword_CreateAcc.requestFocus();
            etPasswordConfirme_CreateAcc.setText("");
        }//fin else valid_passw
        if (chkbTerms_CreateAcc.isChecked()) {
            name = etName_CreateAcc.getText().toString().trim();
            lastname = etLastName_CreateAcc.getText().toString().trim();
            email = etEmail_CreateAcc.getText().toString().trim();
            String delimiter= "@";
            String temp []= email.split(delimiter, 2);
            account= temp[0];
            // final String cue ta=account.toString();
            password=etPassword_CreateAcc.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null && response.length() > 0) {
                                Toast.makeText(CreateAccount.this, account + " Su registro se realizo con Exito!!", Toast.LENGTH_LONG).show();
                                openLogin(email);
                            } else {
                                new MaterialDialog.Builder(CreateAccount.this)
                                        .title("¡Error de conexión!")
                                        .content("Lo lamentamos pero hubo un error en el servidor")
                                        .show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                   String body;
                    if (error.networkResponse.data != null) {

                            try {

                                body = new String(error.networkResponse.data, "UTF-8");

                                Toast.makeText(CreateAccount.this, body, Toast.LENGTH_LONG).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                    }else if(error.networkResponse.data==(null)){
                        new MaterialDialog.Builder(CreateAccount.this)
                                .title("¡Error de conexión!")
                                .content("Lo lamentamos pero hubo un error en el servidor"+" ("+error.getMessage()+")")
                                .show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NOMBRE, name);
                    params.put(KEY_APELLIDOS, lastname);
                    params.put(KEY_EMAIL, email);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_CUENTA, account);
                    params.put("vigencia", "9999-12-31");
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }//Fin isChecked
        else{
            String Mensaje="\"Debe aceptar el acuerdo para proceder con el registro\"";
            failed_createAccount.setText(Mensaje);
            Toast.makeText(CreateAccount.this,Mensaje , Toast.LENGTH_LONG).show();

        }//fin else cheked




    }//Fin RegisterUser

    private void openLogin(String mail) {
        // showProgress(true);
        Intent intent= new Intent(CreateAccount.this,LoginActivity.class);
        intent.putExtra("mail", mail);
        startActivity(intent);
        finish();
    } //Fin open login


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            CreateAccount_form.setVisibility(show ? View.GONE : View.VISIBLE);
            CreateAccount_form.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    CreateAccount_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            createAccount_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            createAccount_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    createAccount_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            createAccount_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            CreateAccount_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }//Fin show progress
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();

        // The activity is about to be destroyed.
    }


}
