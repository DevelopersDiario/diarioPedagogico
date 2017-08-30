package com.dese.diario.Item;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.MainActivity;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.gc.materialdesign.views.Card;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class MyHolderP extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnCreateContextMenuListener, View.OnClickListener {
    public TextView tvUserRec;
    public TextView titlePublication;
    public TextView tvFechaRec;
    public TextView tvPublicationRec;
    //public TextView tvSentimientosRec;

    public ImageView cvFeels, cvTest, cvAnalyze, cvConclusion, cvPlan;

    public CircleImageView imProfileRec;
    public Button btnRPoster;
    public ImageView imvDowloandPDF;
    MyLongClickListener longClickListener;
    ItemClickListener itemClickListener;

    //Multiple
//    private int resource;
//    private boolean modoSeleccion;
   private SparseBooleanArray seleccionados;

    public MyHolderP(View itemView) {
        super(itemView);

        tvUserRec = (TextView) itemView.findViewById(R.id.tvUserRec);
        tvFechaRec = (TextView) itemView.findViewById(R.id.tvFechaRec);
        tvPublicationRec = (TextView) itemView.findViewById(R.id.tvPublicationRec);
        titlePublication = (TextView) itemView.findViewById(R.id.titlePublication);

        cvFeels =(ImageView) itemView.findViewById(R.id.imvFeels);
        cvTest  = (ImageView) itemView.findViewById(R.id.imvTest);
        cvAnalyze= (ImageView) itemView.findViewById(R.id.imvAnalyze);
        cvConclusion= (ImageView) itemView.findViewById(R.id.imvConclusion);
        cvPlan= (ImageView) itemView.findViewById(R.id.imvPlan);

       // imvDowloandPDF=(ImageView) itemView.findViewById(R.id.imvDowloandPDF);

        imProfileRec = (CircleImageView) itemView.findViewById(R.id.imProfileRec);
      //  btnRPoster = (Button) itemView.findViewById(R.id.btnRPoster);


        itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //OUR CONTEXT MENU
         /*menu.setHeaderTitle("Seleccione: ");
           menu.add(0,0,0,"Agregar a Grupo");
*/

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }





}
