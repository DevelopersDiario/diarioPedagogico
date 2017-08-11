package com.dese.diario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class ChooseColor extends AppCompatActivity implements View.OnClickListener {
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardViewOriginal;
    Button buttonDisagree, buttonAgree;

    Toolbar toolbar;
    private SmallBang mSmallBang;
    ImageView img, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11;

    //Theme
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int theme;
    int currentTheme;
    Intent intent;

    //Keys
    final  String KEY_VALUES="VALUES";
    final  String KEY_THEME_CHANGE="THEMECHANGED";
    final  String KEY_THEME="THEME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = ChooseColor.this.getSharedPreferences(KEY_VALUES, Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getInt("THEME", 0);

        mSmallBang = SmallBang.attach2Window(ChooseColor.this);


        inicializarToolbar();


        dialogButtons();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                intent = new Intent(ChooseColor.this, Settings.class);
                startActivity(intent);
                finish();

                Snackbar.make(view, R.string.action_select, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ChooseColor.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);

        }
    }


    @Override
    public void onBackPressed() {
        intent = new Intent(ChooseColor.this, Settings.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(this, Settings.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


    private void dialogButtons() {
        cardView1 = (CardView) findViewById(R.id.card_view1);
        cardView2 = (CardView) findViewById(R.id.card_view2);
        cardView3 = (CardView) findViewById(R.id.card_view3);
        cardView4 = (CardView) findViewById(R.id.card_view4);
        cardView5 = (CardView) findViewById(R.id.card_view5);
        cardView6 = (CardView) findViewById(R.id.card_view6);
        cardView7 = (CardView) findViewById(R.id.card_view7);
        cardView8 = (CardView) findViewById(R.id.card_view8);
        cardView9 = (CardView) findViewById(R.id.card_view9);
        cardView10 = (CardView) findViewById(R.id.card_view10);
        cardViewOriginal = (CardView) findViewById(R.id.card_view_original);
        //  buttonDisagree = (Button) view.findViewById(R.id.buttonDisagree);
        buttonAgree = (Button) findViewById(R.id.buttonAgree);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
        cardView6.setOnClickListener(this);
        cardView7.setOnClickListener(this);
        cardView8.setOnClickListener(this);
        cardView9.setOnClickListener(this);
        cardView10.setOnClickListener(this);
        cardViewOriginal.setOnClickListener(this);
        //  buttonDisagree.setOnClickListener(this);
        buttonAgree.setOnClickListener(this);
        img = (ImageView) (findViewById(R.id.img1));
        img2=  (ImageView)(findViewById(R.id.img2));
        img3 = (ImageView) (findViewById(R.id.img3));
        img4=  (ImageView)(findViewById(R.id.img4));
        img5 = (ImageView) (findViewById(R.id.img5));
        img6=  (ImageView)(findViewById(R.id.img6));
        img7 = (ImageView) (findViewById(R.id.img7));
        img8=  (ImageView)(findViewById(R.id.img8));
        img9=  (ImageView)(findViewById(R.id.img9));
        img10 = (ImageView) (findViewById(R.id.img10));
        img11=  (ImageView)(findViewById(R.id.img11));

        img.setImageResource(R.drawable.circle_item);
        img2.setImageResource(R.drawable.circle_item);
        img3.setImageResource(R.drawable.circle_item);
        img4.setImageResource(R.drawable.circle_item);
        img5.setImageResource(R.drawable.circle_item);
        img6.setImageResource(R.drawable.circle_item);
        img7.setImageResource(R.drawable.circle_item);;
        img8.setImageResource(R.drawable.circle_item);
        img9.setImageResource(R.drawable.circle_item);
        img10.setImageResource(R.drawable.circle_item);
        img11.setImageResource(R.drawable.circle_item);

    }

    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.card_view1:
                addNumber(v);
                img.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);

                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 1).apply();

                break;
            case R.id.card_view2:
                addNumber(v);
                img2.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);

                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                //  ((Settings) getActivity()).setThemeFragment(2);
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 2).apply();
                break;
            case R.id.card_view3:
                addNumber(v);
                img3.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 3).apply();
                break;
            case R.id.card_view4:
                addNumber(v);
                img4.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 4).apply();
                break;
            case R.id.card_view5:
                addNumber(v);
                img5.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 5).apply();
                break;
            case R.id.card_view6:
                addNumber(v);
                img6.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img7.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 6).apply();
                break;
            case R.id.card_view7:
                addNumber(v);
                img7.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);;
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 7).apply();
                break;
            case R.id.card_view8:
                addNumber(v);
                img8.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);;
                img7.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 8).apply();
                break;
            case R.id.card_view9:
                addNumber(v);
                img9.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);;
                img7.setImageResource(R.drawable.circle_item);
                img8.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 9).apply();
                break;
            case R.id.card_view10:
                addNumber(v);
                img10.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);;
                img7.setImageResource(R.drawable.circle_item);
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img11.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 10).apply();
                break;
            case R.id.card_view_original:
                addNumber(v);
                img11.setImageResource(R.drawable.ic_emoticon_white_36dp);
                img.setImageResource(R.drawable.circle_item);
                img2.setImageResource(R.drawable.circle_item);
                img3.setImageResource(R.drawable.circle_item);
                img4.setImageResource(R.drawable.circle_item);
                img5.setImageResource(R.drawable.circle_item);
                img6.setImageResource(R.drawable.circle_item);;
                img7.setImageResource(R.drawable.circle_item);
                img8.setImageResource(R.drawable.circle_item);
                img9.setImageResource(R.drawable.circle_item);
                img10.setImageResource(R.drawable.circle_item);
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                editor = sharedPreferences.edit();
                editor.putInt(KEY_THEME, 11).apply();
                break;

            case R.id.buttonAgree:
                sharedPreferences.edit().putBoolean(KEY_THEME_CHANGE, true).apply();
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    /*------------Theme choose by user--------------------*/
    private void theme() {
        sharedPreferences = getSharedPreferences(KEY_VALUES, Context.MODE_PRIVATE);
        theme = sharedPreferences.getInt(KEY_THEME, 0);
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


    public void addNumber(View view){
        mSmallBang.bang(view,new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("button +4");
            }
        });
    }

    public void redText(View view){
        //mText.setTextColor(0xFFCD8BF8);
        mSmallBang.bang(view,50,new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("text+1");
            }
        });
    }

    public void like(View view){
        img.setImageResource(R.drawable.emoticon);
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
                toast("heart+1");
            }
        });
    }

    private void toast(String text) {
        // Toast.makeText(DialogFragment.instantiate((Settings)getActivity().this, text),Toast.LENGTH_SHORT).show();
    }
}
