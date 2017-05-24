package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dese.diario.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class MyHolderP extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener, View.OnClickListener {
            public TextView tvUserRec;
            public TextView titlePublication;
            public TextView tvFechaRec;
            public TextView tvPublicationRec;
           public CircleImageView imProfileRec;


            MyLongClickListener longClickListener;
            ItemClickListener itemClickListener;


    //Multiple
    private int resource;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;

        public MyHolderP(View itemView) {
            super(itemView);

            tvUserRec = (TextView)itemView.findViewById(R.id.tvUserRec);
            tvFechaRec = (TextView)itemView.findViewById(R.id.tvFechaRec);
            tvPublicationRec = (TextView)itemView.findViewById(R.id.tvPublicationRec);
            titlePublication=(TextView)itemView.findViewById(R.id.titlePublication);
            imProfileRec=(CircleImageView)itemView.findViewById(R.id.imProfileRec);

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
         /*menu.setHeaderTitle("Seleccione: ");
           menu.add(0,0,0,"Agregar a Grupo");
*/

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
