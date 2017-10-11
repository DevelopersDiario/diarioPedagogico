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
import com.dese.diario.DetailMyPublication;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderMP;
import com.dese.diario.Objects.MyPublication;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_MyPubication extends RecyclerView.Adapter<MyHolderMP> {
    ArrayList<MyPublication> listapublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;

    public Adapter_MyPubication(ArrayList<MyPublication> listapublicaciones, Context context) {


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


        final String fecha = listapublicaciones.get(position).getFecha();
        final String observaciones = listapublicaciones.get(position).getObservaciones();
        final String nombre = listapublicaciones.get(position).getNombre();
        //final String cuenta=listapublicaciones.get(position).getCuenta();

        final String titulo = listapublicaciones.get(position).getTitulo();
        final String foto = listapublicaciones.get(position).getFoto();
        final String pa = listapublicaciones.get(position).getIdpublicacion();
        final String sen=listapublicaciones.get(position).getSentimiento();
        final String eva=listapublicaciones.get(position).getEvaluacion();
        final String ana=listapublicaciones.get(position).getAnalisis();
        final String con= listapublicaciones.get(position).getConclusion();
        final String plan=listapublicaciones.get(position).getPlanaccion();



        holder.tvFechaRecMP.setText(fecha);
        holder.tvPublicationRecMP.setText(observaciones);
        holder.tvUserRec.setText(nombre);
        holder.titlePublicationMP.setText(titulo);
       // holder.tvSentimientosRec.setText(sen);
        if(!sen.isEmpty()){
            holder.tlMainSelect.setVisibility(View.VISIBLE);
            holder.cvFeelsMP.setOnClickListener(new View.OnClickListener() {
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
        }else{
            holder.cvFeelsMP.setVisibility(View.GONE);
            holder.tlMainSelect.setVisibility(View.GONE);
        }if(!eva.isEmpty()){
            holder.cvTestMP.setOnClickListener(new View.OnClickListener() {
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
        }else{
            holder.cvTestMP.setVisibility(View.GONE);

        }
        if(!ana.isEmpty()){

            holder.cvAnalyzeMP.setOnClickListener(new View.OnClickListener() {
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
        }else{
            holder.cvAnalyzeMP.setVisibility(View.GONE);
        }
        if(!con.isEmpty()){
            holder.cvConclusionMP.setOnClickListener(new View.OnClickListener() {
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
        }else{
            holder.cvConclusionMP.setVisibility(View.GONE);
        }
        if(!plan.isEmpty()){
            holder.cvPlanMP.setOnClickListener(new View.OnClickListener() {
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
        }else {
            holder.cvPlanMP.setVisibility(View.GONE);
        }

        Picasso.with(context)
                .load(Urls.download + foto)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(_ide,titulo, nombre, fecha, observaciones, foto, pa, sen, eva, ana, con, plan);

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
        Intent i = new Intent(context, DetailMyPublication.class);

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




