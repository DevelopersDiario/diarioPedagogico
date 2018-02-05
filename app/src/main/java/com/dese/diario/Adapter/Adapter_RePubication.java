package com.dese.diario.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Item.MyHolderR;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Objects.RePublication;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_RePubication extends RecyclerView.Adapter<MyHolderR> {
    ArrayList<RePublication> listaRepublicaciones;

    Context context;
    View.OnLongClickListener longClickListener;
    int selectedPos;


    Adapter_File ia;
    //RecyclerView rvItemFeed;
    public Adapter_RePubication(ArrayList<RePublication> listaRepublicaciones, Context context) {


        this.listaRepublicaciones = listaRepublicaciones;
        this.context = context;


    }

    @Override
    public MyHolderR onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repost, parent, false);


        return new MyHolderR(view);
    }

    @Override
    public void onBindViewHolder(MyHolderR holder, final int position) {




        final String t = listaRepublicaciones.get(position).getTitulo();
        final String u = listaRepublicaciones.get(position).getNombre();
        final String f = listaRepublicaciones.get(position).getFoto();
       // final String r=listaRepublicaciones.get(position).getRuta();
        final String o = listaRepublicaciones.get(position).getObservaciones();

      final String sen=listaRepublicaciones.get(position).getSentimiento();
        final String eva=listaRepublicaciones.get(position).getEvaluacion();
        final String ana=listaRepublicaciones.get(position).getAnalisis();
        final String con= listaRepublicaciones.get(position).getConclusion();
        final String plan=listaRepublicaciones.get(position).getPlanaccion();

        holder.titlePublicationR.setText(t);
        holder.tvUserRecR.setText(u);
        holder.tvPublicationRecR.setText(o);
        Picasso.with(context)
                .load(Urls.download + f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRecR);


        listarFile2(listaRepublicaciones.get(position).getIdpublicacion(), holder);

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos =pos;

            }
        });

     /*   holder.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(context)
                        .title("ooooo")
                        .content(position)
                        .show();
            }
        });
*/


    }


    @Override
    public int getItemCount() {
        return (null != listaRepublicaciones ? listaRepublicaciones.size() : 0);
        //return listapublicaciones.size();
    }



    public  void listarFile2(final String ide, final MyHolderR holder){
        final ArrayList<String> filename = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,Urls.obtenerdetallepublicacion ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                // paths = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String file=jsonobject.getString("descripcion");
                                    filename.add(file);
                                    //  if(file!=" "){

                                    ia = new Adapter_File(filename, context);
                                    holder.rvItemFeed.setAdapter(ia);


                                  //  holder.lyContentImagenDetail.setVisibility(View.VISIBLE);
                                    holder.rvItemFeed.setItemAnimator(new DefaultItemAnimator());
                                    holder.rvItemFeed.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                                    HashSet hs2 = new HashSet();
                                    hs2.addAll(filename);
                                    filename.clear();
                                    filename.addAll(hs2);
                                }
                            } catch (JSONException e) {
                                Log.e("Detail Publicacion", "Problema con" + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Horror", "Response--->"+error);
            }

        }

        ) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idpublicacion", ide);
                //headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        queue.add(stringRequest);
    }



}




