package com.dese.diario.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by Eduardo on 19/05/2017.
 */

public class ShowProgressDialog {
    ProgressDialog progressDoalog;
    int progress = 0;

    public  void progressDilog(Context context, final Boolean status) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Cargando");
        //progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setIndeterminate(true);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(status==true){


                    while (progressDoalog.getProgress() <= progressDoalog
                            .getMax()) {
                        Thread.sleep(100);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDoalog.getProgress() == progressDoalog
                                .getMax()) {
                            progressDoalog.dismiss();
                        }
                    }
                    }
                    else {
                        progressDoalog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDoalog.incrementProgressBy(1);
        }
    };

    public  void dismiss(){
        progressDoalog.dismiss();
    }

    public void MaterialDialog(Context context, Boolean status){

        if(status==true){
             new MaterialDialog.Builder(context)
                    .content("Cargando..")
                    .progress(true, 0)
                    .show();
       }
            new MaterialDialog.Builder(context)
                    .content("Cargando..")
                    .progress(true, 0)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {

                        }
                    });

    }

    public void MaterialDialogMsj(Context context, Boolean status, String msj){
        if(status==true){
            new MaterialDialog.Builder(context)
                    .content(msj)
                    .canceledOnTouchOutside(false)
                    .theme(Theme.DARK)
                    .progress(true, 0)
                    .show();
        }
        new MaterialDialog.Builder(context)
                .content(msj)
                .theme(Theme.DARK)
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
    }


    public void DialogProgress(Context c, Boolean status){
        final MaterialDialog.Builder dialogBuilder;


        dialogBuilder = new MaterialDialog.Builder(c);


        if(status){
            dialogBuilder.content("Cargando..")
                    //.cancelable(false)
                    //.autoDismiss(true)
                    .canceledOnTouchOutside(false)
                    .progress(true, 0);
            dialogBuilder.show();
          //  Toast.makeText(c, "tRUE", Toast.LENGTH_LONG).show();
        }else if (!status){
           // Toast.makeText(c, "False", Toast.LENGTH_LONG).show();
            dialogBuilder.build().dismiss();
            dialogBuilder.build().cancel();
        }

    }

    public void MaterialDialogSeek(Context context, Boolean status){

        MaterialDialog.Builder dialogBuilder;


        dialogBuilder = new MaterialDialog.Builder(context)
                .title("Super Dialog")
                .content("blananacldcla")
                .progress(false, 10)
                .negativeText("NON")
                .positiveText("OK")
                .neutralText("RESET")
                .contentGravity(GravityEnum.CENTER)
                .cancelable(false)
                .autoDismiss(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        if (progress < 10) {
                            progress++;
                        }
                        update(materialDialog);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        progress = 0;
                        update(materialDialog);
                    }
                });

        dialogBuilder.show();

    }

    private void update(MaterialDialog dialog){
        ((MaterialDialog) dialog).setProgress(progress);
        if (progress == 10) {
            ((MaterialDialog) dialog).getContentView().setVisibility(View.VISIBLE);
            ((MaterialDialog) dialog).getProgressBar().setVisibility(View.GONE);
        } else {
            ((MaterialDialog) dialog).getContentView().setVisibility(View.GONE);
            ((MaterialDialog) dialog).getProgressBar().setVisibility(View.VISIBLE);
        }
    }
}
