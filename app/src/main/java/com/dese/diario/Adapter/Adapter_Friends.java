package com.dese.diario.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolder;
import com.dese.diario.Item.MyHolderF;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Objects.DataFriends;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.doodle.android.chips.ChipsView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Eduardo on 12/05/2017.
 */

public class Adapter_Friends extends RecyclerView.Adapter<MyHolderF > {
    private static final String KEY_IDG = "idgrupo";
    private static final String KEY_IDU= "idusuario";
    private static final String KEY_IDR = "idrol";

    ArrayList<DataFriends> lista;
    Context context;
    int selectedPos;
    public ChipsView mChipsView;

    ArrayList listMembers;
    public Adapter_Friends(ArrayList lista, Context context) {
        this.lista=lista;
        this.context=context;


    }

    @Override
    public MyHolderF onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friends, parent, false);
        MyHolder holder = new MyHolder(view);

        return new MyHolderF(view);
    }

    @Override
    public void onBindViewHolder(final MyHolderF  holder, int position) {
        holder._idA.setText(lista.get(position).getIdusuario());
        holder.name.setText(lista.get(position).getNombre()+" "+lista.get(position).getApellidos());

        holder.cuen.setText("@"+lista.get(position).getCuenta());
        int r= Integer.parseInt(lista.get(position).getIdrol());
        //Contact contact = new Contact(null, null, null, email, null);

        String role = " ";
        final String f=lista.get(position).getFoto();

        Picasso.with(context)
                .load(Urls.download+f)
                .resize(250, 250)
                .centerCrop()
                .into(holder.imProfileRec);

        holder.tvIdRol.setText("1");

        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos = pos;
            }
        });



        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {


                String email =lista.get(pos).getCuenta();
                String grupi=lista.get(pos).getGrupotmp();
                listProfile(lista.get(pos).getIdusuario());


            }
        });
    }

    private void prevProfileFriends(JSONObject jsonobject){

        try {

            String foto = jsonobject.getString("foto");
            String nombre =jsonobject.getString("nombre");
            String correo  = jsonobject.getString("correo");
            String portada =jsonobject.getString("fportada");

            TextView tvNombre, tvMail;
            ImageView imProfilePreview;
            ImageView imHolder;
            MaterialProgressBar pbLoad;
            boolean wrapInScrollView = false;
            final MaterialDialog dialog = new MaterialDialog.Builder(context)
                    .customView(R.layout.dialog_profile_preview, wrapInScrollView)
                    .show();
            View view = dialog.getCustomView();
            dialog.show();



            imProfilePreview= (ImageView) view.findViewById(R.id.imProfilePreview);
            tvNombre= (TextView) view.findViewById(R.id.tvNamePreview);
            tvMail= (TextView) view.findViewById(R.id.tvMailPreview);
            pbLoad= (MaterialProgressBar) view.findViewById(R.id.pbLoad);
           // imHolder= (ImageView) view.findViewById(R.id.imHolderPreview) ;

            pbLoad.setVisibility(View.VISIBLE);
            pbLoad.setIndeterminate(true);
            Picasso.with(context)
                    .load(Urls.download+foto)
                    .resize(2000, 1200)
                   .centerCrop()
                    .into(  imProfilePreview);
            pbLoad.setVisibility(View.GONE);
           /* Picasso.with(context)
                    .load(Urls.download+portada)
                    .resize(2000, 12000)
                    .centerCrop()
                    .into(  imHolder);*/
            tvNombre.setText(nombre);
            tvMail.setText(correo);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    @Override
    public int getItemCount() {
        return (null!=lista ?lista.size() :0);
        //return listapublicaciones.size();
    }

    private void listProfile(final String iduser) {

        VariablesLogin variablesLogin = new VariablesLogin();

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.filtrousuarioXid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                //listMembers = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);



                                    prevProfileFriends(jsonobject);
                                    //Toast.makeText(context, "Idusu"+nombre+correo+ foto,Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                Log.e("Search Friends", "Error +->" + e);
                            }

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Search Friends", "Response--->"+error);
            }

        }
        ) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("idusuario", iduser);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);

    }


}