package com.dese.diario.Resource.Dialogs;

/**
 * Created by Eduardo on 20/03/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dese.diario.R;
import com.dese.diario.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;


public class ColorChooserDialog extends DialogFragment implements View.OnClickListener {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardViewOriginal;
    Button buttonDisagree, buttonAgree;
    View view;
    int currentTheme;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityOptions options;
    Intent intent;
    Boolean themeChanged = false;
    private SmallBang mSmallBang;

   // GridLayout tablelayout;
    ImageView img, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        // Save current theme to use when user press dismiss inside dialog
        sharedPreferences = getActivity().getSharedPreferences("VALUES", Context.MODE_PRIVATE);
        currentTheme = sharedPreferences.getInt("THEME", 0);

        //inflate theme_dialog.xml
       // view = inflater.inflate(R.layout.theme_dialog, container);

        // remove title (already defined in theme_dialog.xml)
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Declare buttons and onClick methods
        dialogButtons();

        return view;
    }

    private void dialogButtons() {
        cardView1 = (CardView) view.findViewById(R.id.card_view1);
        cardView2 = (CardView) view.findViewById(R.id.card_view2);
        cardView3 = (CardView) view.findViewById(R.id.card_view3);
        cardView4 = (CardView) view.findViewById(R.id.card_view4);
        cardView5 = (CardView) view.findViewById(R.id.card_view5);
        cardView6 = (CardView) view.findViewById(R.id.card_view6);
        cardView7 = (CardView) view.findViewById(R.id.card_view7);
        cardView8 = (CardView) view.findViewById(R.id.card_view8);
        cardView9 = (CardView) view.findViewById(R.id.card_view9);
        cardView10 = (CardView) view.findViewById(R.id.card_view10);
        cardViewOriginal = (CardView)view.findViewById(R.id.card_view_original);
      //  buttonDisagree = (Button) view.findViewById(R.id.buttonDisagree);
        buttonAgree = (Button) view.findViewById(R.id.buttonAgree);

        Context context= getActivity();
        mSmallBang = SmallBang.attach2Window(getActivity());
      //  tablelayout = (GridLayout) view.findViewById(R.id.gridLayout);




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
    }

    public void onClick(View v) {
        img = (ImageView) (view.findViewById(R.id.img1));
        img2=  (ImageView)( view.findViewById(R.id.img2));
        img3 = (ImageView) (view.findViewById(R.id.img3));
        img4=  (ImageView)( view.findViewById(R.id.img4));
        img5 = (ImageView) (view.findViewById(R.id.img5));
        img6=  (ImageView)( view.findViewById(R.id.img6));
        img7 = (ImageView) (view.findViewById(R.id.img7));
        img8=  (ImageView)( view.findViewById(R.id.img8));
        img9=  (ImageView)( view.findViewById(R.id.img9));
        img10 = (ImageView) (view.findViewById(R.id.img10));
        img11=  (ImageView)( view.findViewById(R.id.img11));

        switch (v.getId()) {
            case R.id.card_view1:

                    img2.setSelected(false);
                    img3.setSelected(false);
                    img4.setSelected(false);
                    img5.setSelected(false);
                    img6.setSelected(false);
                    img7.setSelected(false);
                    img8.setSelected(false);
                    img9.setSelected(false);
                    img10.setSelected(false);
                    img11.setSelected(false);

                    img.setSelected(!img.isSelected());
                    if (img.isSelected()) {
                        sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                        ((Settings) getActivity()).setThemeFragment(1);
                    } else {
                        img.setImageResource(R.drawable.circle_item);

                    }

                break;
            case R.id.card_view2:
                img.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img2 .setSelected(!img2.isSelected());
                if(img2.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(2);
                }else {
                    img2.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view3:
                img.setSelected(false);
                img2.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img3 .setSelected(!img3.isSelected());
                if(img3.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(3);
                }else {
                    img3.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view4:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img4 .setSelected(!img4.isSelected());
                if(img3.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(4);
                }else {
                    img4.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view5:
                img.setSelected(false);
                img2.setSelected(false);
                img4.setSelected(false);
                img3.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img5 .setSelected(!img5.isSelected());
                if(img5.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(5);
                }else {
                    img5.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view6:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img6 .setSelected(!img6.isSelected());
                if(img6.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(6);
                }else {
                    img6.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view7:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img7 .setSelected(!img7.isSelected());
                if(img7.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(7);
                }else {
                    img7.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view8:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);
                img11.setSelected(false);

                img8 .setSelected(!img8.isSelected());
                if(img8.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(8);
                }else {
                    img8.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view9:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img11.setSelected(false);

                img9 .setSelected(!img9.isSelected());
                if(img9.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(9);
                }else {
                    img9.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view10:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img11.setSelected(false);

                img10 .setSelected(!img10.isSelected());
                if(img10.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(10);
                }else {
                    img10.setImageResource(R.drawable.circle_item);

                }
                break;
            case R.id.card_view_original:
                img.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(false);
                img4.setSelected(false);
                img5.setSelected(false);
                img6.setSelected(false);
                img7.setSelected(false);
                img8.setSelected(false);
                img9.setSelected(false);
                img10.setSelected(false);

                img11 .setSelected(!img11.isSelected());
                if(img11.isSelected()){
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                ((Settings) getActivity()).setThemeFragment(11);
                break;
                }else {
                    img11.setImageResource(R.drawable.circle_item);

                }
            case R.id.buttonAgree:
                sharedPreferences.edit().putBoolean("THEMECHANGED", true).apply();
                intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);

                break;
            default:
                img.setSelected(false);
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
        img.setImageResource(R.drawable.ic_check_black_24dp);
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