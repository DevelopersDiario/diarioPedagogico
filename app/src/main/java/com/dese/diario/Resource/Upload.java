package com.dese.diario.Resource;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Publication;
import com.dese.diario.R;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by Eduardo on 30/06/2017.
 */

public class Upload {
    final String lineEnd = "\r\n";
    final String twoHyphens = "--";
    final String boundary="qwertyuiop";
    final static String urlUpload= Urls.publicararchivo;
    final VariablesLogin varlogin =new VariablesLogin();

    //ArrayList<String> paths = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void recorrerListPaths(  ArrayList<String> paths, Context context, Intent data, String grupo) {
//        ClipData clipData = data.getClipData();
        for (int i = 0; i < paths.size(); i++) {


            Uri path = Uri.parse(paths.get(i)); //clipData.getItemAt(i).getUri();
            final String file_path = paths.get(i);

               upload(context, file_path, grupo, "Archivo"+i);
        }
    }

    public void upload(final Context context, final String path, final String gpo, final String fname) {
        Thread tread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String id = varlogin.getIdusuario();
                try {
                  //  final File file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    //final String file_path = file.getAbsolutePath();
                    //OkHttpClient client = new OkHttpClient();

                    final String filename=fname;// = file.getName();
                    final String uploadId = UUID.randomUUID().toString();
                    new MultipartUploadRequest(context, uploadId, urlUpload)
                            .addFileToUpload(path, "archivo")
                            .addParameter("titulo", filename)
                            .addParameter("tipoarchivo", "documentos")
                            .addParameter("idgrupo", gpo)
                            .addParameter("idusuario", id)
                            .addParameter("Farchivo", ".pdf")
                            .setMaxRetries(2)
                            .setNotificationConfig(new UploadNotificationConfig()
                                    .setInProgressMessage("Subiendo Archivo")
                                    .setCompletedMessage("Completado correctamente")
                                    .setTitle("Subiendo ")
                            )
                            .setDelegate(new UploadStatusDelegate() {
                                @Override
                                public void onProgress(UploadInfo uploadInfo) {


                                }

                                @Override
                                public void onError(UploadInfo uploadInfo, Exception e) {
                                }

                                @Override
                                public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {


                                   // String dat = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                                    messageAlert("Complet", filename, context);

                                    //Toast.makeText(MainActivity.this.getApplicationContext(),"Imagen subida exitosamente.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(UploadInfo uploadInfo) {
                                }
                            })
                            .startUpload();

                } catch (Exception exc) {
                    System.out.println(exc.getMessage() + " " + exc.getLocalizedMessage());
                }

                    } });
        tread.start();
             }
            public void uploadMultipart(final Context context, final Intent data, final String gpo) {
                Thread tread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String id = varlogin.getIdusuario();
                        try {
                            final File file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                            final String file_path = file.getAbsolutePath();
                            OkHttpClient client = new OkHttpClient();

                            final String filename = file.getName();
                            final String uploadId = UUID.randomUUID().toString();
                            new MultipartUploadRequest(context, uploadId, urlUpload)
                                    .addFileToUpload(file_path, "archivo")
                                    .addParameter("titulo", filename)
                                    .addParameter("tipoarchivo", "documentos")
                                    .addParameter("idgrupo", gpo)
                                    .addParameter("idusuario", id)
                                    .addParameter("Farchivo", ".pdf")
                                    .setMaxRetries(2)
                                    .setNotificationConfig(new UploadNotificationConfig()
                                            .setInProgressMessage("Subiendo Archivo")
                                            .setCompletedMessage("Completado correctamente")
                                            .setTitle("Subiendo ")
                                    )
                                    .setDelegate(new UploadStatusDelegate() {
                                        @Override
                                        public void onProgress(UploadInfo uploadInfo) {


                                        }

                                        @Override
                                        public void onError(UploadInfo uploadInfo, Exception e) {
                                        }

                                        @Override
                                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {


                                            String dat = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                                            messageAlert("Complet", dat, context);

                                            //Toast.makeText(MainActivity.this.getApplicationContext(),"Imagen subida exitosamente.", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(UploadInfo uploadInfo) {
                                        }
                                    })
                                    .startUpload();

                        } catch (Exception exc) {
                            System.out.println(exc.getMessage() + " " + exc.getLocalizedMessage());
                        }

                    }
               /* public void run() {
                    File file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    final String boundary="qwertyuiop";
                    MediaType mediaType = MediaType.parse("multipart/form-data;"+boundary);
                    String content_type =getMimeType(file.getPath());
                    String filename="\\" +file.getName();

                    String file_path = file.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();

                    RequestBody file_body =RequestBody.create(MediaType.parse("multipart/form-data;"),boundary);

                    RequestBody body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("archivo", content_type)
                            //.addFormDataPart("archivo",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                            .addFormDataPart("archivo",filename,RequestBody.create(MediaType.parse(content_type),file))
                            .build();

                    Request request= new Request.Builder()
                            .url("http://192.168.20.25:8084/diariopws/api/1.0/publicacion/publicarArchivo")
                            .post(body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful()){
                            throw new IOException("No se pudo guardar el archivo"+response);
                        }
                        progress.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }*/
                });
                tread.start();
            }

    private  String getMimeType(String path){
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public void messageAlert(String body, String msg, final Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        alertDialog.setTitle("Subir Archivo");

        // Setting Dialog Message
        alertDialog.setMessage( body + "<"+ msg+">");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_apk_box);

        // Showing Alert Message
        alertDialog.show();

    }

    }