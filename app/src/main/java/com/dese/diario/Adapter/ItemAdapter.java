package com.dese.diario.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dese.diario.Item.MyViewHolder;
import com.dese.diario.R;
import com.veer.multiselect.Util.LoadBitmap;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Eduardo on 14/07/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<MyViewHolder> {

    ArrayList<String> paths;
    Context context;


    public ItemAdapter(ArrayList<String> paths, Context context) {


        this.paths = paths;
        this.context = context;

    }

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        File f= new File(paths.get(position));
        String fname=f.getName().toString();
        String type = fname.substring(fname.lastIndexOf(".") + 1);


        //String[] separated = fname.split(".");
        //String tmp = separated[separated.length-1];


        holder.tvItem.setText(fname);

        switch (type){
            case  "jpg":
                LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);
                break;
            case "png":
                LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);

                break;
            case "gif":
                LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);

                break;
            case "doc":
                LoadBitmap.loadBitmap(String.valueOf(R.drawable.filedoc ), holder.ivItem);
                break;
            case "pdf":
                holder.ivItem.setImageResource(R.drawable.filepdf);
            //    LoadBitmap.loadBitmap(String.valueOf(R.drawable.filepdf ), holder.ivItem);
                Toast.makeText(context, type, Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override public int getItemCount() {
        return paths.size();
    }
}
