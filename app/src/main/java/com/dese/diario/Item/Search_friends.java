package com.dese.diario.Item;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dese.diario.Adapter.Adapter_Friends;
import com.dese.diario.Objects.DataFriends;
import com.dese.diario.Utils.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.doodle.android.chips.ChipsView;
import com.doodle.android.chips.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search_friends extends AppCompatActivity  {
    //Usuario

    final static String urlLisUser= Urls.ufilXnombre;
    final static String urlgpodetalle= Urls.addparticipante;

    private ChipsView mChipsView;


    public static final String KEY_NAME = "nombre";
    public static final String KEY_USUARIOGRUPO= "idusuario";


    ArrayAdapter arrayAdapter;
    Adapter_Friends adpt;
    RecyclerView recyclerFriends;
    LinearLayoutManager linearLayoutManager;
    ArrayList listFriends;



    public static final String IDUSUARIO= "idusuario";

    public static final int DETALLEGRUPO = 100;

    //Add Member
    private static final String KEY_IDG = "idgrupo";
    private static final String KEY_IDU= "idusuario";
    private static final String KEY_IDR = "idrol";
    TextView tvNameGF;

    //DETALLE_GRUPO//
    TextView tvidRolF;
    TextView tvIdF;
    TextView tvAccountF;
    TextView tvIdGF;
    EditText etBuscar;

    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;

    CollapsingToolbarLayout collapsing;

    Contact contact;
    ArrayList listMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);


        bindData();

        chipData();


    }

    private void chipData() {

        mChipsView = (ChipsView) findViewById(R.id.cv_contacts);

        // change EditText config
        mChipsView.getEditText().setCursorVisible(true);
        mChipsView.setChipsValidator(new ChipsView.ChipValidator() {
            @Override
            public boolean isValid(Contact contact) {
                if (contact.getDisplayName().equals("asd@qwe.de")) {
                    return false;
                }
                return true;
            }
        });

        mChipsView.setChipsListener(new ChipsView.ChipsListener() {
            @Override
            public void onChipAdded(ChipsView.Chip chip) {
                for (ChipsView.Chip chipItem : mChipsView.getChips()) {
                    Log.d("ChipList", "chip: " + chipItem.toString());
                }
            }

            @Override
            public void onChipDeleted(ChipsView.Chip chip) {

            }

            @Override
            public void onTextChanged(CharSequence text) {

                String textValue = String.valueOf(text);

                if(text.length()>0){
                    searchFriends(textValue);
                }

                else if(text.length()<0) {
                    listFriends.clear();
                    adpt.notifyDataSetChanged();
                }
            }
        });
    }

    private void bindData() {

        etBuscar = (EditText) findViewById(R.id.etBuscar);
        tvIdF= (TextView)findViewById(R.id.tvIdF);
        //cbSelect= (CheckBox) findViewById(R.id.chbSelectF);

        recyclerFriends = (RecyclerView) findViewById(R.id.recyclerFriends);
        linearLayoutManager= new LinearLayoutManager(this);
        recyclerFriends.setLayoutManager(linearLayoutManager);

        collapsing = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsing.setTitle("");



        //Member
        tvNameGF=(TextView) findViewById(R.id.tvNameGF);
        tvIdGF=(TextView) findViewById(R.id.tvIdGF);
        tvidRolF = (TextView)findViewById(R.id.tvidRolF);
        tvAccountF= (TextView) findViewById(R.id.tvAccountF);

        tvNameGF.setText( getIntent().getExtras().getString("namegpo"));
        tvIdGF.setText( getIntent().getExtras().getString("gpo"));

        String g= tvIdGF.getText().toString();


    if(!g.isEmpty()){
        listarMember(g);
        //etBuscar.requestFocus();
        //etBuscar.setFocusable(true);
    }else{
        Toast.makeText(this, "Vacio", Toast.LENGTH_SHORT).show();
    }


        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

                String textValue = String.valueOf(s);

                if(s.length()>=0){
                    searchFriends(textValue);
                }

                else if(s.length()<0) {
                    listFriends.clear();
                    adpt.notifyDataSetChanged();
                }

            }
        });


    }


    public  void searchFriends(final String name){

            RequestQueue queue = Volley.newRequestQueue(this);

        if(name!=null) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLisUser,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //JSONArray jsonArray = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                                try {
                                    listFriends = new ArrayList<>();
                                    JSONArray jsonarray = new JSONArray(response);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        //  System.out.println(jsonobject);
                                        VariablesLogin varllogin = new VariablesLogin();

                                        listFriends.add(new com.dese.diario.Objects.DataFriends(
                                                jsonobject.getString("idusuario"),
                                                jsonobject.getString("nombre"),
                                                jsonobject.getString("apellidos"),
                                                jsonobject.getString("cuenta"),
                                                jsonobject.getString("idrol"),
                                                jsonobject.getString("foto")));
                                        adpt = new Adapter_Friends(listFriends, Search_friends.this);
                                        recyclerFriends.setAdapter(adpt);

                                        /// System.out.println(listpublicaciones);

                                    }
                                } catch (JSONException e) {
                                    Log.e("UUUps!!!!!", "Error!!" + e);
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
                    headers.put(KEY_NAME, name);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };


            queue.add(stringRequest);
        }
        else{
            Toast.makeText(Search_friends.this  ,"Escriba para buscar", Toast.LENGTH_SHORT).show();

        }
// Signify that we are done refreshing

    }// Fin getPersonalInformation



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView tv1=(TextView) findViewById(R.id.tvIdGF);
        TextView tv2 = (TextView)findViewById(R.id.tvidRolF);
        TextView tv3= (TextView) findViewById(R.id.tvIdF);
        TextView tv4= (TextView) findViewById(R.id.tvAccountF);



        String g= tv1.getText().toString();
        String r= tv2.getText().toString();
        String  u = tv3.getText().toString();


        //Contact contact;
        final String email= tv4.getText().toString();

       contact  = new Contact(null, null, null, email, null);
        switch (item.getTitle().toString()){

            case  "Agregar a Grupo":

                //Lista
                List<ChipsView.Chip> lstChips= mChipsView.getChips();
                int tamaño =lstChips.size();
                        ChipsView.Chip ch;
                Contact c;
                        for(int x=0; x < tamaño; x++) {
                                  ch = lstChips.get(x);
                                  c= ch.getContact();
                            if(c.getEmailAddress()!=email){

                                mChipsView.addChip(email, "", contact);

                                try {
                                    registerGroup(g, u, "1");
                                    listFriends.clear();
                                    etBuscar.requestFocus();
                                    etBuscar.setFocusable(true);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                //mChipsView.removeChipBy(contact);
                            }else if(c.getEmailAddress()==email){
                                Toast.makeText(Search_friends.this  ,"Ya esta agregado", Toast.LENGTH_SHORT).show();
                                //mChipsView.addChip(email, "", contact);
                                mChipsView.removeChipBy(contact);

                            }


                             }//end for*//*

                break;

        }
        etBuscar.requestFocus();
        etBuscar.setFocusable(true);

        return super.onContextItemSelected(item);
    }

    private void listarMember(final String idgrupo) {

        VariablesLogin variablesLogin = new VariablesLogin();
        final String id= variablesLogin.getIdusuario().toString();
        final TextView tv4= (TextView) findViewById(R.id.tvAccountF);

        RequestQueue queue = Volley.newRequestQueue(Search_friends.this);

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

                                    contact  = new Contact(null, null, null, email, null);
                                    mChipsView.addChip(email, "", contact);

                                    listMembers.add(jsonobject.getString("cuenta"));
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


    private void registerGroup(final String g, final String u, final String r) throws JSONException{
        final DataFriends dataFriends= new DataFriends();
        StringRequest stringRequest = new StringRequest(Request.Method.POST , urlgpodetalle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("succes")){
                            //  Toast.makeText(MainActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                            Toast.makeText(Search_friends.this, "Se ha agregado correctame a @"+dataFriends.getCuenta().toString(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Search_friends.this, "@"+dataFriends.getCuenta().toString()+"Ya forma parte de este Grupo", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body;
                if (error.networkResponse.data != null) {

                    try {
                        body = new String(error.networkResponse.data, "UTF-8");

                        Toast.makeText(Search_friends.this, body, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }//Fin RegisterUser


    /*------------Theme choose by user--------------------*/
    private void theme() {
        sharedPreferences = getSharedPreferences(getString(R.string.values), Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt(getString(R.string.theme), 0);
        settingTheme(theme);
    }
    public void settingTheme(int theme) {
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme11);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            case 6:
                setTheme(R.style.AppTheme6);
                break;
            case 7:
                setTheme(R.style.AppTheme7);
                break;
            case 8:
                setTheme(R.style.AppTheme8);
                break;
            case 9:
                setTheme(R.style.AppTheme9);
                break;
            case 10:
                setTheme(R.style.AppTheme10);
                break;
            case 11:
                setTheme(R.style.AppTheme);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }




}
