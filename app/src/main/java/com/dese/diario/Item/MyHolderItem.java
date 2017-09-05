package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dese.diario.R;
import com.mostafaaryan.transitionalimageview.TransitionalImageView;

/**
 * Created by Eduardo on 14/07/2017.
 */

public class MyHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView ivItem;
    public TextView tvItem;
   public ImageView imageView;

    ItemClickListener itemClickListener;

    public MyHolderItem(View itemView) {
        super(itemView);

        ivItem  = (ImageView) itemView.findViewById(R.id.ivItem);
        tvItem  = (TextView) itemView.findViewById(R.id.tvItem);
        imageView = (ImageView) itemView.findViewById(R.id.imgShow);
          itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }
}