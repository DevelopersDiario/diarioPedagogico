package com.dese.diario.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolder;
import com.dese.diario.Item.MyHolderF;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Objects.DataFriends;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 12/05/2017.
 */

public class Adapter_Friends extends RecyclerView.Adapter<MyHolderF > {
    private static final String KEY_IDG = "idgrupo";
    private static final String KEY_IDU= "idusuario";
    private static final String KEY_IDR = "idrol";

    ArrayList<DataFriends> lista;
    Context context;
    int selectedPos;
    public ChipsView mChipsView;

    ArrayList listMembers;
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

        holder.tvIdRol.setText("1");

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos = pos;
            }
        });

       // listarMember(lista.get(position).getGrupotmp());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Contact contact;

                String email =lista.get(pos).getCuenta();
                String grupi=lista.get(pos).getGrupotmp();


            }
        });
    }



    @Override
    public int getItemCount() {
        return (null!=lista ?lista.size() :0);
        //return listapublicaciones.size();
    }


}