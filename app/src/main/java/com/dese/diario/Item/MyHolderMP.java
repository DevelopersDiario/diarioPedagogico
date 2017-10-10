package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dese.diario.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class MyHolderMP extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
    public TextView tvUserRec;
    public TextView titlePublicationMP;
    public TextView tvFechaRecMP;
    public TextView tvPublicationRecMP;
    public TableLayout tlMainSelect;

    public ImageView cvFeelsMP, cvTestMP, cvAnalyzeMP, cvConclusionMP, cvPlanMP;

    public CircleImageView imProfileRec;
    public Button btnRPoster;
    public ImageView imvDowloandPDF;
    MyLongClickListener longClickListener;
    ItemClickListener itemClickListener;

    //Multiple
//    private int resource;
//    private boolean modoSeleccion;
   private SparseBooleanArray seleccionados;

    public MyHolderMP(View itemView) {
        super(itemView);

        tvUserRec = (TextView) itemView.findViewById(R.id.tvUserRec);
        tvFechaRecMP = (TextView) itemView.findViewById(R.id.tvFechaRecMP);
        tvPublicationRecMP = (TextView) itemView.findViewById(R.id.tvPublicationRecMP);
        titlePublicationMP = (TextView) itemView.findViewById(R.id.titlePublicationMP);

        cvFeelsMP =(ImageView) itemView.findViewById(R.id.imvFeelsMP);
        cvTestMP  = (ImageView) itemView.findViewById(R.id.imvTestMP);
        cvAnalyzeMP= (ImageView) itemView.findViewById(R.id.imvAnalyzeMP);
        cvConclusionMP= (ImageView) itemView.findViewById(R.id.imvConclusionMP);
        cvPlanMP= (ImageView) itemView.findViewById(R.id.imvPlanMP);


        imProfileRec = (CircleImageView) itemView.findViewById(R.id.imProfileRec);

        tlMainSelect= (TableLayout)itemView.findViewById(R.id.tlMainSelectMP);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);

        seleccionados = new SparseBooleanArray();


    }

    public void setLongClickListener(MyLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        this.longClickListener.onLongClick(getLayoutPosition());
        return false;
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }





}
