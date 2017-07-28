package com.dese.diario.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dese.diario.Item.MyViewHolder;
import com.dese.diario.R;
import com.veer.multiselect.Util.LoadBitmap;

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
        LoadBitmap.loadBitmap(paths.get(position), holder.ivItem);
    }

    @Override public int getItemCount() {
        return paths.size();
    }
}
