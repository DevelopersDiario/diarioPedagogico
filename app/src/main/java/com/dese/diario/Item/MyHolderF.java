package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.dese.diario.R;
import com.doodle.android.chips.ChipsView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class MyHolderF extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener, View.OnClickListener {
        public TextView _idA;
        public TextView cuen;
        public TextView name;
        public  TextView tvIdRol;

        public CircleImageView imProfileRec;

        ChipsView mChipsView;


      ItemClickListener itemClickListener;
     MyLongClickListener longClickListener;

    //Multiple
    private int resource;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;

        public MyHolderF(View itemView) {
            super(itemView);

            _idA = (TextView) itemView.findViewById(R.id.tvIdF);
            cuen = (TextView) itemView.findViewById(R.id.tvAccountF);
            name = (TextView) itemView.findViewById(R.id.tvNameF);
            imProfileRec = (CircleImageView) itemView.findViewById(R.id.imgAmigo);

            mChipsView   = (ChipsView) itemView.findViewById(R.id.cv_contacts);
            this.tvIdRol = (TextView) itemView.findViewById(R.id.tvidRolF);


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
              menu.setHeaderTitle("Seleccione: ");
              menu.add(0,0,0,"Agregar a Grupo");


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
