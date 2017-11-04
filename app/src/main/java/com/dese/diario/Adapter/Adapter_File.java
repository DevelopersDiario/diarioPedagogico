package com.dese.diario.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderItem;
import com.dese.diario.R;
import com.dese.diario.Utils.DownloadTask;
import com.dese.diario.Utils.Urls;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Eduardo on 10/08/2017.
 */

public class Adapter_File extends RecyclerView.Adapter<MyHolderItem> {
            private static String download = Urls.download;
            ArrayList<String> nombrefile;
            Context context;


            public Adapter_File(ArrayList<String> nombrefile, Context context) {


                this.nombrefile = nombrefile;
                this.context = context;

            }
            @Override
            public MyHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView =
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
                return new MyHolderItem(itemView);
            }

            @Override
            public void onBindViewHolder(final MyHolderItem holder, final int position) {
               // File f= new File(paths.get(position));
                final String fname=nombrefile.get(position);
                final String type = fname.substring(fname.lastIndexOf(".") + 1);
                holder.tvItem.setText(fname);
                getExtensionFile(type, holder, fname);



                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {
                        switch (type){
                            case "jpg":

                               selectFile(type, holder, fname, pos);

                                break;
                            case "png":

                                selectFile(type, holder, fname, pos);
                                break;
                            case "gif":

                                selectFile(type, holder, fname, pos);
                                break;
                            case "mp3":
                                selectFile(type, holder, fname, pos);
                                break;
                            case "avi":
                                selectFile(type, holder, fname, pos);
                                break;
                            case "wav":
                                selectFile(type, holder, fname, pos);
                                break;
                            case "doc":
                                dowloandFile(type, holder, fname, pos);
                                break;
                            case "pdf":

                                dowloandFile(type, holder, fname, pos);

                                break;
                            case "xls":
                                dowloandFile(type, holder, fname, pos);
                                break;
                            case "ppt":
                                dowloandFile(type, holder, fname, pos);
                                break;
                            case "mov":
                                dowloandFile(type, holder, fname, pos);
                                break;
                            case "mp4":
                                dowloandFile(type, holder, fname, pos);
                                break;

                        }




                    }
                });

            }
    private void selectFile(final String type, final MyHolderItem holder, final String fname, final int pos){
        final CharSequence[] optione = { "Preview", "Descargar"};
                        new MaterialDialog.Builder(context)
                                .items(optione)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                        switch (which){
                                            case 0:
                                                previewFile(type, holder, fname);
                                                break;

                                            case 1:
                                                String urlDescarga= download+nombrefile.get(pos);
                                                new DownloadTask(context, null, urlDescarga);

                                                break;
                                            default:
                                                dialog.dismiss();
                                                break;
                                        }
                                    }
                                })
                                .show();
    } private void dowloandFile(final String type, final MyHolderItem holder, final String fname, final int pos){
        final CharSequence[] optione = {"Descargar"};
                        new MaterialDialog.Builder(context)
                                .items(optione)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                        switch (which){

                                            case 0:
                                                String urlDescarga= download+nombrefile.get(pos);
                                                new DownloadTask(context, null, urlDescarga);

                                                break;
                                            default:
                                                dialog.dismiss();
                                                break;
                                        }
                                    }
                                })
                                .show();
    }
    private void previewFile(String type, MyHolderItem holder, final String fname) {

        final ImagePopup imagePopup = new ImagePopup(context);
        final MediaPlayer  mp;

        switch (type){
            case "jpg":

                imagePopup.setWindowHeight(1000); // Optional
                imagePopup.setWindowWidth(1000); // Optional
                imagePopup.isImageOnClickClose();
                imagePopup.initiatePopup(holder.ivItem.getDrawable());
                imagePopup.viewPopup();

                break;
            case "png":

                imagePopup.setWindowHeight(1000); // Optional
                imagePopup.setWindowWidth(1000); // Optional
                imagePopup.initiatePopup(holder.ivItem.getDrawable());
                imagePopup.viewPopup();
                //  Toast.makeText(context, "Audio PNG", Toast.LENGTH_SHORT).show();
                break;
            case "gif":

                imagePopup.setWindowHeight(1000); // Optional
                imagePopup.setWindowWidth(1000); // Optional
                imagePopup.initiatePopup(holder.ivItem.getDrawable());
                imagePopup.viewPopup();
                break;
            case "mp3":
                 mp = MediaPlayer.create(context, Uri.parse(download+fname));
                dialogPlayer(mp, fname);

                break;
            case "avi":
                mp = MediaPlayer.create(context, Uri.parse(download+fname));
                dialogPlayer(mp, fname);
                break;
            case "wav":
                mp = MediaPlayer.create(context, Uri.parse(download+fname));
                dialogPlayer(mp, fname);
                break;
            case "doc":
               // dialogDocument(fname);
                break;
            case "pdf":
              //  dialogDocument(fname);

                break;
            case "xls":

                break;
            case "ppt":

                break;

        }
    }

    private void IntenDoc(String fname){
        File temp_file=new File(download+fname);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(temp_file),getMimeType(temp_file.getAbsolutePath()));
        context.startActivity(intent);

    }
    private String getMimeType(String url)
    {
        String parts[]=url.split("\\.");
        String extension=parts[parts.length-1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }
    private void dialogDocument(String fname) {
        boolean wrapInScrollView = true;

        File file = new File(download+fname);
        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_read_document, wrapInScrollView)
                .show();
        View view = dialog.getCustomView();
        WebView webview = (WebView) view.findViewById(R.id.fileWebView);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = download+fname;
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);


    }
    private void dialogPlayer(final MediaPlayer mp, final String fname) {
        boolean wrapInScrollView = true;
        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_audio, wrapInScrollView)
                .show();
        View view = dialog.getCustomView();
        final Button start= (Button) view.findViewById(R.id.btnStart);
        Button stop = (Button) view.findViewById(R.id.btnStop);
        Button pause= (Button)view.findViewById(R.id.btnReplay);
        ImageView exit = (ImageView) view.findViewById(R.id.btnExit);

        final TextView state= (TextView)view.findViewById(R.id.text_dialog);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isClickable())
                mp.start();
                state.setText(fname+ "....");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                state.setText("Stop");
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                state.setText("Pausa");
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }




    private void getExtensionFile(String type, MyHolderItem holder, String name) {
        switch (type){
            case  "jpg":
                //LoadBitmap.loadBitmap(download+name, holder.ivItem);
               Picasso.with(context)
                        .load(download+name)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
                case  "jpeg":
                //LoadBitmap.loadBitmap(download+name, holder.ivItem);
               Picasso.with(context)
                        .load(download+name)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "png":
                //LoadBitmap.loadBitmap(download+name, holder.ivItem);
                Picasso.with(context)
                        .load(download+name)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "gif":
                Picasso.with(context)
                        .load(download+name)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "doc":
                //LoadBitmap.loadBitmap(String.valueOf(R.drawable.filedoc ), holder.ivItem);
                Picasso.with(context)
                        .load(R.drawable.filedoc)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "docx":
                //LoadBitmap.loadBitmap(String.valueOf(R.drawable.filedoc ), holder.ivItem);
                Picasso.with(context)
                        .load(R.drawable.filedoc)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "pdf":
                Picasso.with(context)
                        .load(R.drawable.filepdf)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "xls":
                Picasso.with(context)
                        .load(R.drawable.filexls)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "xlsx":
                Picasso.with(context)
                        .load(R.drawable.filexls)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "pptx":
                Picasso.with(context)
                        .load(R.drawable.fileppt)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "ppt":
                Picasso.with(context)
                        .load(R.drawable.fileppt)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "mp3":
                Picasso.with(context)
                        .load(R.drawable.filemp3)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "avi":
                Picasso.with(context)
                        .load(R.drawable.fileavi)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "wav":
                Picasso.with(context)
                        .load(R.drawable.filewav)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivSound);
                break;
            case "mp4":
                Picasso.with(context)
                        .load(R.drawable.filemov)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "3gp":
                Picasso.with(context)
                        .load(R.drawable.filemov)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "mov":
                Picasso.with(context)
                        .load(R.drawable.filemov)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;

        }

    }
    @Override
    public int getItemCount() {

        return nombrefile.size();
    }

}