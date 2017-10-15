package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.dese.diario.R;



public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener, View.OnClickListener {
    public TextView NombreG;
    public   TextView tvIdG;
    public  TextView tvNameU;

    MyLongClickListener longClickListener;
    ItemClickListener itemClickListener;

    private SparseBooleanArray seleccionados;

    public MyHolder(View itemView) {
        super(itemView);

        this.NombreG = (TextView)itemView.findViewById(R.id.NombreG);
        this.tvIdG = (TextView)itemView.findViewById(R.id.tvIdG);
        this.tvNameU= (TextView) itemView.findViewById(R.id.tvNameU);


        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);

        seleccionados = new SparseBooleanArray();
    }

    public void setLongClickListener(MyLongClickListener longClickListener)
    {
        this.longClickListener=longClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onLongClick(getLayoutPosition());
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //OUR CONTEXT MENU
        // menu.setHeaderTitle("Seleccione: ");
        //menu.add(0,0,0,"Agregar Miembro");



    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
}