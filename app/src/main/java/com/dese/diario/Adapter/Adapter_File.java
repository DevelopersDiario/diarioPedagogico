package com.dese.diario.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dese.diario.Item.ItemClickListener;
import com.dese.diario.Item.MyHolderItem;
import com.dese.diario.Item.MyHolderView;
import com.dese.diario.Objects.Urls;
import com.dese.diario.R;
import com.dese.diario.Resource.DownloadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Eduardo on 10/08/2017.
 */

public class Adapter_File extends RecyclerView.Adapter<MyHolderItem> {
            private static String download = Urls.download;
            ArrayList<String> nombrefile;
            Context context;

            private ProgressDialog pDialog;
            public static final int progress_bar_type = 0;

            public Adapter_File(ArrayList<String> nombrefile, Context context) {


                this.nombrefile = nombrefile;
                this.context = context;

            }
            @Override
            public MyHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView =
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
                return new MyHolderItem(itemView);
            }

            @Override
            public void onBindViewHolder(MyHolderItem holder, final int position) {
               // File f= new File(paths.get(position));
                String fname=nombrefile.get(position);
                String type = fname.substring(fname.lastIndexOf(".") + 1);
                holder.tvItem.setText(fname);
                getExtensionFile(type, holder);



                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        String nombrearchivo= download+nombrefile.get(pos);
                        new DownloadTask(context, null, nombrearchivo);

                    }
                });

            }


    @Override
    public int getItemCount() {

        return nombrefile.size();
    }



    private void getExtensionFile(String type, MyHolderItem holder) {
        switch (type){
            case  "jpg":
                Picasso.with(context)
                        .load(R.drawable.filejpg)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "png":
                Picasso.with(context)
                        .load(R.drawable.filepng)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.ivItem);
                break;
            case "gif":
                Picasso.with(context)
                        .load(R.drawable.unknowfile)
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