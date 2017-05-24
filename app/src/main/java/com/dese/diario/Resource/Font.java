package com.dese.diario.Resource;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dese.diario.Publication;
import com.dese.diario.R;

/**
 * Created by Eduardo on 22/05/2017.
 */

public class Font extends AppCompatActivity {

    AlertDialog.Builder mBuilder;
    View mView;
    Typeface TF1, TF2, TF3, TF4, TF5, TF6, TF7, TF8, TF9, TF10, TF11, TF12, TF13, TF14, TF15;
    Context con;
    EditText edPublication;

    Spinner mSFont;
    ArrayAdapter<String> adapter;
    String[] title;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        bindActivity();
    }
        public  void getContext(Context context){
            con= context;
        }
    private void bindActivity() {

      //  edPublication = (EditText)findViewById(R.id.etPublication);

        String font_path_Abtrakt, font_path_AbteciaBasic, font_path_ActionJackson, font_path_AcuteAbove;
        String font_path_Adore64, AdventureSubtitlesNormal, AtoZ, Congratulations_DEMO, DKPimpernel;
        String KGPPrimaryItalics, Maria_lucia, PENMP___, Precursive_1_FREE, PWSchoolScript, PWScolarpaper;

        font_path_Abtrakt = "font/font1.ttf";
        font_path_AbteciaBasic = "font/font2.ttf";
        font_path_ActionJackson = "font/font3.ttf";
        font_path_AcuteAbove = "font/font4.ttf";
        font_path_Adore64 = "font/font5.ttf";
        AdventureSubtitlesNormal = "font/font6.ttf";
        AtoZ = "font/font7.ttf";
        Congratulations_DEMO = "font/font8.ttf";
        DKPimpernel = "font/font9.otf";
        KGPPrimaryItalics= "font/font10.ttf";
        Maria_lucia= "font/font11.ttf";
        PENMP___= "font/font12.TTF";
        Precursive_1_FREE= "font/font13.otf";
        PWSchoolScript="font/font14.otf";
        PWScolarpaper= "font/font15.ttf";



        TF1 = Typeface.createFromAsset(getAssets(), font_path_Abtrakt);
        TF2 = Typeface.createFromAsset(getAssets(), font_path_AbteciaBasic);
        TF3 = Typeface.createFromAsset(getAssets(), font_path_ActionJackson);
        TF4 = Typeface.createFromAsset(getAssets(), font_path_AcuteAbove);
        TF5 = Typeface.createFromAsset(getAssets(), font_path_Adore64);
        TF6 = Typeface.createFromAsset(getAssets(), AdventureSubtitlesNormal);
        TF7 = Typeface.createFromAsset(getAssets(), AtoZ);
        TF8 = Typeface.createFromAsset(getAssets(), Congratulations_DEMO);
        TF9 = Typeface.createFromAsset(getAssets(), DKPimpernel);
        TF10 = Typeface.createFromAsset(getAssets(), KGPPrimaryItalics);
        TF11 = Typeface.createFromAsset(getAssets(), Maria_lucia);
        TF12 = Typeface.createFromAsset(getAssets(), PENMP___);
        TF13 = Typeface.createFromAsset(getAssets(), Precursive_1_FREE);
        TF14 = Typeface.createFromAsset(getAssets(), PWSchoolScript);
        TF15 = Typeface.createFromAsset(getAssets(), PWScolarpaper);
        Publication p= new Publication();

       title = new String[]{
               "Seleccione fuente",
               "Abstrakt",
               "Abtecia Basic Sans Serif Font",
               "Action Jackson",
               "A Cut Above The Rest",
               "Adore64",
               "Adventure Subtitles Normal",
               "A to Z",
               "Congratulations",
               "KG Primary Italics",
               "Maria Lucia",
               "Pemp",
               "Precursive",
               "PW School Script",
               "PW Scolarpaper"
       };


    }


    public  void seleccionarFont(final Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        mBuilder =new AlertDialog.Builder(context);
        mView =inflater.inflate( R.layout.dialog_font, null );

        mSFont =(Spinner)mView.findViewById(R.id.spnFont);
        adapter =new ArrayAdapter<String>(con,
                android.R.layout.simple_spinner_item, title);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSFont.setAdapter(adapter);

        mBuilder.setTitle(R.string.formato);
        mBuilder.setPositiveButton(R.string.btnAgree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!mSFont.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.prompt_spinner))){

                    dialog.dismiss();
                    switch (mSFont.getSelectedItem().toString()){
                        case "Abstrakt":
                            edPublication.setTypeface(TF1);
                            Toast.makeText(context,
                                    mSFont.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                            break;
                        case "Abtecia Basic Sans Serif Font":
                            edPublication.setTypeface(TF2);
                            break;
                        case "Action Jackson":
                            edPublication.setTypeface(TF3);
                            break;
                        case "A Cut Above The Rest":
                            edPublication.setTypeface(TF4);
                            break;
                        case "Adore64":
                            edPublication.setTypeface(TF5);

                        case "Adventure Subtitles Normal":
                            edPublication.setTypeface(TF6);
                            break;
                        case "A to Z":
                            edPublication.setTypeface(TF7);
                            break;
                        case "Congratulations":
                            edPublication.setTypeface(TF8);
                            break;
                        case "KG Primary Italics":
                            edPublication.setTypeface(TF9);
                            break;
                        case "Maria Lucia":
                            edPublication.setTypeface(TF10);
                            break;
                        case "PENMP":
                            edPublication.setTypeface(TF11);
                            break;
                        case "Precursive":
                            edPublication.setTypeface(TF12);
                            break;
                        case "PW School Script":
                            edPublication.setTypeface(TF13);
                            break;
                        case "PW Scolarpaper":
                            edPublication.setTypeface(TF14);
                            break;
                        case "Seleccione Fuente...":
                            edPublication.setTypeface(Typeface.DEFAULT);
                            //edPublication.setTypeface(TF4);
                            break;

                    }
                }
            }
        });
        mBuilder.setNegativeButton(R.string.btnDisagree, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
        dialog.show();
    }//End Alert Font


    public Typeface getFont(final Context context){

        final Typeface tfSelected=Typeface.DEFAULT;
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.formato);
        final String[] types = {
                "Seleccione fuente",
                "Abstrakt",
                "Abtecia Basic Sans Serif Font",
                "Action Jackson",
                "A Cut Above The Rest",
                "Adore64",
                "Adventure Subtitles Normal",
                "A to Z",
                "Congratulations",
                "KG Primary Italics",
                "Maria Lucia",
                "Pemp",
                "Precursive",
                "PW School Script",
                "PW Scolarpaper"};

        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                switch(which){
                    case 0:
                     //   text.setTypeface(Typeface.DEFAULT);
                        break;
                    case 1:
                      //text.setTypeface(TF1);
                        
                        tfSelected.equals(TF1);
                      //  Toast.makeText(context, text.getText().toString(), Toast.LENGTH_LONG).show();
                        break;
                    case 2:

                        tfSelected.equals(TF2);
                        //text.setTypeface(TF2);
                        break;
                    case 3:
                        edPublication.setTypeface(TF3);
                        break;
                    case 4:
                        edPublication.setTypeface(TF4);
                        break;
                    case 5:
                        edPublication.setTypeface(TF5);
                        break;
                    case 6:
                        edPublication.setTypeface(TF6);
                        break;
                    case 7:
                        edPublication.setTypeface(TF7);
                        break;
                      case 8:
                        edPublication.setTypeface(TF8);
                        break;
                      case 9:
                        edPublication.setTypeface(TF9);
                        break;
                      case 10:
                        edPublication.setTypeface(TF10);
                        break;
                      case 11:
                        edPublication.setTypeface(TF11);
                        break;
                    case 12:
                        edPublication.setTypeface(TF12);
                        break;
                    case 13:
                        edPublication.setTypeface(TF13);
                        break;
                    case 14:
                        edPublication.setTypeface(TF14);

                }
            }

        });

        b.show();
        return  tfSelected;
    }

    public void selectFont(Publication context, final EditText editText){
        bindActivity();
        final Typeface tfSelected=Typeface.DEFAULT;
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(R.string.formato);
        final String [] t={
                "Seleccione fuente",
                "Abstrakt",
                "Abtecia Basic Sans Serif Font",
                "Action Jackson",
                "A Cut Above The Rest",
                "Adore64",
                "Adventure Subtitles Normal",
                "A to Z",
                "Congratulations",
                "KG Primary Italics",
                "Maria Lucia",
                "Pemp",
                "Precursive",
                "PW School Script",
                "PW Scolarpaper"};


        b.setItems(t, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
                switch(which){
                    case 0:
                        editText.setTypeface(Typeface.DEFAULT);

                        break;
                    case 1:
                        editText.setTypeface(TF1);

                        break;
                    case 2:
                        editText.setTypeface(TF2);

                        break;
                    case 3:
                        editText.setTypeface(TF3);
                        break;
                    case 4:
                        editText.setTypeface(TF4);
                        break;
                    case 5:
                        editText.setTypeface(TF5);
                        break;
                    case 6:
                        editText.setTypeface(TF6);
                        break;
                    case 7:
                        editText.setTypeface(TF7);
                        break;
                    case 8:
                        editText.setTypeface(TF8);
                        break;
                    case 9:
                        editText.setTypeface(TF9);
                        break;
                    case 10:
                        editText.setTypeface(TF10);
                        break;
                    case 11:
                        editText.setTypeface(TF11);
                        break;
                    case 12:
                        editText.setTypeface(TF12);
                        break;
                    case 13:
                        editText.setTypeface(TF13);
                        break;
                    case 14:
                        editText.setTypeface(TF14);

                }
            }

        });

        b.show();
    }//End Alert Font









}
