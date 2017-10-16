package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolder;
import com.dese.diario.Item.MyHolderF;
import com.dese.diario.Item.MyLongClickListener;
import com.dese.diario.Item.Search_friends;
import com.dese.diario.Objects.DataFriends;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.dese.diario.Utils.Urls;
import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

       // listarMember(lista.get(position).getGrupotmp());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Contact contact;

                String email =lista.get(pos).getCuenta();
                String grupi=lista.get(pos).getGrupotmp();

              //  contact  = new Contact(null, null, null, email, null);
                        //Lista
                      /*  List<ChipsView.Chip> lstChips= mChipsView.getChips();
                        int tamaño =lstChips.size();
                        ChipsView.Chip ch;
                        Contact c;
                        for(int x=0; x < tamaño; x++) {
                            ch = lstChips.get(x);
                            c= ch.getContact();
                            if(c.getEmailAddress()!=email) {
*/
                      //  mChipsView.addChip(email, "", contact);
                      /*  try {

                            registerGroup(grupi, email, "1");

                        } catch (JSONException e) {
                            Log.e("Adapter_Friends", e.getMessage());
                        }*/



            }
        });
    }



    @Override
    public int getItemCount() {
        return (null!=lista ?lista.size() :0);
        //return listapublicaciones.size();
    }

    private void registerGroup(final String g, final String u, final String r) throws JSONException {
        final DataFriends dataFriends= new DataFriends();

        StringRequest stringRequest = new StringRequest(Request.Method.POST , Urls.addparticipante,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "RES: "+response, Toast.LENGTH_LONG).show();
                        if(response.equals("succes")){
                            //
                            Intent i= new Intent(context, Search_friends.class);
                            context.startActivity(i);
                            Toast.makeText(context, "Se ha agregado correctame a @"+dataFriends.getCuenta().toString(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "@"+dataFriends.getCuenta().toString()+"Ya forma parte de este Grupo", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");

                       Toast.makeText(context, body, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        Log.e("Response:-",e.toString());
                    }
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_IDG, g);
                params.put(KEY_IDU, u);
                params.put(KEY_IDR, r);
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }//Fin RegisterUser


    private void listarMember(final String idgrupo) {

        VariablesLogin variablesLogin = new VariablesLogin();

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.listuserxgpo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSONArray jsonArray = null;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            try {
                                listMembers = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    final String email= jsonobject.getString("cuenta");
                                    //listContact(jsonobject.getString("idusuario"));

                                    // Uri urifoto= Uri.parse(Urls.download+foto);
                                    listMembers.add(jsonobject.getString("cuenta"));
                                    HashSet hs = new HashSet();
                                    hs.addAll(listMembers);
                                    listMembers.clear();
                                    listMembers.addAll(hs);
                                    /*contact  = new Contact(null, null, null, email, null );
                                    mChipsView.addChip(email, "", contact);*/


                                    //Toast.makeText(Search_friends.this, listMembers.get(i).toString(), Toast.LENGTH_SHORT).show();


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
                headers.put("idgrupo", idgrupo);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };


        queue.add(stringRequest);
    }


}