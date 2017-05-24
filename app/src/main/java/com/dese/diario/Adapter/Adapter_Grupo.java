package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dese.diario.Colaboration;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolder;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.Objects.Group;
import com.dese.diario.R;

import java.util.ArrayList;

/**
 * Created by Eduardo on 12/05/2017.
 */

public class  Adapter_Grupo extends RecyclerView.Adapter<MyHolder> {

    ArrayList<Group> listagrupo;
    Context context;
    int selectedPos;

    public Adapter_Grupo(ArrayList listgrupos, Colaboration grupo) {

        this.listagrupo = listgrupos;
        this.context = grupo;
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grupo, parent, false);
        MyHolder holder = new MyHolder(view);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.NombreG.setText(listagrupo.get(position).getNombregrupo());
        holder.tvNameU.setText(listagrupo.get(position).getNombreusuario());//+"->ROL: "+listagrupo.get(position).getIdrol());
        holder.tvIdG.setText(listagrupo.get(position).getIdgrupo());

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos =pos;

            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

                selectGroup(pos);
                //Toast.makeText(c,name,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void selectGroup(int pos){
        selectedPos = Integer.parseInt(listagrupo.get(pos).getIdgrupo());
        Intent sf= new Intent(context, Search_friends.class);
        String n =listagrupo.get(pos).getNombregrupo();
        String i= listagrupo.get(pos).getIdgrupo();
        sf.putExtra("namegpo", n);
        sf.putExtra("gpo", i);
        context.startActivity(sf);
      //  Toast.makeText(context, "POSA-->"+ n+i, Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return (null != listagrupo ? listagrupo.size() : 0);
        //return listapublicaciones.size();
    }


}