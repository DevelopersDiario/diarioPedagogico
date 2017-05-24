package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderP;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Publication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_Pubication extends RecyclerView.Adapter<MyHolderP> {
    ArrayList<Publication> listapublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;

    public Adapter_Pubication(ArrayList<Publication> listapublicaciones, Context context) {
        this.listapublicaciones=listapublicaciones;
        this.context=context;

    }

    @Override
    public MyHolderP onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_publication,parent,false);
        return new MyHolderP(view);
    }

    @Override
    public void onBindViewHolder(MyHolderP holder, int position) {


        final  String d=listapublicaciones.get(position).getFecha();
        final String p=listapublicaciones.get(position).getObservaciones();
        final String u=listapublicaciones.get(position).getNombre();
        final String t=listapublicaciones.get(position).getTitulo();
        final String f=listapublicaciones.get(position).getFoto();

        holder.tvFechaRec.setText(d);
        holder.tvPublicationRec.setText(p);
        holder.tvUserRec.setText(u);
        holder.titlePublication.setText(t);
        Picasso.with(context)
                .load(Urls.fotouser+f)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(t,u, d, p, f);
                //Toast.makeText(c,name,Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return (null!=listapublicaciones ?listapublicaciones.size() :0);
        //return listapublicaciones.size();
    }



    private void openDetailActivity(String t, String u, String d, String p, String f)
    {
        Intent i=new Intent(context, DetailPublication.class);

        //PACK DATA TO SEND
        i.putExtra("TITLE_KEY", t);
        i.putExtra("USER_KEY", u);
        i.putExtra("DATA_KEY", d);
        i.putExtra("PUBLI_KEY", p);
        i.putExtra("FOTO_KEY", f);



        //open activity
        context.startActivity(i);

    }

}
