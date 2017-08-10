package com.dese.diario.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderP;
import com.dese.diario.Item.MyHolderR;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Publication;
import com.dese.diario.Objects.RePublication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_RePubication extends RecyclerView.Adapter<MyHolderR> {
    ArrayList<RePublication> listaRepublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;
    int selectedPos;
    public Adapter_RePubication(ArrayList<RePublication> listaRepublicaciones, Context context) {


        this.listaRepublicaciones = listaRepublicaciones;
        this.context = context;

    }

    @Override
    public MyHolderR onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_repost, parent, false);


        return new MyHolderR(view);
    }

    @Override
    public void onBindViewHolder(MyHolderR holder, int position) {


        final String t = listaRepublicaciones.get(position).getTitulo();
        final String u = listaRepublicaciones.get(position).getNombre();
        final String f = listaRepublicaciones.get(position).getFoto();
        final String r=listaRepublicaciones.get(position).getRuta();
        final String o = listaRepublicaciones.get(position).getObservaciones();

        holder.titlePublicationR.setText(t);
        holder.tvUserRecR.setText(u);
        holder.tvPublicationRecR.setText(o);
     Picasso.with(context)
                .load(Urls.download + f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRecR);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
              //  openDetailActivity(t, u, d, p, f, pa);

                //Toast.makeText(context,"Select+"+ pa,Toast.LENGTH_SHORT).show();
            }
        });

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos =pos;

            }
        });



    }


    @Override
    public int getItemCount() {
        return (null != listaRepublicaciones ? listaRepublicaciones.size() : 0);
        //return listapublicaciones.size();
    }


   /* private void openDetailActivity(String t, String u, String d, String p, String f , final String pa ) {
        Intent i = new Intent(context, DetailPublication.class);

        //PACK DATA TO SEND
        i.putExtra("TITLE_KEY", t);
        i.putExtra("USER_KEY", u);
        i.putExtra("DATA_KEY", d);
        i.putExtra("PUBLI_KEY", p);
        i.putExtra("FOTO_KEY", f);
        listarRepublicaciones(pa);

        //open activity
        context.startActivity(i);

    }*/






}




