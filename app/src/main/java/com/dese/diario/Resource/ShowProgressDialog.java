package com.dese.diario.Resource;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dese.diario.SelectAccount;

/**
 * Created by Eduardo on 19/05/2017.
 */

public class ShowProgressDialog {
    ProgressDialog progressDoalog;

    public  void progressDilog(Context context, final Boolean status) {
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Cargando...");
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


}
