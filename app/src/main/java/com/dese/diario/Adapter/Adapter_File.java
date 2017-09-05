package com.dese.diario.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderItem;
import com.dese.diario.Utils.Urls;
import com.dese.diario.R;
import com.dese.diario.Utils.DownloadTask;
import com.mostafaaryan.transitionalimageview.model.TransitionalImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Eduardo on 10/08/2017.
 */

public class Adapter_File extends RecyclerView.Adapter<MyHolderItem> {
            private static String download = Urls.download;
            ArrayList<String> nombrefile;
            Context context;

          boolean isImageFitToScreen;

            private ProgressDialog pDialog;
            public static final int progress_bar_type = 0;

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
                String fname=nombrefile.get(position);
                String type = fname.substring(fname.lastIndexOf(".") + 1);
                holder.tvItem.setText(fname);
                getExtensionFile(type, holder, fname);



                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(final int pos) {
                        new MaterialDialog.Builder(context)
                                .title("Deseadescargar ")
                                .positiveText("Descargar")
                                .negativeText("cancelar")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        // TODO
                                        String urlDescarga= download+nombrefile.get(pos);
                                        new DownloadTask(context, null, urlDescarga);
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                     /*   String urlDescarga= download+nombrefile.get(pos);
                                        if(isImageFitToScreen) {
                                            isImageFitToScreen=false;
                                            holder.ivItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                                            holder.ivItem.setAdjustViewBounds(true);
                                        }else{
                                            isImageFitToScreen=true;
                                            holder.ivItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                                            holder.ivItem.setScaleType(ImageView.ScaleType.FIT_XY);
                                        }*/

                                    }
                                })
                                .show();


                    }
                });

            }


    @Override
    public int getItemCount() {

        return nombrefile.size();
    }



    private void getExtensionFile(String type, MyHolderItem holder, String name) {
        switch (type){
            case  "jpg":
                Picasso.with(context)
                        .load(download+name)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "png":
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
                        .into(holder.ivItem);
                break;
            case "pdf":
                Picasso.with(context)
                        .load(R.drawable.filepdf)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "xls":
                Picasso.with(context)
                        .load(R.drawable.filexls)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "ppt":
                Picasso.with(context)
                        .load(R.drawable.fileppt)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "mp3":
                Picasso.with(context)
                        .load(R.drawable.filemp3)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "avi":
                Picasso.with(context)
                        .load(R.drawable.fileavi)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "wav":
                Picasso.with(context)
                        .load(R.drawable.filewav)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;

        }

    }


}