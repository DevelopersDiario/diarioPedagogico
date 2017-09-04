package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderMP;
import com.dese.diario.Item.MyHolderP;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Publication;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_MyPubication extends RecyclerView.Adapter<MyHolderMP> {
    ArrayList<Publication> listapublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;

    public Adapter_MyPubication(ArrayList<Publication> listapublicaciones, Context context) {


        this.listapublicaciones = listapublicaciones;
        this.context = context;

    }

    @Override
    public MyHolderMP onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mypublication, parent, false);



        return new MyHolderMP(view);
    }

    @Override
    public void onBindViewHolder(MyHolderMP holder, int position) {
        final String _ide= listapublicaciones.get(position).getIdpublicacion();


        final String d = listapublicaciones.get(position).getFecha();
        final String p = listapublicaciones.get(position).getObservaciones();
        final String u = listapublicaciones.get(position).getNombre();
        final String t = listapublicaciones.get(position).getTitulo();
        final String f = listapublicaciones.get(position).getFoto();
        final String pa = listapublicaciones.get(position).getIdpublicacion();
        final String sen=listapublicaciones.get(position).getSentimiento();
        final String eva=listapublicaciones.get(position).getEvaluacion();
        final String ana=listapublicaciones.get(position).getAnalisis();
        final String con= listapublicaciones.get(position).getConclusion();
        final String plan=listapublicaciones.get(position).getPlanaccion();


        holder.tvFechaRec.setText(d);
        holder.tvPublicationRec.setText(p);
        holder.tvUserRec.setText(u);
        holder.titlePublication.setText(t);
       // holder.tvSentimientosRec.setText(sen);

        holder.cvFeels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("¿Que esta pensando y sintiendo?")
                        .content(sen)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                            }
                        })
                        .show();

            }
        });
        holder.cvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("¿Que es lo bueno y malo de esta experiencia?")
                        .content(eva)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        holder.cvAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("¿Que sentido puede tener esta experiencia?")
                        .content(ana)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
       holder.cvConclusion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new MaterialDialog.Builder(context)
                       .title("¿Qué mas podria haber hecho?")
                       .content(con)
                       .positiveText("Listo")
                       .onPositive(new MaterialDialog.SingleButtonCallback() {
                           @Override
                           public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               dialog.dismiss();
                           }
                       })
                       .show();
           }
       });

        holder.cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title("¿Qué haría en una experiencia similar?")
                        .content(plan)
                        .positiveText("Listo")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        Picasso.with(context)
                .load(Urls.download + f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(_ide,t, u, d, p, f, pa, sen, eva, ana, con, plan);

            }
        });



    }


    @Override
    public int getItemCount() {
        return (null != listapublicaciones ? listapublicaciones.size() : 0);
        //return listapublicaciones.size();
    }


    private void openDetailActivity(String _ide, String t, String u, String d, String p, String f ,
                                    final String pa, String sen, String tes, String ana, String con,
                                    String plan ) {
        Intent i = new Intent(context, DetailPublication.class);
        ListRepublication rp= new ListRepublication();
        //PACK DATA TO SEND
        i.putExtra("_IDE_KEY", _ide);
        i.putExtra("TITLE_KEY", t);
        i.putExtra("USER_KEY", u);
        i.putExtra("DATA_KEY", d);
        i.putExtra("PUBLI_KEY", p);
        i.putExtra("FOTO_KEY", f);
        i.putExtra("PAPA", pa);
        i.putExtra("SEN_KEY", sen);
        i.putExtra("TES_KEY", tes);
        i.putExtra("ANA_KEY", ana);
        i.putExtra("CON_KEY", con);
        i.putExtra("PLAN_KEY", plan);
        //open activity
        context.startActivity(i);

    }



}




