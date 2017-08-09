package com.dese.diario.Resource;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.Publication;
import com.dese.diario.R;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.picasso.Picasso;
import com.veer.multiselect.Util.LoadBitmap;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        File file;
        if(paths != null){
            for (int i = 0; i < paths.size(); i++) {


                Uri path = Uri.parse(paths.get(i)); //clipData.getItemAt(i).getUri();

                final String file_path = paths.get(i);
                file= new File(file_path);
                final String fname= file.getName();
                upload(context, file_path, grupo, fname);
            }
        }else {
           file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            final String file_path = file.getAbsolutePath();
            final String fname= file.getName();
            upload(context, file_path, grupo,fname);

        }

    }

    public void upload(final Context context, final String path, final String gpo, final String fname) {
        Thread tread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String id = varlogin.getIdusuario();
                try {
                    File f= new File(path);
                    String fname=f.getName().toString();
                    String type = "."+fname.substring(fname.lastIndexOf(".") + 1);
                    final String filename=fname;// = file.getName();
                    final String uploadId = UUID.randomUUID().toString();
                    new MultipartUploadRequest(context, uploadId, urlUpload)
                            .addFileToUpload(path, "archivo")
                            .addParameter("titulo", filename)
                            .addParameter("tipoarchivo", "documentos")
                            .addParameter("idgrupo", gpo)
                            .addParameter("idusuario", id)
                            .addParameter("Farchivo", type)
                            .setMaxRetries(2)
                            .setNotificationConfig(new UploadNotificationConfig()
                                    .setInProgressMessage(filename)
                                    .setCompletedMessage("Completado!")
                                    .setTitle("Gestor de Archivos ")
                            )
                            .setDelegate(new UploadStatusDelegate() {
                                @Override
                                public void onProgress(UploadInfo uploadInfo) {


                                }

                                @Override
                                public void onError(UploadInfo uploadInfo, Exception e) {
                                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {

                                    //ELiminar imagen
                                    File eliminar = new File(path);
                                    if (eliminar.exists()) {
                                        if (eliminar.delete()) {
                                            System.out.println("Archivo eliminado:" + eliminar.getPath());
                                        } else {
                                            System.out.println("Archivo no eliminado" + eliminar.getPath());
                                        }
                                    }
                                    Toast.makeText(context.getApplicationContext(),"Imagen subida exitosamente.", Toast.LENGTH_SHORT).show();
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
//
    private String getFileType(String extension){
        String value =  "";
        switch (extension){
            case  ".jpg":
                value="Imagen";
               return value;
            case ".png":
                return "Imagen";
            case ".gif":
                return "Imagen";
            case ".jpeg":
                return "Imagen";
            case ".doc":
                return "Documento";
            case "pdf":
                return "Documento";
            case "xls":
                return "Documento";
            case "ppt":
                return "Documento";
            case "mp3":
                return "Audio";
            case "avi":
                return "Audio";
            case "wav":
                return "Audio";

        }
        return value;

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
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE;
        Log.i("URI",uri+"");
        String result = uri+"";
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length-1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    //Get Real path to Uri in All version android.
    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    }//end
