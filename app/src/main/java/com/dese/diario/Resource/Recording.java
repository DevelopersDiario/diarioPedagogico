package com.dese.diario.Resource;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dese.diario.R;

import java.io.IOException;

/**
 * Created by Eduardo on 21/05/2017.
 */

public class Recording {
    private MediaRecorder recorder = null;
    String FILE_PATH_NAME = null;
    private MediaPlayer m = null;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 29;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 39;
    String   name = "/Audio"+String.valueOf(System.currentTimeMillis())+ ".3gp";


    public Recording() {

        FILE_PATH_NAME = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        FILE_PATH_NAME += name;



    }

   /* if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            Log.d("Home", "Already granted access");
            //initializeView(v);
        }
    }*/


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dialogGrabar(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_audio);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        ImageView exit = (ImageView) dialog.findViewById(R.id.btnExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button start = (Button) dialog.findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Puede grabar", Toast.LENGTH_SHORT).show();

                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(FILE_PATH_NAME);
                try {
                    recorder.prepare();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                recorder.start();

            }
        });
        Button stop = (Button) dialog.findViewById(R.id.btnStop);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Stop", Toast.LENGTH_SHORT).show();

                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;

            }
        });
        Button replay = (Button) dialog.findViewById(R.id.btnReplay);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Reproducir", Toast.LENGTH_SHORT).show();

                m = new MediaPlayer();
                try {
                    m.setDataSource(FILE_PATH_NAME);
                    m.prepare();

                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                m.start();

            }
        });


        dialog.show();

    }//fin alert dialogo


}
