package com.dese.diario.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dese.diario.Item.Item;
import com.dese.diario.Item.MyHolderView;
import com.dese.diario.R;
import com.squareup.picasso.Picasso;
import com.veer.multiselect.Util.LoadBitmap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Eduardo on 14/07/2017.
 */

public class Adapter_Item extends RecyclerView.Adapter<MyHolderView> {

        ArrayList<String> paths;
        Context context;


    public Adapter_Item(ArrayList<String> paths, Context context) {


        this.paths = paths;
        this.context = context;

    }

    @Override public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        return new MyHolderView(itemView);
    }


    @Override public void onBindViewHolder(MyHolderView holder, int position) {

        File f= new File(paths.get(position));
        String fname=f.getName().toString();
        String type = fname.substring(fname.lastIndexOf(".") + 1);



        holder.tvItem.setText(fname);
        // Span the item if active



        switch (type){
            case  "jpg":
                Picasso.with(context)
                        .load(R.drawable.filejpg)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.imType);/*
                Picasso.with(context)
                        .load(paths.get(position))
                        .resize(200, 200)
                        .centerCrop()
                        .into(holder.ivItem);*/
               LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);
                break;
            case "png":
                Picasso.with(context)
                        .load(R.drawable.filepng)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.imType);
                LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);

                break;
            case "gif":
                Picasso.with(context)
                        .load(R.drawable.unknowfile)
                        .resize(1200, 1200)
                        .centerCrop()
                        .into(holder.imType);
                LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);
                break;
            case "doc":
                //LoadBitmap.loadBitmap(String.valueOf(R.drawable.filedoc ), holder.ivItem);
                Picasso.with(context)
                        .load(R.drawable.filedoc)
                        .resize(200, 200)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "pdf":
                Picasso.with(context)
                        .load(R.drawable.filepdf)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "xls":
                Picasso.with(context)
                        .load(R.drawable.filexls)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "ppt":
                Picasso.with(context)
                        .load(R.drawable.fileppt)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "mp3":
                Picasso.with(context)
                        .load(R.drawable.filemp3)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "avi":
                Picasso.with(context)
                        .load(R.drawable.fileavi)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;
            case "wav":
                Picasso.with(context)
                        .load(R.drawable.filewav)
                        .resize(1120, 1120)
                        .centerCrop()
                        .into(holder.imType);
                break;

        }




    }


    @Override public int getItemCount() {
        return paths.size();
    }
}
