package com.dese.diario.Utils;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.dese.diario.Objects.Urls;
import com.dese.diario.POJOS.DatosUsr;
import com.dese.diario.POJOS.VariablesLogin;
import com.dese.diario.R;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Eduardo on 30/06/2017.
 */

public class Upload {

    final static String urlUpload       = Urls.publicararchivo;
    final static String uploadProfile   = Urls.upload;
    final static String upladHolder     = Urls.uploadholder;
    final VariablesLogin varlogin =new VariablesLogin();
    final DatosUsr dusr=new DatosUsr();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void recorrerListPaths(  ArrayList<String> paths, Context context, Intent data, String grupo) {
        File file;
        if(paths != null){
            for (int i = 0; i < paths.size(); i++) {


                Uri path = Uri.parse(paths.get(i)); //clipData.getItemAt(i).getUri();

                final String file_path = paths.get(i);
                file= new File(file_path);
                final String fname= file.getName();
                upload(context, file_path, grupo);
            }
        }else {
           file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
            final String file_path = file.getAbsolutePath();
            final String fname= file.getName();
            upload(context, file_path, grupo);

        }

    }

    public void upload(final Context context, final String path, final String gpo) {
        Thread tread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String id = varlogin.getIdusuario();
                try {
                    File f= new File(path);
                    String fname=f.getName().toString();
                    String type = "."+fname.substring(fname.lastIndexOf(".") + 1);
                    String filetype= getNameFile(type);
                    final String filename=fname;// = file.getName();
                    final String uploadId = UUID.randomUUID().toString();

                    new MultipartUploadRequest(context, uploadId, urlUpload)
                            .addFileToUpload(path, "archivo")
                            .addParameter("titulo", filename)
                            .addParameter("tipoarchivo", filetype)
                            .addParameter("idgrupo", gpo)
                            .addParameter("ruta", path)
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


            public void uploadPictureProfile(final Context context, final String path) {
                Thread tread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String id = varlogin.getIdusuario();
                        try {

                            final File file = new File(path);
                            final String file_path = file.getAbsolutePath();
                            final String filename = file.getName();
                            final String uploadId = UUID.randomUUID().toString();

                            dusr.setFportada(filename);
                            Toast.makeText(context, path, Toast.LENGTH_LONG).show();
                            /*new MultipartUploadRequest(context, uploadId, uploadProfile)
                                    .addFileToUpload(path, "image")
                                    .addParameter("idusuario", id)
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
                                        }

                                        @Override
                                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {


                                            messageAlert("Completado", path, context);
                                          }

                                        @Override
                                        public void onCancelled(UploadInfo uploadInfo) {
                                        }
                                    })
                                    .startUpload();*/

                        } catch (Exception exc) {
                            System.out.println(exc.getMessage() + " " + exc.getLocalizedMessage());
                        }

                    }

                });
                tread.start();
            }


             private String getNameFile(String type) {
                    switch (type){
                    case ".jpg":
                        return "Imagen";
                    case ".png":
                        return "Imagen";
                    case ".gif":
                        return "Imagen";
                    case ".jpeg":
                        return "Imagen";
                    case ".pdf":
                        return "Documento";
                    case ".docx":
                        return "Documento";
                    case ".xls":
                        return "Documento";
                    case ".ppt":
                        return "Documento";
                    case ".mov":
                        return "Audio";
                    case ".mp3":
                        return "Audio";
                    case ".ogg":
                        return "Audio";
                    case ".3gp":
                        return "Audio";
                }

                    return  type;

            }

   /* private  String getMimeType(String path){
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return  MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }*/

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
