package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dese.diario.R;

/**
 * Created by Eduardo on 14/07/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivItem;

    public MyViewHolder(View itemView) {
        super(itemView);

        ivItem = (ImageView) itemView.findViewById(R.id.ivItem);
    }
}