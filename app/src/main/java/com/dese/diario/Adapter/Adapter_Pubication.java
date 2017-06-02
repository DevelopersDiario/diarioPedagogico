package com.dese.diario.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Colaboration;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderP;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.MainActivity;
import com.dese.diario.Objects.DetailPublication;
import com.dese.diario.Objects.Publication;
import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eduardo on 04/04/2017.
 */

public class Adapter_Pubication extends RecyclerView.Adapter<MyHolderP> {
    ArrayList<Publication> listapublicaciones;
    Context context;
    View.OnLongClickListener longClickListener;

    final static String url = Urls.repuplication;

    final static String urllistar = "http://192.168.20.25:8084/diariopws/api/1.0/publicacion/listrepublication";
    final String KEY_IDUSUARIO = "idusuario";
    final String KEY_TITULO = "titulo";
    final String KEY_OBSERVACIONES = "observaciones";
    final String KEY_IDGROUP = "idgrupo";
    final String KEY_ROL = "idrol";
    final String KEY_IDPUBLICACION = "idpublicacion";

    final String KEY_NAMEU = "nombreusuario";
    final String KEY_PAPA = "padre";
    final String KEY_FOTO = "foto";

    final String CONTENT_TYPE = "Content-Type";
    final String APPLICATION = "application/x-www-form-urlencoded";


    RecyclerView recyclerView;

    ArrayList listpublicaciones;
    Adapter_Pubication adapter;

    public Adapter_Pubication(ArrayList<Publication> listapublicaciones, Context context) {


        this.listapublicaciones = listapublicaciones;
        this.context = context;

    }

    @Override
    public MyHolderP onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_publication, parent, false);
        return new MyHolderP(view);
    }

    @Override
    public void onBindViewHolder(MyHolderP holder, int position) {


        final String d = listapublicaciones.get(position).getFecha();
        final String p = listapublicaciones.get(position).getObservaciones();
        final String u = listapublicaciones.get(position).getNombre();
        final String t = listapublicaciones.get(position).getTitulo();
        final String f = listapublicaciones.get(position).getFoto();
        final String pa = listapublicaciones.get(position).getIdpublicacion();


        holder.tvFechaRec.setText(d);
        holder.tvPublicationRec.setText(p);
        holder.tvUserRec.setText(u);
        holder.titlePublication.setText(t);
        Picasso.with(context)
                .load(Urls.fotouser + f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(t, u, d, p, f);
                listarRepublicaciones(pa);
                //Toast.makeText(c,name,Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnRPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertRepublication(pa);
            }
        });


    }


    @Override
    public int getItemCount() {
        return (null != listapublicaciones ? listapublicaciones.size() : 0);
        //return listapublicaciones.size();
    }


    private void openDetailActivity(String t, String u, String d, String p, String f) {
        Intent i = new Intent(context, DetailPublication.class);

        //PACK DATA TO SEND
        i.putExtra("TITLE_KEY", t);
        i.putExtra("USER_KEY", u);
        i.putExtra("DATA_KEY", d);
        i.putExtra("PUBLI_KEY", p);
        i.putExtra("FOTO_KEY", f);


        //open activity
        context.startActivity(i);

    }

    private void AlertRepublication(final String pa) {
        final VariablesLogin varlogin = new VariablesLogin();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialoglayout = inflater.inflate(R.layout.activity_publication, null);

        final EditText TITLE = (EditText) dialoglayout.findViewById(R.id.ettitlepost);
        final EditText PUBLIC = (EditText) dialoglayout.findViewById(R.id.etPublication);
        final Spinner GPO = (Spinner) dialoglayout.findViewById(R.id.spGpoP);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setCancelable(false)
                .setPositiveButton("Republicar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        String titulo = TITLE.getText().toString();
                        String publica = PUBLIC.getText().toString();
                        String idgrupe = String.valueOf(GPO.getItemIdAtPosition(0));
                        String papa = pa;

                        try {
                            registerRePost(titulo, publica, papa, idgrupe);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
        builder.setView(dialoglayout);
        builder.show();
    }


    private void registerRePost(final String t, final String o, final String p, final String g) throws JSONException {
        final VariablesLogin varlogin = new VariablesLogin();

        final String idusuario = varlogin.getIdusuario();
        final String titulo = t;
        final String observaciones = o;
        final String padre = p;
        final String grupe = g;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // failed_regpublication.setText(R.string.message_succes_publication);
                        //openMainactivity();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");

                        //   failed_regpublication.setText(body);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDUSUARIO, idusuario);
                params.put(KEY_TITULO, titulo);
                params.put(KEY_IDGROUP, grupe);
                params.put(KEY_OBSERVACIONES, observaciones);
                params.put(KEY_ROL, "1");
                params.put(KEY_IDPUBLICACION, padre);
                params.put(CONTENT_TYPE, APPLICATION);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }//Fin RegisterPost


    public void listarRepublicaciones(final String pa) {

        RequestQueue queue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, urllistar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                listpublicaciones = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    //  System.out.println(jsonobject);
                                    VariablesLogin varllogin = new VariablesLogin();

                                    listpublicaciones.add(new com.dese.diario.Objects.Publication(
                                            jsonobject.getString(KEY_IDPUBLICACION),
                                            jsonobject.getString(KEY_IDUSUARIO),
                                            jsonobject.getString(KEY_NAMEU),
                                            jsonobject.getString(KEY_FOTO),
                                            jsonobject.getString("fecha"),
                                            jsonobject.getString(KEY_TITULO),
                                            jsonobject.getString(KEY_OBSERVACIONES),
                                            jsonobject.getString(KEY_PAPA)));
                                    adapter = new Adapter_Pubication(listpublicaciones,context);
                                    recyclerView.setAdapter(adapter);
                                    /// System.out.println(listpublicaciones);



                                }
                            } catch (JSONException e) {
                               // Log.e(getString(R.string.UUUps), getString(R.string.Error) + e);
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("°_°", "ocurio un error !");
            }

        }

        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(KEY_PAPA, pa);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);
    }
}




