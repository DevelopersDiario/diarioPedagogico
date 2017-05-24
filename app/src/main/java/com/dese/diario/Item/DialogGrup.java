package com.dese.diario.Item;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dese.diario.R;
import com.dese.diario.Resource.DataBase.AdminBD;

public class DialogGrup extends AppCompatActivity {

    //USUARIOS//

    public static final String NAMEG = "nombregrupo";
    public static final String USUARIOGRUPO= "usuarioalumno";
    public static final String FECHA = "fecha";
    public static final String VIGENCIA = "vigenciahasta";

    public static final int GRUPO = 200;

    EditText txtNameG;
    EditText txtUsuario;
    EditText txtDate;
    EditText txtVige;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_grup);


    }


    /*public void regresar(){



        String nombreg = txtNameG.getText().toString();
        String usuariog = txtUsuario.getText().toString();
        String date = txtDate.getText().toString();
        String vigen = txtVige.getText().toString();


        AdminBD db= new AdminBD(this, "DIARIO", null, 2);//BASE DE DATOS
        db.AltaG(nombreg, usuariog, date, vigen);

        Intent regreso = new Intent();

        regreso.putExtra(NAMEG, nombreg);
        regreso.putExtra(USUARIOGRUPO, usuariog);
        regreso.putExtra(FECHA, date);
        regreso.putExtra(VIGENCIA, vigen);


        setResult(GRUPO,regreso);
        // startActivityForResult(regreso, GRUPO);


        finish();
    }*/
}
