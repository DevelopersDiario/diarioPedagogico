package com.dese.diario;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class facebook extends AppCompatActivity {

    /* Esta clase hace la comunicacion entre los datos obtenidos de facebook y la actividad donde se mostraran dichos datos*/

    String name;
    String lastname;
    String email;
    String url_imagen;

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        name = getIntent().getStringExtra("Nombre");
        lastname = getIntent().getStringExtra("Apellido");
        email = getIntent().getStringExtra("Correo");
        url_imagen = getIntent().getStringExtra("Foto");

        TextView username = (TextView)findViewById(R.id.nameuser);
        username.setText(name);

        TextView apellido = (TextView)findViewById(R.id.apeuser);
        apellido.setText(lastname);

        TextView correo = (TextView)findViewById(R.id.emailuser);
        correo.setText(email);

        imagen = (ImageView)findViewById(R.id.imageuser);

        DescargaImagen n= new DescargaImagen();
        n.execute();
    }
    class DescargaImagen extends AsyncTask<Void,Void,Void> {

        Bitmap descarga =null;
        @Override
        protected Void doInBackground(Void... params) {

            try {//descargamos la imagen de perfil del usuario
                descarga = Picasso.with(facebook.this).load(url_imagen).get();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            if(descarga!=null){
                imagen.setImageBitmap(descarga);
            }
            super.onPostExecute(aVoid);
        }
    }
}
