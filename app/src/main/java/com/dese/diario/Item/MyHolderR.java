package com.dese.diario.Item;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dese.diario.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class MyHolderR extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener, View.OnClickListener {

    MyLongClickListener longClickListener;
    ItemClickListener itemClickListener ;
    public TextView tvUserRecR;
    public TextView titlePublicationR;
    public TextView tvPublicationRecR;
    public CircleImageView imProfileRecR;

    public CardView cvFeels, cvTest, cvAnalyze, cvConclusion, cvPlan;

    //Multiple
    private int resource;
    private boolean modoSeleccion;
    private SparseBooleanArray seleccionados;

    public MyHolderR(View itemView) {
        super(itemView);


        titlePublicationR = (TextView)itemView.findViewById(R.id.titlePublicationR);
        tvUserRecR= (TextView)itemView.findViewById(R.id.tvUserRecR);
        imProfileRecR = (CircleImageView) itemView.findViewById(R.id.imProfileRecR);
        tvPublicationRecR= (TextView) itemView.findViewById(R.id.tvPublicationRecR);

        cvFeels =(CardView)itemView.findViewById(R.id.imvFeelsD);
        cvTest  = (CardView)itemView.findViewById(R.id.imvTestD);
        cvAnalyze= (CardView)itemView.findViewById(R.id.imvAnalyzeD);
        cvConclusion= (CardView)itemView.findViewById(R.id.imvConclusionD);
        cvPlan= (CardView)itemView.findViewById(R.id.imvPlanD);

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
           menu.add(0,0,0,"Abrir");


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }





}
