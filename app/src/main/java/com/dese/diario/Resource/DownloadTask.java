package com.dese.diario.Resource;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.dese.diario.Objects.Urls;
import com.dese.diario.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SONU on 29/10/15.
 */
public class DownloadTask {

    private static final String TAG = "Download Task";
    private Context context;
    private Button buttonText;
    private String downloadUrl = "", downloadFileName = "";
    private ProgressDialog progress;

    public DownloadTask(Context context, ProgressDialog pDialog, String downloadUrl) {
        this.context = context;
        this.progress = pDialog;
        this.downloadUrl = downloadUrl;

        downloadFileName = downloadUrl.replace(Urls.download, "");//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(context, "Download Started", Toast.LENGTH_SHORT).show();
            alertDialogo("Download Started", true);
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {

                   // Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
                    alertDialogo("Download Complete", true);
                } else {
                    //Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
                    alertDialogo("Download Failed",true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            alertDialogo("Download Again", false);
                          //  Toast.makeText(context, "Download Again", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed + why +"+result.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs
                //buttonText.setText(R.string.downloadFailed);
                alertDialogo("Download Failed",true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // buttonText.setEnabled(true);
                       // buttonText.setText(R.string.downloadAgain);
                      //  Toast.makeText(context, "Download Again", Toast.LENGTH_SHORT).show();
                        alertDialogo("Download Again", false);
                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    + "Mi Diario");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }

    private void alertDialogo(String s, final Boolean status) {
        progress=new ProgressDialog(context);
        progress.setMessage(s);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {

                int jumpTime = 0;
                while(jumpTime < totalProgressTime) {
                    try {
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                        progress.setCancelable(status);
                        sleep(200);
                    }
                    catch (InterruptedException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }
}
