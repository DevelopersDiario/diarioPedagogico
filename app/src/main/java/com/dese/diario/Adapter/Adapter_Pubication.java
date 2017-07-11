package com.dese.diario.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Colaboration;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderP;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.MainActivity;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Publication;
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

public class Adapter_Pubication extends RecyclerView.Adapter<MyHolderP> {
    ArrayList<Publication> listapublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;

    public Adapter_Pubication(ArrayList<Publication> listapublicaciones, Context context) {


        this.listapublicaciones = listapublicaciones;
        this.context = context;

    }

    @Override
    public MyHolderP onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_publication, parent, false);



        return new MyHolderP(view);
    }

    @Override
    public void onBindViewHolder(MyHolderP holder, int position) {


        final String d = listapublicaciones.get(position).getFecha();
        final String p = listapublicaciones.get(position).getObservaciones();
        final String u = listapublicaciones.get(position).getNombre();
        final String t = listapublicaciones.get(position).getTitulo();
        final String f = listapublicaciones.get(position).getFoto();
        final String pa = listapublicaciones.get(position).getIdpublicacion();


        holder.tvFechaRec.setText(d);
        holder.tvPublicationRec.setText(p);
        holder.tvUserRec.setText(u);
        holder.titlePublication.setText(t);
        Picasso.with(context)
                .load(Urls.fotouser + f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(t, u, d, p, f, pa);

                //Toast.makeText(context,"Entra a itemClicklistener "+ pa,Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnRPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListRepublication rp= new ListRepublication();
             //   DetailPublication dr= new DetailPublication();
                rp.AlertRepublication(pa, context);
               // dr.listarRe(pa);
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != listapublicaciones ? listapublicaciones.size() : 0);
        //return listapublicaciones.size();
    }


    private void openDetailActivity(String t, String u, String d, String p, String f , final String pa ) {
        Intent i = new Intent(context, DetailPublication.class);
        ListRepublication rp= new ListRepublication();

        //PACK DATA TO SEND
        i.putExtra("TITLE_KEY", t);
        i.putExtra("USER_KEY", u);
        i.putExtra("DATA_KEY", d);
        i.putExtra("PUBLI_KEY", p);
        i.putExtra("FOTO_KEY", f);
        i.putExtra("PAPA", pa);

        //open activity

        context.startActivity(i);

    }

}




