package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolder;
import com.dese.diario.Item.MyHolderF;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.Objects.DataFriends;
import com.dese.diario.Objects.Urls;
import com.dese.diario.R;
import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 12/05/2017.
 */

public class Adapter_Friends extends RecyclerView.Adapter<MyHolderF > {
    ArrayList<DataFriends> lista;
    Context context;
    int selectedPos;
    public ChipsView mChipsView;

    public Adapter_Friends(ArrayList lista, Context context) {
        this.lista=lista;
        this.context=context;


    }

    @Override
    public MyHolderF onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friends, parent, false);
        MyHolder holder = new MyHolder(view);
        return new MyHolderF(view);
    }

    @Override
    public void onBindViewHolder(final MyHolderF  holder, int position) {
        holder._idA.setText(lista.get(position).getIdusuario());
        holder.name.setText(lista.get(position).getNombre()+" "+lista.get(position).getApellidos());

        holder.cuen.setText("@"+lista.get(position).getCuenta());
        int r= Integer.parseInt(lista.get(position).getIdrol());
        //Contact contact = new Contact(null, null, null, email, null);

        String role = " ";
        final String f=lista.get(position).getFoto();

        Picasso.with(context)
                .load(Urls.download+f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);
        switch (r){
            case  1:
                role="Asesor";
             
                break;
            case 2:
                role="Alumno";
                break;
            case 3:
                role="Invitado";
                break;
            default:
                role="Otro";
                break;
        }
        holder.tvIdRol.setText(role);

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos = pos;
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //mChipsView = (ChipsView) holder.findViewById(R.id.cv_contacts);
                String email =lista.get(pos).getCuenta();
                Contact contact = new Contact(null, null, null, email, null);

                Toast.makeText(context,"Click"+pos+lista.get(pos).getCuenta() ,Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return (null!=lista ?lista.size() :0);
        //return listapublicaciones.size();
    }



}